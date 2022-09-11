package com.genesis.genesisapi.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genesis.genesisapi.model.BillByInvoice;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ClientDetails;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.DeliveryReport;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OutgoingReport;
import com.genesis.genesisapi.model.OutstandingBillReport;
import com.genesis.genesisapi.model.Payment;
import com.genesis.genesisapi.model.SoaReport;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.soaCurrent;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Controller
@RequestMapping("/download")
public class ReportsPdfController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ServletContext context;

	@Autowired
	private BillingRepo billingRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private ClientInfoRepo clientRepo;
	
	@Autowired
    private WSOInfoRepo wsoInfoRepo;
	
	@Autowired
    private LotInfoRepo lotInfoRepo;
	
	@Autowired
    private DeliveryListRepo deliveryListRepo;
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/soa/{fileName:.+}/{fileType}/{fromClientInfo}/{toClientInfo}")
	 public void soaReportdownloader(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable("fileName") String fileName, @PathVariable("fileType") String fileType,
			 @PathVariable("fromClientInfo") Long fromClientInfo,
			 @PathVariable("toClientInfo") Long toClientInfo) throws JRException, IOException, NoSuchFieldException, SecurityException {
		String reportName = null;
		long daysOverDue = 0L;
		Boolean check = false;
		DecimalFormat df = new DecimalFormat("###.##");
		JasperPrint p1 = new JasperPrint();
		JRPdfExporter exp = new JRPdfExporter();
		List<List<SoaReport>> list1 = new ArrayList<List<SoaReport>>();
		List<SoaReport> listout = new ArrayList<SoaReport>();
		List<List<soaCurrent>> list2 = new ArrayList<List<soaCurrent>>();
		List<soaCurrent> listCurrent = new ArrayList<soaCurrent>();
		List<ClientDetails> listClient = new ArrayList<ClientDetails>();
		JRXlsExporter xlsExporter = new JRXlsExporter();
		List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
		//List<Object> listJasper = new ArrayList<Object>();
		List<ClientInfo> clientInfo = clientRepo.getClientBetweenClient(fromClientInfo, toClientInfo);
		logger.info("Records successfully retrieved from Client Info table based on from Client Id to Client Id");
        int client=0;
        for(ClientInfo model1 : clientInfo) {
        	client++;
        	ClientDetails objClient = new ClientDetails();
        	objClient.setClientTitle(model1.getClientTitle());
        	objClient.setClientMobileNo(model1.getContactPersonMobileNumbere());
        	listClient.add(objClient);
        	//List<Billing> billingdata = billingRepo.getBillingInfoByCLientId(model1.getClientInfoId());
        	
        	List<Object> billingdata = billingRepo.getBillingInfoByCLientIdGroup(model1.getClientInfoId());
        	if(billingdata.size()>0){
        		check=true;
        	}
        	else{
        		check=false;
        	}
        	List<BillByInvoice> billInvoice= new ArrayList<BillByInvoice>();
        	
        	for (int i = 0; i < billingdata.size(); i++) {
        		Object[] object = (Object[]) billingdata.get(i);
        		System.out.println("Value of Total Amount:"+(Double) object[0]);
        		System.out.println("Value of Total Amount:"+(String) object[1]);
        		BillByInvoice b = new BillByInvoice();
        		b.setTotal_amount((Double) object[0]);
        		b.setInvoice_no((String) object[1]);
        		billInvoice.add(b);
        	}
        	
        	Double current=0.0;
        	Double overdue30=0.0;
        	Double overdue60=0.0;
        	Double overdue90=0.0;
        	Double overdue120=0.0;
        	Double overdueMore120=0.0;
        	Double total=0.0;
        	Double creditBalance=0.0;
        	
        	soaCurrent  objCurrent  = new soaCurrent();
        	
            for (BillByInvoice model : billInvoice){
            	
            	//List<Payment> paymentdata = paymentRepo.findPaymentByInvoiceNo(model.getInvoice_no());
            	List<Payment> paymentdata = paymentRepo.findPaymentByInvoiceNoDistinct(model.getInvoice_no());
            	logger.info("Records successfully retrieved from Payment table based on Disting Invoice No.");
            	List<Billing> billingInfo = billingRepo.getBillingInfoByInvoiceNoGroup(model.getInvoice_no());
            	logger.info("Records successfully retrieved from Billing table based on Invoice No Group");
            	
            	
            	System.out.println("List<Payment> length: "+paymentdata.size());
            	if(paymentdata.size()==0){
            		try{
            			
            			System.out.println("instantPayDt:"+new Date());
            			System.out.println("To date value:"+billingInfo.get(0).getInvoiceDate());
            			Date date2 = new Date();
            			Date date1 = addDays(billingInfo.get(0).getInvoiceDate(),30);
            			long timeDiff = (date2.getTime() - date1.getTime());
            			long diff = TimeUnit.MILLISECONDS.toDays(timeDiff);
            			if(diff<0)
            				daysOverDue=0;
            			else
            				daysOverDue = diff;
                		System.out.println("Difference in days:"+daysOverDue);
            		}catch(Exception e){
            			System.out.println(e.getMessage());
            		}
            		
            		if(daysOverDue==0){
            			current += model.getTotal_amount();
            		}else if(daysOverDue>0 && daysOverDue<31){
            			overdue30 += model.getTotal_amount();
            		}else if(daysOverDue>30 && daysOverDue<61){
            			overdue60 += model.getTotal_amount();
            		}else if(daysOverDue>60 && daysOverDue<91){
            			overdue90 += model.getTotal_amount();
            		}else if(daysOverDue>90 && daysOverDue<121){
            			overdue120 += model.getTotal_amount();
            		}else if(daysOverDue>120){
            			overdueMore120 += model.getTotal_amount();
            		}
            		total = overdue30 + overdue60 + overdue90 + overdue120 + overdueMore120;
            		creditBalance = total + current;
            		SoaReport  obj  = new SoaReport();
            		
            		obj.setInvoiceDate(new SimpleDateFormat("dd-MM-yyyy").format(billingInfo.get(0).getInvoiceDate()));
            		obj.setInvoiceNo(billingInfo.get(0).getInvoiceNo());
        	        obj.setCreditTerms("30");
        	        obj.setDocumentDate("");
        	        obj.setPaymentCreditNote("");
        	        obj.setDaysOverDue(String.valueOf(daysOverDue));
        	        obj.setDebit("0");
        	        obj.setCredit(String.format( "%.2f", model.getTotal_amount()));
        	        obj.setBalanceAmount(String.format( "%.2f", model.getTotal_amount()));
        	        
        	        listout.add(obj);
        	        
            	}
            	else{
            		for(Payment modeldata : paymentdata) {
                		try{
                			
                			System.out.println("instantPayDt:"+modeldata.getPaymentDate());
                			System.out.println("To date value:"+billingInfo.get(0).getInvoiceDate());
                			Date date2 = modeldata.getPaymentDate();
                			Date date1 = addDays(billingInfo.get(0).getInvoiceDate(),30);
                			long timeDiff = (date2.getTime() - date1.getTime());
                			long diff = TimeUnit.MILLISECONDS.toDays(timeDiff);
                			if(diff<0)
                				daysOverDue=0;
                			else
                				daysOverDue = diff;
                    		System.out.println("Difference in days:"+daysOverDue);
                		}catch(Exception e){
                			System.out.println(e.getMessage());
                		}
                		
                		if(daysOverDue==0){
                			current += modeldata.getBalance();
                		}else if(daysOverDue>0 && daysOverDue<31){
                			overdue30 += modeldata.getBalance();
                		}else if(daysOverDue>30 && daysOverDue<61){
                			overdue60 += modeldata.getBalance();
                		}else if(daysOverDue>60 && daysOverDue<91){
                			overdue90 += modeldata.getBalance();
                		}else if(daysOverDue>90 && daysOverDue<121){
                			overdue120 += modeldata.getBalance();
                		}else if(daysOverDue>120){
                			overdueMore120 += modeldata.getBalance();
                		}
                		total = overdue30 + overdue60 + overdue90 + overdue120 + overdueMore120;
                		creditBalance = total + current;
                		
                		SoaReport  obj  = new SoaReport();
                		obj.setInvoiceDate(new SimpleDateFormat("dd-MM-yyyy").format(billingInfo.get(0).getInvoiceDate()));
                		obj.setInvoiceNo(billingInfo.get(0).getInvoiceNo());
            	        obj.setCreditTerms("30");
            	        obj.setDocumentDate(new SimpleDateFormat("dd-MM-yyyy").format(modeldata.getPaymentDate()));
            	        obj.setPaymentCreditNote(modeldata.getCnPnId());
            	        obj.setDaysOverDue(String.valueOf(daysOverDue));
            	        obj.setDebit(String.format( "%.2f", model.getTotal_amount()-modeldata.getBalance()));
            	        obj.setCredit(String.format( "%.2f", model.getTotal_amount()));
            	        obj.setBalanceAmount(String.format( "%.2f", modeldata.getBalance()));
            	        
            	        listout.add(obj);
            	        
                	}
            	}
            	
            }
            objCurrent.setCurrent(String.format( "%.2f", current));
            objCurrent.setOverdue30(String.format( "%.2f", overdue30));
            objCurrent.setOverdue60(String.format( "%.2f", overdue60));
            objCurrent.setOverdue90(String.format( "%.2f", overdue90));
            objCurrent.setOverdue120(String.format( "%.2f", overdue120));
            objCurrent.setOverdueMore120(String.format( "%.2f", overdueMore120));
            objCurrent.setTotal(String.format( "%.2f", total));
            objCurrent.setCreditBalance(String.format( "%.2f", creditBalance));
            
            listCurrent.add(objCurrent);
            list1.add(listout);
            list2.add(listCurrent);
            current=0.0;
        	overdue30=0.0;
        	overdue60=0.0;
        	overdue90=0.0;
        	overdue120=0.0;
        	overdueMore120=0.0;
        	
        		//Jasper Report Work
            	if(check==true){
	            	JRBeanCollectionDataSource SOAJRBean = new JRBeanCollectionDataSource(listout);
	           		JRBeanCollectionDataSource SOAJRBean1 = new JRBeanCollectionDataSource(listCurrent);
	           	    Map<String, Object> parameters = new HashMap<String, Object>();
	           	     
			   	     parameters.put("SOADataSource", SOAJRBean);
			   	     parameters.put("SOADataSource1", SOAJRBean1);
			   	     parameters.put("clientTitle", listClient.get(client-1).getClientTitle());
			   	     parameters.put("clientMobileNo", listClient.get(client-1).getClientMobileNo());
			   	     //parameters.put("listCurrent", listCurrent);
	           	           
	       			reportName = context.getRealPath("/WEB-INF/soaReport");
	       			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
	       			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
	       			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
	       			listJasper.add(print);	       			
            	}
                 
    		check=false;	  
   	        listCurrent.clear();
   	        listout.clear();
   	        
   	        
        }
        //Merging Reports into One Report
  	    List<JRPrintPage> listPg = new ArrayList<JRPrintPage>();
        for(int a=0;a<listJasper.size();a++){
        	
        	for(int b=0,c=0;b<listJasper.get(a).getPages().size();b++){
        		listPg.add(listJasper.get(a).getPages().get(c));
        		c++;
        	}
        }
        for(int d=0;d<listPg.size();d++){
        	p1.addPage(listPg.get(d));
        }
        
        if(fileType.equals("pdf")){
        	exp.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, listJasper);
            exp.setParameter(JRPdfExporterParameter.OUTPUT_FILE, new File(reportName+".pdf"));
             
            exp.exportReport();
            
            String downloadFolder = context.getRealPath("/WEB-INF/");
            Path file = Paths.get(downloadFolder, fileName);
            if (Files.exists(file)) {
            	response.setContentType("application/pdf");
            	response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            	try {
            			Files.copy(file, response.getOutputStream());
            			response.getOutputStream().flush();
            	} catch (IOException e) {
            	}
            } else {
            	System.out.println("Sorry File not found!!!!");
            }
        }
        String[] sheetName = {"Page 1","Page 1"};
        if(fileType.equals("xls")){
        	//xlsExporter.setExporterInput(SimpleExporterInput.getInstance(listJasper));
        	xlsExporter.setExporterInput(new SimpleExporterInput(p1));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
            //xlsExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, "Page 1");
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setDetectCellType(true);
            //configuration.setSheetNames(sheetName);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setCollapseRowSpan(false);
            xlsExporter.setConfiguration(configuration);
            xlsExporter.exportReport();
            
            String downloadFolder = context.getRealPath("/WEB-INF/");
            Path file = Paths.get(downloadFolder, fileName);
            if (Files.exists(file)) {
            	response.setContentType("application/xls");
            	response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            	try {
            			Files.copy(file, response.getOutputStream());
            			response.getOutputStream().flush();
            	} catch (IOException e) {
            	}
            } else {
            	System.out.println("Sorry File not found!!!!");
            }
        }
           
	 }
	// WSO Enquiry Report
	@SuppressWarnings("deprecation")
	@RequestMapping("/wsoenq/{fileName:.+}/{fileType}/{fromWsoNo}/{toWsoNo}")
	 public void wsoEnquiryReportdownloader(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType,
			 @PathVariable("fromWsoNo") Long fromWsoNo,@PathVariable("toWsoNo") Long toWsoNo) throws JRException, IOException, NoSuchFieldException, SecurityException {
		String reportName = null;
		String clientName = null;
		String wsoNo = null;
		String storageDate = null;
		String totalWsoWeight = null;
		int total=0;
		Boolean check = true;
		JasperPrint p1 = new JasperPrint();
		JRPdfExporter exp = new JRPdfExporter();
		JRXlsExporter xlsExporter = new JRXlsExporter();
		List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
		
		List<List<DeliveryReport>> list = new ArrayList<List<DeliveryReport>>();
		List<WSOInfo> wsodata = wsoInfoRepo.findWsoByWsoId(fromWsoNo, toWsoNo);
		List<DeliveryReport> list1 = new ArrayList<DeliveryReport>();
		
		int ttlWsoQty = 0;
        int currentWsoQty = 0;
        int currentWsoWt = 0;
        
        for (WSOInfo model : wsodata)
        { 	
        	if(model.getWsoNo()!=null)
        	{
        		List<DeliveryList> deliverydata = wsoInfoRepo.findDeliveryListByWsoId(model.getWsoId());
        		logger.info("Records successfully retrieved from Delivery List table based on WSO Id");
            	List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(model.getWsoId());
            	logger.info("Records successfully retrieved from Lot Info table based on Wso Id");
            	for(LotInfo lotModel : lotData) {
            		currentWsoQty += lotModel.getCurrentQuantity();
            		currentWsoWt += lotModel.getCurrentQuantity() * lotModel.getUnitNetWeightInKg();
            		ttlWsoQty += lotModel.getLotQuantity();		
            	}
            	
            	if(deliverydata.size()>0){
            		for(DeliveryList deliModel : deliverydata) {
                		DeliveryReport  obj  = new DeliveryReport();
                		obj.setDlNo(deliModel.getDlNo());
                		obj.setDateOfDelivery(new SimpleDateFormat("dd/MM/yyyy").format(deliModel.getDateOfDelivery()));
                		obj.setLotNo(deliModel.getLotInfo().getLotNo());
                		obj.setPackagesOut(deliModel.getTotalQty().toString());
                		obj.setWsoNo(deliModel.getWsoInfo().getWsoNo().toString());
                		obj.setClientName(deliModel.getClientInfo().getClientTitle());
                		int totalWt = (int) deliModel.getWsoInfo().getTotalWsoWeight();
                		obj.setTotalWsoWeight(String.valueOf(totalWt));
                		obj.setStorageDate(new SimpleDateFormat("dd/MM/yyyy").format(deliModel.getWsoInfo().getTallysheet().getStorageDate()));
                		  
            	        list1.add(obj);
            	        total += (int)deliModel.getTotalQty();
            	        clientName=deliModel.getClientInfo().getClientTitle();
            	        wsoNo=deliModel.getWsoInfo().getWsoNo().toString();
            	        storageDate=new SimpleDateFormat("dd/MM/yyyy").format(deliModel.getWsoInfo().getTallysheet().getStorageDate());
            	        totalWt = (int) deliModel.getWsoInfo().getTotalWsoWeight();
            	        totalWsoWeight=String.valueOf(totalWt);
            	        
                	}
            	}else{
            		check=false;
            	}
            	
    	        //list.add(list1);
    	        
    	        //Jasper Report Work
            	if(check == true){
            		JRBeanCollectionDataSource WSOJRBean = new JRBeanCollectionDataSource(list1);
       			 
       		     Map<String, Object> parameters = new HashMap<String, Object>();
       		     
       		     parameters.put("WsoEnquiryDataSource", WSOJRBean);
       		     
       		     parameters.put("clientTitle", clientName);
       		     parameters.put("wsoNo", wsoNo);
       		     parameters.put("currentWsoWt", String.valueOf(currentWsoWt));
       		     parameters.put("storageDate", storageDate);
       		     parameters.put("currentWsoQty", String.valueOf(ttlWsoQty - total));
       		     parameters.put("totalWsoWeight", totalWsoWeight);
       		     parameters.put("ttlWsoQty", String.valueOf(ttlWsoQty));
       		     parameters.put("total", String.valueOf(total));
       			reportName = context.getRealPath("/WEB-INF/wsoEnquiryReport");
       			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
       			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
       			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
       			listJasper.add(print);
       		
            	}
    	         	
    			ttlWsoQty = 0;
    	        currentWsoQty = 0;
    	        currentWsoWt = 0;
    	        total=0;
    	        list1.clear();
    	        check=true;
        	}
        		
        	    
        }
        logger.info("Records successfully retreived from wsoInfo table.");
       //Merging Reports into One Report
  	    List<JRPrintPage> listPg = new ArrayList<JRPrintPage>();
        for(int a=0;a<listJasper.size();a++){
        	
        	for(int b=0,c=0;b<listJasper.get(a).getPages().size();b++){
        		listPg.add(listJasper.get(a).getPages().get(c));
        		c++;
        	}
        }
        for(int d=0;d<listPg.size();d++){
        	p1.addPage(listPg.get(d));
        }
        
        if(fileType.equals("pdf")){
        	exp.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, listJasper);
            
            exp.setParameter(JRPdfExporterParameter.OUTPUT_FILE, new File(reportName+".pdf"));
             
            exp.exportReport();
            
            String downloadFolder = context.getRealPath("/WEB-INF/");
            Path file = Paths.get(downloadFolder, fileName);
            if (Files.exists(file)) {
            	response.setContentType("application/pdf");
            	response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            	try {
            			Files.copy(file, response.getOutputStream());
            			response.getOutputStream().flush();
            	} catch (IOException e) {
            	}
            } else {
            	System.out.println("Sorry File not found!!!!");
            }
        }
        
        if(fileType.equals("xls")){
        	//xlsExporter.setExporterInput(SimpleExporterInput.getInstance(listJasper));
        	xlsExporter.setExporterInput(new SimpleExporterInput(p1));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            xlsExporter.setConfiguration(configuration);
            xlsExporter.exportReport();
            
            String downloadFolder = context.getRealPath("/WEB-INF/");
            Path file = Paths.get(downloadFolder, fileName);
            if (Files.exists(file)) {
            	response.setContentType("application/xls");
            	response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            	try {
            			Files.copy(file, response.getOutputStream());
            			response.getOutputStream().flush();
            	} catch (IOException e) {
            	}
            } else {
            	System.out.println("Sorry File not found!!!!");
            }
        }
           
     }
	//Outgoing Records
	
		@SuppressWarnings("deprecation")
		@RequestMapping("/outgoing/{fileName:.+}/{fileType}/{fromClient}/{toClient}/{fromDate}/{toDate}")
		 public void outgoingReportdownloader(HttpServletRequest request, HttpServletResponse response,
				 @PathVariable("fileName") String fileName, 
				 @PathVariable("fileType") String fileType, 
				 @PathVariable("fromClient") Long fromClient,
				 @PathVariable("toClient") Long toClient,
				 @PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate) throws JRException, IOException, NoSuchFieldException, SecurityException {
			String reportName = null;
			String clientName = null;
			Boolean check = true;
			Boolean printReport = false;
			JasperPrint p1 = new JasperPrint();
			JRPdfExporter exp = new JRPdfExporter();
			JRXlsExporter xlsExporter = new JRXlsExporter();
			
			List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
			//List<Object> listJasper = new ArrayList<Object>();
			JasperPrint listJasper1 = new JasperPrint();
			List<OutgoingReport> listOutgoing = new ArrayList<OutgoingReport>();
			Date fromDate1 = null;
			Date toDate1 = null;
			Long fromClient1 = null;
			Long toClient1 = null;
			int grandQty = 0;
			int grandWt = 0;
			int TtlQty = 0;
			int TtlWt = 0;
			int SubTotalQty = 0;
			int SubTotalWt = 0;
			int counter = 1;
			int counterPrint = 0;
			String dlNo = "";
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
				fromClient1 = Long.valueOf(fromClient);
				toClient1 = Long.valueOf(toClient);
			} catch (ParseException e) {
				e.printStackTrace();
			}

	        List<ClientInfo>  dataList = deliveryListRepo.getClientBetweenClient(fromClient1, toClient1);
	        logger.info("Records successfully retrieved from DeliveryList table based on from and to Client Id");
	        for(ClientInfo model : dataList) 
	        {   
	        	clientName = model.getClientTitle();
	        	grandQty=0;
	        	grandWt=0;
	        	TtlQty=0;
	        	TtlWt=0;
	        	SubTotalQty=0;
	        	SubTotalWt=0;
	        	counter=1;
	        	counterPrint = 0;
	        	List<DeliveryList>  dataList1 = deliveryListRepo.getDeliveryBetweenDate(model.getClientInfoId(), fromDate1, toDate1);
	        	logger.info("Records successfully retrieved from Delivery List table based on Client Id and between Dates");
	        	if(dataList1.size()>0){
	        		for(DeliveryList model1 : dataList1) 
	        		{
	        			if(!dlNo.equals(model1.getDlNo())){
		        			dlNo = model1.getDlNo();
		        			if(counter == 1){
		        				printReport = false;
		        			}
		        			else{
		        				printReport = true;
		        			}		        			
		        		}else
		        		{
		        			printReport = false;
		        		}
	        			
	        			//Jasper Report Work
			        	if(check == true && printReport==true){
			        		grandQty += SubTotalQty;
			        		grandWt += SubTotalWt;
			        		JRBeanCollectionDataSource OutgoingJRBean = new JRBeanCollectionDataSource(listOutgoing);
			      			 
			       		    Map<String, Object> parameters = new HashMap<String, Object>();
			       		     
			       		    parameters.put("OutgoingDataSource", OutgoingJRBean);
			       		    parameters.put("clientTitle", clientName);
			       		    parameters.put("subTotalQty", String.valueOf(SubTotalQty));
			       		    parameters.put("subTotalWt", String.valueOf(SubTotalWt));
			       		    parameters.put("grandQty", String.valueOf(grandQty));
			       		    parameters.put("grandWt", String.valueOf(grandWt));
			       		    parameters.put("end", 0);
			       		    if(counterPrint == 0){
			       		    	parameters.put("begin", 1);
			       		    	System.out.println("Print Counter value:"+counterPrint+"Parameter begin passed with value 1");
			       		    }else
			       		    {
			       		    	parameters.put("begin", 0);
			       		    	System.out.println("Counter value:"+counterPrint+" Parameter begin passed with value 0");
			       		    }
			       			reportName = context.getRealPath("/WEB-INF/outgoingReport");
			       			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
			       			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
			       			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
			       			listJasper.add(print);
			       			listOutgoing.clear();
			       			
			       			SubTotalQty=0;
			       			SubTotalWt=0;
			       			counterPrint++;
			        	}	
			        	//Storing the value from list
		        		OutgoingReport obj = new OutgoingReport();
		        		obj.setDlNo(model1.getDlNo());
		        		obj.setDateOfDelivery(model1.getDateOfDelivery().toString());
		        		obj.setWsoNo(model1.getWsoInfo().getWsoNo());
		        		obj.setLotNo(model1.getLotInfo().getLotNo());
		        		TtlQty = Math.round(model1.getTotalQty());		        		
		        		TtlWt = Math.round(model1.getTotalQty()*model1.getLotInfo().getUnitWeightInKg());
		        		obj.setTtlQty(String.valueOf(TtlQty));
		        		obj.setTtlWt(String.valueOf(TtlWt));
		        		SubTotalQty += TtlQty;
		        		SubTotalWt += TtlWt;
		        		listOutgoing.add(obj);
		        		
		        		counter++;        				        			        			
		        	}
		        	logger.info("Records successfully retreived from deliveryList for Outgoing records using From date to date");
		        	System.out.println("Total Qty:"+TtlQty+" Total Weight: "+TtlWt);
		        	if(check == true){
		        		grandQty += SubTotalQty;
		        		grandWt += SubTotalWt;
		        		JRBeanCollectionDataSource OutgoingJRBean = new JRBeanCollectionDataSource(listOutgoing);
		      			 
		       		    Map<String, Object> parameters = new HashMap<String, Object>();
		       		     
		       		    parameters.put("OutgoingDataSource", OutgoingJRBean);
		       		    parameters.put("clientTitle", clientName);
		       		    parameters.put("subTotalQty", String.valueOf(SubTotalQty));
		       		    parameters.put("subTotalWt", String.valueOf(SubTotalWt));
		       		    parameters.put("grandQty", String.valueOf(grandQty));
		       		    parameters.put("grandWt", String.valueOf(grandWt));
		       		    parameters.put("end", 1);
		       		    if(counterPrint == 0){
		       		    	parameters.put("begin", 1);
		       		    	System.out.println("Print Counter value:"+counterPrint+"Parameter begin passed with value 1");
		       		    }else
		       		    {
		       		    	parameters.put("begin", 0);
		       		    	System.out.println("Print Counter value:"+counterPrint+"Parameter begin passed with value 0");
		       		    }
		       			reportName = context.getRealPath("/WEB-INF/outgoingReport");
		       			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
		       			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
		       			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
		       			listJasper.add(print);
		       			listJasper1=print;
		       			listOutgoing.clear();
		       			SubTotalQty=0;
		       			SubTotalWt=0;
		       			counterPrint++;
		        	}	
	        	}
	        	else{
	        		check = false;
	        	}
				
	        	check = true;
	        }
	      //Merging Reports into One Report
	  	    List<JRPrintPage> listPg = new ArrayList<JRPrintPage>();
	        for(int a=0;a<listJasper.size();a++){
	        	
	        	for(int b=0,c=0;b<listJasper.get(a).getPages().size();b++){
	        		listPg.add(listJasper.get(a).getPages().get(c));
	        		c++;
	        	}
	        }
	        for(int d=0;d<listPg.size();d++){
	        	p1.addPage(listPg.get(d));
	        }
	        
	        if(fileType.equals("pdf")){
	        	exp.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, listJasper);
	            
	            exp.setParameter(JRPdfExporterParameter.OUTPUT_FILE, new File(reportName+".pdf"));
	             
	            exp.exportReport();
	            
	            String downloadFolder = context.getRealPath("/WEB-INF/");
	            Path file = Paths.get(downloadFolder, fileName);
	            if (Files.exists(file)) {
	            	response.setContentType("application/pdf");
	            	response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
	            	try {
	            			Files.copy(file, response.getOutputStream());
	            			response.getOutputStream().flush();
	            	} catch (IOException e) {
	            	}
	            } else {
	            	System.out.println("Sorry File not found!!!!");
	            }
	        }
	        
	        if(fileType.equals("xls")){
	        	//xlsExporter.setExporterInput(SimpleExporterInput.getInstance(listJasper));
	        	xlsExporter.setExporterInput(new SimpleExporterInput(p1));
	            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
	            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	            configuration.setOnePagePerSheet(false);
	            configuration.setDetectCellType(true);
	            configuration.setCollapseRowSpan(false);
	            xlsExporter.setConfiguration(configuration);
	            xlsExporter.exportReport();
	            
	            String downloadFolder = context.getRealPath("/WEB-INF/");
	            Path file = Paths.get(downloadFolder, fileName);
	            if (Files.exists(file)) {
	            	response.setContentType("application/xls");
	            	response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
	            	try {
	            			Files.copy(file, response.getOutputStream());
	            			response.getOutputStream().flush();
	            	} catch (IOException e) {
	            	}
	            } else {
	            	System.out.println("Sorry File not found!!!!");
	            }
	        }
	     }

	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
				
		return cal.getTime();
	}
	

}

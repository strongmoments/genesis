package com.genesis.genesisapi.controller;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.genesis.genesisapi.model.BillByClientAndDateReport;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.BillingTest;
import com.genesis.genesisapi.model.CommonBilling;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.DeliverylistReport;
import com.genesis.genesisapi.model.Item;
import com.genesis.genesisapi.model.LotByClientReport;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OtherChargesBilling;
import com.genesis.genesisapi.model.OutgoingReport;
import com.genesis.genesisapi.model.PymtBillingReport;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.TallysheetReport;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.WsolotReport;
import com.genesis.genesisapi.model.leaseBilling;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.service.WSOService;

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
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download")
public class ReportPdf2Controller {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ServletContext context;
	
	@Autowired
	private BillingRepo billingRepo;
	
	@Autowired
    private PaymentRepo paymentRepo;
	
	@Autowired
    private ClientInfoRepo clientInfoRepo;
	
	@Autowired
    private LotInfoRepo lotInfoRepo;

    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    
    @Autowired
    private DeliveryListRepo deliveryListRepo;
    
	@GetMapping("/paymentBetweenDates/{fileName:.+}/{fileType}/{fromdate}/{todate}")
    public void paymentBetweenDates(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable(value = "fromdate") String fromdate,
    		@PathVariable(value = "todate") String todate,
			@PathVariable("fileName") String fileName,
			@PathVariable("fileType") String fileType)throws JRException, IOException {
		Date fromDate = null;
		Date toDate = null;
		
		try {
			fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			toDate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String reportName = null;
		Boolean check = true;
		Boolean printReport = false;
		JasperPrint p1 = new JasperPrint();
		JRPdfExporter exp = new JRPdfExporter();
		JRXlsExporter xlsExporter = new JRXlsExporter();
		
		List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
		JasperPrint listJasper1 = new JasperPrint();

    	List<Object> data =  paymentRepo.findPaymentBetweenDates(fromDate, toDate);
    	
    	List<PymtBillingReport> pymtBillList = new ArrayList<PymtBillingReport>();
    	
    	String invNo = null;
    	Double total = 0.0;
    	Date prevDate = new Date();
    	Date recivedDate = new Date();
    	Double totRecdAmt = 0.0;
    	int counter = 1;
		int counterPrint = 0;
		
    	for (int i = 0; i < data.size(); i++) {
    		counter=1;
        	counterPrint = 0;
        	
    		PymtBillingReport pymtBillingReport = new PymtBillingReport();
			Object[] object = (Object[]) data.get(i);
			invNo = (String) object[2];
			pymtBillingReport.setClientTitle(billingRepo.getBillDetailByInvoiceNum(invNo).get(0).getClientInfo().getClientTitle());
			
			recivedDate = (Date) object[0];
			String invoiceNo = (String) object[2];
			List<Billing> bilingList = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
			Boolean billPaid = true;
			for (Billing billing : bilingList) {
			    Boolean check1 = billing.isBillPaid();
			    if (check1 == false) {
			    	billPaid = false;
			    	break;
			    }
			}
			/*boolean billPaid = (boolean) object[3];*/
			String status = "";
			if (billPaid == true)
				status = "OK";
			else
				status = "Pending";
			
			Double recdAmout = (Double) object[1];
			
			if (i == 0) {
				totRecdAmt = totRecdAmt + recdAmout;
				prevDate = (Date) object[0];
				pymtBillingReport.setReceivedDate(String.valueOf(object[0]));
				pymtBillingReport.setReceivedAmt(String.format("%.2f", object[1]));
				pymtBillingReport.setInvoiceNo(String.valueOf(invoiceNo));
				pymtBillingReport.setStatus(status);
				pymtBillingReport.setSubTotal(String.format("%.2f", totRecdAmt));
				pymtBillingReport.setVerify("Y");
				total += (Double) object[1];
				pymtBillingReport.setTtlAmt(String.format("%.2f", total));
				pymtBillList.add(pymtBillingReport);
				
				printReport = false;
				/*obj1.put("receivedDate",(Date) object[0]);
				obj1.put("receivedAmt",(Double) object[1]);
				obj1.put("invoiceNo",invoiceNo);
				obj1.put("status",status);
				obj1.put("subTotal",totRecdAmt);
				obj1.put("prntSubTot","false");
				list.add(obj1);*/
			}
			else {
				if (prevDate.compareTo(recivedDate) == 0) {
					totRecdAmt = totRecdAmt + recdAmout;
					
					pymtBillingReport.setReceivedDate(String.valueOf(object[0]));
					pymtBillingReport.setReceivedAmt(String.format("%.2f", object[1]));
					pymtBillingReport.setInvoiceNo(String.valueOf(invoiceNo));
					pymtBillingReport.setStatus(status);
					pymtBillingReport.setSubTotal(String.format("%.2f", totRecdAmt));
					pymtBillingReport.setVerify("Y");
					total += (Double) object[1];
					pymtBillingReport.setTtlAmt(String.format("%.2f", total));
					pymtBillList.add(pymtBillingReport);
					
					if ((i+1) == data.size())
						printReport = true;
					else
					{
						Object[] object1 = (Object[]) data.get(i+1);
						Date nextDate = (Date) object1[0];
						if (recivedDate.compareTo(nextDate) != 0)
							printReport = true;
						else
							printReport = false;
					}
					
					/*obj1.put("receivedDate",(Date) object[0]);
					obj1.put("receivedAmt",(Double) object[1]);
					obj1.put("invoiceNo",invoiceNo);
					obj1.put("status",status);
					obj1.put("subTotal",totRecdAmt);
					
					if ((i+1) == data.size())
						obj1.put("prntSubTot","true");
					else
					{
						Object[] object1 = (Object[]) data.get(i+1);
						Date nextDate = (Date) object1[0];
						if (recivedDate.compareTo(nextDate) != 0)
							obj1.put("prntSubTot","true");
						else
							obj1.put("prntSubTot","false");
					}
					list.add(obj1);*/
				}
				else {
					
					totRecdAmt = 0.0;
					//Normal Working
					totRecdAmt = totRecdAmt + recdAmout;
					
					pymtBillingReport.setReceivedDate(String.valueOf(object[0]));
					pymtBillingReport.setReceivedAmt(String.format("%.2f", object[1]));
					pymtBillingReport.setInvoiceNo(String.valueOf(invoiceNo));
					pymtBillingReport.setStatus(status);
					pymtBillingReport.setSubTotal(String.format("%.2f", totRecdAmt));
					pymtBillingReport.setVerify("Y");
					total += (Double) object[1];
					pymtBillingReport.setTtlAmt(String.format("%.2f", total));
					pymtBillList.add(pymtBillingReport);
					
					if ((i+1) == data.size())
						printReport = true;
					else
					{
						Object[] object1 = (Object[]) data.get(i+1);
						Date nextDate = (Date) object1[0];
						if (recivedDate.compareTo(nextDate) != 0)
							printReport = true;
						else
							printReport = false;
					}
					
					/*obj1.put("receivedDate",(Date) object[0]);
					obj1.put("receivedAmt",(Double) object[1]);
					obj1.put("invoiceNo",invoiceNo);
					obj1.put("status",status);
					obj1.put("subTotal",totRecdAmt);
					
					if ((i+1) == data.size())
						obj1.put("prntSubTot","true");
					else
					{
						Object[] object1 = (Object[]) data.get(i+1);
						Date nextDate = (Date) object1[0];
						if (recivedDate.compareTo(nextDate) != 0)
							obj1.put("prntSubTot","true");
						else
							obj1.put("prntSubTot","false");
					}
					//obj1.put("prntSubTot","false");
					list.add(obj1);*/
					//prevDate = new Date();
					
				}
			}
			
			//Upto here I have put the data in list and also checked the condition that printReport is true or false
			// Now I have to write the code if printReport = true then add into JasperPrint list. See ReoprtPdfController.java
			prevDate = (Date) object[0];
			if(printReport==true){
        		JRBeanCollectionDataSource pymtBillinglistJRBean = new JRBeanCollectionDataSource(pymtBillList);
      			 
       		    Map<String, Object> parameters = new HashMap<String, Object>();
       		    
       		    parameters.put("PymtBillingDataSource", pymtBillinglistJRBean);
       		    parameters.put("total", String.format("%.2f", total));
       		    int endVal = 0;
       		    if (i == (data.size() -1))
       		    	endVal = 1;
    		    else
    		    	endVal = 0;
       		    parameters.put("end", endVal);
       		    
       		    if(counterPrint == 0){
       		    	parameters.put("begin", 1);
       		    	System.out.println("Print Counter value:"+counterPrint+"Parameter begin passed with value 1");
       		    }else
       		    {
       		    	parameters.put("begin", 0);
       		    	System.out.println("Counter value:"+counterPrint+" Parameter begin passed with value 0");
       		    }
       			reportName = context.getRealPath("/WEB-INF/monthpaymentReport");
       			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
       			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
       			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
       			listJasper.add(print);
       			pymtBillList.clear();
       			
       			counterPrint++;
        	}	
    		counter++;        				        			        			
			//Original is downwards
			/*pymtBillingReport.setReceivedDate(String.valueOf(object[0]));
			pymtBillingReport.setReceivedAmt(String.format("%.2f", object[1]));
			total += (Double) object[1];
			pymtBillingReport.setTtlAmt(String.format("%.2f", total));
			pymtBillList.add(pymtBillingReport);*/
		}
    	total = (double) Math.round(total);
    	System.out.println("Total:"+total);
    	
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
        
    	//Downwards is original
     /*HashMap[] row = new HashMap[1];
   	 HashMap obj  = new HashMap();;
   	 obj.put("invoiceNo","ikash");
   	 row[0]= obj;
   	 
   	 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
   	 
   	 JRBeanCollectionDataSource pymtBillinglistJRBean = new JRBeanCollectionDataSource(pymtBillList);
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("PymtBillingDataSource", pymtBillinglistJRBean);
        parameters.put("total", total.toString());
         
   		String reportName1 = context.getRealPath("/WEB-INF/monthpaymentReport");
   		JasperCompileManager.compileReportToFile(reportName1 + ".jrxml");
   		JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName1 + ".jrxml");
   		JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
   		if(fileType.equals("pdf")){
   			byte[] pdf = JasperExportManager.exportReportToPdf(print);
   	        FileOutputStream fos = new FileOutputStream(reportName1+".pdf");
   	        fos.write(pdf);
   	        fos.close();
   	        String downloadFolder = context.getRealPath("/WEB-INF/");
   	        Path file = Paths.get(downloadFolder, fileName);
   	        if (Files.exists(file)) 
   	        {
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
   			xlsExporter.setExporterInput(new SimpleExporterInput(print));
               xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName1+".xls")));
               SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
               configuration.setOnePagePerSheet(false);
               configuration.setDetectCellType(true);
               configuration.setCollapseRowSpan(false);
               xlsExporter.setConfiguration(configuration);
               xlsExporter.exportReport();
               
               String downloadFolder = context.getRealPath("/WEB-INF/");
               Path file = Paths.get(downloadFolder, fileName);
               if (Files.exists(file)) 
               {
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
   		}*/
	}
	@GetMapping("/getBillingByClientIdAndDates/{fileName:.+}/{fileType}/{clientId}/{fromdate}/{todate}")
    public void getBillingByClientIdAndDates(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("fileName") String fileName,
    		@PathVariable("fileType") String fileType,
    		@PathVariable("clientId") Long clientId,
    		@PathVariable("fromdate") String fromdate,
			@PathVariable("todate") String todate)throws JRException, IOException, ParseException {
		Date fromDate = null;
		Date toDate = null;
		
		try {
			fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			toDate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JasperPrint p1 = new JasperPrint();
		JRXlsExporter xlsExporter = new JRXlsExporter();
    	System.out.println("clientId"+clientId+" fromDate"+fromDate+" toDate"+toDate);

    	List<Object> data = billingRepo.getBillingInfoByCLientIdAndBetweenDatesForInvList(clientId, fromDate, toDate);
    	logger.info("Records successfully retrieved from Billing table based on Client Id and Between Dates.");
    	List<BillByClientAndDateReport> billByClientList = new ArrayList<BillByClientAndDateReport>();
    	
    	Double totalsubTotal = 0.0;
    	Double totalGst = 0.0;
    	Double totalTotal = 0.0;
    	for (int i = 0; i < data.size(); i++) {
    		BillByClientAndDateReport billByClientAndDateReport = new BillByClientAndDateReport();
			Object[] object = (Object[]) data.get(i);
			
			String invNo = (String) object[0];
			Date invDate = (Date) object[1];
			Double subTotal = (Double) object[2];
			Double gst = (Double) object[3];
			Double total = (Double) object[4];
			totalsubTotal += subTotal;
			totalGst += gst;
			totalTotal += total;
			
			billByClientAndDateReport.setInvNo(String.valueOf(invNo));
			billByClientAndDateReport.setInvDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(invDate)));
			billByClientAndDateReport.setClient(clientInfoRepo.findByCientId(clientId).getClientTitle());
			billByClientAndDateReport.setSubTotal(String.format("%.2f", subTotal));
			billByClientAndDateReport.setGst(String.format("%.2f", gst));
			billByClientAndDateReport.setTotal(String.format("%.2f", total));
			
			billByClientList.add(billByClientAndDateReport);
		}
     HashMap[] row = new HashMap[1];
   	 HashMap obj  = new HashMap();;
   	 obj.put("invoiceNo","ikash");
   	 row[0]= obj;
   	 
   	 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
   	 
   	 JRBeanCollectionDataSource billingByClientlistJRBean = new JRBeanCollectionDataSource(billByClientList);
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("BillByClientDataSource", billingByClientlistJRBean);
        parameters.put("fromDate", new SimpleDateFormat("dd-MM-yyyy").format(fromDate));
        parameters.put("toDate", new SimpleDateFormat("dd-MM-yyyy").format(toDate));
        parameters.put("clientTitle", clientInfoRepo.findByCientId(clientId).getClientTitle());
        parameters.put("totalsubTotal", String.format("%.2f", totalsubTotal));
        parameters.put("totalGst", String.format("%.2f", totalGst));
        parameters.put("totalTotal", String.format("%.2f", totalTotal));
   		String reportName = context.getRealPath("/WEB-INF/invoicelistReport");
   		JasperCompileManager.compileReportToFile(reportName + ".jrxml");
   		JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
   		JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
   		if(fileType.equals("pdf")){
   			byte[] pdf = JasperExportManager.exportReportToPdf(print);
   	        FileOutputStream fos = new FileOutputStream(reportName+".pdf");
   	        fos.write(pdf);
   	        fos.close();
   	        String downloadFolder = context.getRealPath("/WEB-INF/");
   	        Path file = Paths.get(downloadFolder, fileName);
   	        if (Files.exists(file)) 
   	        {
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
   			xlsExporter.setExporterInput(new SimpleExporterInput(print));
               xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
               SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
               configuration.setOnePagePerSheet(false);
               configuration.setDetectCellType(true);
               configuration.setCollapseRowSpan(false);
               xlsExporter.setConfiguration(configuration);
               xlsExporter.exportReport();
               
               String downloadFolder = context.getRealPath("/WEB-INF/");
               Path file = Paths.get(downloadFolder, fileName);
               if (Files.exists(file)) 
               {
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
	
	@GetMapping("/getLotDetailByClientId/{fileName:.+}/{fileType}/{clientId}/{date1}")
    public void getLotDetailByClientId(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("fileName") String fileName,
    		@PathVariable("fileType") String fileType,
    		@PathVariable("clientId") Long clientId,
    		@PathVariable("date1") String date1)throws JRException, IOException, ParseException {
		Boolean printCheck = true;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String reportName = null;
		Boolean check = true;
		Boolean printReport = false;
		JasperPrint p1 = new JasperPrint();
		JRPdfExporter exp = new JRPdfExporter();
		JRXlsExporter xlsExporter = new JRXlsExporter();
		
		List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
		JasperPrint listJasper1 = new JasperPrint();
		
    	System.out.println("clientId"+clientId);
    	
    	LotInfo model = new LotInfo();
    	List<WSOInfo> wsoInfoList = new ArrayList<WSOInfo>();
    	wsoInfoList = wsoInfoRepo.loadWsoByClientId(clientId,date);
    	logger.info("WSOInfo List Id:"+wsoInfoList);
    	List<Long> wsoIdList = new ArrayList<Long>();
    	for(WSOInfo w : wsoInfoList) {
    		wsoIdList.add(w.getWsoId());	
    	}
    	logger.info("WsoId List:"+wsoIdList);
    	List<String> lotDescList = lotInfoRepo.findDistinctLotByWsoIdList(wsoIdList);
    	logger.info("Records successfully retrieved from Lot Info table based on WSO Id list");
    	int GrandtotalinitialQty = 0;
    	int GrandtotalcurrentQty = 0;
    	int totalinitialQty = 0;
    	int totalcurrentQty = 0;
    	
    	for(String description: lotDescList) {
    		List<LotByClientReport> lotByClientList = new ArrayList<LotByClientReport>();
    		
    		List<LotInfo> lotInfoList = new ArrayList<LotInfo>();
    		lotInfoList = lotInfoRepo.findLotDetailByWsoIdList(wsoIdList, description);
    		System.out.println("Lot List:"+lotInfoList.toString());
    		int initialQty = 0;
			int currentQty = 0;
    		for(LotInfo l: lotInfoList) {
    			List<DeliveryList> delList = deliveryListRepo.getDeliveryByLotIdANDDate(l.getLotId(),date);
    			logger.info("Records successfully retrieved from Delivery List based on Lot Id and Date");
    			if(delList.isEmpty()) {
    				LotByClientReport lotByClientReport = new LotByClientReport();
    				
    				lotByClientReport.setLotDesc(l.getDescription());
            		lotByClientReport.setLotNo(l.getLotNo());
            		lotByClientReport.setWsoNo(l.getWsoInfo().getWsoNo());
            		lotByClientReport.setDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
            		lotByClientReport.setInitialQty(String.valueOf(l.getInitialQuantity()));
            		lotByClientReport.setCurrentQty(String.valueOf(l.getCurrentQuantity()));
            		lotByClientReport.setProdDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
            		lotByClientReport.setExpDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
            		lotByClientReport.setUnitKg(String.format("%.2f", l.getUnitWeightInKg()));
            		lotByClientReport.setUnitQtyLot(String.format("%.2f", l.getUnitGrossWeightInKg()));
            		if(l.getCurrentQuantity()!=0) {
            			lotByClientList.add(lotByClientReport);
            		}
    			}
    			System.out.println("delList: "+delList.toString());
    			int initQty = l.getInitialQuantity();
    			int qtyDelivered = 0;
    			
    			if(!delList.isEmpty()) {
    				for(int i = 0; i<delList.size();i++) {
        				LotByClientReport lotByClientReport = new LotByClientReport();
            			lotByClientReport.setLotDesc(l.getDescription());
                		lotByClientReport.setLotNo(l.getLotNo());
                		lotByClientReport.setWsoNo(l.getWsoInfo().getWsoNo());
                		lotByClientReport.setDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
                		initQty = initQty - qtyDelivered;
                		lotByClientReport.setInitialQty(String.valueOf(l.getInitialQuantity()));
                		lotByClientReport.setCurrentQty(String.valueOf(initQty - delList.get(i).getTotalQty()));
                		lotByClientReport.setProdDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
                		lotByClientReport.setExpDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
                		lotByClientReport.setUnitKg(String.format("%.2f", l.getUnitWeightInKg()));
                		lotByClientReport.setUnitQtyLot(String.format("%.2f", l.getUnitGrossWeightInKg()));
                		if(i == delList.size()-1 && (initQty - delList.get(i).getTotalQty())!=0) {
                			lotByClientList.add(lotByClientReport);
                		}
                		qtyDelivered = delList.get(i).getTotalQty();
        			}
    				
    			}
    		}
    		for(LotByClientReport d : lotByClientList) {
    			totalinitialQty += Integer.parseInt(d.getInitialQty());
    			totalcurrentQty += Integer.parseInt(d.getCurrentQty());
    		}
    		
			System.out.println("totalinitialQty: "+totalinitialQty);
    		System.out.println("totalcurrentQty: "+totalcurrentQty);
			GrandtotalinitialQty += totalinitialQty;
    		GrandtotalcurrentQty += totalcurrentQty;
    		System.out.println("GrandtotalinitialQty: "+GrandtotalinitialQty);
    		System.out.println("GrandtotalcurrentQty: "+GrandtotalcurrentQty);
    		if (lotByClientList == null || lotByClientList.isEmpty())
				System.out.println("lotByClientList is null or Empty");
    		else {
    			HashMap[] row = new HashMap[1];
        	   	HashMap obj  = new HashMap();;
        	   	obj.put("invoiceNo","ikash");
        	   	row[0]= obj;
        	   	 
        	   	JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
        	   	 
        	   	JRBeanCollectionDataSource lotByClientJRBean = new JRBeanCollectionDataSource(lotByClientList);
    	        Map<String, Object> parameters = new HashMap<String, Object>();
    	        
    	        parameters.put("LotByClientDataSource", lotByClientJRBean);
    	        parameters.put("clientTitle", clientInfoRepo.findByCientId(clientId).getClientTitle());
    	        parameters.put("totalinitialQty", String.valueOf(totalinitialQty));
    	        parameters.put("totalcurrentQty", String.valueOf(totalcurrentQty));
    	        parameters.put("GrandtotalinitialQty", String.valueOf(GrandtotalinitialQty));
    	        parameters.put("GrandtotalcurrentQty", String.valueOf(GrandtotalcurrentQty));
    	   		reportName = context.getRealPath("/WEB-INF/stockBalanceReport");
    	   		JasperCompileManager.compileReportToFile(reportName + ".jrxml");
    	   		JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
    	   		JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
    	   		listJasper.add(print);
    	   		lotByClientList.clear();
    	   		
    	   		totalinitialQty = 0;
    	   		totalcurrentQty = 0;
    		}
    		
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
   			/*byte[] pdf = JasperExportManager.exportReportToPdf(p1);
   	        FileOutputStream fos = new FileOutputStream(reportName+".pdf");
   	        fos.write(pdf);
   	        fos.close();*/
   	        String downloadFolder = context.getRealPath("/WEB-INF/");
   	        Path file = Paths.get(downloadFolder, fileName);
   	        if (Files.exists(file)) 
   	        {
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
               if (Files.exists(file)) 
               {
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
	
}
	
	
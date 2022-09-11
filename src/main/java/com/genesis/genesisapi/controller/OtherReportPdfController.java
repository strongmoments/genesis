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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genesis.genesisapi.model.BillByInvoice;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DateInventoryReport;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.FinanceReport;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OutstandingBillReport;
import com.genesis.genesisapi.model.Payment;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.WsoEnquiryReport;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Controller
@RequestMapping("/download")
public class OtherReportPdfController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	 ServletContext context;

	@Autowired
	private BillingRepo billingRepo;
	
	@Autowired
	private TallysheetRepo tallysheetRepo;
	
	@Autowired
	private DeliveryListRepo deliveryListRepo;
	
	@Autowired
	private WSOInfoRepo wsoInfoRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private ClientInfoRepo clientRepo;
	
	@Autowired
	private LotInfoRepo lotInfoRepo;
	
	@RequestMapping("/finance/{fileName:.+}/{fileType}/{fromdate}/{todate}")
	 public void otherdownloader(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("fromdate") String fromdate, 
			 @PathVariable("todate") String todate) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<FinanceReport> listFinance = new ArrayList<FinanceReport>();
		 //String invoiceNo = "I0005";
		 //String fromdate = "2018-01-01";
		 //String todate = "2018-04-01";
		 Double totalAmount = 0.0;
		 Double totalGst = 0.0;
		 Double total = 0.0;
		 String invNo;
		 Double grandCredit=0.0;
		 Double grandCreditAmount = 0.0;
		 Double grandCreditGst = 0.0;
		 Date fromDate1 = null;
		 Date toDate1 = null;
		 JRXlsExporter xlsExporter = new JRXlsExporter();
		 
		 try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 
		 List<Object> data = billingRepo.getStorageReport(fromDate1, toDate1);
		 List<Object> data1 = billingRepo.getChargeItemReport(fromDate1, toDate1);
		 
		 List<String> invoiceList = new ArrayList<String>();
			
			for (int i = 0; i < data.size(); i++) {
				Object[] object = (Object[]) data.get(i);
				invNo = (String) object[4];
				invoiceList.add(invNo);
			}
			for (int i = 0; i < data1.size(); i++) {
				Object[] object = (Object[]) data1.get(i);
				invNo = (String) object[4];
				invoiceList.add(invNo);
			}
			System.out.println("Invoice List:"+invoiceList);
	 		List<Payment> p = paymentRepo.findPaymentByInvoiceNoList(invoiceList);
	 		logger.info("Records successfully retrieved from Payment Table based on Invoice Number List");
	 		
	 		if(p.isEmpty()){
	 			System.out.println("Payment List By Invoice List is Empty");
	 		}else{
	 			//System.out.println("Payment List By Invoice List:"+p);
	 			for(Payment p1 : p){				
	 				//System.out.println("Payment Type:"+p1.getPaymentType());
	 				if (p1.getPaymentType().equals("C")){
	 					grandCredit += p1.getPaidAmount();
	 				}				
	 			}
	 		}
	 		grandCreditAmount = (grandCredit *100)/107;	
	 		grandCreditGst = grandCredit - grandCreditAmount;
	 		System.out.println("Grand Credit:"+grandCredit);
	 		System.out.println("Grand Credit Amount:"+grandCreditAmount);
	 		System.out.println("Grand Credit GST:"+grandCreditGst);
	 		
		 for(int i=0; i<data.size(); i++) {
			 FinanceReport fin = new FinanceReport();
			 Object[] row = (Object[]) data.get(i);
			 fin.setChargeItems((String)row[0]);
			 fin.setRevenue(String.format( "%.2f", (Double)row[1]));
			 totalAmount +=(Double)row[1];
			 totalGst +=(Double)row[3];
			 
			 total = totalAmount - grandCreditAmount - grandCreditGst;
			 fin.setTotalAmount(String.format( "%.2f",totalAmount));
			 fin.setTotalGst(String.format( "%.2f",totalGst));
			 fin.setGrandCreditAmount(String.format( "%.2f",grandCreditAmount));
			 fin.setGrandCreditGst(String.format( "%.2f",grandCreditGst));
			 fin.setTotal(String.format( "%.2f",total));
			 
		 	listFinance.add(fin);
		 }
		 
		 /*for(int i=0; i<data.size(); i++) {
			 FinanceReport fin = new FinanceReport();
			 Object[] row = (Object[]) data.get(i);
			 fin.setChargeItems((String)row[0]+"-Charges");
			 fin.setRevenue(String.format( "%.2f", (Double)row[2]));
			 totalAmount +=(Double)row[2];
			
			 fin.setTotalAmount(String.format( "%.2f",totalAmount));
			 fin.setTotalGst(String.format( "%.2f",totalGst));
			 fin.setTotal(String.format( "%.2f",total));
		 	listFinance.add(fin);
		 }*/
		 
		 
		 for(int i=0; i<data1.size(); i++) {
			 FinanceReport fin = new FinanceReport();
			 Object[] row = (Object[]) data1.get(i);
			 fin.setChargeItems((String)row[0]/*+"-Charges"*/);
			 fin.setRevenue(String.format( "%.2f", (Double)row[1]));
			 
			 totalAmount +=(Double)row[1];
			 totalGst +=(Double)row[3];
			 
			 total = totalAmount - grandCreditAmount - grandCreditGst;
			 fin.setTotalAmount(String.format( "%.2f",totalAmount));
			 fin.setTotalGst(String.format( "%.2f",totalGst));
			 fin.setGrandCreditAmount(String.format( "%.2f",grandCreditAmount));
			 fin.setGrandCreditGst(String.format( "%.2f",grandCreditGst));
			 fin.setTotal(String.format( "%.2f",total));
		 	listFinance.add(fin);
		 }
		 
		 JRBeanCollectionDataSource financeJRBean = new JRBeanCollectionDataSource(listFinance);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("FinanceDataSource", financeJRBean);
	     parameters.put("fromDate",new SimpleDateFormat("dd-MM-yyyy").format(fromDate1).toString()); 
	     parameters.put("toDate",new SimpleDateFormat("dd-MM-yyyy").format(toDate1).toString());
			String reportName = context.getRealPath("/WEB-INF/financeReport");
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
	
	@RequestMapping("/dateInventory/{fileName:.+}/{fileType}/{clientId}/{todate}")
	 public void dateInventorydownloader(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("clientId") Long clientId, 
			 @PathVariable("todate") String todate) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<DateInventoryReport> listdate = new ArrayList<DateInventoryReport>();
		 JRXlsExporter xlsExporter = new JRXlsExporter();
		 Date toDate1 = null;
	
		try {
			toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Integer availableLot = 0;
		Integer ttlinitialQty = 0;
		Integer ttlcurrentQty = 0;
		
		List<TallySheet> tallyData = tallysheetRepo.findTallysheetByClientIdAndDate(clientId, toDate1);
		logger.info("Records successfully retrieved from Tallysheet table based on Client Id and Date");
		JSONArray list = new  JSONArray();
		
		for(TallySheet model : tallyData) {
			List<WSOInfo> wsoData = wsoInfoRepo.findWsoByTallysheet(model.getTallysheetId());
			logger.info("Records successfully retrieved from WSO Ino table based on Tallysheet Id");
			for(WSOInfo wsoModel : wsoData) {
				JSONObject  obj = new JSONObject();
				DateInventoryReport dateIn = new DateInventoryReport();
				List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(wsoModel.getWsoId());
				for(LotInfo lotModel : lotData) {
					availableLot += lotModel.getCurrentQuantity();
					List<DeliveryList> deliveryData = deliveryListRepo.getDeliveryByLotAndDate(lotModel.getLotId(), toDate1);
					logger.info("Records successfully retrieved from Delivery List table based on Lot Id and Date");
					for(DeliveryList deliveryModel : deliveryData) {
						availableLot += deliveryModel.getTotalQty();
					}
					
					if(availableLot > 0) {
						dateIn.setCurrentQty(availableLot);
						dateIn.setWsoNo(lotModel.getWsoInfo().getWsoNo());
						dateIn.setProductCode(lotModel.getLotNo());
						dateIn.setStorageClass(lotModel.getWsoInfo().getStorageClass().getStorageTypeName());
						dateIn.setInitialQty(lotModel.getInitialQuantity());
						dateIn.setProductDate(new SimpleDateFormat("dd-MM-yyyy").format(lotModel.getProductionDate()));
						dateIn.setExpiryDate(new SimpleDateFormat("dd-MM-yyyy").format(lotModel.getExpiryDate()));
						dateIn.setNetWt(String.format( "%.2f", lotModel.getUnitNetWeightInKg()));
						dateIn.setGrossWt(String.format( "%.2f", lotModel.getUnitNetWeightInKg()*availableLot));
						dateIn.setStorageDate(new SimpleDateFormat("dd-MM-yyyy").format(lotModel.getWsoInfo().getTallysheet().getStorageDate()));
						dateIn.setProductDes(lotModel.getWsoInfo().getDescription());
						ttlcurrentQty += availableLot;
						ttlinitialQty += lotModel.getInitialQuantity();
						System.out.println("initial qty : "+ttlinitialQty);
						dateIn.setTtlcurrentQty(ttlcurrentQty);
						dateIn.setTtlinitialQty(ttlinitialQty);
						listdate.add(dateIn);
						availableLot = 0;
					}
					
				}
			}				
		}
		 
		 JRBeanCollectionDataSource dateInventoryJRBean = new JRBeanCollectionDataSource(listdate);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("DateInventoryDataSource", dateInventoryJRBean);
	     parameters.put("asOnDate",new SimpleDateFormat("dd-MM-yyyy").format(toDate1).toString());
	     parameters.put("clientTitle", clientRepo.findByCientId(clientId).getClientTitle());      
			String reportName = context.getRealPath("/WEB-INF/dateInventoryReport");
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
	
	
	
	@RequestMapping("/outstandingBill/{fileName:.+}/{fileType}/{clientId}")
	 public void outstandingBilldownloader(HttpServletRequest request, HttpServletResponse response,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("clientId") Long clientId) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<OutstandingBillReport> listout = new ArrayList<OutstandingBillReport>();
		 JRXlsExporter xlsExporter = new JRXlsExporter();
		 ClientInfo clientInfo = clientRepo.findByCientId(clientId);
	        
	        List<Object> billingdata = billingRepo.getBillingInfoByCLientIdGroup(clientId);
	        logger.info("Records successfully retrieved from Billing table based on Client Id");
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
	    	
	        JSONArray list = new  JSONArray();
	        Date lastBillDate = null;
	        Double lastBillPay = 0.00;
	        Double ttlInvoiceAmount = 0.00;
	        Double ttlBalanceAmount = 0.00;
	        for (BillByInvoice model : billInvoice){
	        	
	        	List<Payment> paymentdata = paymentRepo.findPaymentByInvoiceNoDistinct(model.getInvoice_no());
	        	List<Billing> billingInfo = billingRepo.getBillingInfoByInvoiceNoGroup(model.getInvoice_no());
	        	System.out.println("Lis<Payment> length: "+paymentdata.size());
	        	if(paymentdata.size() == 0) {
	        		OutstandingBillReport  obj  = new OutstandingBillReport();
	    	        obj.setBalance(String.format( "%.2f", model.getTotal_amount()));
	    	        obj.setCredit("30");
	    	        obj.setInvoiceAmt(String.format( "%.2f", model.getTotal_amount()));
	    	        obj.setInvoiceNo(billingInfo.get(0).getInvoiceNo());
	    	        obj.setPaidDate(new SimpleDateFormat("dd-MM-yyyy").format(billingInfo.get(0).getInvoiceDate()));
	    	        listout.add(obj);
	    	        ttlInvoiceAmount += model.getTotal_amount();
	    	        ttlBalanceAmount += model.getTotal_amount();
	        	}else {
	        		for(Payment modeldata : paymentdata) {
		        		OutstandingBillReport  obj  = new OutstandingBillReport();
		    	        obj.setBalance(String.format( "%.2f", modeldata.getBalance()));
		    	        obj.setCredit("30");
		    	        obj.setInvoiceAmt(String.format( "%.2f", model.getTotal_amount()));
		    	        obj.setInvoiceNo(modeldata.getInvoiceNo());
		    	        obj.setPaidDate(new SimpleDateFormat("dd-MM-yyyy").format(billingInfo.get(0).getInvoiceDate()));
		    	        
		    	        listout.add(obj);
		    	        ttlInvoiceAmount += model.getTotal_amount();
		    	        ttlBalanceAmount += modeldata.getBalance();
		    	        if(lastBillDate == null || lastBillDate.before(modeldata.getPaymentDate())) {
		    	        	lastBillDate = modeldata.getPaymentDate();
		    	        	lastBillPay = (double) modeldata.getPaidAmount();
		    	        }
		        	}
	        	}
	        	
	        }
	        
		 
		 JRBeanCollectionDataSource OutstandingJRBean = new JRBeanCollectionDataSource(listout);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("OutstandingDataSource", OutstandingJRBean);
	     parameters.put("ttlInvoiceAmount", String.format( "%.2f", ttlInvoiceAmount));
	     parameters.put("lastBillPay", String.format( "%.2f", lastBillPay));
	     if(lastBillDate == null) {
		     parameters.put("lastBillDate", "");
	     }else {
		     parameters.put("lastBillDate", new SimpleDateFormat("dd-MM-yyyy").format(lastBillDate));
	     }
	     parameters.put("ttlBalanceAmount", String.format( "%.2f", ttlBalanceAmount));
	     parameters.put("clientTitle", clientInfo.getClientTitle());
	     parameters.put("clientMobileNo", clientInfo.getContactPersonMobileNumbere());
	           
		String reportName = context.getRealPath("/WEB-INF/outStandingReport");
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
	
	
	@RequestMapping("/wsoEnquiry/{fileName:.+}/{fromWsoId}/{toWsoId}")
	 public void wsoEnquirydownloader(HttpServletRequest request, HttpServletResponse response,@PathVariable("fileName") String fileName, @PathVariable("fromWsoId") Long fromWsoId, @PathVariable("toWsoId") Long toWsoId) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<WsoEnquiryReport> listwso = new ArrayList<WsoEnquiryReport>();
		 
		 Long fromWsoId1 = Long.valueOf(fromWsoId);
	        Long toWsoId1 = Long.valueOf(toWsoId);
	        List<WSOInfo> wsodata = wsoInfoRepo.findWsoByWsoId(fromWsoId1, toWsoId1);
	        
	        JSONArray list = new  JSONArray();
	        float ttlWsoQty = 0.0f;
	        float currentWsoQty = 0.0f;
	        float currentWsoWt = 0.0f;
	        
	        for (WSOInfo model : wsodata){
	        	List<DeliveryList> deliverydata = wsoInfoRepo.findDeliveryListByWsoId(model.getWsoId());
	        	logger.info("Records successfully retrieved from Delivery List table based on WSO ID");
	        	List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(model.getWsoId());
	        	for(LotInfo lotModel : lotData) {
	        		currentWsoQty += lotModel.getCurrentQuantity();
	        		currentWsoWt += lotModel.getCurrentQuantity() * lotModel.getUnitNetWeightInKg();
	        		ttlWsoQty += lotModel.getLotQuantity();		
	        	}
	        	List<WsoEnquiryReport> listwso1 = new ArrayList<WsoEnquiryReport>();
	        	for(DeliveryList deliModel : deliverydata) {
	        		WsoEnquiryReport  obj  = new WsoEnquiryReport();
	        		obj.setDate(new SimpleDateFormat("dd-MM-yyyy").format(deliModel.getDateOfDelivery()));
	        		obj.setDlNo(deliModel.getDlNo());
	        		obj.setLotNo(deliModel.getLotInfo().getLotNo());
	        		int pckOut = deliModel.getTotalQty();
	        		obj.setPackagesOut(pckOut);
	        		
	        		listwso1.add(obj);
	        	}

	        	listwso.addAll(listwso1);
		        ttlWsoQty = 0.0f;
		        currentWsoQty = 0.0f;
		        currentWsoWt = 0.0f;
	        }

	       		 
		 JRBeanCollectionDataSource WsoEnquiryJRBean = new JRBeanCollectionDataSource(listwso);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     parameters.put("WsoEnquiryDataSource", WsoEnquiryJRBean);
	           
			String reportName = context.getRealPath("/WEB-INF/WsoEnquiryReport");
			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
			byte[] pdf = JasperExportManager.exportReportToPdf(print);
	        FileOutputStream fos = new FileOutputStream(reportName+".pdf");
	        fos.write(pdf);
	        fos.close();
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
}

package com.genesis.genesisapi.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genesis.genesisapi.model.BillByInvoice;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ClientDetails;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DateInventoryReport;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.DeliveryReport;
import com.genesis.genesisapi.model.FinanceReport;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OutgoingReport;
import com.genesis.genesisapi.model.OutstandingBillReport;
import com.genesis.genesisapi.model.Payment;
import com.genesis.genesisapi.model.SoaReport;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.soaCurrent;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.service.EmailService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
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

import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
@Controller
@RequestMapping("/email")
public class EmailPdfController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private BillingRepo billingRepo;
	
	@Autowired
	private EmailService emailService;
	 
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private TallysheetRepo tallysheetRepo;
	
	@Autowired
	private WSOInfoRepo wsoInfoRepo;
	
	@Autowired
	private DeliveryListRepo deliveryListRepo;
	
	@Autowired
	private LotInfoRepo lotInfoRepo;
	
	@Autowired
	private ClientInfoRepo clientRepo;
	
	@RequestMapping("/finance/{fileName:.+}/{fileType}/{fromdate}/{todate}")
	 public void otherdownloader(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("fromdate") String fromdate, 
			 @PathVariable("todate") String todate) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
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
		 JRXlsxExporter xlsExporter = new JRXlsxExporter();
		 
		 try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 
		 List<Object> data = billingRepo.getStorageReport(fromDate1, toDate1);
		 List<Object> data1 = billingRepo.getChargeItemReport(fromDate1, toDate1);
		 logger.info("Records successfully retrieved from storage_type and other_charges");
		 
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
	            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
	            configuration.setOnePagePerSheet(true);
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
			
			//Email Work
			try{
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				
				//helper.setFrom(simpleMailMessage.getFrom());
				helper.setTo(userEmail);
				helper.setSubject("Finance Report");
				helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
				String home = System.getProperty("user.home");
				String nameOfFile = home+"\\Downloads\\" + fileName;
				System.out.println("File to be located:"+nameOfFile);
				
				FileSystemResource file1 = new FileSystemResource(nameOfFile);
				helper.addAttachment(file1.getFilename(), file1);
				mailSender.send(message);
				System.out.println("Email Sent to the Client");
			   
			}catch(Exception e){
				System.out.println(e.getMessage());
			}		
	}
	
	@RequestMapping("/dateInventory/{fileName:.+}/{fileType}/{clientId}/{todate}")
	 public void dateInventorydownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("clientId") Long clientId, 
			 @PathVariable("todate") String todate) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<DateInventoryReport> listdate = new ArrayList<DateInventoryReport>();
		 JRXlsxExporter xlsExporter = new JRXlsxExporter();
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
		logger.info("Records successfully retrieved from Tallysheet table based on ClientId and toDate");
		JSONArray list = new  JSONArray();
		
		for(TallySheet model : tallyData) {
			List<WSOInfo> wsoData = wsoInfoRepo.findWsoByTallysheet(model.getTallysheetId());
			logger.info("Records retrieved from WSO table based on Tallysheet Id.");
			for(WSOInfo wsoModel : wsoData) {
				JSONObject  obj = new JSONObject();
				DateInventoryReport dateIn = new DateInventoryReport();
				List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(wsoModel.getWsoId());
				logger.info("Records successfully retrieved from Lot table on the basis of WSO ID");
				for(LotInfo lotModel : lotData) {
					availableLot += lotModel.getCurrentQuantity();
					List<DeliveryList> deliveryData = deliveryListRepo.getDeliveryByLotAndDate(lotModel.getLotId(), toDate1);
					logger.info("Records successfully retrieved from Delivery List on the basis of Lot Id and Date");
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
	            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
	            configuration.setOnePagePerSheet(true);
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
			
			//Email Work
			try{
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				
				//helper.setFrom(simpleMailMessage.getFrom());
				helper.setTo(userEmail);
				helper.setSubject("Date Inventory Report");
				helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
				String home = System.getProperty("user.home");
				String nameOfFile = home+"\\Downloads\\" + fileName;
				System.out.println("File to be located:"+nameOfFile);
				
				FileSystemResource file1 = new FileSystemResource(nameOfFile);
				helper.addAttachment(file1.getFilename(), file1);
				mailSender.send(message);
				System.out.println("Email Sent to the Client");
			   
			}catch(Exception e){
				System.out.println(e.getMessage());
			}	
	 }
	
	@RequestMapping("/outstandingBill/{fileName:.+}/{fileType}/{clientId}")
	 public void outstandingBilldownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("clientId") Long clientId) throws JRException, IOException, NoSuchFieldException, SecurityException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<OutstandingBillReport> listout = new ArrayList<OutstandingBillReport>();
		 JRXlsxExporter xlsExporter = new JRXlsxExporter();
		 ClientInfo clientInfo = clientRepo.findByCientId(clientId);
	     logger.info("Records successfully retrieved from Client Info table on the basis of client Id");   
	     
	        List<Object> billingdata = billingRepo.getBillingInfoByCLientIdGroup(clientId);
	        logger.info("Records successfully retrieved from Billing table on the basis of client Id");  
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
	        	logger.info("Records retrieved from Payment table on the basis of Invoice Number");
	        	List<Billing> billingInfo = billingRepo.getBillingInfoByInvoiceNoGroup(model.getInvoice_no());
	        	logger.info("Records retrieved from Billing table on the basis of Invoice Number");
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
           SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
           configuration.setOnePagePerSheet(true);
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
		
		//Email Work
		try{
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			//helper.setFrom(simpleMailMessage.getFrom());
			helper.setTo(userEmail);
			helper.setSubject("Outstanding Bill Report");
			helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
			String home = System.getProperty("user.home");
			String nameOfFile = home+"\\Downloads\\" + fileName;
			System.out.println("File to be located:"+nameOfFile);
			
			FileSystemResource file1 = new FileSystemResource(nameOfFile);
			helper.addAttachment(file1.getFilename(), file1);
			mailSender.send(message);
			System.out.println("Email Sent to the Client");
		   
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
	 }
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/soa/{fileName:.+}/{fileType}/{fromClientInfo}/{toClientInfo}")
	 public void soaReportdownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			 @PathVariable("fileName") String fileName, @PathVariable("fileType") String fileType,
			 @PathVariable("fromClientInfo") Long fromClientInfo,
			 @PathVariable("toClientInfo") Long toClientInfo) throws JRException, IOException, NoSuchFieldException, SecurityException {
		httpSession = request.getSession();
		String userEmail = (String) httpSession.getAttribute("emailId");
		String userName = (String) httpSession.getAttribute("userId");
		String reportName = null;
		long daysOverDue = 0L;
		Boolean check = false;
		DecimalFormat df = new DecimalFormat("###.##");
		JRPdfExporter exp = new JRPdfExporter();
		List<List<SoaReport>> list1 = new ArrayList<List<SoaReport>>();
		List<SoaReport> listout = new ArrayList<SoaReport>();
		List<List<soaCurrent>> list2 = new ArrayList<List<soaCurrent>>();
		List<soaCurrent> listCurrent = new ArrayList<soaCurrent>();
		List<ClientDetails> listClient = new ArrayList<ClientDetails>();
		JRXlsxExporter xlsExporter = new JRXlsxExporter();
		List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
		//List<Object> listJasper = new ArrayList<Object>();
		List<ClientInfo> clientInfo = clientRepo.getClientBetweenClient(fromClientInfo, toClientInfo);
		logger.info("Data retrieved from Client Info table");
        int client=0;
        for(ClientInfo model1 : clientInfo) {
        	client++;
        	ClientDetails objClient = new ClientDetails();
        	objClient.setClientTitle(model1.getClientTitle());
        	objClient.setClientMobileNo(model1.getContactPersonMobileNumbere());
        	listClient.add(objClient);
        	//List<Billing> billingdata = billingRepo.getBillingInfoByCLientId(model1.getClientInfoId());
        	
        	List<Object> billingdata = billingRepo.getBillingInfoByCLientIdGroup(model1.getClientInfoId());
        	logger.info("Data retrieved from Billing table based on Client Id");
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
            	logger.info("Records successfully retrieved from Payment table based on Distinct Invoice No.");
            	List<Billing> billingInfo = billingRepo.getBillingInfoByInvoiceNoGroup(model.getInvoice_no());
            	logger.info("Records successfully retrieved from Billing table based on Invoice Number");
            	
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
        	xlsExporter.setExporterInput(SimpleExporterInput.getInstance(listJasper));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(true);
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
      //Email Work
      		try{
      			MimeMessage message = mailSender.createMimeMessage();
      			MimeMessageHelper helper = new MimeMessageHelper(message, true);
      			
      			//helper.setFrom(simpleMailMessage.getFrom());
      			helper.setTo(userEmail);
      			helper.setSubject("SOA Report");
      			helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
      			String home = System.getProperty("user.home");
      			String nameOfFile = home+"\\Downloads\\" + fileName;
      			System.out.println("File to be located:"+nameOfFile);
      			
      			FileSystemResource file1 = new FileSystemResource(nameOfFile);
      			helper.addAttachment(file1.getFilename(), file1);
      			mailSender.send(message);
      			System.out.println("Email Sent to the Client");
      		   
      		}catch(Exception e){
      			System.out.println(e.getMessage());
      		}
           
	 }
	// WSO Enquiry Report
		@SuppressWarnings("deprecation")
		@RequestMapping("/wsoenq/{fileName:.+}/{fileType}/{fromWsoNo}/{toWsoNo}")
		 public void wsoEnquiryReportdownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
				 @PathVariable("fileName") String fileName, 
				 @PathVariable("fileType") String fileType,
				 @PathVariable("fromWsoNo") Long fromWsoNo,@PathVariable("toWsoNo") Long toWsoNo) throws JRException, IOException, NoSuchFieldException, SecurityException {
			httpSession = request.getSession();
			String userEmail = (String) httpSession.getAttribute("emailId");
			String userName = (String) httpSession.getAttribute("userId");
			String reportName = null;
			String clientName = null;
			String wsoNo = null;
			String storageDate = null;
			String totalWsoWeight = null;
			double total=0.0f;
			Boolean check = true;
			JRPdfExporter exp = new JRPdfExporter();
			JRXlsxExporter xlsExporter = new JRXlsxExporter();
			List<JasperPrint> listJasper = new ArrayList<JasperPrint>();
			
			List<List<DeliveryReport>> list = new ArrayList<List<DeliveryReport>>();
			List<WSOInfo> wsodata = wsoInfoRepo.findWsoByWsoId(fromWsoNo, toWsoNo);
			List<DeliveryReport> list1 = new ArrayList<DeliveryReport>();
			
			float ttlWsoQty = 0.0f;
	        float currentWsoQty = 0.0f;
	        float currentWsoWt = 0.0f;
	        
	        for (WSOInfo model : wsodata)
	        { 	
	        	if(model.getWsoNo()!=null)
	        	{
	        		List<DeliveryList> deliverydata = wsoInfoRepo.findDeliveryListByWsoId(model.getWsoId());
	        		logger.info("Data retrieved from Delivery List table based on WSO ID");
	            	List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(model.getWsoId());
	            	logger.info("Data retrieved from Lot Info table based on WSO Id");
	            	for(LotInfo lotModel : lotData) {
	            		currentWsoQty += lotModel.getCurrentQuantity();
	            		currentWsoWt += lotModel.getCurrentQuantity() * lotModel.getUnitNetWeightInKg();
	            		ttlWsoQty += lotModel.getLotQuantity();		
	            	}
	            	
	            	if(deliverydata.size()>0){
	            		for(DeliveryList deliModel : deliverydata) {
	                		DeliveryReport  obj  = new DeliveryReport();
	                		obj.setDlNo(deliModel.getDlNo());
	                		obj.setDateOfDelivery(String.valueOf(deliModel.getDateOfDelivery()));
	                		obj.setLotNo(deliModel.getLotInfo().getLotNo());
	                		obj.setPackagesOut(deliModel.getTotalQty().toString());
	                		obj.setWsoNo(deliModel.getWsoInfo().getWsoNo().toString());
	                		obj.setClientName(deliModel.getClientInfo().getClientTitle());
	                		obj.setTotalWsoWeight(String.valueOf(deliModel.getWsoInfo().getTotalWsoWeight()));
	                		obj.setStorageDate(deliModel.getWsoInfo().getTallysheet().getStorageDate().toString());
	                		  
	            	        list1.add(obj);
	            	        total += deliModel.getTotalQty();
	            	        clientName=deliModel.getClientInfo().getClientTitle();
	            	        wsoNo=deliModel.getWsoInfo().getWsoNo().toString();
	            	        storageDate=deliModel.getWsoInfo().getTallysheet().getStorageDate().toString();
	            	        totalWsoWeight=String.valueOf(deliModel.getWsoInfo().getTotalWsoWeight());
	            	        
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
	       		     parameters.put("currentWsoQty", String.valueOf(currentWsoQty));
	       		     parameters.put("totalWsoWeight", totalWsoWeight);
	       		     parameters.put("ttlWsoQty", String.valueOf(ttlWsoQty));
	       		     parameters.put("total", String.valueOf(total));
	       			reportName = context.getRealPath("/WEB-INF/wsoEnquiryReport");
	       			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
	       			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
	       			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
	       			listJasper.add(print);
	       		
	            	}
	    	         	
	    			ttlWsoQty = 0.0f;
	    	        currentWsoQty = 0.0f;
	    	        currentWsoWt = 0.0f;
	    	        total=0.0;
	    	        list1.clear();
	    	        check=true;
	        	}
	        		
	        	    
	        }
	        logger.info("Records successfully retreived from wsoInfo table.");
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
	        	xlsExporter.setExporterInput(SimpleExporterInput.getInstance(listJasper));
	            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
	            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
	            configuration.setOnePagePerSheet(true);
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
	      //Email Work
      		try{
      			MimeMessage message = mailSender.createMimeMessage();
      			MimeMessageHelper helper = new MimeMessageHelper(message, true);
      			
      			//helper.setFrom(simpleMailMessage.getFrom());
      			helper.setTo(userEmail);
      			helper.setSubject("WSO Enquiry Report");
      			helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
      			String home = System.getProperty("user.home");
      			String nameOfFile = home+"\\Downloads\\" + fileName;
      			System.out.println("File to be located:"+nameOfFile);
      			
      			FileSystemResource file1 = new FileSystemResource(nameOfFile);
      			helper.addAttachment(file1.getFilename(), file1);
      			mailSender.send(message);
      			System.out.println("Email Sent to the Client");
      		   
      		}catch(Exception e){
      			System.out.println(e.getMessage());
      		}   
	     }
		//Outgoing Records
		
			@SuppressWarnings("deprecation")
			@RequestMapping("/outgoing/{fileName:.+}/{fileType}/{fromClient}/{toClient}/{fromDate}/{toDate}")
			 public void outgoingReportdownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
					 @PathVariable("fileName") String fileName, 
					 @PathVariable("fileType") String fileType, 
					 @PathVariable("fromClient") Long fromClient,
					 @PathVariable("toClient") Long toClient,
					 @PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate) throws JRException, IOException, NoSuchFieldException, SecurityException {
				httpSession = request.getSession();
				String userEmail = (String) httpSession.getAttribute("emailId");
				String userName = (String) httpSession.getAttribute("userId");
				String reportName = null;
				String clientName = null;
				Boolean check = true;
				Boolean printReport = false;
				JRPdfExporter exp = new JRPdfExporter();
				JRXlsxExporter xlsExporter = new JRXlsxExporter();
				
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
		        logger.info("Data retrieved from Delivery List table from Client to Client");
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
		        	logger.info("Data retrieved from Delivery List table from Date to Date");
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
		        	xlsExporter.setExporterInput(SimpleExporterInput.getInstance(listJasper));
		            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
		            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		            configuration.setOnePagePerSheet(true);
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
		      //Email Work
	      		try{
	      			MimeMessage message = mailSender.createMimeMessage();
	      			MimeMessageHelper helper = new MimeMessageHelper(message, true);
	      			
	      			//helper.setFrom(simpleMailMessage.getFrom());
	      			helper.setTo(userEmail);
	      			helper.setSubject("Outgoing Report");
	      			helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
	      			String home = System.getProperty("user.home");
	      			String nameOfFile = home+"\\Downloads\\" + fileName;
	      			System.out.println("File to be located:"+nameOfFile);
	      			
	      			FileSystemResource file1 = new FileSystemResource(nameOfFile);
	      			helper.addAttachment(file1.getFilename(), file1);
	      			mailSender.send(message);
	      			System.out.println("Email Sent to the Client");
	      		   
	      		}catch(Exception e){
	      			System.out.println(e.getMessage());
	      		}   
		     }
	//addDays method
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
				
		return cal.getTime();
	}
}

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.XlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.BillingTest;
import com.genesis.genesisapi.model.CommonBilling;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.DeliverylistReport;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OtherChargesBilling;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.TallysheetReport;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.WsolotReport;
import com.genesis.genesisapi.model.leaseBilling;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.service.EmailService;
import com.genesis.genesisapi.service.WSOService;

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
@RequestMapping("/email")
public class SendPdfByEmailController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	 ServletContext context;
	 
	 @Autowired
	 private BillingRepo billingRepo;
	 
	 @Autowired
	 private LotInfoRepo lotInfoRepo;
	 
	 @Autowired
	 private DeliveryListRepo deliveryListRepo;
	 
	 @Autowired
	 private TallysheetRepo tallysheetRepo;
	 
	 @Autowired
	 private WSOService wsoService; 
	 
	 @Autowired
	 private WSOInfoRepo wsoInfoRepo;
	 
	 @Autowired
	 private EmailService emailService;
	 
	 @Autowired
	 private JavaMailSender mailSender;
	 public void setMailSender(JavaMailSender mailSender) {
			this.mailSender = mailSender;
		}
	 
	 
	 @RequestMapping("/common/{fileName:.+}/{fileType}/{invoiceNo}")
	 public void downloader(HttpServletRequest request, HttpServletResponse response,HttpSession httpSession,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("invoiceNo") String invoiceNo) throws JRException, IOException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 logger.info("UserName:"+userName+" Email Id: "+userEmail);
		 logger.info("common downloader() called.");
		 JRXlsxExporter xlsExporter = new JRXlsxExporter();
		 List<CommonBilling> listCommon = new ArrayList<CommonBilling>();
		 List<Billing> data = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
		 Double totalAmount = 0.0;
		 for(Billing model : data) {
			 CommonBilling common = new CommonBilling();
			 float handlingchrg = model.getHandlingCharge();
			 if(handlingchrg == 0) {
				 String desc = "";
				 desc= desc+" "+model.getWsoInfo().getWsoNo()+" Billing Period from "+new SimpleDateFormat("dd-MM-yyyy").format(model.getFromDate())+" to "+new SimpleDateFormat("dd-MM-yyyy").format(model.getToDate());
				 common.setWsoNo(model.getWsoInfo().getWsoNo());
				 common.setDes(desc);
				 common.setFromDate(model.getFromDate());
				 common.setToDate(model.getToDate());
				 common.setWeight(String.format( "%.2f", model.getBillingWeight()));
				 common.setRate( String.format( "%.2f", model.getBillingRate()) );
				 common.setGst( String.format( "%.2f", model.getGst() ));
				 common.setAmount( String.format( "%.2f", model.getNetAmount() ));
				 totalAmount += model.getNetAmount();
				 common.setTtlAmount(String.format( "%.2f", totalAmount ));
			 }else {
				 common.setDes("Handling Charge");
				 common.setWeight("Pallets: "+ model.getWsoInfo().getTotalNoOfPallets());
				 common.setRate( String.format( "%.2f", model.getBillingRate()) );
				 common.setGst( String.format( "%.2f", model.getGst()));
				 common.setAmount(String.format( "%.2f", model.getNetAmount()));
				 totalAmount += model.getNetAmount();
				 common.setTtlAmount(String.format( "%.2f", totalAmount ));
			 }
			 
			 listCommon.add(common);
		 }
		 HashMap[] row = new HashMap[1];
		 HashMap obj  = new HashMap();;
		 obj.put("invoiceNo","ikash");
		 row[0]= obj;
		 
		 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
		 
		 JRBeanCollectionDataSource commonJRBean = new JRBeanCollectionDataSource(listCommon);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("CommonDataSource", commonJRBean);
	     BillingTest inoiceDetail  = new BillingTest();
	     //inoiceDetail.setInvoiceNo("vikash  hara");
	     parameters.put("invoice", invoiceNo);
	     String ctitle  = URLDecoder.decode(data.get(0).getClientInfo().getClientAddress1(), "UTF-8");     
	     parameters.put("clientTitle", data.get(0).getClientInfo().getClientTitle()+"\n"+ctitle);
	     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	     String d = dateFormat.format(data.get(0).getInvoiceDate());
	     parameters.put("invoiceDate",d);
	     parameters.put("totalAmount",String.format( "%.2f", totalAmount ));
	     
	     logger.info("reportName load");
			String reportName = context.getRealPath("/WEB-INF/commonbilling");
			JasperCompileManager.compileReportToFile(reportName + ".jrxml");
			logger.info("Report name is : "+reportName);
			JasperReport jasperSubReport = JasperCompileManager.compileReport(reportName + ".jrxml");
			logger.info("Jasper compiling file.");
			JasperPrint print = JasperFillManager.fillReport(jasperSubReport, parameters, new JREmptyDataSource() );
			logger.info("common file filed with actual data");
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
						  //Email Work
						    MimeMessage message = mailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(message, true);
							
							//helper.setFrom(simpleMailMessage.getFrom());
							helper.setTo(userEmail);
							helper.setSubject("Common Billing Report");
							helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
							String home = System.getProperty("user.home");
							String nameOfFile = home+"\\Downloads\\" + fileName;
							System.out.println("File to be located:"+nameOfFile);
							//FileSystemResource file1 = new FileSystemResource("C:\\Users\\lenovo\\Downloads\\commonbilling.pdf");
							FileSystemResource file1 = new FileSystemResource(nameOfFile);
							helper.addAttachment(file1.getFilename(), file1);
							mailSender.send(message);
							System.out.println("Email Sent to the Client");
		        	} catch (IOException | MessagingException e) {
		        	}
	        	} else {
	        		System.out.println("Sorry File not found!!!!");
	        	}

			}
			if(fileType.equals("xls")){
				xlsExporter.setExporterInput(new SimpleExporterInput(print));
	            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
	            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	            configuration.setOnePagePerSheet(true);
	            configuration.setDetectCellType(true);
	            configuration.setCollapseRowSpan(false);
	            xlsExporter.setConfiguration((XlsxReportConfiguration) configuration);
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
	 
	 @RequestMapping("/lease/{fileName:.+}/{fileType}/{invoiceNo}")
	 public void leasedownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("invoiceNo") String invoiceNo) throws JRException, IOException, ParseException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 List<leaseBilling> listLease = new ArrayList<leaseBilling>();
		 //String invoiceNo = "I0002";
		 JRXlsxExporter xlsExporter = new JRXlsxExporter();
		 List<Billing> data = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
		 logger.info("Records successfully retrieved from Billing table based on Invoice Number");
		 Double totalAmount = 0.00;
		 for(Billing model : data) {
			 float handlingchrg = model.getHandlingCharge();
			 if(handlingchrg == 0) {
				 leaseBilling lease = new leaseBilling();
				 lease.setStorage(model.getStorageClass().getStorageTypeName());
				 String fDate=model.getFromDate().toString();
				 lease.setFromDate(new SimpleDateFormat("dd-MM-yyyy").format(model.getFromDate()));
				 lease.setToDate(new SimpleDateFormat("dd-MM-yyyy").format(model.getToDate()));
				 lease.setRate(String.format( "%.2f", model.getBillingRate()));
				 lease.setGst( String.format( "%.2f", model.getGst()));
				 lease.setAmount( String.format( "%.2f", model.getNetAmount()));
				 totalAmount += model.getNetAmount();
				 lease.setTtlAmount( String.format( "%.2f",totalAmount));
				 
				 listLease.add(lease);
			 }
			 
		 }
		 HashMap[] row = new HashMap[1];
		 HashMap obj  = new HashMap();;
		 obj.put("invoiceNo","ikash");
		 row[0]= obj;
		 
		 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
		 
	     JRBeanCollectionDataSource leaseJRBean = new JRBeanCollectionDataSource(listLease);
	     

	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("LeaseDataSource", leaseJRBean);
	     BillingTest inoiceDetail  = new BillingTest();
	     //inoiceDetail.setInvoiceNo("vikash  hara");
	     parameters.put("invoice", invoiceNo);
	    String ctitle  = URLDecoder.decode(data.get(0).getClientInfo().getClientAddress1(), "UTF-8");
	     parameters.put("clientTitle",data.get(0).getClientInfo().getClientTitle()+"\n"+ctitle );
	     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	     String d = dateFormat.format(data.get(0).getInvoiceDate());
	     parameters.put("invoiceDate",d);
	     parameters.put("totalAmount",String.format( "%.2f",totalAmount));
	     
			String reportName = context.getRealPath("/WEB-INF/leasebilling");
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
						  //Sending Email
						    MimeMessage message = mailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(message, true);
							
							//helper.setFrom(simpleMailMessage.getFrom());
							helper.setTo(userEmail);
							helper.setSubject("Lease Billing Report");
							helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
							String home = System.getProperty("user.home");
							String nameOfFile = home+"\\Downloads\\" + fileName;
							System.out.println("File to be located:"+nameOfFile);
							//FileSystemResource file1 = new FileSystemResource("C:\\Users\\lenovo\\Downloads\\leasebilling.pdf");
							FileSystemResource file1 = new FileSystemResource(nameOfFile);
							helper.addAttachment(file1.getFilename(), file1);
							mailSender.send(message);
							System.out.println("Email Sent to the Client");
		        	} catch (IOException | MessagingException e) {
		        	}
	        	} else {
	        		System.out.println("Sorry File not found!!!!");
	        	}

			}
			if(fileType.equals("xls")){
				xlsExporter.setExporterInput(new SimpleExporterInput(print));
	            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
	            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	            configuration.setOnePagePerSheet(true);
	            configuration.setDetectCellType(true);
	            configuration.setCollapseRowSpan(false);
	            xlsExporter.setConfiguration((XlsxReportConfiguration) configuration);
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
	 
	 @RequestMapping("/other/{fileName:.+}/{fileType}/{invoiceNo}")
	 public void otherdownloader(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
			 @PathVariable("fileName") String fileName, 
			 @PathVariable("fileType") String fileType, 
			 @PathVariable("invoiceNo") String invoiceNo) throws JRException, IOException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 List<OtherChargesBilling> listOther = new ArrayList<OtherChargesBilling>();
		 JRXlsxExporter xlsExporter = new JRXlsxExporter();
		 List<Billing> data = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
		 logger.info("Records successfully retrieved from Billing based on Invoice No.");
		 Double totalAmount = 0.0;
		 for(Billing model : data) {
		 	OtherChargesBilling oth = new OtherChargesBilling();
			 oth.setChargeItem(URLDecoder.decode(model.getNaration(), "UTF-8"));
			 oth.setRate(String.format( "%.2f",model.getBillingRate()));
			 oth.setGst( String.format( "%.2f",model.getGst()) );
			 oth.setAmount( String.format( "%.2f",model.getNetAmount()));
			 totalAmount += model.getNetAmount();
			 oth.setTtlAmount( String.format( "%.2f",totalAmount));
			 listOther.add(oth);
		 }
		 HashMap[] row = new HashMap[1];
		 HashMap obj  = new HashMap();;
		 obj.put("invoiceNo","ikash");
		 row[0]= obj;
		 
		 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
		 
		 JRBeanCollectionDataSource otherJRBean = new JRBeanCollectionDataSource(listOther);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("OtherDataSource", otherJRBean);
	     BillingTest inoiceDetail  = new BillingTest();
	     //inoiceDetail.setInvoiceNo("vikash  hara");
	     parameters.put("invoice", invoiceNo);
	     String ctitle  = URLDecoder.decode(data.get(0).getClientInfo().getClientAddress1(), "UTF-8");
	     parameters.put("clientTitle", data.get(0).getClientInfo().getClientTitle()+"\n"+ctitle);
	     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	     String d = dateFormat.format(data.get(0).getInvoiceDate());
	     parameters.put("invoiceDate",d);
	     parameters.put("totalAmount",String.format( "%.2f",totalAmount));
	          
			String reportName = context.getRealPath("/WEB-INF/otherbilling");
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
						  //Sending Email
						    MimeMessage message = mailSender.createMimeMessage();
							MimeMessageHelper helper = new MimeMessageHelper(message, true);
							
							//helper.setFrom(simpleMailMessage.getFrom());
							helper.setTo(userEmail);
							helper.setSubject("Other Billing Report");
							helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
							String home = System.getProperty("user.home");
							String nameOfFile = home+"\\Downloads\\" + fileName;
							System.out.println("File to be located:"+nameOfFile);
							FileSystemResource file1 = new FileSystemResource(nameOfFile);
							helper.addAttachment(file1.getFilename(), file1);
							mailSender.send(message);
							System.out.println("Email Sent to the Client");
		        	} catch (IOException | MessagingException e) {
		        	}
	        	} else {
	        		System.out.println("Sorry File not found!!!!");
	        	}

			}
			if(fileType.equals("xls")){
				xlsExporter.setExporterInput(new SimpleExporterInput(print));
	            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
	            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
	            configuration.setOnePagePerSheet(true);
	            configuration.setDetectCellType(true);
	            configuration.setCollapseRowSpan(false);
	            xlsExporter.setConfiguration((XlsxReportConfiguration) configuration);
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
	 @RequestMapping("/wsolot/{fileName:.+}/{wsoId}")
	 public void downloader(@PathVariable Long wsoId, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession,@PathVariable("fileName") String fileName) throws JRException, IOException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 
		 DecimalFormat df = new DecimalFormat("###.##");
		 List<WsolotReport> listWsolot = new ArrayList<WsolotReport>();
		 List<LotInfo> lotdataList = lotInfoRepo.findLotByWsoId(wsoId);
		 logger.info("Records successfully retrieved from Lot Info table based on Wso Id.");
		 float ttllotWt = 0.0f;
		 Integer ttllotQty = 0;
		 Integer ttlPallets = 0;
	     for (LotInfo model : lotdataList){
	         WsolotReport  wso  = new WsolotReport();
	         wso.setWsoNo(model.getWsoInfo().getWsoNo());
	         wso.setLotNo(model.getLotNo());
	         wso.setDes(model.getDescription());
	         wso.setPallets(model.getWsoInfo().getTotalNoOfPallets());
	         wso.setLotQuantity(model.getLotQuantity());
	         wso.setLotWeight(String.format( "%.2f", model.getUnitGrossWeightInKg()));
	         ttllotWt += model.getUnitGrossWeightInKg();
	         ttllotQty += model.getLotQuantity();
	         ttlPallets += model.getWsoInfo().getTotalNoOfPallets();
	         wso.setTtllotWeight(String.format( "%.2f", ttllotWt));
	         wso.setTtllotQuantity(ttllotQty);
	         wso.setTtlpallets(ttlPallets);
	         listWsolot.add(wso);
	     }
	     
	     WSOInfo  dataList = wsoService.getWSOInfo(Boolean.FALSE, wsoId);
	     HashMap[] row = new HashMap[1];
		 HashMap obj  = new HashMap();;
		 obj.put("invoiceNo","ikash");
		 row[0]= obj;
		 
		 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
		 
		 JRBeanCollectionDataSource wsolotlistJRBean = new JRBeanCollectionDataSource(listWsolot);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("WsolotDataSource", wsolotlistJRBean);
	     BillingTest inoiceDetail  = new BillingTest();
	     //inoiceDetail.setInvoiceNo("vikash  hara");
	     String ref = dataList.getTallysheet().getRefDry().toString();
	     parameters.put("refDry", ref);
	     parameters.put("clientTitle", dataList.getTallysheet().getClientInfo().getClientTitle());
	     parameters.put("tallyNo",dataList.getTallysheet().getTallysheetNumber());
	     parameters.put("exVessel",dataList.getTallysheet().getExVessel());
	     parameters.put("tempRec",Double.valueOf(df.format(dataList.getTallysheet().getTempRecorded())));
	     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	     String d = dateFormat.format(dataList.getBillStartDate());
	     parameters.put("storageDate",d);
	   
	    // parameters.put("totalAmount",totalAmount);
	    
			String reportName = context.getRealPath("/WEB-INF/wsolotReport");
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
	    //Sending Email
	    MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		//helper.setFrom(simpleMailMessage.getFrom());
		helper.setTo(userEmail);
		helper.setSubject("WSO Lot Report");
		helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
		String home = System.getProperty("user.home");
		String nameOfFile = home+"\\Downloads\\" + fileName;
		System.out.println("File to be located:"+nameOfFile);
		
		FileSystemResource file1 = new FileSystemResource(nameOfFile);
		helper.addAttachment(file1.getFilename(), file1);
		mailSender.send(message);
		System.out.println("Email Sent to the Client");
	   } catch (IOException | MessagingException e) {
	   }
	  } else {
	   System.out.println("Sorry File not found!!!!");
	  }

	 }
	 
	 @RequestMapping("/deliverylist/{fileName:.+}/{dlId}")
	 public void deliverydownloader(@PathVariable Long dlId, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession,@PathVariable("fileName") String fileName) throws JRException, IOException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 
		 List<DeliverylistReport> listDeliverylist = new ArrayList<DeliverylistReport>();
		 
		 DeliveryList  dataList = deliveryListRepo.findById(dlId).get();
		 String dlNumber = dataList.getDlNo();
	     List<DeliveryList> lotdataList = deliveryListRepo.getAllLotByDlNo(dlNumber);
	     logger.info("Records successfully retrieved from Delivery List table based on DL NO.");
	     for (DeliveryList model : lotdataList){
	    	 DeliverylistReport  dl  = new DeliverylistReport();
	    	 dl.setWsoNo(model.getWsoInfo().getWsoNo());
	    	 dl.setLotNo(model.getLotInfo().getLotNo());
	    	 dl.setDes(model.getLotInfo().getDescription());
	    	 dl.setLotQuantity(model.getTotalQty());
	    	 
	         listDeliverylist.add(dl);
	     }
	     
	     JRBeanCollectionDataSource deliverylistJRBean = new JRBeanCollectionDataSource(listDeliverylist);

	     Map<String, Object> parameters = new HashMap<String, Object>();
	     parameters.put("DeliverylistDataSource", deliverylistJRBean);
	     //parameters.put("logo", ClassLoader.getSystemResource("../images/logo4.png").getPath());
	     
			String reportName = context.getRealPath("/WEB-INF/deliverylistReport");
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
	    //Sending Email
	    MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		//helper.setFrom(simpleMailMessage.getFrom());
		helper.setTo(userEmail);
		helper.setSubject("Delivery List Report");
		helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
		String home = System.getProperty("user.home");
		String nameOfFile = home+"\\Downloads\\" + fileName;
		System.out.println("File to be located:"+nameOfFile);
		
		FileSystemResource file1 = new FileSystemResource(nameOfFile);
		helper.addAttachment(file1.getFilename(), file1);
		mailSender.send(message);
		System.out.println("Email Sent to the Client");
	   } catch (IOException | MessagingException e) {
	   }
	  } else {
	   System.out.println("Sorry File not found!!!!");
	  }

	 }
	 
	 @RequestMapping("/tallysheet/{fileName:.+}/{tallysheetId}")
	 public void tallysheetdownloader(@PathVariable Long tallysheetId, HttpServletRequest request, HttpServletResponse response,HttpSession httpSession,@PathVariable("fileName") String fileName) throws JRException, IOException {
		 httpSession = request.getSession();
		 String userEmail = (String) httpSession.getAttribute("emailId");
		 String userName = (String) httpSession.getAttribute("userId");
		 
		 List<TallysheetReport> listTally = new ArrayList<TallysheetReport>();
		 DecimalFormat df = new DecimalFormat("###.##");
		 
		 Float ttlWsoQuantity = 0.0f;
	     List<LotInfo> lotDataList = null;
		 List<WSOInfo> wsodataList = wsoInfoRepo.findWsoByTallysheet(tallysheetId);
	     logger.info("Records successfully retrieved from Wso Info table based on tallysheet Id.");
		 float ttllotWt = 0.0f;
		 float ttllotQty = 0.0f;
		 Integer ttlPallets = 0;
		 for (WSOInfo model : wsodataList){
	         TallysheetReport  tally  = new TallysheetReport();
	         
	         lotDataList = lotInfoRepo.findLotByWsoId(model.getWsoId());
	         logger.info("Records successfully retrieved from WSO Info table based on Wso Id.");
	         for(LotInfo modelLot : lotDataList) {
	         	ttlWsoQuantity += modelLot.getLotQuantity();
	         	ttllotQty += modelLot.getLotQuantity();
	         }
	         tally.setLotQuantity(String.format( "%.2f", ttlWsoQuantity));
	         ttlWsoQuantity = 0.0f;
	         tally.setWsoNo(model.getWsoNo());
	         tally.setDes(model.getDescription());
	         tally.setLotWeight(String.format( "%.2f", model.getTotalWsoWeight()));
	         tally.setPallets(model.getTotalNoOfPallets());
	         ttllotWt += model.getTotalWsoWeight();
	         ttlPallets += model.getTotalNoOfPallets();
	         tally.setTtllotWeight(String.format( "%.2f", ttllotWt));
	         tally.setTtlpallets(ttlPallets);
	         tally.setTtllotQuantity(String.format( "%.2f", ttllotQty));
	         tally.setStorage(model.getStorageClass().getStorageTypeName());
	         tally.setRemarks(model.getRemarks());
	         listTally.add(tally);
	     }
		 TallySheet  dataList = tallysheetRepo.findById(tallysheetId).get();
		 logger.info("Records successfully retrieved from tallysheet table based on tallysheet Id");
		 HashMap[] row = new HashMap[1];
		 HashMap obj  = new HashMap();;
		 obj.put("invoiceNo","ikash");
		 row[0]= obj;
		 
		 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
		 
		 JRBeanCollectionDataSource tallysheetJRBean = new JRBeanCollectionDataSource(listTally);
	     Map<String, Object> parameters = new HashMap<String, Object>();
	     
	     parameters.put("TallysheetDataSource", tallysheetJRBean);
	     BillingTest inoiceDetail  = new BillingTest();
	     //inoiceDetail.setInvoiceNo("vikash  hara");
	     parameters.put("tallyNo", dataList.getTallysheetNumber());
	     parameters.put("clientTitle", dataList.getClientInfo().getClientTitle());
	     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	     String d = dateFormat.format(dataList.getStorageDate());
	     parameters.put("storageDate",d);
	     parameters.put("exVessel",dataList.getExVessel());
	     parameters.put("unstuffTemp",Double.valueOf(df.format(dataList.getTempUnstuddUnload())));
	     parameters.put("tempRec",Double.valueOf(df.format(dataList.getTempRecorded())));
	    // parameters.put("totalAmount",totalAmount);
	          
			String reportName = context.getRealPath("/WEB-INF/tallysheetReport");
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
	    //Sending Email
	    MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		//helper.setFrom(simpleMailMessage.getFrom());
		helper.setTo(userEmail);
		helper.setSubject("Tallysheet Report");
		helper.setText("Dear "+userName+",\n\nPlease find Report as an attachment.\n\nThanking you,\n\nYours,\n\nFor Genesis Cold Storage.\n\n\n\n\nNote:This is an auto generated mail. Please do not reply to this mail.");
		String home = System.getProperty("user.home");
		String nameOfFile = home+"\\Downloads\\" + fileName;
		System.out.println("File to be located:"+nameOfFile);
		
		FileSystemResource file1 = new FileSystemResource(nameOfFile);
		helper.addAttachment(file1.getFilename(), file1);
		mailSender.send(message);
		System.out.println("Email Sent to the Client");
	   } catch (IOException | MessagingException e) {
	   }
	  } else {
	   System.out.println("Sorry File not found!!!!");
	  }

	 }
}

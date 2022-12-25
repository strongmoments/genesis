package com.genesis.genesisapi.controller;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.BillingTest;
import com.genesis.genesisapi.model.CommonBilling;
import com.genesis.genesisapi.model.Item;
import com.genesis.genesisapi.model.OtherChargesBilling;
import com.genesis.genesisapi.model.leaseBilling;
import com.genesis.genesisapi.repository.BillingRepo;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download")
public class PrintPdfController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
 @Autowired
 ServletContext context;
 
 @Autowired
 private BillingRepo billingRepo;

 @RequestMapping("/common/{fileName:.+}/{fileType}/{invoiceNo}")
 public void downloader(HttpServletRequest request, HttpServletResponse response,
		 @PathVariable("fileName") String fileName, 
		 @PathVariable("fileType") String fileType, 
		 @PathVariable("invoiceNo") String invoiceNo) throws JRException, IOException {
	 logger.info("common downloader() called.");
	 JRXlsxExporter xlsExporter = new JRXlsxExporter();
	// JRXlsxExporter xlsExporter = new JRXlsxExporter();
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
			 common.setSubTotal(String.format( "%.2f", model.getNetAmount() - model.getGst() ));
			 totalAmount += model.getNetAmount();
			 common.setTtlAmount(String.format( "%.2f", totalAmount ));
		 }else {
			 common.setDes("Handling Charge");
			 common.setWeight("Pallets: "+ model.getWsoInfo().getTotalNoOfPallets());
			 common.setRate( String.format( "%.2f", model.getBillingRate()) );
			 common.setGst( String.format( "%.2f", model.getGst()));
			 common.setAmount(String.format( "%.2f", model.getNetAmount()));
			 common.setSubTotal(String.format( "%.2f", model.getNetAmount() - model.getGst() ));
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
	        	} catch (IOException e) {
	        	}
        	} else {
        		System.out.println("Sorry File not found!!!!");
        	}

		}
		if(fileType.equals("xls")){
			xlsExporter.setExporterInput(new SimpleExporterInput(print));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(reportName+".xls")));
           // SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			//XlsxReportConfiguration configuration = new XlsxReportConfiguration();
			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
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
 
 @RequestMapping("/lease/{fileName:.+}/{fileType}/{invoiceNo}")
 public void leasedownloader(HttpServletRequest request, HttpServletResponse response,
		 @PathVariable("fileName") String fileName, 
		 @PathVariable("fileType") String fileType, 
		 @PathVariable("invoiceNo") String invoiceNo) throws JRException, IOException, ParseException {
	 List<leaseBilling> listLease = new ArrayList<leaseBilling>();
	 //String invoiceNo = "I0002";
	 JRXlsxExporter xlsExporter = new JRXlsxExporter();
	 List<Billing> data = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
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
 
 @RequestMapping("/other/{fileName:.+}/{fileType}/{invoiceNo}")
 public void otherdownloader(HttpServletRequest request, HttpServletResponse response,
		 @PathVariable("fileName") String fileName, 
		 @PathVariable("fileType") String fileType, 
		 @PathVariable("invoiceNo") String invoiceNo) throws JRException, IOException {
	 List<OtherChargesBilling> listOther = new ArrayList<OtherChargesBilling>();
	 JRXlsxExporter xlsExporter = new JRXlsxExporter();
	 List<Billing> data = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
	 logger.info("Records successfully retrieved from Billing table based on Invoice No");
	 
	 Double totalAmount = 0.0;
	 for(Billing model : data) {
	 	OtherChargesBilling oth = new OtherChargesBilling();
		 oth.setChargeItem(URLDecoder.decode(model.getNaration(), "UTF-8"));
		 oth.setBillableUnit(String.format( "%.2f",model.getBillingQuantity()));
		 oth.setRate(String.format( "%.2f",model.getBillingRate()));
		 oth.setSubTotal(String.format( "%.2f",model.getBillingQuantity()*model.getBillingRate()));
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
 
}
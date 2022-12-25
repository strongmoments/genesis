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

import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
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
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.CommonBilling;
import com.genesis.genesisapi.model.CustListReport;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.DeliverylistReport;
import com.genesis.genesisapi.model.Item;
import com.genesis.genesisapi.model.LotByClientReport;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OtherChargesBilling;
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
import com.genesis.genesisapi.repository.OtherChargesRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download")
public class ReportPdf3Controller {
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
    private OtherChargesRepo otherChargesRepo;
	
	@GetMapping("/getCustomerlist/{fileName:.+}/{fileType}/{fromdate}/{todate}")
    public void getCustomerlist(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("fileName") String fileName,
    		@PathVariable("fileType") String fileType,
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
		JRXlsxExporter xlsExporter = new JRXlsxExporter();
    	System.out.println("fromDate"+fromDate+" toDate"+toDate);

    	String storageType = "";
    	float  othersChrg = 0.0f;
    	double totalRevenue = 0.0;
    	double gst = 0.0;
    	double totalBilableAmtRT = 0.0;
    	double totalBilableAmtFBA = 0.0;
    	double totalShowGst = 0.0;
    	double totalOthersChrg = 0.0;
    	double totalTotalBilableAmtFBA = 0.0;
    	double totalTotalBilableAmtRT = 0.0;
    	double totalTotalRevenue = 0.0;
    	double netAmount = 0.0;
    	double totalStorage = 0.0;
    	double others=0.0;
    	double totalBilableAmt = 0.0;
    	List<ClientInfo> clientList =  clientInfoRepo.findAll();
    	
    	List<CustListReport> custListReportList = new ArrayList<CustListReport>();
    	
    	for (ClientInfo c: clientList) {
    		List<Object> data =  billingRepo.getBillingByCLientIdAndBetweenDates(c.getClientInfoId(), fromDate, toDate);
    		logger.info("Records successfully retrieved from Billing table based on Client Id and Between Dates");
    		
    		System.out.println("data size is :"+data.size());
    		for (int i = 0; i < data.size(); i++) {
    			Object[] object = (Object[]) data.get(i);
    			System.out.println("Handling Charge:"+(Float) object[0]);
    			othersChrg += (Float) object[0];
        		System.out.println("Net Amount:"+(Double) object[1]);
        		netAmount += (Double) object[1];
    		}
    		

    		if(otherChargesRepo.getBilableAmt(c.getClientInfoId(), fromDate, toDate) != null) {
				totalBilableAmt = otherChargesRepo.getBilableAmt(c.getClientInfoId(), fromDate, toDate);
				logger.info("Records successfully retrieved from Other Charges table based on ClientInfoId and between Dates");
				if(totalBilableAmt != 0.0) {
					System.out.println("totalBilableAmt:"+totalBilableAmt);
					//handOthersChrg += totalBilableAmt;
				}
			}
    		if(otherChargesRepo.getBilableAmtRT(c.getClientInfoId(), fromDate, toDate) != null) {
				totalBilableAmtRT = otherChargesRepo.getBilableAmtRT(c.getClientInfoId(), fromDate, toDate);
				logger.info("Records successfully retrieved from Other Charges table based on ClientInfoId and between Dates");
				if(totalBilableAmtRT != 0.0) {
					System.out.println("totalBilableAmtRT:"+totalBilableAmtRT);
				}
				
			}
    		if(otherChargesRepo.getBilableAmtFBA(c.getClientInfoId(), fromDate, toDate) != null) {
				totalBilableAmtFBA = otherChargesRepo.getBilableAmtFBA(c.getClientInfoId(), fromDate, toDate);
				logger.info("Records successfully retrieved from Other Charges table based on ClientInfoId and between Dates");
				if(totalBilableAmtFBA != 0.0) {
					System.out.println("totalBilableAmtFBA:"+totalBilableAmtFBA);
				}
			}
    		List<Object> data2 =  billingRepo.getStorageType(c.getClientInfoId(), fromDate, toDate);
    		logger.info("Records successfully retrieved from Billing table based on ClientInfoId and between Dates");
    		if(!data2.isEmpty()) {
    			System.out.println("data size is :"+data2.size());
        		for (int i = 0; i < data2.size(); i++) {
        			Object[] object = (Object[]) data2.get(i);
        			System.out.println("Storage Type:"+(String) object[0]);
        		}
        		Object[] object = (Object[]) data2.get(0);
        		storageType = (String) object[0];
    		}
    		
    		List<Object> data3 = billingRepo.getBillingInfoByCLientIdAndBetweenDates(c.getClientInfoId(), fromDate, toDate);
    		logger.info("Records successfully retrieved from Billing table based on ClientInfoId and between Dates");
        	for (int i = 0; i < data3.size(); i++) {
    			Object[] object = (Object[]) data3.get(i);
    			String invNo = (String) object[0];
    			Date invDate = (Date) object[1];
    			Double subTotal = (Double) object[2];
    			gst = (Double) object[3];
    			totalRevenue += (Double) object[4];
    		}
        	float gstRate = wsoInfoRepo.findAll().get(0).getGst();
        	
        	double storageGst = 0.0;
        	double otherGst = 0.0;
        	double fbaGst = 0.0;
        	double rtGst = 0.0;
        	double totalGst = 0.0;
        	double gstFigure = 0.01 * gstRate;
        	
    		others = totalBilableAmt - totalBilableAmtFBA - totalBilableAmtRT;
    		//totalRevenue = others + totalBilableAmtFBA + totalBilableAmtRT + netAmount;
    		
    		double expectedTotRev = (totalRevenue * 100)/107;
        	double storage = expectedTotRev - (others+totalBilableAmtFBA+totalBilableAmtRT);
        	double totRev = storage + others + totalBilableAmtFBA + totalBilableAmtRT;
        	logger.info("ExpectedToTRev:"+expectedTotRev+"storage:"+storage+"totRev:"+totRev);
        	otherGst = others * gstFigure;
        	fbaGst = totalBilableAmtFBA * gstFigure;
        	rtGst = totalBilableAmtRT * gstFigure;
			//if (storage < 0) storage = 0;
        	storageGst = storage * gstFigure;
        	totalGst = storageGst + otherGst + fbaGst + rtGst;
        	
    		CustListReport custListReport = new CustListReport();
    		custListReport.setClientTitle(c.getClientTitle());
    		//custListReport.setStorageType(String.format("%.2f",totalRevenue - (others+totalBilableAmtFBA+totalBilableAmtRT)));
    		custListReport.setStorageType(String.format("%.2f",storage));
    		custListReport.setHandOthersChrg(String.format("%.2f", others));
    		custListReport.setTotalBilableAmtFBA(String.format("%.2f", totalBilableAmtFBA));
    		custListReport.setTotalBilableAmtRT(String.format("%.2f", totalBilableAmtRT));	
    		
    		double showGst = 0.00;
			showGst = totalGst;
			
			custListReport.setGst(String.format("%.2f", showGst));
    		custListReport.setTotalRevenue(String.format("%.2f", totRev));
    		custListReportList.add(custListReport);
			
    		totalOthersChrg += others;
	    	totalTotalBilableAmtFBA += totalBilableAmtFBA;
	    	totalTotalBilableAmtRT += totalBilableAmtRT;
	    	totalTotalRevenue += totRev;
	    	//totalTotalRevenue += totalRevenue;
	    	totalStorage += storage;
	    	totalShowGst += showGst;
	    	//totalStorage += (totalRevenue - (others+totalBilableAmtFBA+totalBilableAmtRT));
			storageType = "";
			othersChrg = 0.0f;
			totalRevenue = 0.0;
			totalBilableAmtRT = 0.0;
	    	totalBilableAmtFBA = 0.0;	
	    	totalBilableAmt = 0.0;
	    	others = 0.0;
	    	netAmount = 0.0;
		}
    	
     HashMap[] row = new HashMap[1];
   	 HashMap obj  = new HashMap();;
   	 obj.put("invoiceNo","ikash");
   	 row[0]= obj;
   	 
   	 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
   	 
   	 JRBeanCollectionDataSource customerListJRBean = new JRBeanCollectionDataSource(custListReportList);
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("CustomerListDataSource", customerListJRBean);
        parameters.put("fromDate", new SimpleDateFormat("dd-MM-yyyy").format(fromDate));
        parameters.put("toDate", new SimpleDateFormat("dd-MM-yyyy").format(toDate));
        parameters.put("totalStorage", String.format("%.2f", totalStorage));
        parameters.put("totalHandOthersChrg", String.format("%.2f", totalOthersChrg));
        parameters.put("totalTotalBilableAmtFBA", String.format("%.2f", totalTotalBilableAmtFBA));
        parameters.put("totalTotalBilableAmtRT", String.format("%.2f", totalTotalBilableAmtRT));
        parameters.put("totalTotalRevenue", String.format("%.2f", totalTotalRevenue));
        parameters.put("totalShowGst", String.format("%.2f", totalShowGst));
   		String reportName = context.getRealPath("/WEB-INF/customerlist");
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
	
	
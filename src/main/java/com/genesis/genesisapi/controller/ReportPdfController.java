package com.genesis.genesisapi.controller;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import net.sf.jasperreports.export.XlsxReportConfiguration;
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
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.DeliverylistReport;
import com.genesis.genesisapi.model.Item;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download")
public class ReportPdfController {
 private final Logger logger = LoggerFactory.getLogger(this.getClass());
 @Autowired
 ServletContext context;
 
 @Autowired
 private BillingRepo billingRepo;
 
 @Autowired
 private WSOInfoRepo wsoInfoRepo;

 @Autowired
 private LotInfoRepo lotInfoRepo;
 
 @Autowired
 private DeliveryListRepo deliveryListRepo;
 
 @Autowired
 private TallysheetRepo tallysheetRepo;
 
 @Autowired
 private WSOService wsoService; 
 @RequestMapping("/wsolot/{fileName:.+}/{fileType}/{wsoId}")
 public void downloader(@PathVariable Long wsoId, HttpServletRequest request, HttpServletResponse response,
		 @PathVariable("fileName") String fileName,
		 @PathVariable("fileType") String fileType) throws JRException, IOException {
	 JasperPrint p1 = new JasperPrint();
	 DecimalFormat df = new DecimalFormat("###.##");
	 JRXlsxExporter xlsExporter = new JRXlsxExporter();
	 List<WsolotReport> listWsolot = new ArrayList<WsolotReport>();
	 List<LotInfo> lotdataList = lotInfoRepo.findLotByWsoId(wsoId);
	 logger.info("Records successfully retrieved from Lot Info table based on WSO Id.");
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
         wso.setLotWeight(String.format( "%.0f", model.getUnitGrossWeightInKg()));
         ttllotWt += model.getUnitGrossWeightInKg();
         ttllotQty += model.getLotQuantity();
         ttlPallets += model.getWsoInfo().getTotalNoOfPallets();
         wso.setTtllotWeight(String.format( "%.0f", ttllotWt));
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
            xlsExporter.setConfiguration((XlsxReportConfiguration) configuration);
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
 
 @RequestMapping("/deliverylist/{fileName:.+}/{fileType}/{dlId}")
 public void deliverydownloader(@PathVariable Long dlId, HttpServletRequest request, HttpServletResponse response,
		 @PathVariable("fileName") String fileName,
		 @PathVariable("fileType") String fileType) throws JRException, IOException {
	 List<DeliverylistReport> listDeliverylist = new ArrayList<DeliverylistReport>();
	 DecimalFormat df = new DecimalFormat("###.##");
	 JasperPrint p1 = new JasperPrint();
	 JRXlsxExporter xlsExporter = new JRXlsxExporter();
	 DeliveryList  dataList = deliveryListRepo.findById(dlId).get();
	 logger.info("Records successfully retrieved from Delivery List based on dlId");
	 
	 String dlNumber = dataList.getDlNo();
     List<DeliveryList> lotdataList = deliveryListRepo.getAllLotByDlNo(dlNumber);
     logger.info("Records successfully retrieved from Delivery List table based on DL NO");
     Integer ttllotQty = 0;
     for (DeliveryList model : lotdataList){
    	 DeliverylistReport  dl  = new DeliverylistReport();
    	 dl.setWsoNo(model.getWsoInfo().getWsoNo());
    	 dl.setLotNo(model.getLotInfo().getLotNo());
    	 dl.setDes(model.getLotInfo().getDescription());
    	 dl.setLotQuantity(model.getTotalQty());
    	 ttllotQty += model.getTotalQty();
    	 dl.setTtllotQuantity(ttllotQty);
         listDeliverylist.add(dl);
     }
     
     HashMap[] row = new HashMap[1];
	 HashMap obj  = new HashMap();;
	 obj.put("invoiceNo","ikash");
	 row[0]= obj;
	 
	 JRMapArrayDataSource dataSource = new JRMapArrayDataSource(row);
	 
	 JRBeanCollectionDataSource deliverylistJRBean = new JRBeanCollectionDataSource(listDeliverylist);
     Map<String, Object> parameters = new HashMap<String, Object>();
     
     parameters.put("DeliverylistDataSource", deliverylistJRBean);
     BillingTest inoiceDetail  = new BillingTest();
     //inoiceDetail.setInvoiceNo("vikash  hara");
     DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
     String d = dateFormat.format(dataList.getDateOfDelivery());
     parameters.put("deliveryDate", d);
     parameters.put("clientTitle", dataList.getClientInfo().getClientTitle());
     parameters.put("dlNo",dataList.getDlNo());
     parameters.put("vehicleNo",dataList.getVehicleNo());
     parameters.put("nricNo",dataList.getNricOfReceiver());
     parameters.put("nameOfReceiver",dataList.getNameOfReceiver());
     parameters.put("printedtime",new SimpleDateFormat("HH:mm:ss").format(new Date()));
     DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
     String t = dateFormat1.format(dataList.getTimeOfIssue());
     parameters.put("timeOfIssue",t);
    // parameters.put("totalAmount",totalAmount);
     
		String reportName = context.getRealPath("/WEB-INF/deliverylistReport");
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
            xlsExporter.setConfiguration((XlsxReportConfiguration) configuration);
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
 
 @RequestMapping("/tallysheet/{fileName:.+}/{fileType}/{tallysheetId}")
 public void tallysheetdownloader(@PathVariable Long tallysheetId, HttpServletRequest request, HttpServletResponse response,
		 @PathVariable("fileName") String fileName,
		 @PathVariable("fileType") String fileType) throws JRException, IOException {
	 List<TallysheetReport> listTally = new ArrayList<TallysheetReport>();
	 DecimalFormat df = new DecimalFormat("###.##");
	 JRXlsxExporter xlsExporter = new JRXlsxExporter();
	 JasperPrint p1 = new JasperPrint();
	 Float ttlWsoQuantity = 0.0f;
     List<LotInfo> lotDataList = null;
	 List<WSOInfo> wsodataList = wsoInfoRepo.findWsoByTallysheet(tallysheetId);
     logger.info("Records successfully retrieved from Wso Info table based on Tallysheet Id.");
	 float ttllotWt = 0.0f;
	 float ttllotQty = 0.0f;
	 Integer ttlPallets = 0;
	 for (WSOInfo model : wsodataList){
         TallysheetReport  tally  = new TallysheetReport();
         
         lotDataList = lotInfoRepo.findLotByWsoId(model.getWsoId());
         logger.info("Records successfully retrieved from Lot Info table based on WSO Id");
         for(LotInfo modelLot : lotDataList) {
         	ttlWsoQuantity += modelLot.getLotQuantity();
         	ttllotQty += modelLot.getLotQuantity();
         }
         tally.setLotQuantity(String.format( "%.0f", ttlWsoQuantity));
         ttlWsoQuantity = 0.0f;
         tally.setWsoNo(model.getWsoNo());
         tally.setDes(model.getDescription());
         tally.setLotWeight(String.format( "%.0f", model.getTotalWsoWeight()));
         tally.setPallets(model.getTotalNoOfPallets());
         ttllotWt += model.getTotalWsoWeight();
         ttlPallets += model.getTotalNoOfPallets();
         tally.setTtllotWeight(String.format( "%.0f", ttllotWt));
         tally.setTtlpallets(ttlPallets);
         tally.setTtllotQuantity(String.format( "%.0f", ttllotQty));
         tally.setStorage(model.getStorageClass().getStorageTypeName());
         tally.setRemarks(model.getRemarks());
         listTally.add(tally);
     }
	 TallySheet  dataList = tallysheetRepo.findById(tallysheetId).get();
	 logger.info("Records successfully retrieved from Tallysheet table based on tallysheet Id");
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
     parameters.put("refDry", dataList.getRefDry().toString());
     parameters.put("lorryContainer", dataList.getLorryContainer().toString());
     parameters.put("exVessel",dataList.getExVessel());
     parameters.put("unstuffTemp",Double.valueOf(df.format(dataList.getTempUnstuddUnload())));
     parameters.put("tempRec",Double.valueOf(df.format(dataList.getTempRecorded())));
     parameters.put("containerNo", dataList.getContainerNo());
    // parameters.put("totalAmount",totalAmount);
          
		String reportName = context.getRealPath("/WEB-INF/tallysheetReport");
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
            xlsExporter.setConfiguration((XlsxReportConfiguration) configuration);
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
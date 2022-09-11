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

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
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
import com.genesis.genesisapi.repository.ReportsRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.repository.WarehouseInfoRepo;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download")
public class WSOBillReportPOI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WSOService wsoService;

    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    
    @Autowired
    private DeliveryListRepo deliveryListRepo;
    
    @Autowired
    private TallysheetRepo tallysheetRepo;
    @Autowired
    private ReportsRepo reportsRepo;
    
    @Autowired
    private BillingRepo billingRepo;
    
    @Autowired
    private WarehouseInfoRepo warehouseInfoRepo;
    
    @Autowired
    private LotInfoRepo lotInfoRepo;
    
    @Autowired
    private ClientInfoRepo clientInfoRepo;
    
	@GetMapping("/loadAllWsoBill/{fileName:.+}/{fileType}/{clientId}/{wsoNo}")
    public ResponseEntity<byte[]> getLotDetailByClientId(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("fileName") String fileName,
    		@PathVariable("fileType") String fileType,
    		@PathVariable("clientId") Long clientId,
    		@PathVariable("wsoNo") String wsoNo)throws JRException, IOException, ParseException {
		Boolean printCheck = true;
		
		String reportName = null;
		Boolean check = true;
		
    	System.out.println("clientId"+clientId);
    	String clientName = clientInfoRepo.findByCientId(clientId).getClientTitle();
    	String currDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	
    	String[] columns = {"BILLING PERIOD", "PACKAGES BAL", "WEIGHT BAL", "AMOUNT BILLED($$)", "INVOICE NO.", "INVOICE DATE", "GST AMOUNT($$)", "RATES($$)"};
    	String[] columns2 = {"DL NO.", "DEL DATE", "PKGS OUT", "WT DEL", "BAL PKGS", "BAL WT."};
    	// Create a Workbook
        //Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file
    	HSSFWorkbook workbook = new HSSFWorkbook();
        /* CreationHelper helps us create instances for various things like DataFormat,
           Hyperlink, RichTextString etc in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        //Sheet sheet = workbook.createSheet("Stock Balance");
        HSSFSheet sheet = workbook.createSheet("MyBanner");
         // read the image to the stream
                
        /* Create a Workbook and Worksheet */
        //HSSFWorkbook my_workbook = new HSSFWorkbook();
        //HSSFSheet my_sheet = my_workbook.createSheet("MyBanner");               
        /* Read the input image into InputStream */
        File file = new File("");
        file = ResourceUtils.getFile("classpath:static\\image\\logo4.png");
        // For Mac OS : 
        //file = ResourceUtils.getFile("classpath:static/image/logo4.png");
        System.out.println("file: "+file.getAbsolutePath());
        
        //InputStream my_banner_image = new FileInputStream("classpath:static\\image\\logo4.png");
        InputStream my_banner_image = new FileInputStream(file.getAbsolutePath());
        /* Convert Image to byte array */
        byte[] bytes = IOUtils.toByteArray(my_banner_image);
        /* Add Picture to workbook and get a index for the picture */
        int my_picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        /* Close Input Stream */
        my_banner_image.close();                
        /* Create the drawing container */
        Drawing drawing = sheet.createDrawingPatriarch();
        /* Create an anchor point */
        ClientAnchor my_anchor = new HSSFClientAnchor();
        /* Define top left corner, and we can resize picture suitable from there */
        my_anchor.setCol1(0);
        my_anchor.setRow1(0);  
        my_anchor.setCol2(5);
        my_anchor.setRow2(9);
        /* Invoke createPicture and pass the anchor point and ID */
        Picture  my_picture = drawing.createPicture(my_anchor, my_picture_id);
        /* Call resize method, which resizes the image */
        double scale = 1;
        my_picture.resize(scale);     
        
        
        // Create a Font for styling header cells
        Font topHeaderFont = workbook.createFont();
        //topHeaderFont.setBold(true);
        topHeaderFont.setFontHeightInPoints((short) 17);
        topHeaderFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
        
        CellStyle topCellStyle = workbook.createCellStyle();
        //topCellStyle.setAlignment(HorizontalAlignment.CENTER);
        topCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        topCellStyle.setFont(topHeaderFont);
        
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 13);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        headerCellStyle.setFont(headerFont);
        
        CellStyle normalCellStyle = workbook.createCellStyle();
        normalCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        
        // Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        dateCellStyle.setAlignment(dateCellStyle.ALIGN_LEFT);
        
        Row topHeaderRow = sheet.createRow(10);
        Cell topCell = topHeaderRow.createCell(1);
        topCell.setCellValue("WSO Billing Report");
        topCell.setCellStyle(topCellStyle);
        
        Row dateRow = sheet.createRow(11);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Date :");
        dateCell.setCellStyle(headerCellStyle);
        
        Cell dateCell1 = dateRow.createCell(1);
        dateCell1.setCellValue(currDate);
        dateCell1.setCellStyle(dateCellStyle);
        
        Row clientNameRow = sheet.createRow(12);
        Cell clientNameCell = clientNameRow.createCell(0);
        clientNameCell.setCellValue("Client Name : ");
        clientNameCell.setCellStyle(headerCellStyle);
        
        Cell clientNameCell1 = clientNameRow.createCell(1);
        clientNameCell1.setCellValue(clientName);
        clientNameCell1.setCellStyle(normalCellStyle);
        
        Row wsoNoRow = sheet.createRow(13);
        Cell wsoNoCell = wsoNoRow.createCell(0);
        wsoNoCell.setCellValue("Wso No : ");
        wsoNoCell.setCellStyle(headerCellStyle);
        
        Cell wsoNoCell1 = wsoNoRow.createCell(1);
        wsoNoCell1.setCellValue(wsoNo);
        wsoNoCell1.setCellStyle(normalCellStyle);
        
        // Create Other rows and cells with employees data
        int rowNum = 16;

        // Write the output to a file
        
    	JSONArray list = new  JSONArray();
    	WSOInfo wsoInfo = wsoInfoRepo.findWsoInfoByWsoNo(wsoNo);
    	int totQty = 0;
    	int pkgBal = 0;

    	if (wsoInfo != null) {
    		
    		
    		//JSON Object
    		JSONObject obj = new JSONObject();
    		obj.put("wsoNo", wsoInfo.getWsoNo());
    		obj.put("storageDate", wsoInfo.getTallysheet().getStorageDate());
    		obj.put("storageClass", wsoInfo.getStorageClass().getStorageTypeName());
    		obj.put("wsoWt", wsoInfo.getTotalWsoWeight());
    		obj.put("totPallets", wsoInfo.getTotalNoOfPallets());
    		obj.put("wsoTotQty", lotInfoRepo.findTotQtyLotByWsoId(wsoInfo.getWsoId()));
    		
    		list.add(obj);
    		
    		//Excel Sheet First Part
    		Row row = sheet.createRow(rowNum++);
        	
    		row.createCell(0).setCellValue("Storage Date :");
    		row.createCell(1).setCellValue(wsoInfo.getTallysheet().getStorageDate());
    		row.createCell(2).setCellValue("Storage Class :");
    		row.createCell(3).setCellValue(wsoInfo.getStorageClass().getStorageTypeName());
    		row.createCell(4).setCellValue("WSO Weight : ");
    		row.createCell(5).setCellValue(wsoInfo.getTotalWsoWeight());
    		
    		row.getCell(1).setCellStyle(dateCellStyle);//Set the style for Storage Date
    		
    		for(int i = 0; i < 5; i=i+2){//For each cell in the row 
    	        row.getCell(i).setCellStyle(headerCellStyle);//Set the style
    	    }
    		
    		for(int i = 3; i < 6; i=i+2){//For each cell in the row 
    	        row.getCell(i).setCellStyle(normalCellStyle);//Set the style
    	    }
    		Row rowSecond = sheet.createRow(rowNum++);
    		rowSecond.createCell(2).setCellValue("WSO No :");
    		rowSecond.createCell(3).setCellValue(wsoInfo.getWsoNo());
    		rowSecond.createCell(4).setCellValue("WSO Quantity :");
    		rowSecond.createCell(5).setCellValue(lotInfoRepo.findTotQtyLotByWsoId(wsoInfo.getWsoId()));
    		rowSecond.createCell(6).setCellValue("Pallets :");
    		rowSecond.createCell(7).setCellValue(wsoInfo.getTotalNoOfPallets());
    		
    		for(int i = 2; i < 7; i=i+2){//For each cell in the row 
    			rowSecond.getCell(i).setCellStyle(headerCellStyle);//Set the style
    	    }
    		
    		for(int i = 3; i < 8; i=i+2){//For each cell in the row 
    			rowSecond.getCell(i).setCellStyle(normalCellStyle);//Set the style
    	    }
    		
    		Row rowThree = sheet.createRow(rowNum++);
    		
    		rowThree.createCell(2).setCellValue("Curr Qty : ");
    		rowThree.createCell(3).setCellValue(lotInfoRepo.findTotCurrQtyLotByWsoId(wsoInfo.getWsoId()));
    		rowThree.createCell(4).setCellValue("Curr Wt :");
    		rowThree.createCell(5).setCellValue(String.format("%.2f",lotInfoRepo.findTotCurrWtLotByWsoId(wsoInfo.getWsoId())));
    		
    		rowThree.getCell(2).setCellStyle(headerCellStyle);//Set the style
    		rowThree.getCell(4).setCellStyle(headerCellStyle);//Set the style
    		rowThree.getCell(3).setCellStyle(normalCellStyle);//Set the style
    		rowThree.getCell(5).setCellStyle(normalCellStyle);//Set the style
    		
    		// Create a Row
    		rowNum = rowNum + 1;
            Row headerRow = sheet.createRow(rowNum);
            
            // Creating cells
            for(int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }
            
            rowNum = rowNum + 1;
            
    		totQty = lotInfoRepo.findTotQtyLotByWsoId(wsoInfo.getWsoId());
    		logger.info("Bill details of WSO No "+wsoInfo.getWsoNo()+" Bill Details: \n");
    		List<Billing> billingList = billingRepo.getBillDetailByWsoId(wsoInfo.getWsoId());
    		Float TotalWsoWeight = wsoInfo.getTotalWsoWeight();
    		
    		if (!billingList.isEmpty()) {
    			/* billingList.forEach((temp) -> {
        			System.out.println(temp.toString());
        		}); */
    			
    			//Second way to iterate the list
    			JSONArray billList = new  JSONArray();
    			int i = 0;
        		while (i < billingList.size()) {
        			JSONObject obj1 = new JSONObject();
            		obj1.put("fromDate", billingList.get(i).getFromDate());
            		obj1.put("toDate", billingList.get(i).getToDate());
            		
        			pkgBal = totQty;
        			//This portion need to be checked for accuracy. Reason I have to get that for that billing period.
        			//Moreover I have to get Balace Weight also. Date Written: 22-11-2019
        			if (deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), billingList.get(i).getFromDate()) != null){
        				pkgBal = totQty - deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), billingList.get(i).getFromDate());
        			}
        			obj1.put("pkgBal", pkgBal);
        			System.out.println("Package balance: "+ pkgBal);
        			//To get Balance Weight of WSO till the billing date
        			
        			List<DeliveryList> delList = deliveryListRepo.getDeliveryByWsoId(wsoInfo.getWsoId());
            		
        			float BalWeight = TotalWsoWeight;
            		if(!delList.isEmpty()) {
            			int j = 0;
            			while (j < delList.size()) {
                			float wtDeliverd = delList.get(j).getLotInfo().getUnitNetWeightInKg() * delList.get(j).getTotalQty();
                			BalWeight = BalWeight - wtDeliverd;
            				j++;
            			}
            			obj1.put("balWeight", BalWeight);
            			logger.info("Balance Weight: "+ BalWeight);
            		}
            		else
            		{
            			//obj1.put("balWeight", 0.00f);
            			logger.info("Delivery list is empty for wso no: "+wsoInfo.getWsoNo());
            		}
            		obj1.put("billWt", billingList.get(i).getBillingWeight());
            		obj1.put("amtBilled", billingList.get(i).getNetAmount());
            		obj1.put("invNo", billingList.get(i).getInvoiceNo());
            		obj1.put("invDate", billingList.get(i).getInvoiceDate());
            		obj1.put("gst", billingList.get(i).getGst());
            		obj1.put("rate", billingList.get(i).getBillingRate());
        			System.out.println(billingList.get(i));
        			
        			// Excel Part 2
        			Row row1 = sheet.createRow(rowNum++);
        	            			
        	    	String date1 = new SimpleDateFormat("dd-MM-yyyy").format(billingList.get(i).getFromDate());
        	    	String date2 = new SimpleDateFormat("dd-MM-yyyy").format(billingList.get(i).getToDate());
        	    	String date3 = date1 + " - " + date2;
        	        row1.createCell(0).setCellValue(date3);

        	        row1.createCell(1)
        	                .setCellValue(pkgBal);
        	        
        	        row1.createCell(2)
        	        		.setCellValue(String.format("%.2f",billingList.get(i).getBillingWeight()));
        	        
        	        row1.createCell(3)
    						.setCellValue(String.format("%.2f",billingList.get(i).getNetAmount()));

        	        row1.createCell(4)
        	                .setCellValue(billingList.get(i).getInvoiceNo());
        	        
        	        row1.createCell(5)
        	        		.setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(billingList.get(i).getInvoiceDate())));

        	        row1.createCell(6)
        	        		.setCellValue(String.format("%.2f",billingList.get(i).getGst()));

        			row1.createCell(7)
        			        .setCellValue(String.format("%.2f",billingList.get(i).getBillingRate()));
        			
        			for(int j = 0; j < row1.getLastCellNum(); j++){//For each cell in the row 
        		        row1.getCell(j).setCellStyle(normalCellStyle);//Set the sty;e
        		    }
        	    	 
        			i++;
        			billList.add(obj1);
        		}
        		list.add(billList);
    		}
    		obj.put("currQty", lotInfoRepo.findTotCurrQtyLotByWsoId(wsoInfo.getWsoId()));
    		obj.put("currWt", lotInfoRepo.findTotCurrWtLotByWsoId(wsoInfo.getWsoId()));
    		logger.info("Wso Current Qty: "+lotInfoRepo.findTotCurrQtyLotByWsoId(wsoInfo.getWsoId()));
    		logger.info("WSO Current Wt.: "+lotInfoRepo.findTotCurrWtLotByWsoId(wsoInfo.getWsoId()));
    		
    		//Excel Sheet Third Part
    		rowNum++;
    		
    		// Create a Row
    		rowNum = rowNum + 1;
            Row headerRow2 = sheet.createRow(rowNum);
            
            // Creating cells
            for(int i = 0; i < columns2.length; i++) {
                Cell cell = headerRow2.createCell(i);
                cell.setCellValue(columns2[i]);
                cell.setCellStyle(headerCellStyle);
            }
            rowNum++;
            
    		List<DeliveryList> delList = deliveryListRepo.getDeliveryByWsoId(wsoInfo.getWsoId());
    		
    		Float BalanceWeight = TotalWsoWeight;
    		if(!delList.isEmpty()) {
    			JSONArray deliList = new  JSONArray();
    			int i = 0;
    			System.out.println("Delivery List of Wso No: "+wsoInfo.getWsoNo());
    			while (i < delList.size()) {
    				JSONObject obj2 = new JSONObject();
    				pkgBal = totQty;
        			if (deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), delList.get(i).getDateOfDelivery()) != null){
        				pkgBal = totQty - deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), delList.get(i).getDateOfDelivery());
        			}
        			System.out.println("Package balance: "+ pkgBal);
        			float wtDeliverd = delList.get(i).getLotInfo().getUnitNetWeightInKg() * delList.get(i).getTotalQty();
        			logger.info("Weight Delivered: "+wtDeliverd);
        			BalanceWeight = Math.abs(BalanceWeight - wtDeliverd);
        			logger.info("Balance Weight: "+ BalanceWeight);
        			
        			obj2.put("dlNo", delList.get(i).getDlNo());
        			obj2.put("dlDate", delList.get(i).getDateOfDelivery());
        			obj2.put("pkgOut", delList.get(i).getTotalQty());
        			obj2.put("wtDelivered", wtDeliverd);
        			obj2.put("balPkgs", pkgBal);
        			obj2.put("balWt", BalanceWeight);
        			
        			deliList.add(obj2);
        			
        			// Excel Part 4
        			Row row4 = sheet.createRow(rowNum++);
        	            			
        	        row4.createCell(0).setCellValue(delList.get(i).getDlNo());

        	        row4.createCell(1)
        	                .setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(delList.get(i).getDateOfDelivery()));
        	        
        	        row4.createCell(2)
        	        		.setCellValue(delList.get(i).getTotalQty());
        	        
        	        row4.createCell(3)
    						.setCellValue(String.format("%.2f",wtDeliverd));

        	        row4.createCell(4)
        	                .setCellValue(pkgBal);
        	        
        	        row4.createCell(5)
        	        		.setCellValue(String.format("%.2f", BalanceWeight));
        			
        			for(int j = 0; j < row4.getLastCellNum(); j++){//For each cell in the row 
        		        row4.getCell(j).setCellStyle(normalCellStyle);//Set the sty;e
        		    }
        			
    				System.out.println(delList.get(i));
    				i++;
    			}
    			list.add(deliList);
    		}
    		else
    		{
    			logger.info("Delivery list is empty for wso no: "+wsoInfo.getWsoNo());
    		}
    		
    	}
    	else
    		logger.info("Client Id:"+clientId+" and wsoNo: "+wsoNo+ " returned null");
    	
    	// Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    		
        ByteArrayOutputStream byteFile = new ByteArrayOutputStream();
        workbook.write(byteFile);
        if(null!=workbook && null!=byteFile) {
            workbook.close();
            byteFile.close();
        }
	    byte[] documentContent = byteFile.toByteArray();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	    headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+fileName);
	    headers.setContentLength(documentContent.length);
	    return new ResponseEntity<byte[]>(documentContent, headers, HttpStatus.OK);
	}
}

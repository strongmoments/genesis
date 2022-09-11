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
public class ReportExcelPOI {
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
    
	@GetMapping("/getLotDetailByClientIdPOI/{fileName:.+}/{fileType}/{clientId}/{date1}")
    public ResponseEntity<byte[]> getLotDetailByClientId(HttpServletRequest request, HttpServletResponse response,
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
		
    	System.out.println("clientId"+clientId);
    	String clientName = clientInfoRepo.findByCientId(clientId).getClientTitle();
    	String currDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    	
    	String[] columns = {"LOT NO", "LOT DESCRIPTION", "WSO NO", "STORAGE CLASS", "DATE", "INIT QTY", "CURR QTY", "PROD DATE", "EXP DATE", "UNIT (N)(KG)", "UNIT QTY/LOT"};
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
        my_anchor.setCol1(2);
        my_anchor.setRow1(2);  
        my_anchor.setCol2(8);
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
        //headerCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        headerCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        headerCellStyle.setFont(headerFont);
        
        CellStyle normalCellStyle = workbook.createCellStyle();
        //normalCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        normalCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        
        Row topHeaderRow = sheet.createRow(10);
        Cell topCell = topHeaderRow.createCell(5);
        topCell.setCellValue("Stock Balance Report");
        topCell.setCellStyle(topCellStyle);
        
        Row clientNameRow = sheet.createRow(11);
        Cell clientNameCell = clientNameRow.createCell(0);
        clientNameCell.setCellValue("Client Name:");
        clientNameCell.setCellStyle(headerCellStyle);
        
        Cell clientNameCell1 = clientNameRow.createCell(1);
        clientNameCell1.setCellValue(clientName);
        clientNameCell1.setCellStyle(normalCellStyle);
        
        Row dateRow = sheet.createRow(12);
        Cell dateCell = dateRow.createCell(0);
        dateCell.setCellValue("Date :");
        dateCell.setCellStyle(headerCellStyle);
        
        Cell dateCell1 = dateRow.createCell(1);
        dateCell1.setCellValue(currDate);
        dateCell1.setCellStyle(normalCellStyle);
        // Create a Row
        Row headerRow = sheet.createRow(14);
        
        // Creating cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 15;

        // Write the output to a file
        
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
    	logger.info("Records successfully retrieved from Lot Info table based on WSOId List");
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
    			List<DeliveryList> delListCheck = deliveryListRepo.getDeliveryByLotIdANDBetweenCurentAndGivenDate(l.getLotId(),date);
    			boolean check1 = false;
    			if(delList.isEmpty()) {
    				int qtyToAdd = 0;
    				if(!delListCheck.isEmpty())
	    			{
	    				qtyToAdd = deliveryListRepo.getSumOfDeliveryByLotIdANDBetweenCurentAndGivenDate(l.getLotId(),date);
	    				logger.info("Yes there are delivery list for this lot id: " + l.getLotId());
	    				check1 = true;
	    				logger.info("Value of check: "+check1);
	    			}
    				LotByClientReport lotByClientReport = new LotByClientReport();
    				
    				lotByClientReport.setLotDesc(l.getDescription());
            		lotByClientReport.setLotNo(l.getLotNo());
            		lotByClientReport.setWsoNo(l.getWsoInfo().getWsoNo());
            		lotByClientReport.setDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
            		lotByClientReport.setInitialQty(String.valueOf(l.getInitialQuantity()));
            		lotByClientReport.setCurrentQty(String.valueOf(l.getCurrentQuantity()+qtyToAdd));
            		lotByClientReport.setProdDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
            		lotByClientReport.setExpDate(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
            		lotByClientReport.setUnitKg(String.format("%.2f", l.getUnitWeightInKg()));
            		//lotByClientReport.setUnitQtyLot(String.format("%.2f", l.getUnitGrossWeightInKg()));
            		lotByClientReport.setUnitQtyLot(String.valueOf(l.getUnitQuantity()));
            		lotByClientReport.setStorageClass(l.getWsoInfo().getStorageClass().getStorageTypeName());
            		if(check1 == false) {
            			if(l.getCurrentQuantity()!=0) {
                			lotByClientList.add(lotByClientReport);
                			
                			Row row = sheet.createRow(rowNum++);
                			
                	        row.createCell(0).setCellValue(l.getLotNo());

                	        row.createCell(1)
                	                .setCellValue(l.getDescription());
                	        
                	        row.createCell(2)
                	        		.setCellValue(l.getWsoInfo().getWsoNo());
                	        
                	        row.createCell(3)
            						.setCellValue(l.getWsoInfo().getStorageClass().getStorageTypeName());

                	        row.createCell(4)
                	                .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
                	        
                	        row.createCell(5)
                	        		.setCellValue(String.valueOf(l.getInitialQuantity()));

                	        row.createCell(6)
                	        		.setCellValue(String.valueOf(l.getCurrentQuantity()));

                			row.createCell(7)
                			        .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
                			
                			row.createCell(8)
                	        		.setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
                			
                			row.createCell(9)
                	        		.setCellValue(String.format("%.2f", l.getUnitWeightInKg()));
                			
                			/*row.createCell(10)
                					.setCellValue(String.format("%.2f", l.getUnitGrossWeightInKg()));*/
                			row.createCell(10)
        							.setCellValue(String.valueOf(l.getUnitQuantity()));
                			
                			for(int i = 0; i < row.getLastCellNum(); i++){//For each cell in the row 
                		        row.getCell(i).setCellStyle(normalCellStyle);//Set the sty;e
                		    }
                			//totalinitialQty += l.getInitialQuantity();
                			//totalcurrentQty += l.getCurrentQuantity();
                			
                			
                		}
        			}else {
        				lotByClientList.add(lotByClientReport);
            			
            			Row row = sheet.createRow(rowNum++);
            			
            	        row.createCell(0).setCellValue(l.getLotNo());

            	        row.createCell(1)
            	                .setCellValue(l.getDescription());
            	        
            	        row.createCell(2)
            	        		.setCellValue(l.getWsoInfo().getWsoNo());
            	        
            	        row.createCell(3)
        						.setCellValue(l.getWsoInfo().getStorageClass().getStorageTypeName());

            	        row.createCell(4)
            	                .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
            	        
            	        row.createCell(5)
            	        		.setCellValue(String.valueOf(l.getInitialQuantity()));

            	        row.createCell(6)
            	        		.setCellValue(String.valueOf(l.getCurrentQuantity()));

            			row.createCell(7)
            			        .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
            			
            			row.createCell(8)
            	        		.setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
            			
            			row.createCell(9)
            	        		.setCellValue(String.format("%.2f", l.getUnitWeightInKg()));
            			
            			/*row.createCell(10)
            					.setCellValue(String.format("%.2f", l.getUnitGrossWeightInKg()));*/
            			row.createCell(10)
    							.setCellValue(String.valueOf(l.getUnitQuantity()));
            			
            			for(int i = 0; i < row.getLastCellNum(); i++){//For each cell in the row 
            		        row.getCell(i).setCellStyle(normalCellStyle);//Set the sty;e
            		    }
            			//totalinitialQty += l.getInitialQuantity();
            			//totalcurrentQty += l.getCurrentQuantity();
        			}
            		
    			}
    			System.out.println("delList: "+delList.toString());
    			int initQty = l.getInitialQuantity();
    			int qtyDelivered = 0;
    			
    			if(!delList.isEmpty()) {
    				if(!delListCheck.isEmpty())
	    			{
	    				logger.info("Yes there are delivery list for this lot id: " + l.getLotId());
	    				check1 = true;
	    				logger.info("Value of check: "+check1);
	    				logger.info("DelListCheck :");
	    				int sizeOfDelListCheck = delListCheck.size();
	    				for(int i=0;i<sizeOfDelListCheck;i++) {
	    					System.out.println("Delivery "+i+" : "+delListCheck.get(i).toString());
	    				}
	    			}
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
                		//lotByClientReport.setUnitQtyLot(String.format("%.2f", l.getUnitGrossWeightInKg()));
                		lotByClientReport.setUnitQtyLot(String.valueOf(l.getUnitQuantity()));
                		lotByClientReport.setStorageClass(l.getWsoInfo().getStorageClass().getStorageTypeName());
                		
                		if(i == delList.size()-1 && (initQty - delList.get(i).getTotalQty())!=0) {
                			if(check1 == false) {
                				if(l.getCurrentQuantity()!=0) {
                        			lotByClientList.add(lotByClientReport);
                        			
                        			Row row = sheet.createRow(rowNum++);
                        			
                        	        row.createCell(0)
                        	                .setCellValue(l.getLotNo());

                        	        row.createCell(1)
                        	                .setCellValue(l.getDescription());
                        	        
                        	        row.createCell(2)
                        	        		.setCellValue(l.getWsoInfo().getWsoNo());
                        	        
                        	        row.createCell(3)
                	        				.setCellValue(l.getWsoInfo().getStorageClass().getStorageTypeName());

                        	        row.createCell(4)
                        	                .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
                        	        
                        	        row.createCell(5)
                        	        		.setCellValue(String.valueOf(l.getInitialQuantity()));

                        	        row.createCell(6)
                        	        		.setCellValue(String.valueOf(initQty - delList.get(i).getTotalQty()));

                        			row.createCell(7)
                        			        .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
                        			
                        			row.createCell(8)
                        	        		.setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
                        			
                        			row.createCell(9)
                        	        		.setCellValue(String.format("%.2f", l.getUnitWeightInKg()));
                        			
                        			/*row.createCell(10)
                        					.setCellValue(String.format("%.2f", l.getUnitGrossWeightInKg()));*/
                        			row.createCell(10)
        									.setCellValue(String.valueOf(l.getUnitQuantity()));
                        			
                        			for(int j = 0; j < row.getLastCellNum(); j++){//For each cell in the row 
                        		        row.getCell(j).setCellStyle(normalCellStyle);//Set the sty;e
                        		    }
                        			//totalinitialQty += l.getInitialQuantity();
                        			//totalcurrentQty += initQty - delList.get(i).getTotalQty();
                        		}
                			}else {
                    			lotByClientList.add(lotByClientReport);
                    			
                    			Row row = sheet.createRow(rowNum++);
                    			
                    	        row.createCell(0)
                    	                .setCellValue(l.getLotNo());

                    	        row.createCell(1)
                    	                .setCellValue(l.getDescription());
                    	        
                    	        row.createCell(2)
                    	        		.setCellValue(l.getWsoInfo().getWsoNo());
                    	        
                    	        row.createCell(3)
            	        				.setCellValue(l.getWsoInfo().getStorageClass().getStorageTypeName());

                    	        row.createCell(4)
                    	                .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getWsoInfo().getTallysheet().getStorageDate())));
                    	        
                    	        row.createCell(5)
                    	        		.setCellValue(String.valueOf(l.getInitialQuantity()));

                    	        row.createCell(6)
                    	        		.setCellValue(String.valueOf(initQty - delList.get(i).getTotalQty()));

                    			row.createCell(7)
                    			        .setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getProductionDate())));
                    			
                    			row.createCell(8)
                    	        		.setCellValue(String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(l.getExpiryDate())));
                    			
                    			row.createCell(9)
                    	        		.setCellValue(String.format("%.2f", l.getUnitWeightInKg()));
                    			
                    			/*row.createCell(10)
                    					.setCellValue(String.format("%.2f", l.getUnitGrossWeightInKg()));*/
                    			row.createCell(10)
    									.setCellValue(String.valueOf(l.getUnitQuantity()));
                    			
                    			for(int j = 0; j < row.getLastCellNum(); j++){//For each cell in the row 
                    		        row.getCell(j).setCellStyle(normalCellStyle);//Set the sty;e
                    		    }
                    			//totalinitialQty += l.getInitialQuantity();
                    			//totalcurrentQty += initQty - delList.get(i).getTotalQty();
                    		}
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
    			Row row = sheet.createRow(rowNum++);
    			
        		row.createCell(5).setCellValue(totalinitialQty);
        		row.createCell(6).setCellValue(totalcurrentQty);
        		for(int i = 5; i < 6; i++){//For each cell in the row 
    		        row.getCell(i).setCellStyle(normalCellStyle);//Set the sty;e
    		    }
        		
    	   		lotByClientList.clear();
    	   		totalinitialQty = 0;
    	   		totalcurrentQty = 0;
    		}
    	}	
    	Row row = sheet.createRow(rowNum++);
    	
		row.createCell(4).setCellValue("Grand Total ");
		row.createCell(5).setCellValue(GrandtotalinitialQty);
		row.createCell(6).setCellValue(GrandtotalcurrentQty);
		
		for(int i = 4; i < 6; i++){//For each cell in the row 
	        row.getCell(i).setCellStyle(normalCellStyle);//Set the sty;e
	    }
    	// Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
    	/*FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Lenovo\\Downloads\\StockBalanceTest.xls");
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();	  	*/   
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

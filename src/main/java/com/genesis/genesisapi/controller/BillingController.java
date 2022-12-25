package com.genesis.genesisapi.controller;


import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.genesis.genesisapi.exceptions.BillingNotFoundException;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.OtherCharges;
import com.genesis.genesisapi.model.Payment;
import com.genesis.genesisapi.model.StorageType;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.WarehouseInfo;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.ClientStorageInfoRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.ReportsRepo;
import com.genesis.genesisapi.repository.StorageTypeRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.repository.WarehouseInfoRepo;
import com.genesis.genesisapi.service.BillingService;
import com.genesis.genesisapi.service.OtherChargesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.net.URLDecoder;
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
import java.util.Set;

@RestController
@RequestMapping("/billings")
@CrossOrigin("*")
public class BillingController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BillingService billingService;
    
    @Autowired
    private TallysheetRepo tallysheetRepo;
    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    
    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private LotInfoRepo lotInfoRepo;
    
    @Autowired
    private DeliveryListRepo deliveryListRepo;
    
    
    @Autowired
    private ClientStorageInfoRepo clientStorageInfoRepo;
    
    @Autowired
    private StorageTypeRepo storageTypeRepo;
    
    @Autowired
    private WarehouseInfoRepo warehouseInfoRepo;
    
    @Autowired
    private ReportsRepo reportsRepo;
    
    @Autowired
    private OtherChargesService otherChargesService;
    
    @Autowired
    private ClientInfoRepo clientInfoRepo;
    
    @Autowired
    private PaymentRepo paymentRepo;

	@Value("${current.gst}")
	private String gstFromConfiguratin;

    @GetMapping("/")
    public List<Billing> fingAllBilling(){
    	logger.info("Records accessed from BillingController");
        return billingService.getBilling();
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<Billing> findBillingById(@PathVariable Long billingId) throws BillingNotFoundException{
        Billing billing = billingService.getBilling(billingId);
        if(billing == null){
        	logger.info("Exception in BillingController: Billing Id Not Found");
        	throw new BillingNotFoundException(billingId);
            //return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
        	logger.info("Record accessed from billing table with billingId:"+billingId);
            return new ResponseEntity<>(billing, HttpStatus.OK);
        
    }
    @ExceptionHandler(BillingNotFoundException.class)
	public ModelAndView handleBillingNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("BillingNotFound");
	    return modelAndView;
	}


    @PostMapping("/")
    public Billing saveBilling(@RequestBody Billing billing){
        System.out.println("billings.....");
        logger.info("Record saved successfully in Billing table with username:"+billing.getBillingId());
        return billingService.saveBilling(billing);
    }

    @PutMapping("/{billingId}")
    public ResponseEntity<Billing> updateBilling(@PathVariable Long billingId, @Valid @RequestBody Billing billing){
        Billing billingData = billingService.getBilling(billingId);
        if(billingData == null){
        	logger.info("Exception in BillingController: Billing Id Not Found. Not able to update Data.");
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        billingData.setBillingQuantity(billing.getBillingQuantity());
        billingData.setBillingRate(billing.getBillingRate());
        billingData.setBillingWeight(billing.getBillingWeight());
        billingData.setFromDate(billing.getFromDate());
        billingData.setToDate(billing.getToDate());

        billingData.setGst(billing.getGst());
        billingData.setInvoiceDate(billing.getInvoiceDate());
        billingData.setInvoiceNo(billing.getInvoiceNo());
        billingData.setNetAmount(billing.getNetAmount());

        Billing updatedBilling = billingService.saveBilling(billingData);
        logger.info("Record updated in billing table with billingId:"+billingId);
        return new ResponseEntity<>(updatedBilling, HttpStatus.OK);
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<Billing> deleteBilling(@PathVariable Long billingId){
        Billing billing = billingService.getBilling(billingId);
        if(billing == null){
        	logger.info("Exception in BillingController: Billing Id Not Found. Not able to delete record.");
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        

        Billing updatedBilling = billingService.saveBilling(billing);
        logger.info("Record successfully deleted from billing table with billingId:"+billingId);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value="/loadAllBill",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject  loadAllBill(@RequestParam(value = "billType",required = false) String billType,
    								 @RequestParam(value = "fromClient",required = false) String fromClientId,
    								 @RequestParam(value = "toClientInfo",required = false) String toClientInfoId){
    	JSONObject response = new JSONObject();
    	Long fromClient = 0L;
    	Long toClientInfo = 0L;
    	try{
    		fromClient = Long.parseLong(fromClientId);
    		toClientInfo = Long.parseLong(toClientInfoId);
    	}catch(Exception e){
    		logger.info("Exception in BillingController: "+e.getMessage());
    	}
    	
    	List<Long> storageTypeId = new ArrayList<Long>();
    	if(StringUtils.isNotBlank(billType)){
    		if("common".equalsIgnoreCase(billType)){
    			storageTypeId.add(1L);
    			storageTypeId.add(2L);
    			storageTypeId.add(3L);
    			storageTypeId.add(4L);
    			storageTypeId.add(5L);
    			List<TallySheet> tallySheet = null;
    			if(fromClient == 0  &&  toClientInfo == 0){
    				tallySheet  = tallysheetRepo.findAllVerifiedTallySheet();
    				logger.info("Data Successfully retrieved from TallySheet table. All are Verified tallysheet Records.");
    			}else{
    				tallySheet  = tallysheetRepo.getApprovedTallySheetBetweenClient(fromClient,toClientInfo);
    				logger.info("Data retrieved from TallySheet between Clients.");
    			}
    	    	List<Long> tallySheetList =  new ArrayList<Long>();
    	    	for(TallySheet model : tallySheet){
    	    		tallySheetList.add(model.getTallysheetId());
    	    	}
    	    	response.put("type", "common");
    	    	response.put("data", wsoInfoRepo.loadBillByClientAndBillType(storageTypeId, tallySheetList));
    		}
    		if("lease".equalsIgnoreCase(billType)){
    			storageTypeId.add(6L);
    			storageTypeId.add(7L);
    			storageTypeId.add(8L);
    			storageTypeId.add(9L);
    			storageTypeId.add(10L);
    			storageTypeId.add(11L);
    			response.put("type", "lease");
    			List<Long> clientIdList = new ArrayList<Long>();
    			List<ClientInfo> dataList =   clientInfoRepo.clientBetweenClient(fromClient, toClientInfo);
    			logger.info("Data Successfully retrieved from Client Table. From Client - To Client.");
    			for(ClientInfo modal :  dataList){
    				clientIdList.add(modal.getClientInfoId());	
    			}
    			if(fromClient == 0 && toClientInfo == 0 ){
    				response.put("data", wsoInfoRepo.loadLeaasBillAndBillTypeAllClient(storageTypeId));
    				logger.info("Data successfully retrieved from Billig Table and Client Info table");
    			}else{
    				response.put("data", wsoInfoRepo.loadLeaasBillAndBillType(storageTypeId, fromClient, toClientInfo));
    				logger.info("Data successfully retrieved from Billig Table and Client Info table");
    			}
    	    	
    		}
    		if("other".equalsIgnoreCase(billType)){
    			if(fromClient == 0 && toClientInfo == 0 ){
    				response.put("data", billingRepo.getOtherBillAllDetail());
    				logger.info("Records successfully retrieved from Billing and Client Info table.");
    			}else{
    				response.put("data", billingRepo.getOtherBillDetail(fromClient, toClientInfo));	
    				logger.info("Records successfully retrieved from Billing and Client Info table.");
    			}
    			
    		}
    	}
    	logger.info("Records successfully retrieved from billing table.");
    	return response;
    }

    @RequestMapping(value="/getBillingByInvoice",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject  getBillingByInvoice(@RequestParam(value = "invoiceNumber",required = false) String invoiceNumber){
    	JSONObject response = new JSONObject();
    	response.put("data", billingRepo.getBillDetailByInvoiceNum(invoiceNumber));
    	logger.info("Records successfully retrieved from billing table using invoice number.");
    	return response;
    }
    
  //@PostMapping("/getBillingByClientIdAndDates")
    @RequestMapping(value="/getBillingByClientIdAndDates",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject  getBillingByClientIdAndDates(@RequestParam(value = "clientId",required = false) Long clientId,
    		@RequestParam(value = "fromdate",required = false) String fromdate,
			 @RequestParam(value = "todate",required = false) String todate){
    	//Long clientId = Long.parseLong(clientInfo);
    	System.out.println("ClientId:"+clientId+" fromDate:"+fromdate+" toDate:"+todate);
    	Date fromDate = null;
		Date toDate = null;
		
		try {
			fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			toDate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray list = new  JSONArray();
    	JSONObject response = new JSONObject();
    	List<Object> data = billingRepo.getBillingInfoByCLientIdAndBetweenDatesForInvList(clientId, fromDate, toDate);
    	logger.info("Records retrieved from Billing table on the basis of Client Id and Between Dates.");
    	for (int i = 0; i < data.size(); i++) {
    		JSONObject  obj1  = new JSONObject();
			Object[] object = (Object[]) data.get(i);
			String invNo = (String) object[0];
			Date invDate = (Date) object[1];
			Double subTotal = (Double) object[2];
			Double gst = (Double) object[3];
			Double total = (Double) object[4];
			
			obj1.put("invNo",invNo);
			obj1.put("invDate",invDate);
			obj1.put("client",clientInfoRepo.findByCientId(clientId).getClientTitle());
			obj1.put("subTotal",subTotal);
			obj1.put("gst",gst);
			obj1.put("total",total);
			list.add(obj1);
		}
    	System.out.println("Data is:"+list);
    	response.put("data",list);
    	logger.info("Records successfully retrieved from billing table using clientId and date range.");
    	return response;
    }
    
    
   @RequestMapping(value="/generateBill",method={RequestMethod.GET,RequestMethod.POST})
    public List<Billing> generateBill(@RequestParam(value = "fromClientInfo",required = false) Integer fromClientInfo,
			@RequestParam(value = "toClientInfo",required = false) Integer toClientInfo,
			@RequestParam(value = "billType",required = false) String billType){
    	
	 //  List<WarehouseInfo> dataList  = warehouseInfoRepo.findAll();
	   Map<Long,String> invoiceMap = new HashMap<Long,String>();
       float gst  = Float.parseFloat(gstFromConfiguratin);
       /*if(null != dataList && !dataList.isEmpty()){
       	gst  = dataList.get(0).getApplicableGst();
       }*/
    	List<Billing> blingList = new ArrayList<Billing>();
    	Long fromClientId = Long.valueOf(fromClientInfo);
    	Long toClientId = Long.valueOf(toClientInfo);
    	Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date fromDate = c.getTime();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date toDate = c.getTime();
        List<Long> tallySheetId = new ArrayList<Long>();
        String invoiceNumber  = "";
        List<Long> leaseStorageType = new ArrayList<Long>();
		leaseStorageType.add(6L);
		leaseStorageType.add(7L);
		leaseStorageType.add(8L);
		leaseStorageType.add(9L);
		leaseStorageType.add(10L);
		leaseStorageType.add(11L);
		if(StringUtils.isNotBlank(billType)){
			
			if("lease".equalsIgnoreCase(billType)){
				List<ClientStorageInfo> clientstorage = clientStorageInfoRepo.findByClientIdAndStoregeTypeId(fromClientId,toClientId,leaseStorageType, new Date());
				for(ClientStorageInfo model : clientstorage){
					Billing billing = new Billing();
					Calendar tobillingdate2 = Calendar.getInstance();
	    			tobillingdate2.setTime(model.getLastBillDate());
	    			tobillingdate2.add(Calendar.DAY_OF_MONTH, 1);
	    			billing.setFromDate(tobillingdate2.getTime());
	    			
	    			Calendar tobillingdate = Calendar.getInstance();
	    			tobillingdate.setTime(model.getLastBillDate());
	    			int day = tobillingdate.get(Calendar.DATE);
	    			
	    			int month = tobillingdate.get(Calendar.MONTH) + 1;
	    			System.out.println("Month: "+ month);
	    			if((month == 2) && (day == 28 || day == 29 )) {
	    				day = tobillingdate2.getActualMaximum(Calendar.DAY_OF_MONTH);
	    				tobillingdate.add(Calendar.DAY_OF_MONTH, day);
	    			} 
	    			else if(day == 30 || day ==31  ) {
	    				day = tobillingdate2.getActualMaximum(Calendar.DAY_OF_MONTH);
	    				tobillingdate.add(Calendar.DAY_OF_MONTH, day);
	    				
	    			}else {
	    				int maxdayofmonth = tobillingdate.getActualMaximum(Calendar.DAY_OF_MONTH);
	    				if(maxdayofmonth==30) {
	    					tobillingdate.add(Calendar.DAY_OF_MONTH, 30);	
	    				}
	    				if(maxdayofmonth==31) {
	    					tobillingdate.add(Calendar.DAY_OF_MONTH, 31);	
	    				}
	    				if(maxdayofmonth==28) {
	    					tobillingdate.add(Calendar.DAY_OF_MONTH, 28);	
	    				}
	    				if(maxdayofmonth==29) {
	    					tobillingdate.add(Calendar.DAY_OF_MONTH, 29);	
	    				}
	    				
	    			}
	    			billing.setToDate(tobillingdate.getTime());
					
	    			
					int diffMonth = 1;
					float  totalAmount  = model.getMonthlyRate() *diffMonth;
					float ttlgst =totalAmount*gst/100;
					totalAmount = ttlgst+totalAmount;
					 
					if(invoiceMap.get(model.getClientInfo().getClientInfoId()) == null ){
						invoiceNumber = getNewInvoiceNumber();
						invoiceMap.put(model.getClientInfo().getClientInfoId(), invoiceNumber);
					}
					billing.setInvoiceNo(invoiceNumber);
					
					
					Calendar temp2 = Calendar.getInstance();
					temp2.setTime(model.getLastBillDate());
					temp2.add(Calendar.DAY_OF_MONTH, 1);
		
					String netAmount = new DecimalFormat("##.##").format(totalAmount);
					billing.setNetAmount(Double.parseDouble(netAmount));
					billing.setStorageClass(model.getStorageType());
					billing.setClientInfo(model.getClientInfo());
					
					billing.setBillingRate(model.getMonthlyRate());
					ClientInfo cmodel  = new ClientInfo();
					cmodel.setClientInfoId(model.getClientInfo().getClientInfoId());
					billing.setClientInfo(cmodel);
					billing.setInvoiceDate(new Date());
					billing.setGst(ttlgst);
					billing =  billingRepo.save(billing);
					if(model.isIsfirstBilling()&& model.getHandlingCharges()>0){
						Billing billing2 = new Billing();
						
						totalAmount = 0;
						float handlingC = model.getHandlingCharges();
						float hgst  = (handlingC*gst/100);
						totalAmount = hgst + handlingC;
						billing2.setGst(hgst);
						netAmount = new DecimalFormat("##.##").format(totalAmount);
						billing2.setNetAmount(Double.parseDouble(netAmount));
						billing2.setBillingRate(handlingC);
						billing2.setHandlingCharge(handlingC);
						billing2.setInvoiceDate(new Date());
						billing2.setInvoiceNo(invoiceNumber);
						billing2.setStorageClass(model.getStorageType());
						billing2.setClientInfo(cmodel);
						billing2.setFromDate(tobillingdate2.getTime());
						billing2.setToDate(tobillingdate.getTime());
						billingRepo.save(billing2);
						 model.setIsfirstBilling(false);
						 clientStorageInfoRepo.save(model);
						 logger.info("Records successfully saved in billing table.");
					 }
					blingList.add(billing);
					model.setLastBillDate(tobillingdate.getTime());
					clientStorageInfoRepo.save(model);
					logger.info("Records successfully saved in billing table.");
				}
			}else if("other".equalsIgnoreCase(billType)){
				List<OtherCharges> otherChargeList = otherChargesService.getOtherChargesBetweenClient(fromClientId, toClientId);
				for(OtherCharges model : otherChargeList){
					
					if(invoiceMap.get(model.getClientInfo().getClientInfoId()) == null ){
						invoiceNumber = getNewInvoiceNumber();
						invoiceMap.put(model.getClientInfo().getClientInfoId(), invoiceNumber);
					}
					Billing billing = new Billing();
					billing.setBillingQuantity(model.getBilableUnit());
					billing.setBillingRate(model.getBilableRate());
					double netAmount = model.getBilableAmount();
					float ggst = (float) ((netAmount*gst)/100);
					billing.setGst(ggst);
					netAmount = netAmount+ggst;
					String netAmount2 = new DecimalFormat("##.##").format(netAmount);
					billing.setNetAmount(Double.parseDouble(netAmount2));
					billing.setClientInfo(model.getClientInfo());
					billing.setChargeItems(model.getChargeItems());
					billing.setInvoiceDate(new Date());
					billing.setInvoiceNo(invoiceNumber);
					billing =  billingRepo.save(billing);
					billing.setFormNo(model.getFormNo());
					billing.setNaration(model.getNaration());
					
					
	    			model.setBillGenerated(true);
	    			otherChargesService.saveOtherCharges(model);
	    			logger.info("Records successfully saved in other charges table.");
				}
				
			}else{
				
				List<TallySheet>  tallySheetList = 	tallysheetRepo.findTallysheetByBetweenClientId(fromClientId, toClientId);
		    	logger.info("Start of Billing in Common Type");
				logger.info("TallysSheetList: "+tallySheetList);
				if(!tallySheetList.isEmpty()){
		    		
		    	for(TallySheet tlscheet :tallySheetList ){
		    		
		    		List<WSOInfo>  wsoList = 	wsoInfoRepo.findAllWSObyTallySheet(tlscheet.getTallysheetId(), new Date(),leaseStorageType);
		    		logger.info("wsoList: "+wsoList);
		    		if(!wsoList.isEmpty()){
		    			
		    			for(WSOInfo model : wsoList ){
		    				Billing billing = new Billing();
		    			float totalweight =	0;
		    			Boolean GenBill = false;
		    			 /*for common billing callculation
		    			  * Date toddate = model.getBillEndDate(); 
		    			 
		    			 Calendar fromDae = Calendar.getInstance();
		    			 Calendar fromDae2 = Calendar.getInstance();
			    			
		    			 fromDae.setTime(model.getBillEndDate());
		    			 fromDae2.setTime(model.getBillEndDate());
		    			 fromDae.add(Calendar.DAY_OF_MONTH, -1);
		    			 int day = fromDae.get(Calendar.DATE);
		    			 if(day == 30 || day == 31) {
		    				 
		    			 }
		    			 
		    			 if(day == 30 || day ==31 ) {
			    				day = fromDae.getActualMaximum(Calendar.DAY_OF_MONTH);
			    				fromDae2.add(Calendar.DAY_OF_MONTH, -day);
			    				
			    			}else {
			    				int maxdayofmonth = fromDae2.getActualMaximum(Calendar.DAY_OF_MONTH);
			    				if(maxdayofmonth==30) {
			    					fromDae2.add(Calendar.DAY_OF_MONTH, -30);	
			    				}
			    				if(maxdayofmonth==31) {
			    					fromDae2.add(Calendar.DAY_OF_MONTH, -31);	
			    				}
			    				
			    			}*/
			    		
		    			float deliverdWeight = 0;
		    			System.out.println("WSO ID: "+model.getWsoId()+" WSI Bill End Date: "+model.getBillEndDate());
		    			List<DeliveryList> deliModel = deliveryListRepo.getDeliveryByWsoAndDate2(model.getWsoId(),  model.getBillEndDate());
		    			logger.info("deliModel :"+deliModel);
		    			for(DeliveryList deModel : deliModel) {
			    			System.out.println("Total Qty: "+deModel.getTotalQty()+" and Delivered Weight: "+deModel.getLotInfo().getUnitNetWeightInKg()*deModel.getTotalQty());
			    			deliverdWeight = deliverdWeight+( deModel.getLotInfo().getUnitNetWeightInKg()*deModel.getTotalQty());
			    		}
		    			logger.info("Total Delivered Weight: "+deliverdWeight);
		    			List<LotInfo> lotDetails  = 	 lotInfoRepo.findLotByWsoId(model.getWsoId());
		    			logger.info("lotDetails :"+lotDetails);
		    			for( LotInfo lotModel :lotDetails){
		    				//totalweight += lotModel.getUnitGrossWeightInKg();
		    				totalweight += lotModel.getUnitNetWeightInKg() * lotModel.getLotQuantity();
		    			}
		    			logger.info("totalweight :"+totalweight);
		    			for( LotInfo lotModel :lotDetails){
		    				Calendar tobillingdate2 = Calendar.getInstance();
			    			
			    			tobillingdate2.setTime(model.getBillEndDate());
			    			tobillingdate2.add(Calendar.DAY_OF_MONTH, 1);
			    			Date FromDate = tobillingdate2.getTime();
			    			System.out.println("Billing From Date: "+FromDate);
			    			
			    			Calendar tobillingdate = Calendar.getInstance();
			    			tobillingdate.setTime(model.getBillEndDate());
			    			int day = tobillingdate.get(Calendar.DATE);
			    			
			    			int month = tobillingdate.get(Calendar.MONTH) + 1;
			    			System.out.println("Month: "+ month);
			    			if((month == 2) && (day == 28 || day == 29 )) {
			    				day = tobillingdate2.getActualMaximum(Calendar.DAY_OF_MONTH);
			    				tobillingdate.add(Calendar.DAY_OF_MONTH, day);
			    			} 
			    			else if(day == 30 || day ==31 ) {
			    				day = tobillingdate2.getActualMaximum(Calendar.DAY_OF_MONTH);
			    				tobillingdate.add(Calendar.DAY_OF_MONTH, day);
			    			}else {
			    				int maxdayofmonth = tobillingdate.getActualMaximum(Calendar.DAY_OF_MONTH);
			    				if(maxdayofmonth==30) {
			    					tobillingdate.add(Calendar.DAY_OF_MONTH, 30);	
			    				}
			    				if(maxdayofmonth==31) {
			    					tobillingdate.add(Calendar.DAY_OF_MONTH, 31);	
			    				}
			    				//code for testing
			    				if(maxdayofmonth==28) {
			    					tobillingdate.add(Calendar.DAY_OF_MONTH, 28);	
			    				}
			    				if(maxdayofmonth==29) {
			    					tobillingdate.add(Calendar.DAY_OF_MONTH, 29);	
			    				}
			    			}
			    			
			    			Date ToDate = tobillingdate.getTime();
			    			System.out.println("Billing To Date: "+ToDate);
			    			
			    			List<DeliveryList> deliModelLot = deliveryListRepo.getDeliveryByWsoAndBetweenDates(lotModel.getLotId(), FromDate, ToDate);
		    				logger.info("Delivery Between Dates: "+deliModelLot);
		    				logger.info("lotModel.getCurrentQuantity() : "+lotModel.getCurrentQuantity());
			    			if(lotModel.getCurrentQuantity() != 0) {
		    					System.out.println("Current Qty is not zero");
		    					GenBill = true;
		    				}
		    				else if (!deliModelLot.isEmpty()) {
		    					System.out.println("Current Qty is zero but Delivery List exists");
		    					GenBill = true;
		    				}
		    					
		    				else {
		    					System.out.println("Current Qty is zero and Delivery List doesn't exist.");
		    				}
		    			}
		    			System.out.println("Deliverd Weight"+deliverdWeight);
		    			System.out.println("billing for wight"+deliverdWeight);
		    			totalweight = totalweight - deliverdWeight;
		    			System.out.println("billing for wight"+totalweight);
		    			if(totalweight <=0){
		    				continue;
		    			}
		    			
		    			if (!GenBill)
		    				continue; 
		    			
		    			if(leaseStorageType.contains(model.getStorageClass().getStorageTypeId())){
		    				continue;
		    			}
		    			
		    			if(totalweight>1000){
		    				totalweight = getAVErageWsoWeight(totalweight);
		    				logger.info("total weight is greater than 1000 so totalweight :"+totalweight);
		    			}else{
		    				totalweight =1;
		    				logger.info("total weight is :"+1);
		    			}
		    			
		    			float ammount = totalweight * model.getInvoiceRate();
		    			float ttlgst = ammount*gst/100;
		    			float toatlAmmount = ammount + ttlgst;

		    			String netAmount = new DecimalFormat("##.##").format(toatlAmmount);
		    			
		    			Set<LotInfo> lotInfo  = model.getLotInfo();
		    			Integer billingQuantity = 0;
		    			for(LotInfo lotdetail :lotInfo){
		    				billingQuantity += lotdetail.getCurrentQuantity();
		    			}
		    			billing.setBillingQuantity(billingQuantity);
		    			billing.setBillingWeight(totalweight*1000);
		    			billing.setBillingRate(model.getInvoiceRate());
		    			Calendar tobillingdate2 = Calendar.getInstance();
		    			
		    			tobillingdate2.setTime(model.getBillEndDate());
		    			tobillingdate2.add(Calendar.DAY_OF_MONTH, 1);
		    			billing.setFromDate(tobillingdate2.getTime());
		    			
		    			
		    			Calendar tobillingdate = Calendar.getInstance();
		    			tobillingdate.setTime(model.getBillEndDate());
		    			int day = tobillingdate.get(Calendar.DATE);
		    			
		    			int month = tobillingdate.get(Calendar.MONTH) + 1;
		    			System.out.println("Month: "+ month);
		    			if((month == 2) && (day == 28 || day == 29 )) {
		    				day = tobillingdate2.getActualMaximum(Calendar.DAY_OF_MONTH);
		    				tobillingdate.add(Calendar.DAY_OF_MONTH, day);
		    			} 
		    			else if(day == 30 || day ==31 ) {
		    				day = tobillingdate2.getActualMaximum(Calendar.DAY_OF_MONTH);
		    				tobillingdate.add(Calendar.DAY_OF_MONTH, day);
		    			}else {
		    				int maxdayofmonth = tobillingdate.getActualMaximum(Calendar.DAY_OF_MONTH);
		    				if(maxdayofmonth==30) {
		    					tobillingdate.add(Calendar.DAY_OF_MONTH, 30);	
		    				}
		    				if(maxdayofmonth==31) {
		    					tobillingdate.add(Calendar.DAY_OF_MONTH, 31);	
		    				}
		    				//code for testing
		    				if(maxdayofmonth==28) {
		    					tobillingdate.add(Calendar.DAY_OF_MONTH, 28);	
		    				}
		    				if(maxdayofmonth==29) {
		    					tobillingdate.add(Calendar.DAY_OF_MONTH, 29);	
		    				}
		    			}
		    			
		    			
		    			billing.setToDate(tobillingdate.getTime());
		    			billing.setGst(ttlgst);
		    			billing.setInvoiceDate(new Date());
		    			if(invoiceMap.get(tlscheet.getClientInfo().getClientInfoId()) == null ){
							invoiceNumber = getNewInvoiceNumber();
							invoiceMap.put(tlscheet.getClientInfo().getClientInfoId(), invoiceNumber);
						}
		    			
		    			billing.setInvoiceNo(invoiceNumber);
		    			billing.setNetAmount(Double.parseDouble(netAmount));
		    			billing.setTallysheet(model.getTallysheet());
		    			billing.setWsoInfo(model);
		    			billing.setStorageClass(model.getStorageClass());
		    			billing.setClientInfo(tlscheet.getClientInfo());
		    			billing =  billingRepo.save(billing);
		    			logger.info("Billing is :"+billing);
		    			logger.info("Records successfully saved in billing table.");
		    			if(model.isIsfirstBilling()){
		    				Billing billing2 = new Billing();
		    				
		    				float handlingCharge = 0;
		    				List<ClientStorageInfo> cstorageList = clientStorageInfoRepo.getClientStorageByclientAndStorageId(tlscheet.getClientInfo().getClientInfoId(), model.getStorageClass().getStorageTypeId());
		    				if(!cstorageList.isEmpty()){
		    					handlingCharge = cstorageList.get(0).getHandlingCharges();
		    				}
		    				
		    				
		    				if(handlingCharge >0) {
		    				billing2.setBillingRate(handlingCharge);
		    				handlingCharge = handlingCharge*model.getTotalNoOfPallets();
		    				billing2.setHandlingCharge(handlingCharge);
		    				
		    				float hgst = (handlingCharge*gst)/100;
		    				toatlAmmount = 0;
		    				toatlAmmount = handlingCharge+hgst;
		    				netAmount = new DecimalFormat("##.##").format(toatlAmmount);
		    				billing2.setInvoiceNo(invoiceNumber);
		    				billing2.setGst(hgst);
		    				billing2.setNetAmount(Double.parseDouble(netAmount));
		    				billing2.setClientInfo(tlscheet.getClientInfo());
		    				billing2.setStorageClass(model.getStorageClass());
		    				billing2.setInvoiceDate(new Date());
		    				billing2.setFromDate(tobillingdate2.getTime());
		    				billing2.setWsoInfo(model);
			    			billing2.setStorageClass(model.getStorageClass());
			    			billing2.setClientInfo(tlscheet.getClientInfo());
		    				billing2.setToDate(tobillingdate.getTime());
		    				billing2.setToDate(tobillingdate.getTime());
		    				billing2.setFromDate(tobillingdate2.getTime());
		    				billing2 =  billingRepo.save(billing2);
		    				logger.info("Records successfully saved in billing table.");
		    				
		    				model.setIsfirstBilling(false);
		    				}
		    			}
		    			
		    		
		    			blingList.add(billing);
		    			Calendar calendar = Calendar.getInstance();         
		    			calendar.add(Calendar.MONTH, 1);
		    			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		    			Date nextMonthFirstDay = calendar.getTime();
		    			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		    			Date nextMonthLastDay = calendar.getTime();
		    			model.setBillStartDate(tobillingdate.getTime());
		    			model.setBillEndDate(tobillingdate.getTime());
		    			wsoInfoRepo.save(model);
		    			logger.info("Records successfully saved in wsoInfo table.");
		    			}
		    		}else{
		    			System.out.println("No WSO Found for billing");
		    		}
		    	}
		    	}else{
		    		System.out.println("Tally Sheet Not found ");
		    	}
			}
			
		}
		//if()

    	logger.info("Billing List is :"+blingList);
    	
    	return blingList;
    }
    
    public float getAVErageWsoWeight(float totalweight){
    	System.out.println("Inside getAVErageWsoWeight Method. Value of totalweight:"+totalweight);
    	
    	float temp  = 0.0F;
    	temp =totalweight/1000;
    	System.out.println("Value of Temp: "+temp);
    	int finalToal = 0;
    	int remainder =(int) (totalweight%1000);
    	System.out.println("Remainder :"+remainder);
    	int remTemp = remainder/100;
    	if(remTemp < 5) {
    		finalToal = (int) temp;
    	}
    	else
    	{
    		finalToal = (int)(temp + 1);
    	}
		/*
		 * if (remainder > 0){ finalToal = (int)(temp + 1); } else { finalToal = (int)
		 * temp; }
		 */
    	System.out.println("After checking condition, finalToal:"+finalToal);
    	/*totalweight =totalweight/1000;
    	System.out.println("After Rounding of, totalweight:"+totalweight);
    	finalToal = (int)Math.round(totalweight);
    	System.out.println("After Rounding of, finalToal:"+finalToal);
    	 temp  = temp%1000;
    	 System.out.println("temp:"+temp);
    	 if(temp>49){
    		 finalToal = finalToal+1; 
    	 }
    	 System.out.println("After checking condition, finalToal:"+finalToal);*/
    	return finalToal;
    }
	@RequestMapping(value="/getAllBillingreports",method={RequestMethod.GET,RequestMethod.POST})
	public JSONObject loadDeliveryListReport(){
		JSONObject  responsejson  = new JSONObject();
		JSONArray list = new  JSONArray();
		List<Billing> dataList = billingService.getBilling();
		for(Billing model : dataList){
			JSONObject  obj  = new JSONObject();
			obj.put("wsoNo", model.getWsoInfo().getWsoNo());
			obj.put("fromDate", model.getFromDate());
			obj.put("netAmount", model.getNetAmount());
			obj.put("billingWeight", model.getBillingWeight());
			obj.put("billingRate", model.getBillingRate());
			obj.put("toDate", model.getToDate());
			list.add(obj);
		}
		responsejson.put("data",list);
		logger.info("Records successfully retrevied form billing table.");
		return responsejson;
	}
	
	@RequestMapping(value="/getBillingByInvoiceForOther",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject  getBillingByInvoiceForOther(@RequestParam(value = "invoiceNumber",required = false) String invoiceNumber){
    	JSONObject response = new JSONObject();
    	
    	 List<Billing> dataList = billingRepo.getBillDetailByInvoiceNum(invoiceNumber);
    	 JSONArray list = new  JSONArray();
    	for(Billing model : dataList) {
    		JSONObject  obj1  = new JSONObject();
    		obj1.put("invoiceNo",model.getInvoiceNo());
    		obj1.put("invoiceDate",model.getInvoiceDate());
    		obj1.put("gst",model.getGst());
    		obj1.put("chargeItem", model.getChargeItems().getChargeItem());
    		obj1.put("netAmount", model.getNetAmount());
    		obj1.put("billingRate", model.getBillingRate());
    		
            list.add(obj1);
    	}
    	response.put("data", list);
    	logger.info("Records successfully retreived from billing table.");
    	return response;
    }
	
	public String  getNewInvoiceNumber(){
		 String invoiceNumber;//  = "INVOICE-"+System.currentTimeMillis();
			Billing bInfo = billingRepo.findTop1ByOrderByBillingIdDesc();
			logger.info("Records successfully retreived from billing table.");
			if(bInfo != null) {
				String wsoNo = bInfo.getInvoiceNo();
				String[] arr = wsoNo.split("I");
				int val= Integer.parseInt(arr[1])+1;
				//invoiceNumber = "I000"+val;
				
				int tempNo = 10000 + val;
	    		String tempString = String.valueOf(tempNo);
	    		invoiceNumber = tempString.replaceFirst("1", "I");

			}else {
				invoiceNumber = "I0001";
			}
		return invoiceNumber;
	}
	
	 @RequestMapping(value="/getStorageReport",method={RequestMethod.GET,RequestMethod.POST})
	    public JSONObject  loadAllBill(@RequestParam(value = "fromdate",required = false) String fromdate,
	    								 @RequestParam(value = "todate",required = false) String todate){
		 JSONObject  responsejson  = new JSONObject();
	   
		 /*String fromdate = "2018-01-01";
		 String todate = "2018-04-01";*/
		 String invNo;
		 Double grandCredit=0.0;
		 Double grandCreditAmount = 0.0;
		 Double grandCreditGst = 0.0;
		 
		 System.out.println("fromdate : "+fromdate);
		 System.out.println("todate : "+todate);
		 Date fromDate1 = null;
		 Date toDate1 = null;
		
		try {
			fromDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<Object> b = billingRepo.getStorageReport(fromDate1, toDate1);
		List<Object> b1 = billingRepo.getChargeItemReport(fromDate1, toDate1);
		List<String> invoiceList = new ArrayList<String>();
		
		for (int i = 0; i < b.size(); i++) {
			Object[] object = (Object[]) b.get(i);
			invNo = (String) object[4];
			invoiceList.add(invNo);
		}
		for (int i = 0; i < b1.size(); i++) {
			Object[] object = (Object[]) b1.get(i);
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
 		
		responsejson.put("data",b);
		responsejson.put("data1",b1);
		responsejson.put("grandCredit",grandCredit);
		responsejson.put("grandCreditAmount",grandCreditAmount);
		responsejson.put("grandCreditGst",grandCreditGst);
        return responsejson;
	 }
	    
	 @RequestMapping(value="/loadAllPayment/{clientId}",method={RequestMethod.GET,RequestMethod.POST})
	    public JSONObject  loadAllBillForPayment(@PathVariable Long clientId ) {
		 JSONObject  responsejson  = new JSONObject();
		 List<Object> b = billingRepo.getBillForPayment(clientId);
		 JSONArray list = new JSONArray();
		 for (int i=0; i<b.size(); i++){
			 Object[] row = (Object[]) b.get(i);
			   System.out.println("Element "+i+(String)row[1]);
			   List<Payment> paydata = paymentRepo.findLastPaymentByInvoiceNo((String)row[1]);
			   if(paydata.size() == 0) {
				   JSONObject obj = new JSONObject();
				   obj.put("invoiceNo", "0");
				   obj.put("balanceAmount", "0");
				   list.add(obj);
			   }else {
				   JSONObject obj = new JSONObject();
				   obj.put("invoiceNo", paydata.get(0).getInvoiceNo());
				   obj.put("balanceAmount", paydata.get(0).getBalance());
				   list.add(obj);
			   }
			   
			   
		}
		
		 responsejson.put("data", b);
		 responsejson.put("data1", list);
		 
		 return responsejson;
	 }
	 
	 @RequestMapping(value="/makePayment",method={RequestMethod.GET,RequestMethod.POST})
	    public JSONObject makePayment(HttpSession httpSession, @RequestParam(value = "paymentData",required = false) String paymentData, @RequestParam(value = "paymentType",required = false) String paymentType){
	    
		 	String userId = (String)httpSession.getAttribute("userId");
		   JSONObject  responsejson  = new JSONObject();
		   String tempPaymentType = paymentType;
	    	JSONArray jarray  = new JSONArray();
	    	try{
	    		paymentData = URLDecoder.decode(paymentData, "UTF-8");
			}catch(Exception ex){logger.info("Exception from DeliveryController: "+ex.getMessage());}
			
			try{
				JSONParser parser = new JSONParser();
				if(StringUtils.isNotBlank(paymentData)){
					jarray = (JSONArray) parser.parse(paymentData);
				}
			}catch(Exception e){	
				logger.info("Exception from DeliveryController: "+e.getMessage());
			}
			
			for(int i =0; i< jarray.size(); i++){
				Payment model  = new Payment();
				JSONObject  obj  = (JSONObject)jarray.get(i);
				model.setPaymentDate(new Date());
				String invoiceNo =  (String) obj.get("invoiceNo");
				
				 paymentType = (String) obj.get("paymentType");
				model.setInvoiceNo(invoiceNo);
				model.setPaymentType(paymentType);
				String cnpnId = getcNpNNumber(paymentType);
				model.setCnPnId(cnpnId);
	    		String balance =  (String) obj.get("balance");
	    		String amount =  (String) obj.get("amount");
	    		String cnoteAmount = (String) obj.get("cnote");
	    		float payamount = Float.parseFloat(amount);
	    		float cnoteAmt = Float.parseFloat(cnoteAmount);
	    		if(payamount  == 0 && "p".equalsIgnoreCase(paymentType) ) {
	    			continue;
	    		}
	    		if("p".equalsIgnoreCase(paymentType)  && "C".equalsIgnoreCase(tempPaymentType) ) {
	    			continue;
	    		}
	    		float requiredAmnt = Float.parseFloat(balance);
	    		float currBalance = 0F;
	    		/*if("C".equalsIgnoreCase(paymentType)) {
	    			//model.setPaidAmount(cnoteAmt);
	    			//model.setBalance(0);
	    			System.out.println("cnoteAmt:"+cnoteAmt);
	    			model.setCreditNote(cnoteAmount);
		    		System.out.println("Inside C Payment Type Block!");
	    		}*/
	    		if("P".equalsIgnoreCase(paymentType)) {
	    			if(payamount >= requiredAmnt) {
		    			model.setBalance(0F);
		    			model.setPaidAmount(requiredAmnt);
		    		}else {
		    				float discount = Float.parseFloat(cnoteAmount);
		    				if(!(discount>0 && discount<=requiredAmnt)) {
		    					discount = 0F;
		    				}
		    				model.setCreditNote(Float.toString(discount));
		    				float blnc = (requiredAmnt -payamount) - discount;
		    				model.setPaidAmount(payamount);
		    				model.setBalance(blnc);
		    		}
		    		model.setCredit("0");
		    		model.setPaymentUser(userId);
		    		paymentRepo.save(model);
		    		logger.info("Records successfully saved in Payment table");
		    		System.out.println("Inside P Payment Type Block!");
	    		}
	    		/*else {
	    			
		    		if(payamount >= requiredAmnt) {
		    			model.setBalance(0F);
		    			model.setPaidAmount(requiredAmnt);
		    		}else {
		    				float blnc = requiredAmnt -payamount;
		    				model.setPaidAmount(payamount);
		    				if("c".equalsIgnoreCase(tempPaymentType) ) {
		    					//model.setBalance(0);
		    					//model.setBalance(requiredAmnt-cnoteAmt);
		    				}else {
		    					model.setBalance(blnc);
		    				}
		    		}
		    		model.setCredit("0");
		    		model.setPaymentUser(userId);
	    		}*/
	    		//paymentRepo.save(model);
	    		if(!"C".equalsIgnoreCase(paymentType)) {
	    			List<Billing> dataList = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
	    			logger.info("Records retrieved from Billing table on the basis of invoice number");
		    		if(null != dataList  && !dataList.isEmpty()) {
		    			for(Billing billMOdel  : dataList) {
		    				if("C".equalsIgnoreCase(tempPaymentType)) {
		    					billMOdel.setBillPaid(true);
		    					billingRepo.save(billMOdel);
		    				}else {
		    					if(payamount >= requiredAmnt) {
			    					billMOdel.setBillPaid(true);
			    					billingRepo.save(billMOdel);
					    		}else {
			    					billMOdel.setBillPaid(false);
			    					billingRepo.save(billMOdel);
					    		}	
		    				}
		    				
		    				
		    			}
		    		
		    		}
	    		}
	    		
	    		
			}
			responsejson.put("status", "success");
	    	return responsejson;
	 }
	 
	 
	 public String  getcNpNNumber(String PaymentType) {
		 	List<Payment>  dataList =  paymentRepo.findLatestcnPnNo(PaymentType);
		 	logger.info("Records retrieved from Payment table on the basis of Payment type.");
		 	String finalId = "";
		 	 if(dataList != null && dataList.size()>0) {
		 		Payment model  = dataList.get(0);
		 		String cnpnId = model.getCnPnId();
		 		if("c".equalsIgnoreCase(PaymentType)) {
		 			String arr[] = cnpnId.split("C");
		 			int val= Integer.parseInt(arr[1])+1;
		 			finalId = "C0000"+val;
		 		}
		 		if("p".equalsIgnoreCase(PaymentType)) {
		 			String arr[] = cnpnId.split("P");
		 			int val= Integer.parseInt(arr[1])+1;
		 			finalId = "P0000"+val;
		 		}
		 }else {
			 if("p".equalsIgnoreCase(PaymentType)) {
		 			finalId = "P00001";
		 		}
			 if("C".equalsIgnoreCase(PaymentType)) {
		 			finalId = "C00001";
		 		}
		 }
		 	 return finalId;
	 }
	 
		public static Date addDays(Date date, int days) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
					
			return cal.getTime();
		}
}



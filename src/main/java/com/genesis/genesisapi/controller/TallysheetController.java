package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.TallySheetNotFoundException;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.service.TallsheetService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tallysheets")
@CrossOrigin("*")
public class TallysheetController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TallsheetService tallysheetService;

    @Autowired
    private TallysheetRepo tallysheetRepo;
    
    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    
    @Autowired
    private LotInfoRepo lotInfoRepo;
    
    @Autowired
    private DeliveryListRepo deliveryListRepo;

    @GetMapping("/")
    public List<TallySheet> fingAllTallysheet(){

        List<TallySheet> tallysheet = tallysheetService.getAllTallysheet(Boolean.FALSE);
        logger.info("Records successfully retreived from tallysheet table.");
        return tallysheet;
        //return new ModelAndView("tallysheet","tallysheet",tallysheet);
    }

    @GetMapping("/{tallysheetId}")
    public List<TallySheet> findTallysheetById(@PathVariable Long tallysheetId) throws TallySheetNotFoundException{
    	ClientInfo clientInfo = new ClientInfo();
    	clientInfo.setClientInfoId(tallysheetId);
    	clientInfo.setClientActive(true);
        List<TallySheet> tallysheet = tallysheetService.getTallysheet(clientInfo);
        if(tallysheet==null || tallysheet.isEmpty()){
        	logger.info("Exception in TallysheetController: Tallysheet Id not found.");
        	throw new TallySheetNotFoundException(tallysheetId);
        }
        else
        	logger.info("Records successfully retreived from tallysheet table where tallysheetId is "+tallysheetId);
        	return tallysheet ;
    }
    
    @ExceptionHandler(TallySheetNotFoundException.class)
	public ModelAndView handleTallySheetNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("TallySheetNotFound");
	    return modelAndView;
	}


    @PostMapping("/")
    public TallySheet saveTallysheet(@RequestBody TallySheet tallysheet){
     
        if(tallysheet.getTallysheetNumber().isEmpty()) {
        	TallySheet taSheet = tallysheetRepo.findTop1ByOrderByTallysheetIdDesc();
        	if(taSheet != null) {
        		String tallyNo = taSheet.getTallysheetNumber();
        		String[] arr = tallyNo.split("T");
                System.out.println("tallysheet====== "+arr[1]);
        		int val= Integer.parseInt(arr[1])+1;
        		tallyNo = "T0000"+val;
        		tallysheet.setTallysheetNumber(tallyNo);
        		tallysheet.setTallysheetId(-1L);
        	}else {
        		//tallysheet.setTallysheetNumber("TS-1001");
                tallysheet.setTallysheetNumber("T00001");
        	}
        }else{
        	 if(tallysheet.isVerify()){
             	List<WSOInfo> wsoList = 	wsoInfoRepo.findWsoByTallysheet(tallysheet.getTallysheetId());
             	for(WSOInfo  model : wsoList ){
             		model.setBillStartDate(tallysheet.getStorageDate());
             		model.setFirstBillDate(tallysheet.getStorageDate());
             		Calendar tobillingdate = Calendar.getInstance();
        				tobillingdate.setTime(tallysheet.getStorageDate());
         			tobillingdate.set(Calendar.MONTH, 2);
         			model.setBillEndDate(tobillingdate.getTime());
             		wsoInfoRepo.save(model);
             	}
             }
        }
        logger.info("Records successfully saved in tallysheet table.");
        return tallysheetService.saveTallysheet(tallysheet);
    }

    @PutMapping("/{tallysheetId}")
    public ResponseEntity<TallySheet> updateTallysheet(@PathVariable Long tallysheetId, @RequestBody TallySheet tallysheet){
        System.out.println("tallsheet put called...."+tallysheet.getExVessel());
        TallySheet tallysheetData = tallysheetService.getTallysheet(Boolean.FALSE, tallysheetId);
        if(tallysheetData == null)
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        tallysheetData.setTallysheetNumber(tallysheet.getTallysheetNumber());
        /*tallysheetData.setWarehouseName(tallysheet.getWarehouseName());*/
        tallysheetData.setRefDry(tallysheet.getRefDry());
        tallysheetData.setLorryContainer(tallysheet.getLorryContainer());
        tallysheetData.setContainerNo(tallysheet.getContainerNo());
        tallysheetData.setExVessel(tallysheet.getExVessel());
        tallysheetData.setTempRecorded(tallysheet.getTempRecorded());
        tallysheetData.setStorageDate(tallysheet.getStorageDate());
        tallysheetData.setTempUnstuddUnload(tallysheet.getTempUnstuddUnload());
        tallysheetData.setDeleted(false);
        
        if(tallysheet.isVerify()){
        	List<WSOInfo> wsoList = 	wsoInfoRepo.findWsoByTallysheet(tallysheetId);
        	for(WSOInfo  model : wsoList ){
        		Date myDate = tallysheetData.getStorageDate();
        		Calendar cal1 = Calendar.getInstance();
        		cal1.setTime(myDate);
        		cal1.add(Calendar.DATE, -1);
        		Date previousDate = cal1.getTime();
        		System.out.println("previousDate : "+previousDate);
        		model.setBillStartDate(previousDate);
        		model.setFirstBillDate(previousDate);
        		//Calendar tobillingdate = Calendar.getInstance();
   				//tobillingdate.setTime(previousDate);
    			//tobillingdate.set(Calendar.DAY_OF_MONTH, -1);
    			model.setBillEndDate(previousDate);
        		wsoInfoRepo.save(model);
        	}
        	tallysheetData.setVerify(true);
        }else{
        	tallysheetData.setVerify(false);
        }
        

        TallySheet updatedTallysheet = tallysheetService.saveTallysheet(tallysheetData);
        logger.info("Records successfully updated in tallysheet table where tallysheetId is "+tallysheetId);
        return new ResponseEntity<>(updatedTallysheet, HttpStatus.OK);
    }

    @DeleteMapping("/{tallysheetId}")
    public ResponseEntity<TallySheet> deleteTallysheet(@PathVariable Long tallysheetId){
        TallySheet tallysheet = tallysheetService.getTallysheet(Boolean.FALSE, tallysheetId);
        if(tallysheet == null){
        	logger.info("Exception in TallysheetController: Not able to delete record with tallysheetId: "+tallysheetId);
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tallysheet.setDeleted(true);
        TallySheet updatedTallysheet = tallysheetService.saveTallysheet(tallysheet);
        logger.info("Records successfully deleted from tallysheet table where tallysheetId is "+tallysheetId);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allTallysheetId")
    public @ResponseBody ResponseEntity<List<TallySheet>> allTallysheet(){
       List<TallySheet> tallySheet = tallysheetService.findTallysheetIdAndNo();
        System.out.println(tallySheet);
        logger.info("Records successfully retreived from tallysheet table.");
        return  new ResponseEntity<>(tallySheet,HttpStatus.OK);
    }

    @GetMapping("/getAllTallyByClientId/{clientId}")
    public List<TallySheet> getAllTallyByClientId(@PathVariable Long clientId){
        List<TallySheet> tallysheet = tallysheetRepo.findTallysheetByClientId(clientId);
        logger.info("Records successfully retreived from tallysheet table where clientId is "+clientId);
        return tallysheet;
    }

    @GetMapping("/getAllTallyByBetweenClientId/{clientId1}/{clientId2}")
    public List<TallySheet> getAllTallyByBetweenClientId(@PathVariable Long clientId1, @PathVariable Long clientId2){
        List<TallySheet> tallysheet = tallysheetRepo.findTallysheetByBetweenClientId(clientId1, clientId2);
        logger.info("Records successfully retreived from tallysheet table between clientId "+clientId1+" and "+clientId2);
        return tallysheet;
    }
    
    
    @GetMapping("/getTallysheetDetail/{tallySheetId}")
    public TallySheet getTallysheetDetail(@PathVariable Long tallySheetId){
    	TallySheet  returnObj = new TallySheet();
    	List<TallySheet> tallysheet =  tallysheetRepo.getTallysheetDetail(tallySheetId);
    	if(!tallysheet.isEmpty()){
    		returnObj = tallysheet.get(0);
    		return returnObj;
    	}
    	logger.info("Records successfully retreived from tallysheet table where tallySheetId is "+tallySheetId);
    	return returnObj;
    }
    
    
    @RequestMapping(value="/getAllTallysheetreports",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadWsoReport(){
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();
        List<TallySheet> dataList = tallysheetService.getAllTallysheet(Boolean.FALSE);
        for(TallySheet model : dataList){
            JSONObject  obj  = new JSONObject();
            obj.put("clientTitle", model.getClientInfo().getClientTitle());
            obj.put("tallysheetNumber", model.getTallysheetNumber());
            obj.put("storageDate", model.getStorageDate());
            obj.put("exVessel", model.getExVessel());
            obj.put("containerNo", model.getContainerNo());
            obj.put("tallysheetId", String.valueOf(model.getTallysheetId()));
            list.add(obj);

        }
        logger.info("Records successfully retreived from tallysheet table.");
        responsejson.put("data",list);
        return responsejson;
    }

    @RequestMapping(value="/getAllTallysheetreportsByTallyNo/{tallysheetNo}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadTallyReportByTallyNo(@PathVariable String tallysheetNo){
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();
        List<TallySheet> dataList = tallysheetRepo.findBytallysheetNumberIgnoreCaseContaining(tallysheetNo);
        for(TallySheet model : dataList){
            JSONObject  obj  = new JSONObject();
            obj.put("clientTitle", model.getClientInfo().getClientTitle());
            obj.put("tallysheetNumber", model.getTallysheetNumber());
            obj.put("storageDate", model.getStorageDate());
            obj.put("exVessel", model.getExVessel());
            obj.put("containerNo", model.getContainerNo());
            obj.put("tallysheetId", String.valueOf(model.getTallysheetId()));
            list.add(obj);

        }
        logger.info("Records successfully retreived from tallysheet table.");
        responsejson.put("data",list);
        return responsejson;
    }

    
    @RequestMapping(value="/getAllTallysheeetreports/{tallysheetId}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadWsoByIdReport(@PathVariable Long tallysheetId){
        JSONObject  responsejson  = new JSONObject();
        TallySheet  dataList = tallysheetRepo.findById(tallysheetId).get();

        JSONObject  obj  = new JSONObject();
        obj.put("tallysheetNumber", dataList.getTallysheetNumber());
        obj.put("clientTitle", dataList.getClientInfo().getClientTitle());
        obj.put("storageDate", dataList.getStorageDate());
        obj.put("tempRecorded", dataList.getTempRecorded());
        obj.put("exVessel", dataList.getExVessel());
        obj.put("tempUnstuddUnload", dataList.getTempUnstuddUnload());
        obj.put("refDry", dataList.getRefDry());
        obj.put("containerNo", dataList.getContainerNo());
        obj.put("lorryContainer", dataList.getLorryContainer());
        JSONArray list = new  JSONArray();
        Float ttlWsoQuantity = 0.0f;
        
        //long wsoId = 1;
        List<LotInfo> lotDataList = null;
        
        List<WSOInfo> wsodataList = wsoInfoRepo.findWsoByTallysheet(tallysheetId);
        for (WSOInfo model : wsodataList){
            JSONObject  obj1  = new JSONObject();
            
            lotDataList = lotInfoRepo.findLotByWsoId(model.getWsoId());
            for(LotInfo modelLot : lotDataList) {
            	ttlWsoQuantity += modelLot.getLotQuantity();
            }
            obj1.put("ttlWsoQuantity",ttlWsoQuantity);
            ttlWsoQuantity = 0.0f;
            obj1.put("wsoNo",model.getWsoNo());
                obj1.put("description",model.getDescription());
            obj1.put("totalWsoWeight",model.getTotalWsoWeight());
            obj1.put("totalNoOfPallets",model.getTotalNoOfPallets());
            obj1.put("storageTypeName",model.getStorageClass().getStorageTypeName());
            obj1.put("invoiceRate",model.getInvoiceRate());
            obj1.put("remarks",model.getRemarks());
           // obj1.put("remarks",model.getLotInfo());
            list.add(obj1);
        }
        responsejson.put("data",obj);
        responsejson.put("wsoDetail",list);
        logger.info("Records successfully retreived from tallysheet table where tallysheetId is "+tallysheetId);
        return responsejson;
    }
    
    @RequestMapping(value="/loadInventoryByClient/{clientId}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject  loadInventoryByDateAndClient(@PathVariable Long clientId, @RequestParam(value = "fromdate",required = false) String fromdate){
	 JSONObject  responsejson  = new JSONObject();
   
	 //String fromdate = "2018-01-01";
	 //Long clientId=1L;
	 /*String todate = "2018-04-01";*/
	 
//	 System.out.println("fromdate : "+fromdate);
//	 System.out.println("todate : "+todate);
	 Date fromDate1 = null;
	 
	
	try {
		fromDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
		//toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
	Float availableLot = 0.0f;
	List<TallySheet> tallyData = tallysheetRepo.findTallysheetByClientIdAndDate(clientId, fromDate1);
	JSONArray list = new  JSONArray();
	
	for(TallySheet model : tallyData) {
		List<WSOInfo> wsoData = wsoInfoRepo.findWsoByTallysheet(model.getTallysheetId());
		for(WSOInfo wsoModel : wsoData) {
			JSONObject  obj = new JSONObject();
			List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(wsoModel.getWsoId());
			for(LotInfo lotModel : lotData) {
				availableLot += lotModel.getCurrentQuantity();
				List<DeliveryList> deliveryData = deliveryListRepo.getDeliveryByLotAndDate(lotModel.getLotId(), fromDate1);
				for(DeliveryList deliveryModel : deliveryData) {
					availableLot += deliveryModel.getTotalQty();
				}
				if(availableLot > 0) {
					obj.put("availableLot", availableLot);
					obj.put("wsoNo", lotModel.getWsoInfo().getWsoNo());
					obj.put("lotNo", lotModel.getLotNo());
					obj.put("storageClasss", lotModel.getWsoInfo().getStorageClass().getStorageTypeName());
					obj.put("initialQuantity", lotModel.getInitialQuantity());
					obj.put("productionDate", lotModel.getProductionDate());
					obj.put("expiryDate", lotModel.getExpiryDate());
					obj.put("netWeight", lotModel.getUnitNetWeightInKg());
					obj.put("grossWeight", lotModel.getUnitNetWeightInKg()*availableLot);
					obj.put("storageDate", lotModel.getWsoInfo().getTallysheet().getStorageDate());
					obj.put("productDescription", lotModel.getWsoInfo().getDescription());
					
					list.add(obj);
					availableLot = 0.0f;
				}
				
			}
		}				
	}
	
	/*for(TallySheet model : tallyData) {
		List<WSOInfo> wsoData = wsoInfoRepo.findWsoByTallysheet(model.getTallysheetId());
		for(WSOInfo wsoModel : wsoData) {
			JSONObject  obj = new JSONObject();
			List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(wsoModel.getWsoId());
			for(LotInfo lotModel : lotData) {
				availableLot += lotModel.getCurrentQuantity();
			}
			List<DeliveryList> deliveryData = deliveryListRepo.getDeliveryByWsoAndDate(wsoModel.getWsoId(), fromDate1);
			for(DeliveryList deliveryModel : deliveryData) {
				availableLot += deliveryModel.getTotalQty();
			}
			obj.put("availableLot", availableLot);
			obj.put("wsoNo", wsoModel.getWsoNo());
			
			list.add(obj);
			availableLot = 0.0f;
		}				
	}*/
		
	responsejson.put("data",list);
	//responsejson.put("data1",b1);
    return responsejson;
 }
 

}

package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.service.LotService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lotInfos")
@CrossOrigin("*")
public class LotController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LotService lotService;

    @Autowired
    private LotInfoRepo lotInfoRepo;

    @Autowired
    private WSOInfoRepo wsoInfoRepo;

    @Autowired
    private TallysheetRepo tallysheetRepo;
    
    @Autowired
    private DeliveryListRepo deliveryListRepo;

    @GetMapping("/")
    public List<LotInfo> fingAllLotInfo(){
        return lotService.getAllLotInfo(Boolean.FALSE);
    }

    @GetMapping("/lot/{wsoId}")
    public List<LotInfo> fingLotInfoOf24Hours(@PathVariable Long wsoId){

        return lotService.getLotInfoWithin24Hours(wsoId, new Date(System.currentTimeMillis() - 24*60*60*1000));
    }


    @GetMapping("/{lotInfoId}")
    public ResponseEntity<LotInfo> findLotInfoById(@PathVariable Long lotInfoId){
        LotInfo lotInfo = lotService.getLotInfo(Boolean.FALSE, lotInfoId);
        logger.info("Records successfully retrieved from Lot Info table based on Lot Info Id.");
        if(lotInfo == null)
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(lotInfo, HttpStatus.OK);
    }

    @PostMapping("/")
    public LotInfo saveLotInfo(@RequestBody LotInfo lotInfo){
    	Integer initalQty = lotInfo.getLotQuantity();
    	Integer currentQty = lotInfo.getLotQuantity();
    	lotInfo.setCurrentQuantity(currentQty);
    	lotInfo.setInitialQuantity(initalQty);

    	Long wsoInfoId = lotInfo.getWsoInfo().getWsoId();
        Long tallyId = wsoInfoRepo.findById(wsoInfoId).get().getTallysheet().getTallysheetId();
        TallySheet tallySheet = tallysheetRepo.findById(tallyId).get();
        Float grndTtlQty = tallySheet.getGrndTtlQty();
        Float grndTot = lotInfoRepo.getTallysheetGrandTotal(tallyId);
        //Float grndTot = tallySheet.getGrndTot();

        Float grndTtlQty1 = lotInfo.getLotQuantity()+grndTtlQty;
        //Float grndTot1 = lotInfo.getUnitGrossWeightInKg()+grndTot;
        //Float totUnitWt = lotService.findTotalUnitWeightLotByWsoId(wsoInfoId);
        Float totUnitWt = grndTot;
        logger.info("WSOID "+wsoInfoId+" totUnitWt: "+totUnitWt);
        if (totUnitWt != null)
        {
        	Float grndTot1 = (float)Math.round(totUnitWt + (lotInfo.getUnitNetWeightInKg() * lotInfo.getLotQuantity()));
        	logger.info("WSOID "+wsoInfoId+" Grand Total: "+grndTot1);
            tallySheet.setGrndTtlQty(grndTtlQty1);
            tallySheet.setGrndTot(grndTot1);
        }
        else {
        	Float grndTot1 = (float)Math.round(lotInfo.getUnitNetWeightInKg() * lotInfo.getLotQuantity());
        	logger.info("WSOID "+wsoInfoId+" Grand Total: "+grndTot1);
            tallySheet.setGrndTtlQty(grndTtlQty1);
            tallySheet.setGrndTot(grndTot1);
        }
        	
        tallysheetRepo.save(tallySheet);
        return lotService.saveLotInfo(lotInfo);
    }
    

    @PutMapping("/{lotInfoId}")
    public ResponseEntity<LotInfo> updateLotInfo(@PathVariable Long lotInfoId, @Valid @RequestBody LotInfo lotInfo){
        LotInfo lotInfoData = lotService.getLotInfo(Boolean.FALSE, lotInfoId);
        logger.info("Records successfully retrieved from Lot Info table based on Lot Info Id.");
        if(lotInfoData == null)
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LotInfo existingData=lotInfoRepo.findById(lotInfoId).get();
    	Integer existingDataLotQuantity = existingData.getLotQuantity();
    	Float existingDataGrossWt = existingData.getUnitGrossWeightInKg();
    	Float existingDataUnitWt = existingData.getUnitNetWeightInKg();
    	
    	Long wsoInfoId = lotInfo.getWsoInfo().getWsoId();
        Long tallyId = wsoInfoRepo.findById(wsoInfoId).get().getTallysheet().getTallysheetId();
        TallySheet tallySheet = tallysheetRepo.findById(tallyId).get();
        logger.info("Records successfully retrieved from TallySheet based on tallyId");
        Float grndTtlQty = tallySheet.getGrndTtlQty();
        Float grndTot = lotInfoRepo.getTallysheetGrandTotal(tallyId);
        //Float grndTot = tallySheet.getGrndTot();

        Integer ttlQty = lotInfo.getLotQuantity();
        Float grsWt = lotInfo.getUnitGrossWeightInKg();
        Float grndTtlQty1 = grndTtlQty - existingDataLotQuantity + ttlQty;
        //Float grndTot1 = grndTot -existingDataGrossWt + grsWt;
        
        //Float totUnitWt = lotService.findTotalUnitWeightLotByWsoId(wsoInfoId) - (existingDataUnitWt * existingDataLotQuantity);
        Float totUnitWt = grndTot - (existingDataUnitWt * existingDataLotQuantity);
        logger.info("WSOID "+wsoInfoId+" totUnitWt: "+totUnitWt);
        if (totUnitWt != null)
        {
        	Float grndTot1 = (float)Math.round(totUnitWt + (lotInfo.getUnitNetWeightInKg() * lotInfo.getLotQuantity()));
        	logger.info("WSOID "+wsoInfoId+" Grand Total: "+grndTot1);
        	tallySheet.setGrndTtlQty(grndTtlQty1);
            System.out.println(tallySheet.getGrndTtlQty());
            tallySheet.setGrndTot(grndTot1);
            System.out.println(tallySheet.getGrndTot());
        }
        else {
        	Float grndTot1 = (float)Math.round(lotInfo.getUnitNetWeightInKg() * lotInfo.getLotQuantity());
        	logger.info("WSOID "+wsoInfoId+" Grand Total: "+grndTot1);
        	tallySheet.setGrndTtlQty(grndTtlQty1);
            System.out.println(tallySheet.getGrndTtlQty());
            tallySheet.setGrndTot(grndTot1);
            System.out.println(tallySheet.getGrndTot());
        }
        
        tallysheetRepo.save(tallySheet);

        lotInfoData.setLotNo(lotInfo.getLotNo());
        lotInfoData.setPackages(lotInfo.getPackages());
        lotInfoData.setLotQuantity(lotInfo.getLotQuantity());
        lotInfoData.setExpiryDate(lotInfo.getExpiryDate());
        lotInfoData.setProductionDate(lotInfo.getProductionDate());
        lotInfoData.setRoomNo(lotInfo.getRoomNo());
        lotInfoData.setUnitNetWeightInKg(lotInfo.getUnitNetWeightInKg());
        lotInfoData.setUnitWeightInKg(lotInfo.getUnitWeightInKg());
        lotInfoData.setUnitGrossWeightInKg(lotInfo.getUnitGrossWeightInKg());
        lotInfoData.setUnitQuantity(lotInfo.getUnitQuantity());
        lotInfoData.setDeleted(false);
    	
    	        
        LotInfo updatedLotInfo = lotService.saveLotInfo(lotInfoData);
        return new ResponseEntity<>(updatedLotInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{lotInfoId}")
    public String deleteLotInfo(@PathVariable Long lotInfoId){

        LotInfo lotInfo = lotInfoRepo.findById(lotInfoId).get();
        logger.info("Records retrieved from Lot Info table based on Lot Info Id");
        Integer lotQty = lotInfo.getLotQuantity();
        //Float grsWt = lotInfo.getUnitGrossWeightInKg();
        Float unitNetWt = lotInfo.getUnitNetWeightInKg() * lotQty;
        Long tallyId = lotInfo.getWsoInfo().getTallysheet().getTallysheetId();
        
        TallySheet tallyData = tallysheetRepo.findById(tallyId).get();
        logger.info("Records retrieved from TallySheet table based on tally Id");
        tallyData.setGrndTot((float)Math.round(tallyData.getGrndTot() - unitNetWt));
        tallyData.setGrndTtlQty(tallyData.getGrndTtlQty() - lotQty);
        tallysheetRepo.save(tallyData);
        
        lotInfo.setDeleted(Boolean.TRUE);
        lotService.saveLotInfo(lotInfo);
        // List<LotInfo> lotInfo = lotService.getAllLotInfo(Boolean.FALSE);
        return  "OK";
    }
    @GetMapping("/allLotId")
    public @ResponseBody ResponseEntity<List<LotInfo>> allLot(){
        List<LotInfo> lotInfos = lotService.findLotIdAndNo();
        logger.info("Records retrieved from Lot Info table based on Lot Info Id and Lot No");
        System.out.println(lotInfos);
        return  new ResponseEntity<>(lotInfos,HttpStatus.OK);
    }

    @GetMapping("/getAllLotByWsoId/{wsoId}")
    public List<LotInfo> getAllWsoByTallyId(@PathVariable Long wsoId){
        List<LotInfo> lotInfos = lotInfoRepo.findLotByWsoId(wsoId);
        logger.info("Records retrieved from Lot Info based on WSO Id");
        return lotInfos;
    }
    
    @GetMapping("/getLotDetail/{lotId}")
    public LotInfo getLotDetail(@PathVariable Long lotId){
    	LotInfo model = new LotInfo();
        List<LotInfo> lotInfos = lotInfoRepo.findLotDetailByLotId(lotId);
        logger.info("Records retrieved from Lot Info based on Lot Id");
        if(!lotInfos.isEmpty()){
        	 model =lotInfos.get(0);
        }
        return model;
    }
    
    
    @PostMapping("/getLotDetailByClientId")
    public JSONObject getLotDetailByClientId(@RequestParam("clientId") Long clientId,
    		@RequestParam(value = "date1",required = false) String date1){
    	Date date = null;
		
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
    	//System.out.println("Lot List"+lotInfoList.toString());
    	JSONObject response = new JSONObject();
    	JSONArray list = new  JSONArray();
    	
    	for(String description: lotDescList) {
    		JSONArray list1 = new  JSONArray();
        	JSONObject  obj1  = new JSONObject();
        	
    		List<LotInfo> lotInfoList = new ArrayList<LotInfo>();
    		lotInfoList = lotInfoRepo.findLotDetailByWsoIdList(wsoIdList, description);
    		System.out.println("Lot List:"+lotInfoList.toString());
    		System.out.println("__________________________");
    		for(LotInfo l: lotInfoList) {
    			List<DeliveryList> delList = deliveryListRepo.getDeliveryByLotIdANDDate(l.getLotId(),date);
    			logger.info("Records retrieved from Delivery List table based on Lot Id and Date");
    			List<DeliveryList> delListCheck = deliveryListRepo.getDeliveryByLotIdANDBetweenCurentAndGivenDate(l.getLotId(),date);
    			boolean check = false;
    			if(delList.isEmpty()) {
    				int qtyToAdd = 0;
    				if(!delListCheck.isEmpty())
	    			{
	    				qtyToAdd = deliveryListRepo.getSumOfDeliveryByLotIdANDBetweenCurentAndGivenDate(l.getLotId(),date);
	    				logger.info("Yes there are delivery list for this lot id: " + l.getLotId());
	    				check = true;
	    				logger.info("Value of check: "+check);
	    			}
    				JSONObject obj = new JSONObject();
    				obj.put("lotDesc", l.getDescription());
            		obj.put("lotNo", l.getLotNo());
            		obj.put("wsoNo", l.getWsoInfo().getWsoNo());
            		obj.put("date", l.getWsoInfo().getTallysheet().getStorageDate());
            		//obj.put("date", delList.get(i).getDateOfDelivery());
            		obj.put("initialQty",l.getInitialQuantity());
            		//obj.put("initialQty",initQty - qtyDelivered);
            		//initQty = initQty - qtyDelivered;
            		obj.put("currentQty", l.getCurrentQuantity()+qtyToAdd);
            		//obj.put("currentQty", initQty - delList.get(i).getTotalQty());
            		obj.put("prodDate", l.getProductionDate());
            		obj.put("expDate", l.getExpiryDate());
            		obj.put("unitKg", l.getUnitWeightInKg());
            		//obj.put("unitQtyLot", l.getUnitGrossWeightInKg());
            		obj.put("unitQtyLot", l.getUnitQuantity());
            		obj.put("storageClass", l.getWsoInfo().getStorageClass().getStorageTypeName());
            		if(check == false) {
        				if(l.getCurrentQuantity()!=0) {
        					list1.add(obj);
        				}
        			}else {
        				list1.add(obj);
        			}
            		/*if(l.getCurrentQuantity()!=0)
            			list1.add(obj);*/
            		/*if(initQty - d.getTotalQty()>0)
            			list1.add(obj);*/
            		
            		//qtyDelivered = delList.get(i).getTotalQty();
    			}
    			System.out.println("delList: "+delList.toString());
    			int initQty = l.getInitialQuantity();
    			int qtyDelivered = 0;
    			
    			if(!delList.isEmpty()) {
    				
	    			if(!delListCheck.isEmpty())
	    			{
	    				logger.info("Yes there are delivery list for this lot id: " + l.getLotId());
	    				check = true;
	    				logger.info("Value of check: "+check);
	    				logger.info("DelListCheck :");
	    				int sizeOfDelListCheck = delListCheck.size();
	    				for(int i=0;i<sizeOfDelListCheck;i++) {
	    					System.out.println("Delivery "+i+" : "+delListCheck.get(i).toString());
	    				}
	    			}
    				for(int i =0; i<delList.size();i++) {
        				JSONObject obj = new JSONObject();
        				obj.put("lotDesc", l.getDescription());
                		obj.put("lotNo", l.getLotNo());
                		obj.put("wsoNo", l.getWsoInfo().getWsoNo());
                		obj.put("date", l.getWsoInfo().getTallysheet().getStorageDate());
                		//obj.put("date", delList.get(i).getDateOfDelivery());
                		//obj.put("initialQty",l.getInitialQuantity());
                		initQty = initQty - qtyDelivered;
                		//obj.put("initialQty",initQty - qtyDelivered);
                		//obj.put("initialQty",initQty);
                		obj.put("initialQty",l.getInitialQuantity());
                		//obj.put("currentQty", l.getCurrentQuantity());
                		obj.put("currentQty", initQty - delList.get(i).getTotalQty());
                		obj.put("prodDate", l.getProductionDate());
                		obj.put("expDate", l.getExpiryDate());
                		obj.put("unitKg", l.getUnitWeightInKg());
                		//obj.put("unitQtyLot", l.getUnitGrossWeightInKg());
                		obj.put("unitQtyLot", l.getUnitQuantity());
                		obj.put("storageClass", l.getWsoInfo().getStorageClass().getStorageTypeName());
                		
                		if(i == delList.size()-1 && (initQty - delList.get(i).getTotalQty())!=0) {
                			if(check == false) {
                				if(l.getCurrentQuantity()!=0) {
                					list1.add(obj);
                				}
                			}else {
                				list1.add(obj);
                			}
                		}
                				
                		/*if(initQty - d.getTotalQty()>0)
                			list1.add(obj);*/
                		
                		qtyDelivered = delList.get(i).getTotalQty();
        			}
    			}
    			
        		
    		}
    		System.out.println("Value in Array objList1: "+list1.toJSONString());
    		list.add(list1);
    	}
    	response.put("data", list);
    	return response;
    }
}

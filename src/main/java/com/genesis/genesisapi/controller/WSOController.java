package com.genesis.genesisapi.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.genesis.genesisapi.exceptions.WareHouseInfoNotFoundException;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.WarehouseInfo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.ReportsRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.repository.WarehouseInfoRepo;
import com.genesis.genesisapi.service.WSOService;


@RestController
@RequestMapping("/wsoInfos")
@CrossOrigin("*")
public class WSOController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WSOService wsoService;

    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    @Autowired
    private TallysheetRepo tallysheetRepo;
    @Autowired
    private ReportsRepo reportsRepo;
    
    @Autowired
    private WarehouseInfoRepo warehouseInfoRepo;
    
    @Autowired
    private LotInfoRepo lotInfoRepo;

    @Value("${current.gst}")
    private String gstFromConfiguratin;
    

    @GetMapping("/")
    public List<WSOInfo> fingAllWSOInfo(){
    	logger.info("Records successfully retreived from wsoInfo table.");
        return wsoService.getAllWSOInfo(Boolean.FALSE);
    }

    @GetMapping("/{wsoInfoId}")
    public ResponseEntity<WSOInfo> findWSOInfoById(@PathVariable Long wsoInfoId) throws WareHouseInfoNotFoundException{
        WSOInfo WSOInfo = wsoService.getWSOInfo(Boolean.FALSE, wsoInfoId);
        if(WSOInfo == null){
            logger.info("Exception in WSOController: WSO Id not found");
            throw new WareHouseInfoNotFoundException(wsoInfoId);
        	//return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
        	logger.info("Records successfully retreived from wsoInfo table where wsoInfoId = "+wsoInfoId);
            return new ResponseEntity<>(WSOInfo, HttpStatus.OK);
    }
    @ExceptionHandler(WareHouseInfoNotFoundException.class)
	public ModelAndView handleWareHouseInfoNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("WareHouseInfoNotFound");
	    
	    return modelAndView;
	}
    
    @GetMapping("/getAllWsoByClientId/{clientId}")
    public List<WSOInfo> getAllWsoByClientIdWithDlNo(@PathVariable Long clientId){
    	List<WSOInfo> respList = new ArrayList<WSOInfo>();
    	List<Long> tallyListId = new ArrayList<Long>();
    	List<TallySheet> tallyList  = tallysheetRepo.getApprovedTallySheetByClient(clientId);
    	if(!tallyList.isEmpty()){
    		for( TallySheet obj : tallyList){
    			tallyListId.add(obj.getTallysheetId());
    		}
    	}
    	
    	respList = 	wsoService.loadAllWsoByclientAndTallyAproved(tallyListId);
    	logger.info("Records successfully retreived from wsoInfo table where clientId = "+clientId);
    	return respList; 
    }
    
    @GetMapping("/getAllWsoByClientIdWithDlNo/{clientId}")
    public List<WSOInfo> getAllWsoByClientId(@PathVariable Long clientId){
    	List<WSOInfo> respList = new ArrayList<WSOInfo>();
    	List<Long> tallyListId = new ArrayList<Long>();
    	List<TallySheet> tallyList  = tallysheetRepo.getApprovedTallySheetByClient(clientId);
    	if(!tallyList.isEmpty()){
    		for( TallySheet obj : tallyList){
    			tallyListId.add(obj.getTallysheetId());
    		}
    	}
    	
    	List<Long> wsoId = new ArrayList<Long>();
    	respList = 	wsoService.loadAllWsoByclientAndTallyAproved(tallyListId);
    	Integer lotQty = 0;
    	for(WSOInfo model : respList) {
    		List<LotInfo> datalot = lotInfoRepo.findLotByWsoId(model.getWsoId());
    		for(LotInfo modellot : datalot) {
    			List<LotInfo> lot = lotInfoRepo.findLotDetailByLotId(modellot.getLotId());
    			lotQty += lot.get(0).getCurrentQuantity();
    		}
    		if(lotQty == 0) {
    			wsoId.add(model.getWsoId());
    		}
    		lotQty = 0;
    	}
    	if(wsoId.size() == 0) {
    		respList = 	wsoService.loadAllWsoByclientAndTallyAproved(tallyListId);
    	}else {
    		respList = 	wsoInfoRepo.loadAllWsoByclientAndTallyAprovedAndExcept(tallyListId, wsoId);
    	}
    	
    	logger.info("Records successfully retreived from wsoInfo table where clientId = "+clientId);
    	return respList; 
    }

    

    @PostMapping("/")
    public WSOInfo saveWSOInfo(@RequestBody WSOInfo wsoInfo){
    	//List<WarehouseInfo> dataList  = warehouseInfoRepo.findAll();
        float gst  = Float.parseFloat(gstFromConfiguratin);
        /*if(null != dataList && !dataList.isEmpty()){
        	gst  = dataList.get(0).getApplicableGst();
        }*/
    	
        String tranWsoWt = wsoInfo.getTransWsoWt();
        Float totalWsoWeight = Float.valueOf(tranWsoWt);
        wsoInfo.setTotalWsoWeight(totalWsoWeight);
        wsoInfo.setBillStartDate(new Date());
        wsoInfo.setBillEndDate(new Date());
        wsoInfo.setFirstBillDate(new Date());
        WSOInfo wInfo = wsoInfoRepo.findTop1ByOrderByWsoIdDesc();
    	if(wInfo != null) {
    		String wsoNo = wInfo.getWsoNo();
    		String[] arr = wsoNo.split("G");
    		int val= Integer.parseInt(arr[1])+1;
    		
    		//wsoNo = "G0000"+val;
    		int tempNo = 100000 + val;
    		String tempString = String.valueOf(tempNo);
    		wsoNo = tempString.replaceFirst("1", "G");
    		
    		wsoInfo.setWsoNo(wsoNo);
    	}else {
    		wsoInfo.setWsoNo("G00001");
    	}

        java.util.Date now = new Date();
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(now);
        myCal.add(Calendar.MONTH, +1);
        now = myCal.getTime();
        wsoInfo.setNextBillDate(now);
       try {
		String remark = URLDecoder.decode(wsoInfo.getRemarks(), "UTF-8");
		String descri = URLDecoder.decode(wsoInfo.getDescription(), "UTF-8");
		wsoInfo.setRemarks(remark);
		wsoInfo.setDescription(descri);
		wsoInfo.setGst(gst);
		wsoInfo.setIsfirstBilling(true);
		wsoInfo.setWsoId(-1);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		logger.info("Exception in WSOController: "+e.getMessage());
		e.printStackTrace();
	}
       logger.info("Records successfully saved in wsoInfo table.");
        return wsoService.saveWSOInfo(wsoInfo);
    }

    @PutMapping("/{wsoInfoId}")
    public WSOInfo updateWSOInfo(@PathVariable Long wsoInfoId, @Valid @RequestBody WSOInfo wsoInfo){
        WSOInfo wsoInfoData = wsoService.getWSOInfo(Boolean.FALSE, wsoInfoId);
        String tranWsoWt = wsoInfo.getTransWsoWt();
        //System.out.println("TranWSOWt:"+tranWsoWt);
        Float totalWsoWeight = Float.valueOf(tranWsoWt);
        //System.out.println("Total WSO Weight:"+totalWsoWeight);
        //Float testWSoWt = Float.parseFloat(tranWsoWt);
        //System.out.println("Test WSO Weight:"+testWSoWt);
        wsoInfoData.setTotalWsoWeight(totalWsoWeight);
        wsoInfoData.setCargo(wsoInfo.getCargo());
        wsoInfoData.setRemarks(wsoInfo.getRemarks());
        //wsoInfoData.setStorageClass(wsoInfo.getStorageClass());
        wsoInfoData.setTotalNoOfPallets(wsoInfo.getTotalNoOfPallets());
        /*wsoInfoData.setGst(wsoInfo.getGst());*/
        wsoInfoData.setInvoiceRate(wsoInfo.getInvoiceRate());
        wsoInfoData.setDescription(wsoInfo.getDescription());
        wsoInfoData.setDeleted(false);
        wsoInfoData.setWsoNo(wsoInfoData.getWsoNo());
        wsoInfoData.setFirstBillDate(wsoInfo.getFirstBillDate());
        //Long stId = wsoInfo.getStorageClass().getStorageTypeId();
        //wsoInfoData.setStorageClass(wsoInfo.getStorageClass());
        WSOInfo updatedWSOInfo = wsoService.saveWSOInfo(wsoInfoData);
        logger.info("Records successfully updated in wsoInfo table where wsoInfoId = "+wsoInfoId);
        return updatedWSOInfo;
    }


    @DeleteMapping("/{wsoInfoId}")
    public String deleteLotInfo(@PathVariable Long wsoInfoId){
        WSOInfo wsoInfo = wsoService.getWSOInfo(Boolean.FALSE, wsoInfoId);
        //List<LotInfo> lotInfos = wsoInfoRepo.findByisDeletedAndWsoId(Boolean.FALSE, wsoInfo.getWsoId());

        Long countLot = lotInfoRepo.findCountLotByWsoId(wsoInfo.getWsoId());
        System.out.println("CountLot : "+countLot);
        if (countLot > 0){
            return "cancel";
        }else{
            /*List<LotInfo> lotInfos = lotInfoRepo.findLotByWsoId(wsoInfo.getWsoId());
        for(LotInfo model : lotInfos){
            model.setDeleted(Boolean.TRUE);
            lotInfoRepo.save(model);
        }*/
       wsoInfo.setDeleted(Boolean.TRUE);
       logger.info("Record successfully deleted in wsoInfo table where wsoInfoId = "+wsoInfoId);
        wsoService.saveWSOInfo(wsoInfo);
            return  "OK";
        }
    }

    @GetMapping("/allWsoId")
    public @ResponseBody ResponseEntity<List<WSOInfo>> allTallysheet(){
        List<WSOInfo> wsoInfos = wsoService.findWsoIdAndNo();
        System.out.println(wsoInfos);
        logger.info("Records successfully retreived from wsoInfo table.");
        return  new ResponseEntity<>(wsoInfos,HttpStatus.OK);
    }

    @GetMapping("/getAllWsoByTallyId/{tallyId}")
    public List<WSOInfo> getAllWsoByTallyId(@PathVariable Long tallyId){

        List<WSOInfo> wsoInfos = wsoInfoRepo.findWsoByTallysheetId(tallyId);
        logger.info("Records successfully retreived from wsoInfo table where tallyId = "+tallyId);
        return wsoInfos;
        //return new ModelAndView("tallysheet","tallysheet",tallysheet);
    }

    @GetMapping("/getAllWsoByTallyIdAndDate/{tallyId}")
    public List<WSOInfo> getAllWsoByTallyIdAndDate(@PathVariable Long tallyId){
        List<WSOInfo> wsoInfos = wsoInfoRepo.findWsoByTallysheetIdAndDate(tallyId, new Date());
        System.out.println(wsoInfos);
        logger.info("Records successfully retreived from wsoInfo table where tallyId = "+tallyId);
        return wsoInfos;
        //return new ModelAndView("tallysheet","tallysheet",tallysheet);
    }
    
    @RequestMapping(value="/loadAllWsos",method={RequestMethod.GET,RequestMethod.POST})
    public List<WSOInfo> loadAllBill(@RequestParam(value = "clientId",required = false) String clientId,@RequestParam(value = "tallysheetId",required = false) String tallysheetId){
    	if("".equalsIgnoreCase(tallysheetId)){
    		List<WSOInfo> dataList = new ArrayList<WSOInfo>();
    		return dataList;
    	}else{
    		Long tallyNo = 0L;
    		try{
    			tallyNo = Long.parseLong(tallysheetId);
    		}catch(Exception e){logger.info("Exception in WSOController: "+e.getMessage());}
    		return wsoInfoRepo.findWsoByTallysheet(tallyNo);
    	}
    	
    	
    }
    
    @RequestMapping(value="/loadAllWsosByWsoNo",method={RequestMethod.GET,RequestMethod.POST})
    public List<WSOInfo> loadAllWsosByWsoNo(@RequestParam(value = "clientId",required = false) String clientId,
											@RequestParam(value = "wsoNo",required = false) String wsoNo,
											@RequestParam(value = "tallysheetId",required = false) String tallysheetId){
    	if("".equalsIgnoreCase(tallysheetId)){
    		List<WSOInfo> dataList = new ArrayList<WSOInfo>();
    		return dataList;
    	}else{
    		Long tallyNo = 0L;
    		try{
    			tallyNo = Long.parseLong(tallysheetId);
    		}catch(Exception e){logger.info("Exception in WSOController: "+e.getMessage());}
    		return wsoInfoRepo.findWsoByTallysheetAndWso(tallyNo, wsoNo);
    	}
    	
    	
    }
    
    @RequestMapping(value="/setdTallytoSession",method={RequestMethod.GET,RequestMethod.POST})
    public List<WSOInfo> setdTallytoSession(@RequestParam(value = "clientInfo",required = false) String clientInfo, @RequestParam(value = "tallysheetId",required = false) String tallysheetNumber,  @RequestParam(value = "change",required = false) String change,  HttpServletRequest request){
    	request.getSession().setAttribute("clientId", clientInfo);;
    	request.getSession().setAttribute("tallyId", tallysheetNumber);;
    	request.getSession().setAttribute("change", change);;
    	List<WSOInfo> data = new ArrayList<WSOInfo>();
    	logger.info("Records successfully retreived from wsoInfo table.");
    	return data;
    }
    
    @RequestMapping(value="/loadsessionDetail",method={RequestMethod.GET,RequestMethod.POST})
    public List<TallySheet> loadsessionDetail(HttpServletRequest request){
    	TallySheet  obj = new TallySheet();
    	List<TallySheet> data = new ArrayList<TallySheet>();
    	String clientId = (String)request.getSession().getAttribute("clientId");
    	String tallySheetNo = (String)request.getSession().getAttribute("tallyId");
    	request.getSession().setAttribute("clientId", "");;
    	request.getSession().setAttribute("tallyId", "");;
    	if(clientId != ""){
    		Long tallyNo = 0L;
    		Long clientInfo = 0L;
    		try{
    			clientInfo = Long.parseLong(clientId);
    			tallyNo = Long.parseLong(tallySheetNo);
    			List<TallySheet>  datas = tallysheetRepo.findTallysheetByClientId(clientInfo,tallyNo);
    			if(!datas.isEmpty()){
    				return datas;
    			}else{
    				return data;
    			}
    		}catch(Exception e){logger.info("Exception in WSOController: "+e.getMessage());}
    		
    	}else{
    		logger.info("Records successfully retreived from wsoInfo table.");
    		return data;
    	}
    	logger.info("Records successfully retreived from wsoInfo table.");
    	return data;
    }
    
    @GetMapping("/getWsoDetail/{wsoId}")
    public WSOInfo getLotDetail(@PathVariable Long wsoId){
    	logger.info("Records successfully retreived from wsoInfo table where wsoId = "+wsoId);
        return wsoInfoRepo.findByisDeletedAndWsoId(false, wsoId);
    }
    
    @RequestMapping(value="/getWsoReports",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject getWsoReports(@RequestParam(value = "clientId",required = false) String clientId){
    	JSONObject  responsejson  = new JSONObject();
    	responsejson.put("data", reportsRepo.getwsoReports());
    	logger.info("Records successfully retreived from wsoInfo table.");
    	return responsejson;
    }
    
    @RequestMapping(value="/getBillsreports",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject getBillsreports(@RequestParam(value = "clientId",required = false) String clientId){
    	JSONObject  responsejson  = new JSONObject();
    	responsejson.put("data", reportsRepo.getBillsreports());
    	logger.info("Records successfully retreived from wsoInfo table.");
    	return responsejson;
    }
    
    @RequestMapping(value="/getAllWsoreports",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadWsoReport(){
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();
          List<WSOInfo>  dataList = wsoService.getAllWSOInfo(Boolean.FALSE);
          for(WSOInfo model : dataList){
              JSONObject  obj  = new JSONObject();
              obj.put("wsoNo", model.getWsoNo());
              obj.put("totalWsoWeight", model.getTotalWsoWeight());
              obj.put("cargo", model.getCargo());
              obj.put("totalNoOfPallets", model.getTotalNoOfPallets());
              //obj.put("totalWsoWeight", model.getLotInfo().get);
              obj.put("wsoId", String.valueOf(model.getWsoId()));
              list.add(obj);

          }
        logger.info("Records successfully retreived from wsoInfo table.");
        responsejson.put("data",list);
        return responsejson;
    }

    @RequestMapping(value="/getAllWsoreportsByWsoNo/{wsoNo}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadWsoReportByWsoNo(@PathVariable String wsoNo){
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();
          List<WSOInfo>  dataList = wsoInfoRepo.findByWsoNoIgnoreCaseContaining(wsoNo);
          for(WSOInfo model : dataList){
              JSONObject  obj  = new JSONObject();
              obj.put("wsoNo", model.getWsoNo());
              obj.put("totalWsoWeight", model.getTotalWsoWeight());
              obj.put("cargo", model.getCargo());
              obj.put("totalNoOfPallets", model.getTotalNoOfPallets());
              //obj.put("totalWsoWeight", model.getLotInfo().get);
              obj.put("wsoId", String.valueOf(model.getWsoId()));
              list.add(obj);

          }
        logger.info("Records successfully retreived from wsoInfo table.");
        responsejson.put("data",list);
        return responsejson;
    }

    
    @RequestMapping(value="/getAllWsoreports/{wsoId}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadWsoByIdReport(@PathVariable Long wsoId){
        JSONObject  responsejson  = new JSONObject();
        WSOInfo  dataList = wsoService.getWSOInfo(Boolean.FALSE, wsoId);

        JSONObject  obj  = new JSONObject();
        obj.put("tallysheetNumber", dataList.getTallysheet().getTallysheetNumber());
        obj.put("clientTitle", dataList.getTallysheet().getClientInfo().getClientTitle());
        obj.put("refDry", dataList.getTallysheet().getRefDry());
        obj.put("tempRecorded", dataList.getTallysheet().getTempRecorded());
        obj.put("wsoNo", dataList.getWsoNo());
        obj.put("totalWsoWeight", dataList.getTotalWsoWeight());
        obj.put("totalPallets", dataList.getTotalNoOfPallets());
        obj.put("remarks", dataList.getRemarks());
        obj.put("storageDate", dataList.getBillStartDate());
        obj.put("exVessel", dataList.getTallysheet().getExVessel());


        JSONArray list = new  JSONArray();
        List<LotInfo> lotdataList = lotInfoRepo.findLotByWsoId(wsoId);
        for (LotInfo model : lotdataList){
            JSONObject  obj1  = new JSONObject();
            obj1.put("wsoNo",dataList.getWsoNo());
            obj1.put("lotNo",model.getLotNo());
            obj1.put("description",model.getDescription());
            obj1.put("totalPallets",model.getWsoInfo().getTotalNoOfPallets());
            obj1.put("lotQuantity",model.getLotQuantity());
            obj1.put("unitGrossWeightInKg",model.getUnitGrossWeightInKg());
            obj1.put("lotWeight",model.getUnitNetWeightInKg());
            obj1.put("currentQuantity",model.getCurrentQuantity());
            list.add(obj1);
        }
        responsejson.put("data",obj);
        responsejson.put("lotDetail",list);
        logger.info("Records successfully retreived from wsoInfo table.");
        return responsejson;
    }

    @RequestMapping(value="/getOnlyWsoreports/{wsoId}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadOnlyWsoByIdReport(@PathVariable Long wsoId){
        JSONObject  responsejson  = new JSONObject();

        JSONArray list = new  JSONArray();
        List<LotInfo> lotdataList = lotInfoRepo.findLotByWsoId(wsoId);
        int lotQuantity = 0;
        for (LotInfo model : lotdataList){
            lotQuantity += model.getLotQuantity();
        }
        WSOInfo  dataList = wsoService.getWSOInfo(Boolean.FALSE, wsoId);

        JSONObject  obj  = new JSONObject();
        obj.put("tallysheetNumber", dataList.getTallysheet().getTallysheetNumber());
        obj.put("clientTitle", dataList.getTallysheet().getClientInfo().getClientTitle());
        obj.put("refDry", dataList.getTallysheet().getRefDry());
        obj.put("tempRecorded", dataList.getTallysheet().getTempRecorded());
        obj.put("wsoNo", dataList.getWsoNo());
        obj.put("totalWsoWeight", dataList.getTotalWsoWeight());
        obj.put("totalPallets", dataList.getTotalNoOfPallets());
        obj.put("remarks", dataList.getRemarks());
        obj.put("description", dataList.getDescription());
        obj.put("storageDate", dataList.getTallysheet().getStorageDate());
        obj.put("lotQuantity", lotQuantity);
        obj.put("exVessel", dataList.getTallysheet().getExVessel());


        responsejson.put("data",obj);
       // responsejson.put("lotDetail",list);
        logger.info("Records successfully retreived from wsoInfo table.");
        return responsejson;
    }
   
    @GetMapping(value="/getLotInfoGrossWeight/{wsoId}")
    public JSONObject getLotInfoGrossWeight(@PathVariable Long wsoId, @RequestParam(value = "unitGrossWeightInKg",required = false) String unitGrossWeightInKg
    		, @RequestParam(value = "lotId",required = false) String lotId){
        JSONObject  responsejson  = new JSONObject();
        JSONObject  obj  = new JSONObject();
        
        
        float newGrosswt = Float.valueOf(unitGrossWeightInKg);
        //List<LotInfo> lotInfos = lotInfoRepo.findLotByWsoId(wsoId);
        float grossWeight = 0.0f;
        float wsoWeight = 0.0f;
		/*
		 * for(LotInfo model : lotInfos) { grossWeight +=
		 * model.getUnitGrossWeightInKg(); }
		 */
        grossWeight = lotInfoRepo.findTotLotByWsoId(wsoId);
        if(lotId != "") {
        	Long lotid = Long.valueOf(lotId);
        	grossWeight -= lotInfoRepo.findById(lotid).get().getUnitGrossWeightInKg();
        }
        
        float finalgrossWeight = grossWeight + newGrosswt;
        Optional<WSOInfo> wsoInfos = wsoInfoRepo.findById(wsoId);
        WSOInfo wsoInfo=wsoInfos.get();
        wsoWeight = wsoInfo.getTotalWsoWeight();
        
        if(finalgrossWeight > wsoWeight) {
        	float available = wsoWeight - grossWeight;
        	obj.put("lessGrossWeight", available);
        	responsejson.put("data",obj);
        }else {
        	responsejson.put("data","ok");
        }
        logger.info("Records successfully retreived from wsoInfo table where wsoId = "+wsoId);
        return responsejson;
    }
    
    @GetMapping(value="/getLotInfoNo/{wsoId}")
    public JSONObject getLotInfoNo(@PathVariable Long wsoId, @RequestParam(value = "lotNo",required = false) String lotNo
    		, @RequestParam(value = "lotId",required = false) String lotId){
        JSONObject  responsejson  = new JSONObject();
        JSONObject  obj  = new JSONObject();
        
        String lotNoStatus = "";
        
        if(lotId != "") {
        	Long lotid = Long.valueOf(lotId);
        	String currentlotNo = lotInfoRepo.findById(lotid).get().getLotNo();
        	if(currentlotNo.equals(lotNo)) {
        		lotNoStatus = "lotNo is available";
        	}else {
        		LotInfo lotInfoData = lotInfoRepo.findByWsoInfoWsoIdAndLotNo(wsoId, lotNo);
            	if(lotInfoData != null) {
                	lotNoStatus = "lotNo is not available";
                }else {
                	lotNoStatus = "lotNo is available";
                }
        	}
        	
        	
        }else {
        	LotInfo lotInfoData = lotInfoRepo.findByWsoInfoWsoIdAndLotNo(wsoId, lotNo);
        	if(lotInfoData != null) {
            	lotNoStatus = "lotNo is not available";
            }else {
            	lotNoStatus = "lotNo is available";
            }
        }
        responsejson.put("data",lotNoStatus);
        logger.info("Records successfully retreived from lotInfo table where lotNo = "+lotNo);
        return responsejson;
    }
    
    
    @GetMapping("/getAllWsoByTallysheetId/{tallyId}")
    public List<WSOInfo> getAllWsoByTallysheetId(@PathVariable Long tallyId){

        List<WSOInfo> wsoInfos = wsoInfoRepo.findWsoByTallysheet(tallyId);
        logger.info("Records successfully retreived from wsoInfo table where tallyId = "+tallyId);
        return wsoInfos;
        //return new ModelAndView("tallysheet","tallysheet",tallysheet);
    }
    
    @RequestMapping(value="/wsoInquiryByWso",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject wsoInquiryByWso(@RequestParam(value = "fromWsoId",required = false) String fromWsoId
    		, @RequestParam(value = "toWsoId",required = false) String toWsoId){
        JSONObject  responsejson  = new JSONObject();

        /*String fromWsoNo = "G00001";
        String toWsoNo = "G00004";*/
        
        /*String fromWsoId = "1";
        String toWsoId = "4";*/
        
        Long fromWsoId1 = Long.valueOf(fromWsoId);
        Long toWsoId1 = Long.valueOf(toWsoId);
        List<WSOInfo> wsodata = wsoInfoRepo.findWsoByWsoId(fromWsoId1, toWsoId1);
        
        JSONArray list = new  JSONArray();
        float ttlWsoQty = 0.0f;
        float currentWsoQty = 0.0f;
        float currentWsoWt = 0.0f;
        float ttlPackagesOut = 0.0f;
        int balanceQty = 0;
        
        for (WSOInfo model : wsodata){
        	List<DeliveryList> deliverydata = wsoInfoRepo.findDeliveryListByWsoId(model.getWsoId());
        	List<LotInfo> lotData = lotInfoRepo.findLotByWsoId(model.getWsoId());
        	for(LotInfo lotModel : lotData) {
        		currentWsoQty += lotModel.getCurrentQuantity();
        		currentWsoWt += lotModel.getCurrentQuantity() * lotModel.getUnitNetWeightInKg();
        		ttlWsoQty += lotModel.getLotQuantity();		
        	}
        	JSONArray list1 = new  JSONArray();
        	for(DeliveryList deliModel : deliverydata) {
        		JSONObject  obj  = new JSONObject();
    	        obj.put("dlNo", deliModel.getDlNo());
    	        obj.put("dateOfDelivery", deliModel.getDateOfDelivery());
    	        obj.put("lotNo", deliModel.getLotInfo().getLotNo());
    	        obj.put("packagesOut", deliModel.getTotalQty());
    	        ttlPackagesOut += deliModel.getTotalQty();
    	        obj.put("wsoNo", deliModel.getWsoInfo().getWsoNo());
    	        obj.put("clientName", deliModel.getClientInfo().getClientTitle());
    	        obj.put("totalWsoWeight", deliModel.getWsoInfo().getTotalWsoWeight());
    	        obj.put("storageDate", deliModel.getWsoInfo().getTallysheet().getStorageDate());
    	        obj.put("currentWsoQty", currentWsoQty);
    	        obj.put("currentWsoWt", currentWsoWt);
    	        obj.put("ttlWsoQty", ttlWsoQty);
    	        
    	        list1.add(obj);
        	}
        	
	        list.add(list1);
	        balanceQty = (int) (ttlWsoQty - ttlPackagesOut);
	        ttlWsoQty = 0.0f;
	        currentWsoQty = 0.0f;
	        currentWsoWt = 0.0f;
        }

        responsejson.put("data",list);
        responsejson.put("data1", balanceQty);
        ttlPackagesOut = 0.0f;
       // responsejson.put("lotDetail",list);
        logger.info("Records successfully retreived from wsoInfo table.");
        return responsejson;
    }
    
    
    
}

package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.DeliveryListNotFoundException;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.service.DeliveryService;
import com.genesis.genesisapi.service.WSOService;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deliveryLists")
@CrossOrigin("*")
public class DeliveryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryListRepo deliveryListRepo;
    @Autowired
    private ClientInfoRepo clientInfoRepo;
    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    @Autowired
    private LotInfoRepo lotInfoRepo;
    
    @GetMapping("/")
    public List<DeliveryList> fingAllDeliveryListt(){
    	logger.info("Record successfully retrieved from deliveryList.");
        return deliveryService.getAllDeliveyList(Boolean.FALSE);
    }

    @GetMapping("/{deliveryListId}")
    public ResponseEntity<DeliveryList> findDeliveryListById(@PathVariable Long deliveryListId) throws DeliveryListNotFoundException{
        DeliveryList deliveryList = deliveryService.getDeliveryList(Boolean.FALSE, deliveryListId);
        if(deliveryList == null){
        	logger.info("Exception from DeliveryController: Delivery Id not found.");
        	throw new DeliveryListNotFoundException(deliveryListId);
        	//return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
        	logger.info("Record successfully retrieved from deliveryList with deliveryListId:"+deliveryListId);
            return new ResponseEntity<>(deliveryList, HttpStatus.OK);
        
    }
    
    @ExceptionHandler(DeliveryListNotFoundException.class)
	public ModelAndView handleDeliveryListNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("DeliveryListNotFound");
	    return modelAndView;
	}
    @GetMapping("/client/{clientId}")
    public List<DeliveryList> findListOfDeliveryListByClientId(@PathVariable Long clientId){
        System.out.println("calling......");
        List<DeliveryList> deliveryList = deliveryService.getListOfDeliveryList(clientId, Boolean.FALSE);
        logger.info("Record successfully retrieved from deliveryList with clientId:"+clientId);
        return deliveryList;
    }

    @RequestMapping(value="/saveDeliveryList",method={RequestMethod.GET,RequestMethod.POST})
    public DeliveryList saveDeliveryList(@RequestParam(value = "dateOfDelivery",required = false) String dateOfDelivery,
    									@RequestParam(value = "clientId",required = false) String clientId,
    									@RequestParam(value = "dlNo",required = false) String dlNo,
    									@RequestParam(value = "timeOfIssue",required = false) String timeOfIssue,
    									@RequestParam(value = "nameOfReceiver",required = false) String nameOfReceiver,
    									@RequestParam(value = "vehicleNo",required = false) String vehicleNo,
    									@RequestParam(value = "nricOfReceiver",required = false) String nricOfReceiver,
    									@RequestParam(value = "verify",required = false) String verify,
    									@RequestParam(value = "wsoInfows",required = false) String wsoDetails
    									){
    	Date dateOfDeli = null;
    	Date timeOfIsu = null;
    	try {
			dateOfDeli=new SimpleDateFormat("yyyy-MM-dd").parse(dateOfDelivery);
			timeOfIsu=new Date(new SimpleDateFormat("hh:mm").parse(timeOfIssue).getTime());
		} catch (ParseException e1) {
			logger.info("Exception from DeliveryController:"+e1.getMessage());
			e1.printStackTrace();
		}
    	Long clientinfoId = 0L;
    	Long wsoInfoId = 0L;
    	Long lotinfoId = 0L;
    	Integer pkgout = 0;
    	DeliveryList response = null;
    	boolean verifys = false;
    	JSONArray jarray  = new JSONArray();
    	ArrayList<Long> al = new ArrayList<Long>();
    	if("".equalsIgnoreCase(dlNo)){
            PageRequest pageble  = PageRequest.of(0, 1, Sort.by("outgoingInventoryId").descending());
            Page<DeliveryList>   dInfo = deliveryListRepo.findAll(pageble);
    	    /*for(DeliveryList model : dInfo) {
    	    	String[] tokens = model.getDlNo().split("D00000");
    	    	al.add(Long.valueOf(tokens[tokens.length-1]));
    	    }
    	    Collections.sort(al);*/
    		//dlNo = "DL-"+System.currentTimeMillis();
            if(dInfo != null) {
                /*String wsoNo = al.get(0).getDlNo();
                String[] arr = wsoNo.split("D");
                int val= Integer.parseInt(arr[1])+1;*/
            	Long val = dInfo.getContent().get(0).getOutgoingInventoryId()+1;
                dlNo = "D00000"+val;
            }else {
                dlNo = "D000001";
            }
    	}
    	if("true".equalsIgnoreCase(verify)){
    		verifys = true;
    	}
    	try{
    		wsoDetails = URLDecoder.decode(wsoDetails, "UTF-8");
		}catch(Exception ex){logger.info("Exception from DeliveryController: "+ex.getMessage());}
		
		try{
			JSONParser parser = new JSONParser();
			if(StringUtils.isNotBlank(wsoDetails)){
				jarray = (JSONArray) parser.parse(wsoDetails);
			}
		}	
		catch(Exception e){	
			logger.info("Exception from DeliveryController: "+e.getMessage());
		}

		List<DeliveryList> dataList = deliveryListRepo.findByDlNo(dlNo);
		if(!dataList.isEmpty()){
			for(DeliveryList model1 :dataList ){
				deliveryListRepo.delete(model1);
			}
		}
		for(int i =0; i< jarray.size(); i++){
			JSONObject  obj  = (JSONObject)jarray.get(i);
			DeliveryList model  = new DeliveryList();
	    		String wsoInfo =  (String) obj.get("wsoInfo");
	    		String lotInfoId =  (String) obj.get("lotInfo");
	    		String packagesOut =  (String) obj.get("packagesOut");
	    		try{
	        		clientinfoId  = Long.parseLong(clientId);
	        		wsoInfoId = Long.parseLong(wsoInfo);
	        		lotinfoId = Long.parseLong(lotInfoId);
	        		pkgout  =  Integer.parseInt(packagesOut);
	        				
	        	}catch(Exception e){
	        		logger.info("Exception from DeliveryController: "+e.getMessage());
	        	}
	    		Optional<ClientInfo> clientInfoDatas = clientInfoRepo.findById(clientinfoId);
	        	Optional<WSOInfo> wSOInfos = wsoInfoRepo.findById(wsoInfoId);
	        	Optional<LotInfo> lotInfos = lotInfoRepo.findById(lotinfoId);
            ClientInfo clientInfoData = clientInfoDatas.get();
            WSOInfo wSOInfo = wSOInfos.get();
            LotInfo lotInfo = lotInfos.get();

            model.setClientInfo(clientInfoData);

	        	model.setWsoInfo(wSOInfo);
	        	model.setLotInfo(lotInfo);
	        	model.setDateOfDelivery(dateOfDeli);
	        	model.setDlNo(dlNo);
	        	model.setTimeOfIssue(timeOfIsu);
	        	model.setNameOfReceiver(nameOfReceiver);
	        	model.setVehicleNo(vehicleNo);
	        	model.setNricOfReceiver(nricOfReceiver);
	        	model.setVerify(verifys);
	        	model.setTotalQty(pkgout);
	        	if(verifys){
	    	    	Integer temp  = lotInfo.getCurrentQuantity();
	    	    	temp  = temp - pkgout;
	    	    	lotInfo.setCurrentQuantity(temp);
	    	    	lotInfoRepo.save(lotInfo);
	        	}
			
	        	response = model;
	    	deliveryService.saveDeliveryList(model);
		}
		logger.info("Record successfully saved in deliveryList with clientId:"+clientId);
    	return  response;
    	//return null;
    }

    @PutMapping("/{deliveryListId}")
    public ResponseEntity<DeliveryList> updateDeliveryList(@PathVariable Long deliveryListId, @Valid @RequestBody DeliveryList deliveryList){
        DeliveryList deliveryList1 = deliveryService.getDeliveryList(Boolean.FALSE, deliveryListId);
        if(deliveryList1 == null){
        	logger.info("Not able to reteive record to update form deliveryList with deliveryListId:"+deliveryListId);
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        deliveryList1.setNameOfReceiver(deliveryList.getNameOfReceiver());
        deliveryList1.setVehicleNo(deliveryList.getVehicleNo());
        deliveryList1.setNricOfReceiver(deliveryList.getNricOfReceiver());
        deliveryList1.setVerify(deliveryList.isVerify());
        deliveryList1.setDeleted(false);

        DeliveryList updatedDeliveryList = deliveryService.saveDeliveryList(deliveryList1);
        return new ResponseEntity<>(updatedDeliveryList, HttpStatus.OK);
    }

    @DeleteMapping("/{deliveryListId}")
    public ResponseEntity<DeliveryList> deleteDeliveryList(@PathVariable Long deliveryListId){
        DeliveryList deliveryList = deliveryService.getDeliveryList(Boolean.FALSE, deliveryListId);
        if(deliveryList == null){
        	logger.info("Exception from DeliveryController: Delivery List Id not found. Not able to delete record with deliveryListId: "+deliveryListId);
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        deliveryList.setDeleted(true);
        DeliveryList updatedDeliveryList = deliveryService.saveDeliveryList(deliveryList);
        logger.info("Record successfully deleted in deliveryList with deliveryListId:"+deliveryListId);
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/clientVehicalNo/{clientId}")
    public List<DeliveryList> findListOfGetVehicleNoByClientId(@PathVariable Long clientId){
        System.out.println("calling......");
        List<DeliveryList> deliveryList = deliveryListRepo.findVehicleNoByClientInfoClientInfoId(clientId);
        logger.info("Client Vehicle No successfully retreived from deliveryList with clientId:"+clientId);
        System.out.println(deliveryList);
        return deliveryList;
    }

    @GetMapping("/clientDl/{clientId}")
    public JSONObject findListOfGetDlByClientId(@PathVariable Long clientId){
        System.out.println("calling......");
         ClientInfo clientInfo = new ClientInfo();
         clientInfo.setClientInfoId(clientId);
        PageRequest pageble  = PageRequest.of(0, 30, Sort.by("dateOfDelivery").descending());
        Page<DeliveryList> dataList= deliveryListRepo.findTop50DlByClientId(clientId,pageble);
        List<DeliveryList> deliveryList = dataList.getContent();
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();
        ArrayList<Long> al = new ArrayList<Long>();
        for(DeliveryList model : deliveryList) {
	    	String[] tokens = model.getDlNo().split("D00000");
	    	al.add(Long.valueOf(tokens[tokens.length-1]));
	    }
        
        
        List<Long> alUnique = al.stream().distinct().collect(Collectors.toList());
        Collections.sort(alUnique);
        for(int i = 0; i < alUnique.size() ; i++) {
        	JSONObject obj = new JSONObject();
        	obj.put("dl", String.valueOf(alUnique.get(i)));
        	list.add(obj);
        }
        responsejson.put("data",list);
        
        logger.info("clientDl successfully retreived from deliveryList with clientId:"+clientId);
        
        System.out.println(deliveryList);
        return responsejson;
    }

    @GetMapping("/clientNric/{clientId}")
    public List<DeliveryList> findListOfGetNricByClientId(@PathVariable Long clientId){
        System.out.println("calling......");
        List<DeliveryList> deliveryList = deliveryListRepo.findNricByClientInfoClientInfoId(clientId);
        System.out.println(deliveryList);
        logger.info("ClientNric successfully retreived from deliveryList with clientId:"+clientId);
        return deliveryList;
    }

    @GetMapping("/nameOfReceiver/{clientId}")
    public List<DeliveryList> findListOfGetNameOfReceiverByClientId(@PathVariable Long clientId){
        System.out.println("calling......");
        List<DeliveryList> deliveryList = deliveryListRepo.findNameOfReceiverByClientInfoClientInfoId(clientId);
        System.out.println(deliveryList);
        logger.info("Name of Receiver successfully retreived from deliveryList with clientId:"+clientId);
        return deliveryList;
    }
    
    
    @RequestMapping(value="/findDeliveryDetail",method={RequestMethod.GET,RequestMethod.POST})
    public List<DeliveryList> saveDeliveryList(@RequestParam(value = "dlNo",required = false) String dlNo){
    	List<DeliveryList> deliveryList  = deliveryListRepo.findByDlNo(dlNo);
    	logger.info("Delivery Detail successfully retreived from deliveryList.");
    	return deliveryList;
    }
    

    @RequestMapping(value="/getAllDeliveryListreports",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadDeliveryListReport(){
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();

        List<DeliveryList> dataList = deliveryListRepo.getAllDeliveryByDlNo();
        System.out.println(dataList.toString());
        /*List<DeliveryList> dataList = deliveryService.getAllDeliveyList(Boolean.FALSE);*/
        for(DeliveryList model : dataList){
            JSONObject  obj  = new JSONObject();
            obj.put("clientTitle", model.getClientInfo().getClientTitle());
            //obj.put("wsoNo", model.getWsoInfo().getWsoNo());
            obj.put("dateOfDelivery", model.getDateOfDelivery());
            obj.put("dlNo", model.getDlNo());
            obj.put("totalQty", model.getTotalQty());
            obj.put("nameOfReceiver", model.getNameOfReceiver());
            obj.put("timeOfIssue", model.getTimeOfIssue());
            obj.put("vehicleNo", model.getVehicleNo());
            obj.put("wsoNo", model.getWsoInfo().getWsoNo());
            obj.put("outgoingInventoryId", String.valueOf(model.getOutgoingInventoryId()));
            list.add(obj);

        }

        responsejson.put("data",list);
        logger.info("Records successfully retreived from deliveryList");
        return responsejson;
    }

    @RequestMapping(value="/getAllDeliveryListreportsByDlNo/{dlno}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadDeliveryListReportByDlno(@PathVariable String dlno){
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();

        List<DeliveryList> dataList = deliveryListRepo.getAllDeliveryByDlNoAndDlno(dlno);
        System.out.println(dataList.toString());
        /*List<DeliveryList> dataList = deliveryService.getAllDeliveyList(Boolean.FALSE);*/
        for(DeliveryList model : dataList){
            JSONObject  obj  = new JSONObject();
            obj.put("clientTitle", model.getClientInfo().getClientTitle());
            //obj.put("wsoNo", model.getWsoInfo().getWsoNo());
            obj.put("dateOfDelivery", model.getDateOfDelivery());
            obj.put("dlNo", model.getDlNo());
            obj.put("totalQty", model.getTotalQty());
            obj.put("nameOfReceiver", model.getNameOfReceiver());
            obj.put("timeOfIssue", model.getTimeOfIssue());
            obj.put("vehicleNo", model.getVehicleNo());
            obj.put("wsoNo", model.getWsoInfo().getWsoNo());
            obj.put("outgoingInventoryId", String.valueOf(model.getOutgoingInventoryId()));
            list.add(obj);

        }

        responsejson.put("data",list);
        logger.info("Records successfully retreived from deliveryList");
        return responsejson;
    }

    
    @RequestMapping(value="/getDeliveryListreports/{deliveryId}",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadDeliveryByIdReport(@PathVariable Long deliveryId){
        JSONObject  responsejson  = new JSONObject();
        Optional<DeliveryList> dataLists = deliveryListRepo.findById(deliveryId);
        DeliveryList dataList=dataLists.get();



        JSONObject  obj  = new JSONObject();
        obj.put("dlNo", dataList.getDlNo());
        obj.put("clientTitle", dataList.getClientInfo().getClientTitle());
        obj.put("dateOfDelivery", dataList.getDateOfDelivery());
        obj.put("vehicleNo", dataList.getVehicleNo());
        obj.put("nricOfReceiver", dataList.getNricOfReceiver());
        obj.put("timeOfIssue", dataList.getTimeOfIssue());
        obj.put("nameOfReceiver", dataList.getNameOfReceiver());

        JSONArray list = new  JSONArray();
        String dlNumber = dataList.getDlNo();
        List<DeliveryList> wsodataList = deliveryListRepo.getAllLotByDlNo(dlNumber);
        for (DeliveryList model : wsodataList){
            JSONObject  obj1  = new JSONObject();
            obj1.put("lotNo",model.getLotInfo().getLotNo());
            obj1.put("wsoNo",model.getWsoInfo().getWsoNo());
            obj1.put("lotQuantity",model.getTotalQty());
            obj1.put("description",model.getLotInfo().getDescription());
            //obj1.put("cargo",model.getLotInfo().getC);

            list.add(obj1);
        }
        responsejson.put("data",obj);
        responsejson.put("deliveryLotDetail",list);
        logger.info("Records successfully retreived from deliveryList using deliveryId:"+deliveryId);
        return responsejson;
    }
    
    @RequestMapping(value="/loadDeliveryBetweenDatesReport",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject loadDeliveryBetweenDatesReport(@RequestParam(value = "fromDate",required = false) String fromDate,
													@RequestParam(value = "toDate",required = false) String toDate,
													@RequestParam(value = "fromClient",required = false) String fromClient,
													@RequestParam(value = "toClient",required = false) String toClient){
										    
        JSONObject  responsejson  = new JSONObject();
        JSONArray list = new  JSONArray();
    	 /*String fromdate = "2018-01-01";
		 String todate = "2018-04-01";
		 String fromClient = "1";
		 String toClient = "5";*/
		 
		 /*System.out.println("fromdate : "+fromDate);
		 System.out.println("todate : "+toDate);*/
		 Date fromDate1 = null;
		 Date toDate1 = null;
		 Long fromClient1 = null;
		 Long toClient1 = null;
		
		try {
			fromDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			toDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
			fromClient1 = Long.valueOf(fromClient);
			toClient1 = Long.valueOf(toClient);
		} catch (ParseException e) {
			e.printStackTrace();
		}

        List<ClientInfo>  dataList = deliveryListRepo.getClientBetweenClient(fromClient1, toClient1);
        for(ClientInfo model : dataList) {
        	JSONArray list1 = new  JSONArray();
        	JSONObject  obj1  = new JSONObject();
        	/*obj1.put("clientTitle", model.getClientTitle());*/
        	
        	List<DeliveryList>  dataList1 = deliveryListRepo.getDeliveryBetweenDate(model.getClientInfoId(), fromDate1, toDate1);
        	/*
        	ArrayList<Long> al = new ArrayList<Long>();
        	ArrayList<String> alfinal = new ArrayList<String>();
            for(DeliveryList model1 : dataList1) {
    	    	String[] tokens = model1.getDlNo().split("D00000");
    	    	al.add(Long.valueOf(tokens[tokens.length-1]));
    	    }
            
            List<Long> alUnique = al.stream().distinct().collect(Collectors.toList());
            Collections.sort(alUnique);
            
            for(int i = 0; i < alUnique.size(); i++) {
            	alfinal.add("D00000" + alUnique.get(i));
            }
            List<DeliveryList>  dataList2 = deliveryListRepo.getDeliveryIndl(alfinal);*/
        	for(DeliveryList model1 : dataList1) {
        		JSONObject  obj  = new JSONObject();
        		obj.put("clientTitle", model1.getClientInfo().getClientTitle());
        		obj.put("dlNo", model1.getDlNo());
            	obj.put("dateOfDelivery", model1.getDateOfDelivery());
            	obj.put("wsoNo", model1.getWsoInfo().getWsoNo());
            	obj.put("lotNo", model1.getLotInfo().getLotNo());
            	obj.put("ttlQty", model1.getTotalQty());
            	obj.put("ttlWt", model1.getTotalQty()*model1.getLotInfo().getUnitWeightInKg());
            	list1.add(obj);
            	
        	}
        	
        	list.add(list1);
        }
        
        responsejson.put("data", list);

        logger.info("Records successfully retreived from deliveryList for Outgoing records using From date to date");
        return responsejson;
    }

}

package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.ClientNotFoundException;
import com.genesis.genesisapi.model.*;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.repository.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/clientInfos")
@CrossOrigin("*")
public class ClientInfoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClientInfoRepo clientInfoRepo;

    @Autowired
    private ClientStorageInfoRepo clientStorageInfoRepo;

    @Autowired
    private StorageTypeRepo storageTypeRepo;

    @Autowired
    private TallysheetRepo tallysheetRepo;

    @Autowired
    private WSOInfoRepo wsoInfoRepo;

    @Autowired
    private LotInfoRepo lotInfoRepo;

    @Autowired
    private OtherChargesRepo otherChargesRepo;

    @GetMapping("/")
    public ResponseEntity<?> getAllClient(
            @RequestParam(value ="pageNumber", defaultValue = "0") int page,
            @RequestParam(value ="pageSize", defaultValue = "3") int size,
            @RequestParam(value ="name", defaultValue = "") String empName){
        Map<String, Object> response = new HashMap<>();

        PageRequest pageble  = PageRequest.of(page, size);
        Page<ClientInfo> requestedPage = clientInfoRepo.findAll(pageble);
        response.put("totalElement", requestedPage.getTotalElements());
        response.put("totalPage", requestedPage.getTotalPages());
        response.put("numberOfelement", requestedPage.getNumberOfElements());
        response.put("currentPageNmber", requestedPage.getNumber());
        response.put("data", requestedPage.getContent());
        logger.info("Record successfully retrieved from clientInfo.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/searchClientTitle/{searchClientTitle}")
    public ResponseEntity<List<ClientInfo>> getAllClientLikeTitle(@PathVariable String searchClientTitle){
        List<ClientInfo> clientInfo = clientInfoRepo.findByClientTitleIgnoreCaseContaining(searchClientTitle);
        logger.info("Record successfully retrieved from clientInfo.");
        return new ResponseEntity<>(clientInfo, HttpStatus.OK);
    }

    @GetMapping("/allClientTitle")
    public ResponseEntity<List<ClientInfo>> getClient(){
         List<ClientInfo> clientInfo = clientInfoRepo.findClientInfo();
         logger.info("Record successfully retrieved from clientInfo. All Client Titles retrieved.");
        return new ResponseEntity<>(clientInfo, HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientInfo> getClientById(@PathVariable Long clientId) throws ClientNotFoundException{
    	
    	Optional<ClientInfo> clientInfos = clientInfoRepo.findById(clientId);
        ClientInfo clientInfo=clientInfos.get();
        if(clientInfos.isEmpty()){
        	logger.debug("This is a debug message in ClientInfoController");
            logger.info("Exception in ClientInfoController: Client Not Found");
            logger.warn("This is a warn message in ClientInfoController");
            logger.error("This is an error message in ClientInfoController");
        	throw new ClientNotFoundException(clientId);
        }
        else
        	logger.info("Record successfully retrieved from clientInfo with clientId:"+clientId);
        	return new ResponseEntity<>(clientInfo, HttpStatus.OK);

        
    }
    
    @ExceptionHandler(ClientNotFoundException.class)
	public ModelAndView handleClientNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("ClientNotFound");
	    return modelAndView;
	}

    @PostMapping("/saveclientList/{clientId}")
    public ClientInfo saveClientInfo(@PathVariable Long clientId, @RequestParam(value = "storageTypeName",required = false) Long storageTypeId,
                                     @RequestParam(value = "storageStartDate",required = false) String storageStartDate,
                                     @RequestParam(value = "storageEndDate",required = false) String storageEndDate,
                                     @RequestParam(value = "monthlyRate",required = false) float monthlyRate,
                                     @RequestParam(value = "handlingCharges",required = false) float handlingCharges,
                                     @RequestParam(value = "lastBillDate",required = false) String lastBillDate,
                                     @RequestParam(value = "nextBillDate",required = false) String nextBillDate)throws ParseException{

    
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientInfoId(clientId);
        StorageType storateType = new StorageType();
        storateType.setStorageTypeId(storageTypeId);
        
        
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        warehouseInfo.setWarehouseInfoId(1);
        

        ClientStorageInfo clientStorageInfo = new ClientStorageInfo();
        clientStorageInfo.setMonthlyRate(monthlyRate);

        Date nextBillDate1=new SimpleDateFormat("yyyy-MM-dd").parse(nextBillDate);
        Date lastBillDate1=new SimpleDateFormat("yyyy-MM-dd").parse(lastBillDate);
        Date storageStartDate1=new SimpleDateFormat("yyyy-MM-dd").parse(storageStartDate);
        Date storageEndDate1=new SimpleDateFormat("yyyy-MM-dd").parse(storageEndDate);
        System.out.println("storageEndDate1 : "+storageEndDate1);

        clientStorageInfo.setNextBillDate(nextBillDate1);
        clientStorageInfo.setLastBillDate(lastBillDate1);
        clientStorageInfo.setStorageStartDate(storageStartDate1);
        clientStorageInfo.setStorageEndDate(storageEndDate1);
        clientStorageInfo.setHandlingCharges(handlingCharges);
        clientStorageInfo.setIsfirstBilling(true);
        clientStorageInfo.setClientInfo(clientInfo);
        clientStorageInfo.setStorageType(storateType);
        clientStorageInfo.setWarehouseInfo(warehouseInfo);
        clientStorageInfoRepo.save(clientStorageInfo);
        logger.info("Record successfully saved in clientInfo.");
        return clientInfo;
    }


    @PutMapping("/saveclientList/{clientStorageId}")
    public ClientStorageInfo updateClientInfo(@PathVariable Long clientStorageId, @RequestParam(value = "storageTypeName",required = false) Long storageTypeId,
                                     @RequestParam(value = "storageStartDate",required = false) String storageStartDate,
                                     @RequestParam(value = "storageEndDate",required = false) String storageEndDate,
                                     @RequestParam(value = "monthlyRate",required = false) float monthlyRate,
                                     @RequestParam(value = "handlingCharges",required = false) float handlingCharges)throws ParseException{

        System.out.println("ClientInfo save calling.....");
        Optional<ClientStorageInfo> clientStorageInfos = clientStorageInfoRepo.findById(clientStorageId);
        ClientStorageInfo clientStorageInfo= clientStorageInfos.get();
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        warehouseInfo.setWarehouseInfoId(1);

        StorageType storateType = new StorageType();
        storateType.setStorageTypeId(storageTypeId);
        clientStorageInfo.setStorageType(storateType);

        clientStorageInfo.setMonthlyRate(monthlyRate);
        Date storageStartDate1=new SimpleDateFormat("yyyy-MM-dd").parse(storageStartDate);
        Date storageEndDate1=new SimpleDateFormat("yyyy-MM-dd").parse(storageEndDate);
        System.out.println("storageEndDate1 : "+storageEndDate1);

        
        clientStorageInfo.setStorageStartDate(storageStartDate1);
        clientStorageInfo.setStorageEndDate(storageEndDate1);
        clientStorageInfo.setHandlingCharges(handlingCharges);
        clientStorageInfo.setIsfirstBilling(true);
        
        clientStorageInfo.setWarehouseInfo(warehouseInfo);
        ClientStorageInfo updatedClientInfo = clientStorageInfoRepo.save(clientStorageInfo);
        logger.info("Record successfully updated in clientInfo with clientStorageId:"+clientStorageId);
        return updatedClientInfo;
    }



    @PostMapping("/saveclientListOnly")
    public ClientInfo saveClientInfoOnly(@RequestBody ClientInfo clientInfoReq)throws ParseException{

        System.out.println("ClientInfo save calling.....");
        clientInfoReq.setDeleted(Boolean.FALSE);
        ClientInfo clientInfoData =  clientInfoRepo.save(clientInfoReq);
        logger.info("Record successfully saved in clientInfo.");
        return clientInfoData;
    }

    @PutMapping("/saveclientListOnly/{clientInfoId}")
    public ClientInfo updateClientInfo(@PathVariable Long clientInfoId, @RequestBody ClientInfo clientInfoRe)throws ParseException{
        System.out.println("---------");
       Optional<ClientInfo>  clientInfoDatas = clientInfoRepo.findById(clientInfoId);
        ClientInfo  clientInfoData=  clientInfoDatas.get();
        clientInfoData.setClientAddress1(clientInfoRe.getClientAddress1());
        clientInfoData.setClientAddress2(clientInfoRe.getClientAddress2());
        clientInfoData.setClientCity(clientInfoRe.getClientCity());
        clientInfoData.setClientCountry(clientInfoRe.getClientCountry());
        clientInfoData.setClientState(clientInfoRe.getClientState());
        clientInfoData.setClientTitle(clientInfoRe.getClientTitle());
        clientInfoData.setClientZip(clientInfoRe.getClientZip());
        clientInfoData.setContactPersonFirstName(clientInfoRe.getContactPersonFirstName());
        clientInfoData.setContactPersonLastName(clientInfoRe.getContactPersonLastName());

        clientInfoData.setContactPersonMiddleName(clientInfoRe.getContactPersonMiddleName());
        clientInfoData.setContactPersonMobileNumbere(clientInfoRe.getContactPersonMobileNumbere());
        clientInfoData.setContactPersonPhoneNumber(clientInfoRe.getContactPersonPhoneNumber());

        ClientInfo updatedClientInfo = clientInfoRepo.save(clientInfoData);
        logger.info("Record successfully updated in clientInfo with clientInfoId:"+clientInfoId);
        return updatedClientInfo;
    }

    @DeleteMapping("/{clientId}")
    public String deleteClientStorage(@PathVariable Long clientId) {
        Optional<ClientInfo> clientInfos = clientInfoRepo.findById(clientId);
        ClientInfo clientInfo= clientInfos.get();
        List<ClientStorageInfo> clientStorageInfos = clientStorageInfoRepo.findByClientId(clientId);

        if (clientStorageInfos != null){

        }else {
            for (ClientStorageInfo model : clientStorageInfos) {
                model.setDeleted(Boolean.TRUE);
                clientStorageInfoRepo.save(model);
            }
        }

        List<TallySheet> tallySheets = tallysheetRepo.findTallysheetByVerifyAndClientId(clientId);
        if (tallySheets == null){

        }else {
            for (TallySheet model : tallySheets){
                List<WSOInfo> wsoInfos = wsoInfoRepo.findWsoByTallysheet(model.getTallysheetId());
                if (wsoInfos == null){

                }else {
                    for (WSOInfo modelWso : wsoInfos){
                        List<LotInfo> lotInfos = lotInfoRepo.findLotByWsoId(modelWso.getWsoId());
                        if (lotInfos == null){

                        }else {
                            for (LotInfo modelLot : lotInfos){
                                modelLot.setDeleted(Boolean.TRUE);
                                lotInfoRepo.save(modelLot);
                            }
                        }

                        modelWso.setDeleted(Boolean.TRUE);
                        wsoInfoRepo.save(modelWso);
                    }
                model.setDeleted(Boolean.TRUE);
                logger.info("Record successfully deleted in clientInfo with clientId:"+clientId);
                tallysheetRepo.save(model);
                }
            }
        }

        List<OtherCharges> otherCharges = otherChargesRepo.findByClientId(clientId);
        for (OtherCharges modelOther : otherCharges){
            modelOther.setDeleted(Boolean.TRUE);
            otherChargesRepo.save(modelOther);
        }

        clientInfo.setDeleted(Boolean.TRUE);
        clientInfoRepo.save(clientInfo);

        return "OK";
    }
    
    @GetMapping("/countDetails")
    public JSONObject noOfClient(){
        JSONObject response = new JSONObject();
        JSONObject obj = new JSONObject();
        Long client = clientInfoRepo.findCountClientInfo();
        Long tallysheet = tallysheetRepo.findCountTallysheet();
        Long wsoInfo = wsoInfoRepo.findCountWsoInfo();
        Long lotInfo = lotInfoRepo.findCountLotInfo();

        obj.put("countClient",String.valueOf(client));
        obj.put("countTallysheet",String.valueOf(tallysheet));
        obj.put("countWsoInfo",String.valueOf(wsoInfo));
        obj.put("countLotInfo",String.valueOf(lotInfo));

        response.put("data", obj);
        logger.info("Record successfully retreived from clientInfo.");
        return response;
    }
    
    
    @GetMapping("/countDetailsAnd")
    public JSONArray countDetailsAnd(){
    	JSONArray  response = new JSONArray();
        JSONObject obj = new JSONObject();
        Long client = clientInfoRepo.findCountClientInfo();
        Long tallysheet = tallysheetRepo.findCountTallysheet();
        Long wsoInfo = wsoInfoRepo.findCountWsoInfo();
        Long lotInfo = lotInfoRepo.findCountLotInfo();

        obj.put("countClient",String.valueOf(client));
        obj.put("countTallysheet",String.valueOf(tallysheet));
        obj.put("countWsoInfo",String.valueOf(wsoInfo));
        obj.put("countLotInfo",String.valueOf(lotInfo));

        response.add(obj);
        logger.info("Record successfully retreived from clientInfo.");
        return response;
    }


}

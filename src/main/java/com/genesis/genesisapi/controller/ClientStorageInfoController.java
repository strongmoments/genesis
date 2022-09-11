package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.ClientStorageInfoNotFoundException;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.ClientStorageInfoRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientStorageInfos")
@CrossOrigin("*")
public class ClientStorageInfoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClientStorageInfoRepo clientStorageInfoRepo;

    @Autowired
    private ClientInfoRepo clientInfoRepo;

    @GetMapping("/")
    public ResponseEntity<List<ClientStorageInfo>> getAllClientStorageInfo(){
        List<ClientStorageInfo> clientStorageInfos = clientStorageInfoRepo.findAll();
        logger.info("Record successfully retrieved from clientInfo table.");
        return new ResponseEntity<>(clientStorageInfos, HttpStatus.OK);
    }

    @GetMapping("/{clientStorageInfoId}")
    public ResponseEntity<ClientStorageInfo> getClientStorageInfoById(@PathVariable Long clientStorageInfoId) throws ClientStorageInfoNotFoundException{
        ClientStorageInfo clientStorageInfo = clientStorageInfoRepo.findById(clientStorageInfoId).get();
        if(clientStorageInfo == null){
        	logger.info("Exception in ClientStorageInfoController: ClientStorageInfoId not Found.");
        	throw new ClientStorageInfoNotFoundException(clientStorageInfoId);
        }
        else
        	logger.info("Records successfully retrieved from client_storage_info.");
        	return new ResponseEntity<>(clientStorageInfo, HttpStatus.OK);
    }
    @ExceptionHandler(ClientStorageInfoNotFoundException.class)
	public ModelAndView handleClientStorageInfoNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("ClientStorageInfoNotFound");
	    return modelAndView;
	}

        @GetMapping("/clientId/{clientId}")
    public ResponseEntity<List<ClientStorageInfo>> getClientStorageInfoByClientId(@PathVariable Long clientId){
            System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        //ClientInfo clientInfo = clientInfoRepo.findOne(clientId);
            //Long lclientId = Long.valueOf(clientId);
        List<ClientStorageInfo> clientStorageInfo = clientStorageInfoRepo.findByClientId(clientId);
            System.out.println(clientStorageInfo);
            logger.info("Records successfully retrieved from client_storage_info with clientId:"+clientId);
        return new ResponseEntity<>(clientStorageInfo, HttpStatus.OK);
    }

    @PostMapping("/{clientInfoId}")
    public ClientStorageInfo getClientStorageInfoById(@RequestBody ClientStorageInfo clientStorageInfo,@PathVariable Long clientId){
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setClientInfoId(clientId);
        clientStorageInfo.setClientInfo(clientInfo);
        ClientStorageInfo clientStorageInfoData = clientStorageInfoRepo.save(clientStorageInfo);
        logger.info("Records successfully saved in client_storage_info with clientId:"+clientId);
        return clientStorageInfoData;
    }


    @PutMapping("/{clientStorageInfoId}")
    public ResponseEntity<ClientStorageInfo> updateClientInfo(@PathVariable Long clientStorageInfoId, @Valid @RequestBody ClientStorageInfo clientStorageInfo1){
        ClientStorageInfo clientStorageInfo = clientStorageInfoRepo.findById(clientStorageInfoId).get();
        if(clientStorageInfo == null){
        	logger.info("Exception in ClientStorageInfoController: ClientStorageInfoId not Found. Not able to udpate record with clientStorageInfoId:"+clientStorageInfoId);
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        

        clientStorageInfo.setLastBillDate(clientStorageInfo1.getLastBillDate());
        clientStorageInfo.setNextBillDate(clientStorageInfo1.getNextBillDate());
        clientStorageInfo.setStorageStartDate(clientStorageInfo1.getStorageStartDate());
        clientStorageInfo.setStorageEndDate(clientStorageInfo1.getStorageEndDate());
        clientStorageInfo.setMonthlyRate(clientStorageInfo1.getMonthlyRate());
        ClientStorageInfo updatedClientStorageInfo = clientStorageInfoRepo.save(clientStorageInfo);
        logger.info("Records successfully updated in client_storage_info with clientStorageInfoId:"+clientStorageInfoId);
        return new ResponseEntity<>(updatedClientStorageInfo, HttpStatus.OK);
    }

    @DeleteMapping("/{clientStorageId}")
    public String deleteClientStorage(@PathVariable Long clientStorageId) {
        ClientStorageInfo clientStorageInfo = clientStorageInfoRepo.findByClientStorageId(clientStorageId);
        clientStorageInfo.setDeleted(Boolean.TRUE);
        logger.info("Records successfully deleted in client_storage_info with clientStorageId:"+clientStorageId);
        clientStorageInfoRepo.save(clientStorageInfo);
        return "OK";
    }
    
    

    }

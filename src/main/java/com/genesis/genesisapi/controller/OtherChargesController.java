package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.OtherChargeNotFoundException;
import com.genesis.genesisapi.model.ChargeItems;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.OtherCharges;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.service.OtherChargesService;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/otherCharges")
@CrossOrigin("*")
public class OtherChargesController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OtherChargesService otherChargesService;

    @GetMapping("/")
    public List<OtherCharges> fingAllOtherCharges(){
    	logger.info("Records successfully retreived from other_charges table.");
        return otherChargesService.getOtherCharges();
    }

    @GetMapping("/{otherChargesId}")
    public ResponseEntity<OtherCharges> findOtherChargesById(@PathVariable Long otherChargesId) throws OtherChargeNotFoundException{
        OtherCharges otherCharges = otherChargesService.getOtherCharges(otherChargesId);
        if(otherCharges == null || otherCharges.equals(null)){
        	logger.info("Exception from OtherChargesController: OtherCharges Id not found.");
        	throw new OtherChargeNotFoundException(otherChargesId);
            //return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
        	logger.info("Records successfully retreived from other_charges table using otherChargesId:"+otherChargesId);
            return new ResponseEntity<>(otherCharges, HttpStatus.OK);
    }
    @ExceptionHandler(OtherChargeNotFoundException.class)
	public ModelAndView handleOtherChargeNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("OtherChargeNotFound");
	    return modelAndView;
	}


    @RequestMapping(value="/saveOtherCharges",method={RequestMethod.GET,RequestMethod.POST})
    public OtherCharges saveDeliveryList(@RequestParam(value = "data",required = false) String data, 
    									@RequestParam(value = "clientId",required = false) String clientId,
    									@RequestParam(value = "verifyInd",required = false) String verifyInd,
    									@RequestParam(value = "formNumber",required = false) String formNumber,
    									@RequestParam(value = "warehouseName",required = false) String warehouseName
    									){
    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
    	 SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    	Long clientsno  = 0L;
    	OtherCharges  resoponse = null;
    	try{
    		clientsno= 	Long.parseLong(clientId);
    	}catch(Exception e){
    		logger.info("Exception from OtherChargesController: "+e.getMessage());
    	}
    	
    	if(StringUtils.isBlank(formNumber)){
    		String formN  = otherChargesService.findTop1OrderByFormnoDesc();
    		if(formN != null) {
                String[] arr = formN.split("F");
                int val= Integer.parseInt(arr[1])+1;
                formNumber = "F00000"+val;

            }else {
            	formNumber = "F000001";
            }
    		
    	}else{
    		List<OtherCharges> datalist = otherChargesService.getAllFormNoByClientIdAndFormNu(clientsno,formNumber);
    		logger.info("Records retrieved from Other Charges table based on ClientId and Form Number");
        	if(!datalist.isEmpty()){
        		otherChargesService.deleteModel(datalist);
        	}
    	}
    	
    	
    	JSONArray jarray  = new JSONArray();
    	try{
    		data = URLDecoder.decode(data, "UTF-8");
		}catch(Exception ex){logger.info("Exception from OtherChargesController: "+ex.getMessage());}
		
		try{
			JSONParser parser = new JSONParser();
			if(StringUtils.isNotBlank(data)){
				jarray = (JSONArray) parser.parse(data);
			}
		}	
		catch(Exception e){	
			logger.info("Exception from OtherChargesController: "+e.getMessage());
		}
		
		for(int i =0; i< jarray.size(); i++){
			JSONObject  obj  = (JSONObject)jarray.get(i);
			OtherCharges  object = new OtherCharges();
			
			String fromTime  =(String) obj.get("fromTime");
			String ToTime  =(String) obj.get("ToTime");
			String fromDate  =(String) obj.get("fromDate");
			String toDate  =(String) obj.get("toDate");
			String bilableUnit  =(String) obj.get("bilableUnit");
			String bilableRate  =(String) obj.get("bilableRate");
			String naration  =(String) obj.get("naration");
			String chargeItemId  =(String) obj.get("chargeItemId");
			Long chrgItms = 0L;
			Float billunit = 0.0f;
			Float billrate  = 0.0f;
			Date frmTime  = new Date();
			Date toTime  = new Date();
			Date frmDate  = new Date();
			Date tDate  = new Date();
			
			try{
				chrgItms = 	Long.parseLong(chargeItemId);
				billunit = Float.parseFloat(bilableUnit);
				billrate = Float.parseFloat(bilableRate);
				
				frmDate = formatter.parse(fromDate);
				tDate = formatter.parse(toDate);
				 frmTime = time.parse(fromTime);
				 toTime = time.parse(ToTime);
				
			}catch(Exception e){
				logger.info("Exception from OtherChargesController: "+e.getMessage());
				
			}
			
		
			object.setVerifyInd(verifyInd);
			object.setFormNo(formNumber);
			object.setWarehouseName(warehouseName);
			ChargeItems chargItems  = new ChargeItems();
			chargItems.setChargeItemId(chrgItms);
			object.setChargeItems(chargItems);
			object.setBilableUnit(billunit);
			object.setBilableRate(billrate);
			object.setBilableAmount(billunit*billrate);
			object.setNaration(naration);
			 object.setFromDate(frmDate);
			 object.setToDate(tDate);
			 object.setFromTime(frmTime);
			 object.setToTime(toTime);
			
			
			ClientInfo clientModel  = new ClientInfo();
			clientModel.setClientInfoId(clientsno);
			
			object.setClientInfo(clientModel);

			resoponse = otherChargesService.saveOtherCharges(object);
			
		}
		
		logger.info("Records successfully saved in other_charges table.");
		return resoponse;
    	
	}
    

    @PutMapping("/{otherChargesId}")
    public ResponseEntity<OtherCharges> updateOtherCharges(@PathVariable Long otherChargesId, @Valid @RequestBody OtherCharges otherCharges){
        OtherCharges otherChargesData = otherChargesService.getOtherCharges(otherChargesId);
        if(otherChargesData == null){
        	logger.info("Exception from OtherChargesController: Other Charges Id not found. Not able to update record with otherChargesId: "+otherChargesId);
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        otherChargesData.setBilableAmount(otherCharges.getBilableAmount());
        otherChargesData.setBilableRate(otherCharges.getBilableRate());
        otherChargesData.setBilableUnit(otherCharges.getBilableUnit());
        otherChargesData.setChargeItems(otherCharges.getChargeItems());
        otherChargesData.setFormNo(otherCharges.getFormNo());

        otherChargesData.setFromDate(otherCharges.getFromDate());
        otherChargesData.setToDate(otherCharges.getToDate());
        otherChargesData.setFromTime(otherCharges.getFromTime());
        otherChargesData.setToTime(otherCharges.getToTime());
        otherChargesData.setGrandTotalAmount(otherCharges.getGrandTotalAmount());

        /*otherChargesData.setGst(otherCharges.getGst());*/
        otherChargesData.setNaration(otherCharges.getNaration());
        otherChargesData.setTotalAmount(otherCharges.getTotalAmount());
        otherChargesData.setVerifyInd(otherCharges.getVerifyInd());
        otherChargesData.setWarehouseName(otherCharges.getWarehouseName());

        OtherCharges updatedOtherCharges = otherChargesService.saveOtherCharges(otherChargesData);
        logger.info("Records successfully updated in other_charges table where otherChargesId:"+otherChargesId);
        return new ResponseEntity<>(updatedOtherCharges, HttpStatus.OK);
    }

    @DeleteMapping("/{OtherChargesId}")
    public ResponseEntity<OtherCharges> deleteOtherCharges(@PathVariable Long OtherChargesId){
        OtherCharges OtherCharges = otherChargesService.getOtherCharges(OtherChargesId);
        if(OtherCharges == null){
        	logger.info("Exception from OtherChargesController: Other Charges Id not found. Not able to delete record with otherChargesId: "+OtherChargesId);
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        /* OtherCharges.setDeleted(true);*/
        OtherCharges updatedOtherCharges = otherChargesService.saveOtherCharges(OtherCharges);
        logger.info("Records successfully deleted in other_charges table where OtherChargesId:"+OtherChargesId);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/geteOtherChargeDetail/{clientId}")
    public JSONObject geteOtherChargeDetail(@PathVariable Long clientId){
    	JSONObject responseJson = new JSONObject();
    	responseJson.put("data", otherChargesService.getAllFormNoByClient(clientId));
    	logger.info("Records successfully retreived from other_charges table where clientId:"+clientId);
    	return responseJson; 
    }

    
    @GetMapping("/geteOtherChargeDetail/{clientId}/{formNumber}")
    public List<OtherCharges> geteOtherChargeDetailByFormNumber(@PathVariable Long clientId, @PathVariable  String formNumber){
    	List<OtherCharges> dataList  = new ArrayList<OtherCharges>();
    	dataList = otherChargesService.getAllFormNoByClientIdAndFormNu(clientId, formNumber);
    	logger.info("Records successfully retreived from other_charges table where clientId:"+clientId+" and formNumber:"+formNumber);
    	return dataList; 
    }

}

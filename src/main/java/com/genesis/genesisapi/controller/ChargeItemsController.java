package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.ChargeItemNotFoundException;
import com.genesis.genesisapi.model.ChargeItems;
import com.genesis.genesisapi.service.ChargeItemsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chargeItems")
@CrossOrigin("*")
public class ChargeItemsController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ChargeItemsService chargeItemsService;

    @GetMapping("/")
    public List<ChargeItems> fingAllChargeItems(){
    	logger.info("Record successfully retrieved from charge_items.");
        return chargeItemsService.getChargeItems();
    }

    @GetMapping("/{chargeItemsId}")
    public ResponseEntity<ChargeItems> findChargeItemsById(@PathVariable Long chargeItemsId) throws ChargeItemNotFoundException{
        ChargeItems chargeItems = chargeItemsService.getChargeItems(chargeItemsId);
        if(chargeItems == null){
        	logger.info("Exception in ChargeItemsController: Charge Item Id Not Found");
        	throw new ChargeItemNotFoundException(chargeItemsId);
            //return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else
        	logger.info("Record successfully retrieved from charge_items with chargeItemsId:"+chargeItemsId);
            return new ResponseEntity<>(chargeItems, HttpStatus.OK);
        
    }
    @ExceptionHandler(ChargeItemNotFoundException.class)
	public ModelAndView handleChargeItemNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("ChargeItemNotFound");
	    return modelAndView;
	}


    @PostMapping("/")
    public ChargeItems saveChargeItems(@RequestBody ChargeItems chargeItems){
    	logger.info("Record successfully saved in charge_items.");
        return chargeItemsService.saveChargeItems(chargeItems);
    }

    @PutMapping("/{chargeItemsId}")
    public ResponseEntity<ChargeItems> updateChargeItems(@PathVariable Long chargeItemsId, @Valid @RequestBody ChargeItems chargeItems){
        ChargeItems chargeItemsData = chargeItemsService.getChargeItems(chargeItemsId);
        if(chargeItemsData == null){
        	logger.info("Exception in ChargeItemsController: Charge Item Id Not Found.Not able to update record with chargeItemsId"+chargeItemsId);
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        chargeItemsData.setChargeItem(chargeItems.getChargeItem());

        ChargeItems updatedChargeItems = chargeItemsService.saveChargeItems(chargeItemsData);
        logger.info("Record successfully updated in charge_items with chargeItemsId:"+chargeItemsId);
        return new ResponseEntity<>(updatedChargeItems, HttpStatus.OK);
    }

    @DeleteMapping("/{chargeItemsId}")
    public ResponseEntity<ChargeItems> deleteChargeItems(@PathVariable Long chargeItemsId){
        ChargeItems chargeItems = chargeItemsService.getChargeItems(chargeItemsId);
        if(chargeItems == null){
        	logger.info("Exception in ChargeItemsController: Charge Item Id Not Found. Not able to delete record with chargeItemsId"+chargeItemsId);
        	return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        ChargeItems updatedChargeItems = chargeItemsService.saveChargeItems(chargeItems);
        logger.info("Record successfully deleted from charge_items with chargeItemsId:"+chargeItemsId);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}

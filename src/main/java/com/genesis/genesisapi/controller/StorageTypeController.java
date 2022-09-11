package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.StorageTypeNotFoundException;
import com.genesis.genesisapi.model.StorageType;
import com.genesis.genesisapi.repository.StorageTypeRepo;

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
import java.util.Optional;

@RestController
@RequestMapping("/storageType")
@CrossOrigin("*")
public class StorageTypeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StorageTypeRepo storageTypeRepo;

    @GetMapping("/")
    public ResponseEntity<List<StorageType>> getAllStorageType(){
        List<StorageType> storageTypes = storageTypeRepo.findAll();
        logger.info("Records successfully retreived from Storage_type table.");
        return new ResponseEntity<>(storageTypes, HttpStatus.OK);
    }

    @GetMapping("/{storageTypeId}")
    public ResponseEntity<StorageType> getStorageTypeById(@PathVariable Long storageTypeId) throws StorageTypeNotFoundException{
       //Optional <Long> id= Optional.ofNullable(storageTypeId);
        Optional<StorageType>  storageType = storageTypeRepo.findById(storageTypeId);
        if(storageType == null){
        	logger.info("Exception in StorageTypeController: StorageType Id not found.");
        	throw new StorageTypeNotFoundException(storageTypeId);
        }
        else
        	logger.info("Records successfully retreived from Storage_type table where storageTypeId:"+storageTypeId);
        	return new ResponseEntity<>(storageType.get(), HttpStatus.OK);
    }
    
    @ExceptionHandler(StorageTypeNotFoundException.class)
	public ModelAndView handleStorageTypeNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("StorageTypeNotFound");
	    return modelAndView;
	}


    @PostMapping()
    public StorageType saveStorageType(@RequestBody StorageType storageType){
        StorageType storageTypeData = storageTypeRepo.save(storageType);
        logger.info("Records successfully saved in Storage_type table.");
        return storageTypeData;
    }


    @PutMapping("/{storageTypeId}")
    public ResponseEntity<StorageType> updateStorageType(@PathVariable Long storageTypeId, @Valid @RequestBody StorageType storageType1){
        Optional<StorageType> storageTypes = storageTypeRepo.findById(storageTypeId);
        if(storageTypes.isEmpty()){
        	logger.info("Exception in StorageTypeController: StorageType Id not found. Not able to update record with storageTypeId: "+storageTypeId);
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StorageType storageType =storageTypes.get();

        storageType.setStorageTypeName(storageType1.getStorageTypeName());
        StorageType updatedStorageType = storageTypeRepo.save(storageType);
        logger.info("Records successfully updated in Storage_type table where storageTypeId:"+storageTypeId);
        return new ResponseEntity<>(updatedStorageType, HttpStatus.OK);
    }

}

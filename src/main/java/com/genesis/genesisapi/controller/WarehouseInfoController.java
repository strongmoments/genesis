package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.WareHouseInfoNotFoundException;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.WarehouseInfo;
import com.genesis.genesisapi.repository.WarehouseInfoRepo;

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
@RequestMapping("/warehouseInfos")
@CrossOrigin("*")
public class WarehouseInfoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WarehouseInfoRepo warehouseInfoRepo;

    @GetMapping("/")
    public ResponseEntity<List<WarehouseInfo>> getAllWarehouseInfo(){
        List<WarehouseInfo> warehouseInfos = warehouseInfoRepo.findAll();
        logger.info("Records successfully retreived from warehouse_info table.");
        return new ResponseEntity<>(warehouseInfos, HttpStatus.OK);
    }

    @GetMapping("/{warehouseInfoId}")
    public ResponseEntity<WarehouseInfo> getWarehouseInfoById(@PathVariable Long warehouseInfoId) throws WareHouseInfoNotFoundException{
        WarehouseInfo warehouseInfo = warehouseInfoRepo.findById(warehouseInfoId).get();
        if(warehouseInfo == null){
        	logger.info("Exception in WarehouseInfoController: WarehouseInfo Id not found");
        	throw new WareHouseInfoNotFoundException(warehouseInfoId);
        }
        logger.info("Records successfully retreived from warehouse_info table where warehouseInfoId = "+warehouseInfoId);
        return new ResponseEntity<>(warehouseInfo, HttpStatus.OK);
    }
    @ExceptionHandler(WareHouseInfoNotFoundException.class)
	public ModelAndView handleWareHouseInfoNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("WareHouseInfoNotFound");
	    return modelAndView;
	}


    @PostMapping()
    public WarehouseInfo getWarehouseInfoById(@RequestBody WarehouseInfo warehouseInfo){
        WarehouseInfo warehouseInfoData = warehouseInfoRepo.save(warehouseInfo);
        logger.info("Records successfully saved in warehouse_info table.");
        return warehouseInfoData;
    }


    @PutMapping("/{warehouseInfoId}")
    public ResponseEntity<WarehouseInfo> updateWarehouseInfo(@PathVariable Long warehouseInfoId, @Valid @RequestBody WarehouseInfo warehouseInfo1){
        WarehouseInfo warehouseInfo = warehouseInfoRepo.findById(warehouseInfoId).get();
        if(warehouseInfo == null){
        	logger.info("Exception in WarehouseInfoController: Not able to update record with warehouseInfoId:"+warehouseInfoId);
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        warehouseInfo.setWarehouseName(warehouseInfo1.getWarehouseName());
        warehouseInfo.setApplicableGst(warehouseInfo1.getApplicableGst());
        WarehouseInfo updatedWarehouseInfo = warehouseInfoRepo.save(warehouseInfo);
        logger.info("Records successfully updated in warehouse_info table where warehouseInfoId = "+warehouseInfoId);
        return new ResponseEntity<>(updatedWarehouseInfo, HttpStatus.OK);
    }

}

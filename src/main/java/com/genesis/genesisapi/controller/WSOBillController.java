package com.genesis.genesisapi.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.ReportsRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import com.genesis.genesisapi.repository.WarehouseInfoRepo;
import com.genesis.genesisapi.service.WSOService;


@RestController
@RequestMapping("/wsoBill")
@CrossOrigin("*")
public class WSOBillController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WSOService wsoService;

    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    
    @Autowired
    private DeliveryListRepo deliveryListRepo;
    
    @Autowired
    private TallysheetRepo tallysheetRepo;
    @Autowired
    private ReportsRepo reportsRepo;
    
    @Autowired
    private BillingRepo billingRepo;
    
    @Autowired
    private WarehouseInfoRepo warehouseInfoRepo;
    
    @Autowired
    private LotInfoRepo lotInfoRepo;
    
    @GetMapping("/getAllWsoByClientId/{clientId}")
    public List<WSOInfo> getAllWsoByClientId(@PathVariable Long clientId){
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
    
    @PostMapping("/loadAllWsoBill")
    public JSONObject loadAllWsoBill(@RequestParam(value = "clientId") String clientId,
			@RequestParam(value = "wsoNo") String wsoNo){
    	
    	JSONObject response = new JSONObject();
    	JSONArray list = new  JSONArray();
    	WSOInfo wsoInfo = wsoInfoRepo.findWsoInfoByWsoNo(wsoNo);
    	int totQty = 0;
    	int pkgBal = 0;
    	if (wsoInfo != null) {
    		logger.info("WSO Number: "+wsoInfo.getWsoNo());
    		logger.info("Date Strored: "+wsoInfo.getTallysheet().getStorageDate());
    		logger.info("Storage Class: "+wsoInfo.getStorageClass().getStorageTypeName());
    		logger.info("WSO Wt. :"+wsoInfo.getTotalWsoWeight());
    		logger.info("Total Pallets: "+wsoInfo.getTotalNoOfPallets());
    		logger.info("Wt.per pallets: "+wsoInfo.getTotalWsoWeight()/wsoInfo.getTotalNoOfPallets());
    		logger.info("Wso Total Qty: "+lotInfoRepo.findTotQtyLotByWsoId(wsoInfo.getWsoId()));
    		
    		//JSON Object
    		JSONObject obj = new JSONObject();
    		obj.put("wsoNo", wsoInfo.getWsoNo());
    		obj.put("storageDate", wsoInfo.getTallysheet().getStorageDate());
    		obj.put("storageClass", wsoInfo.getStorageClass().getStorageTypeName());
    		obj.put("wsoWt", wsoInfo.getTotalWsoWeight());
    		obj.put("totPallets", wsoInfo.getTotalNoOfPallets());
    		obj.put("wsoTotQty", lotInfoRepo.findTotQtyLotByWsoId(wsoInfo.getWsoId()));
    		
    		list.add(obj);
    		
    		totQty = lotInfoRepo.findTotQtyLotByWsoId(wsoInfo.getWsoId());
    		logger.info("Bill details of WSO No "+wsoInfo.getWsoNo()+" Bill Details: \n");
    		List<Billing> billingList = billingRepo.getBillDetailByWsoId(wsoInfo.getWsoId());
    		Float TotalWsoWeight = wsoInfo.getTotalWsoWeight();
    		
    		if (!billingList.isEmpty()) {
    			/* billingList.forEach((temp) -> {
        			System.out.println(temp.toString());
        		}); */
    			
    			//Second way to iterate the list
    			JSONArray billList = new  JSONArray();
    			int i = 0;
        		while (i < billingList.size()) {
        			JSONObject obj1 = new JSONObject();
            		obj1.put("fromDate", billingList.get(i).getFromDate());
            		obj1.put("toDate", billingList.get(i).getToDate());
            		
        			pkgBal = totQty;
        			//This portion need to be checked for accuracy. Reason I have to get that for that billing period.
        			//Moreover I have to get Balace Weight also. Date Written: 22-11-2019
        			if (deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), billingList.get(i).getFromDate()) != null){
        				pkgBal = totQty - deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), billingList.get(i).getFromDate());
        			}
        			obj1.put("pkgBal", pkgBal);
        			System.out.println("Package balance: "+ pkgBal);
        			//To get Balance Weight of WSO till the billing date
        			
        			List<DeliveryList> delList = deliveryListRepo.getDeliveryByWsoId(wsoInfo.getWsoId());
            		
        			float BalWeight = TotalWsoWeight;
            		if(!delList.isEmpty()) {
            			int j = 0;
            			while (j < delList.size()) {
                			float wtDeliverd = delList.get(j).getLotInfo().getUnitNetWeightInKg() * delList.get(j).getTotalQty();
                			BalWeight = BalWeight - wtDeliverd;
            				j++;
            			}
            			obj1.put("balWeight", BalWeight);
            			logger.info("Balance Weight: "+ BalWeight);
            		}
            		else
            		{
            			//obj1.put("balWeight", 0.00f);
            			logger.info("Delivery list is empty for wso no: "+wsoInfo.getWsoNo());
            		}
            		obj1.put("billWt", billingList.get(i).getBillingWeight());
            		obj1.put("amtBilled", billingList.get(i).getNetAmount());
            		obj1.put("invNo", billingList.get(i).getInvoiceNo());
            		obj1.put("invDate", billingList.get(i).getInvoiceDate());
            		obj1.put("gst", billingList.get(i).getGst());
            		obj1.put("rate", billingList.get(i).getBillingRate());
        			System.out.println(billingList.get(i));
        			i++;
        			billList.add(obj1);
        		}
        		list.add(billList);
    		}
    		obj.put("currQty", lotInfoRepo.findTotCurrQtyLotByWsoId(wsoInfo.getWsoId()));
    		obj.put("currWt", lotInfoRepo.findTotCurrWtLotByWsoId(wsoInfo.getWsoId()));
    		logger.info("Wso Current Qty: "+lotInfoRepo.findTotCurrQtyLotByWsoId(wsoInfo.getWsoId()));
    		logger.info("WSO Current Wt.: "+lotInfoRepo.findTotCurrWtLotByWsoId(wsoInfo.getWsoId()));
    		List<DeliveryList> delList = deliveryListRepo.getDeliveryByWsoId(wsoInfo.getWsoId());
    		
    		Float BalanceWeight = TotalWsoWeight;
    		if(!delList.isEmpty()) {
    			JSONArray deliList = new  JSONArray();
    			int i = 0;
    			System.out.println("Delivery List of Wso No: "+wsoInfo.getWsoNo());
    			while (i < delList.size()) {
    				JSONObject obj2 = new JSONObject();
    				pkgBal = totQty;
        			if (deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), delList.get(i).getDateOfDelivery()) != null){
        				pkgBal = totQty - deliveryListRepo.getDeliveryByWsoAndDate3(wsoInfo.getWsoId(), delList.get(i).getDateOfDelivery());
        			}
        			System.out.println("Package balance: "+ pkgBal);
        			float wtDeliverd = delList.get(i).getLotInfo().getUnitNetWeightInKg() * delList.get(i).getTotalQty();
        			logger.info("Weight Delivered: "+wtDeliverd);
        			BalanceWeight = Math.abs(BalanceWeight - wtDeliverd);
        			logger.info("Balance Weight: "+ BalanceWeight);
        			
        			obj2.put("dlNo", delList.get(i).getDlNo());
        			obj2.put("dlDate", delList.get(i).getDateOfDelivery());
        			obj2.put("pkgOut", delList.get(i).getTotalQty());
        			obj2.put("wtDelivered", wtDeliverd);
        			obj2.put("balPkgs", pkgBal);
        			obj2.put("balWt", BalanceWeight);
        			
        			deliList.add(obj2);
    				System.out.println(delList.get(i));
    				i++;
    			}
    			list.add(deliList);
    		}
    		else
    		{
    			logger.info("Delivery list is empty for wso no: "+wsoInfo.getWsoNo());
    		}
    		
    	}
    	else
    		logger.info("Client Id:"+clientId+" and wsoNo: "+wsoNo+ " returned null");
    	
		response.put("data", list);
		return response;
    	
    }
}

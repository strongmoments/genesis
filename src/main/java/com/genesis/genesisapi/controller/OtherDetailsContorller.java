package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.ClientStorageInfoNotFoundException;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.ClientStorageInfoRepo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import com.genesis.genesisapi.repository.NextBillingRepo;
import com.genesis.genesisapi.repository.OtherChargesRepo;
import com.genesis.genesisapi.repository.PaymentRepo;
import com.genesis.genesisapi.repository.TallysheetRepo;
import com.genesis.genesisapi.repository.WSOInfoRepo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.genesis.genesisapi.controller.FindToFromDate;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clientStorageInfos")
@CrossOrigin("*")

public class OtherDetailsContorller {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private ClientStorageInfoRepo clientStorageInfoRepo;

    @Autowired
    private ClientInfoRepo clientInfoRepo;
    
    @Autowired
    private NextBillingRepo nextBillingRepo;
    
    @Autowired
    private TallysheetRepo tallysheetRepo;
    
    @Autowired
    private LotInfoRepo lotInfoRepo;
    
    @Autowired
    private WSOInfoRepo wsoInfoRepo;
    
    @Autowired
    private PaymentRepo paymentRepo;
    
    @Autowired
    private BillingRepo billingRepo;
    
    @Autowired
    private OtherChargesRepo otherChargesRepo;
    
    @PostMapping("/nextMonthBilling")
    public JSONObject nextMonthBillingInfo() {
    	List<Long> commonStorageType = new ArrayList<Long>();
    	commonStorageType.add(1L);
    	commonStorageType.add(2L);
    	commonStorageType.add(3L);
    	commonStorageType.add(4L);
    	commonStorageType.add(5L);
        List<ClientStorageInfo> clientStorageInfo = clientStorageInfoRepo.getClientStorageInfoOfNextMonth(commonStorageType);
        logger.info("Records successfully retrieved from client_storage_info");
        JSONObject response = new JSONObject();
        JSONArray list = new JSONArray();
        List<Float> invoiceRate = new ArrayList<Float>();
        List<WSOInfo> wsoInfo = new ArrayList<WSOInfo>();
        List<LotInfo> lotInfo = new ArrayList<LotInfo>();
        List<Long> wsoList = new ArrayList<Long>();
        List<Long> lotList = new ArrayList<Long>();
        FindToFromDate find = new FindToFromDate();
        int i=0,currentQty=0,totalLot=0;
        float amount = 0.0f;
        
        int l = 0;
        //Long lotId=0l;
        for(ClientStorageInfo cs: clientStorageInfo) {
        	JSONObject obj = new JSONObject();
        	Long clientId = cs.getClientInfo().getClientInfoId();
        	
        	List<TallySheet> tallysheet = nextBillingRepo.findTallysheetByClientId(clientId);
        	System.out.println("Tallysheet: "+tallysheet);
        	ArrayList<Long> tallysheetList = new ArrayList<Long>();
        	for(TallySheet list1: tallysheet) {
        		tallysheetList.add(list1.getTallysheetId());
        	}
        	if(!tallysheetList.isEmpty()) {
        		wsoInfo = nextBillingRepo.getWsoIdList(tallysheetList);
        		for(WSOInfo list1: wsoInfo) {
        			wsoList.add(list1.getWsoId());
            	}
                System.out.println("WSOList: "+wsoList);
                if(!wsoList.isEmpty()) {
                	lotInfo = nextBillingRepo.findLotByWsoId(wsoList);
                	for(LotInfo list1: lotInfo) {
                		lotList.add(list1.getLotId());
                	}
                	for(Long lotId: lotList) {
                		//lotId = id.longValue();
                		currentQty = nextBillingRepo.getLotCurrentQty(lotId);
                		LotInfo lo = lotInfoRepo.findLotDetailByLotId(lotId).get(0);
                		logger.info("Records successfully retrieved from Lot Info table");
                		//WsoInfo wo  = wsoInfoRepo.findByisDeletedAndWsoId(Boolean.FALSE, wsoId);
                		amount += (currentQty * lo.getUnitNetWeightInKg()/1000)* lo.getWsoInfo().getInvoiceRate() + ((currentQty * lo.getUnitNetWeightInKg()/1000)* lo.getWsoInfo().getInvoiceRate())/100* lo.getWsoInfo().getGst() ; 
                		if(currentQty == 0) {
                			l++;
                		}
                		totalLot += currentQty;
                		System.out.println("totalLot:"+totalLot);
                	}
                    System.out.println("lotList: "+lotList);
                    if(!lotList.isEmpty()) {
/*                    	invoiceRate = nextBillingRepo.getWsoInfoAmount(tallysheetList);
                        System.out.println("invoiceRate: "+invoiceRate);*/
                    }
                    
                }
        	}
            /*for(Float f: invoiceRate) {
            	amount += f;
            }*/
            List<String> dateList = find.getToFromDate(cs.getStorageStartDate().toString());
            if(totalLot !=0) {
            	obj.put("clientName", cs.getClientInfo().getClientTitle());
                obj.put("fromDate", dateList.get(0));
            	obj.put("toDate", dateList.get(1));
            	obj.put("totalWSO", wsoList.size()-l);
                obj.put("totalLot", totalLot);
                obj.put("amount", amount);            
                obj.put("clientId", clientId);
                list.add(obj);
                amount = 0.0f;
                totalLot=0;
                l=0;
                tallysheetList.clear();
                lotList.clear();
                wsoList.clear();
            }
            
            //obj.put("tallysheetList", tallysheetList);
            
        	
        }
        response.put("data", list);
        return response;
        //return new ResponseEntity<>(clientStorageInfo,HttpStatus.OK);
    } 
    
    @PostMapping("/nextBilling")
    public JSONObject nextBillingInfo(@RequestParam(value = "clientId",required = false) String clientId) {
    	JSONObject  responsejson  = new JSONObject();
    	System.out.println("hello......"+clientId);
    	JSONArray list = new  JSONArray();
    	
    	ClientInfo client = clientInfoRepo.findByCientId(Long.valueOf(clientId));
    	JSONObject  obj  = new JSONObject();
        obj.put("clientTitle", client.getClientTitle());
        Float ttlWsoQuantity = 0.0f;
        Float amount = 0.0f;
        Float gst = 0.0f;
        Float wt = 0.0f;
        Float invoiceRate = 0.0f;
        Float actualGst = 0.0f;
        Float totalWSOWeight = 0.0f;
        //long wsoId = 1;
        List<LotInfo> lotDataList = null;
        
    	List<TallySheet> tally = tallysheetRepo.findTallysheetByClientId(Long.valueOf(clientId));
    	logger.info("Records successfully retrieved from Tallysheet table based on Client Id");
    	for(TallySheet tallyData : tally) {
    		List<WSOInfo> wsodataList = wsoInfoRepo.findWsoByTallysheet(tallyData.getTallysheetId());
            for (WSOInfo model : wsodataList){
                JSONObject  obj1  = new JSONObject();
                
                lotDataList = lotInfoRepo.findLotByWsoId(model.getWsoId());
                logger.info("Records successfully retrieved from Lot Info table based on WSO ID");
                for(LotInfo modelLot : lotDataList) {
                	ttlWsoQuantity += modelLot.getCurrentQuantity();
                	totalWSOWeight += modelLot.getCurrentQuantity() * modelLot.getUnitNetWeightInKg();
                }
                invoiceRate = model.getInvoiceRate();
                gst = model.getGst();
                wt = totalWSOWeight;
                actualGst = (invoiceRate*wt/1000)/100*gst;
                amount = (invoiceRate*wt/1000) + actualGst;
                if(ttlWsoQuantity != 0) {
                obj1.put("ttlWsoQuantity",ttlWsoQuantity);
                ttlWsoQuantity = 0.0f;
                obj1.put("wsoNo",model.getWsoNo());
                //obj1.put("description",model.getDescription());
                obj1.put("totalWsoWeight",totalWSOWeight);
                obj1.put("totalNoOfPallets",model.getTotalNoOfPallets());
                obj1.put("storageTypeName",model.getStorageClass().getStorageTypeName());
                obj1.put("amount",amount);
                obj1.put("gst",actualGst);
                /*obj1.put("invoiceRate",model.getInvoiceRate());
                obj1.put("remarks",model.getRemarks());*/
               // obj1.put("remarks",model.getLotInfo());
                list.add(obj1);
                totalWSOWeight = 0.0f;
                }
            }
            
    	}
    	
    	responsejson.put("data",obj);
        responsejson.put("wsoDetail",list);
    	return responsejson;
    }
    
    @RequestMapping(value="/paymentBetweenDates",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject paymentByMonthAndYear(@RequestParam(value = "fromdate",required = false) String fromdate,
			 								@RequestParam(value = "todate",required = false) String todate) {
    	Date fromDate = null;
		Date toDate = null;
		
		try {
			fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			toDate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	JSONObject response = new JSONObject();
    	System.out.println("hello------------------"+fromDate+"===="+toDate);

    	/*Integer month1 = Integer.valueOf(month);
    	Integer year1 =  Integer.valueOf(year);*/
    	List<Object> data =  paymentRepo.findPaymentBetweenDates(fromDate, toDate);
    	logger.info("Records successfully retrieved from Payment table between dates");
    	JSONArray list = new  JSONArray();
    	String invNo = null;
    	Date prevDate = new Date();
    	Date recivedDate = new Date();
    	Double totRecdAmt = 0.0;
    	for (int i = 0; i < data.size(); i++) {
    		JSONObject  obj1  = new JSONObject();
			Object[] object = (Object[]) data.get(i);
			invNo = (String) object[2];
			obj1.put("clientTitle",billingRepo.getBillDetailByInvoiceNum(invNo).get(0).getClientInfo().getClientTitle());
			recivedDate = (Date) object[0];
			String invoiceNo = (String) object[2];
			List<Billing> bilingList = billingRepo.getBillDetailByInvoiceNum(invoiceNo);
			Boolean billPaid = true;
			for (Billing billing : bilingList) {
			    Boolean check = billing.isBillPaid();
			    if (check == false) {
			    	billPaid = false;
			    	break;
			    }
			}
			/*boolean billPaid = (boolean) object[3];*/
			String status = "";
			if (billPaid == true)
				status = "OK";
			else
				status = "Pending";
			
			Double recdAmout = (Double) object[1];
			if (i == 0) {
				totRecdAmt = totRecdAmt + recdAmout;
				prevDate = (Date) object[0];
				obj1.put("receivedDate",(Date) object[0]);
				obj1.put("receivedAmt",(Double) object[1]);
				obj1.put("invoiceNo",invoiceNo);
				obj1.put("status",status);
				obj1.put("subTotal",totRecdAmt);
				obj1.put("prntSubTot","false");
				list.add(obj1);
			}
			else {
				if (prevDate.compareTo(recivedDate) == 0) {
					totRecdAmt = totRecdAmt + recdAmout;
					obj1.put("receivedDate",(Date) object[0]);
					obj1.put("receivedAmt",(Double) object[1]);
					obj1.put("invoiceNo",invoiceNo);
					obj1.put("status",status);
					obj1.put("subTotal",totRecdAmt);
					
					if ((i+1) == data.size())
						obj1.put("prntSubTot","true");
					else
					{
						Object[] object1 = (Object[]) data.get(i+1);
						Date nextDate = (Date) object1[0];
						if (recivedDate.compareTo(nextDate) != 0)
							obj1.put("prntSubTot","true");
						else
							obj1.put("prntSubTot","false");
					}
					list.add(obj1);
				}
				else {
					
					totRecdAmt = 0.0;
					//Normal Working
					totRecdAmt = totRecdAmt + recdAmout;
					obj1.put("receivedDate",(Date) object[0]);
					obj1.put("receivedAmt",(Double) object[1]);
					obj1.put("invoiceNo",invoiceNo);
					obj1.put("status",status);
					obj1.put("subTotal",totRecdAmt);
					
					if ((i+1) == data.size())
						obj1.put("prntSubTot","true");
					else
					{
						Object[] object1 = (Object[]) data.get(i+1);
						Date nextDate = (Date) object1[0];
						if (recivedDate.compareTo(nextDate) != 0)
							obj1.put("prntSubTot","true");
						else
							obj1.put("prntSubTot","false");
					}
					//obj1.put("prntSubTot","false");
					list.add(obj1);
					//prevDate = new Date();
					
				}
			}
			prevDate = (Date) object[0];
		}
    	
    	response.put("data",list);
    	return response;
    }
    
    @RequestMapping(value="/customerlist",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject customerlist(@RequestParam(value = "fromdate",required = false) String fromdate,
			 @RequestParam(value = "todate",required = false) String todate){
    	String storageType = "";
    	float othersChrg = 0.0f;
    	double totalRevenue = 0.0;
    	double gst = 0.0;
    	double netAmount = 0.0;
    	double totalBilableAmtRT = 0.0;
    	double totalBilableAmtFBA = 0.0;
    	double others=0.0;
    	double totalBilableAmt = 0.0;
    	
    	Date fromDate = null;
		Date toDate = null;
		
		try {
			fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
			toDate = new SimpleDateFormat("yyyy-MM-dd").parse(todate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	JSONObject response = new JSONObject();
    	//System.out.println("clientId:"+clientId);
    	Long clientId1 = 2L;
    	List<ClientInfo> clientList =  clientInfoRepo.findAll();
    	
    	JSONArray list = new  JSONArray();
    	
    	for (ClientInfo c: clientList) {
    		List<Object> data =  billingRepo.getBillingByCLientIdAndBetweenDates(c.getClientInfoId(), fromDate, toDate);
    		logger.info("Records successfully retrieved from Billing table based on Client Id and betweent Dates");
    		System.out.println("data size is :"+data.size());
    		for (int i = 0; i < data.size(); i++) {
        		JSONObject  obj1  = new JSONObject();
    			Object[] object = (Object[]) data.get(i);
    			System.out.println("Handling Charge:"+(Float) object[0]);
    			othersChrg += (Float) object[0];
        		System.out.println("Net Amount:"+(Double) object[1]);
        		netAmount += (Double) object[1];
    		}
    		
    		if(otherChargesRepo.getBilableAmt(c.getClientInfoId(), fromDate, toDate) != null) {
				totalBilableAmt = otherChargesRepo.getBilableAmt(c.getClientInfoId(), fromDate, toDate);
				if(totalBilableAmt != 0.0) {
					System.out.println("totalBilableAmt:"+totalBilableAmt);
					//handOthersChrg += totalBilableAmt;
				}
			}
    		if(otherChargesRepo.getBilableAmtRT(c.getClientInfoId(), fromDate, toDate) != null) {
				totalBilableAmtRT = otherChargesRepo.getBilableAmtRT(c.getClientInfoId(), fromDate, toDate);
				if(totalBilableAmtRT != 0.0) {
					System.out.println("totalBilableAmtRT:"+totalBilableAmtRT);
				}
			}
    		if(otherChargesRepo.getBilableAmtFBA(c.getClientInfoId(), fromDate, toDate) != null) {
				totalBilableAmtFBA = otherChargesRepo.getBilableAmtFBA(c.getClientInfoId(), fromDate, toDate);
				if(totalBilableAmtFBA != 0.0) {
					System.out.println("totalBilableAmtFBA:"+totalBilableAmtFBA);
				}
			}		
    		List<Object> data2 =  billingRepo.getStorageType(c.getClientInfoId(), fromDate, toDate);
    		if(!data2.isEmpty()) {
    			System.out.println("data size is :"+data2.size());
        		for (int i = 0; i < data2.size(); i++) {
            		JSONObject  obj1  = new JSONObject();
        			Object[] object = (Object[]) data2.get(i);
        			System.out.println("Storage Type:"+(String) object[0]);
        		}
        		Object[] object = (Object[]) data2.get(0);
        		storageType = (String) object[0];
    		}
    		
    		List<Object> data3 = billingRepo.getBillingInfoByCLientIdAndBetweenDates(c.getClientInfoId(), fromDate, toDate);
        	for (int i = 0; i < data3.size(); i++) {
    			Object[] object = (Object[]) data3.get(i);
    			String invNo = (String) object[0];
    			Date invDate = (Date) object[1];
    			Double subTotal = (Double) object[2];
    			gst = (Double) object[3];
    			totalRevenue += (Double) object[4];
    		}
        	float gstRate = wsoInfoRepo.findAll().get(0).getGst();
        	
        	double storageGst = 0.0;
        	double otherGst = 0.0;
        	double fbaGst = 0.0;
        	double rtGst = 0.0;
        	double totalGst = 0.0;
        	double gstFigure = 0.01 * gstRate;
        	
        	others = totalBilableAmt - totalBilableAmtFBA - totalBilableAmtRT;
        	double expectedTotRev = (totalRevenue * 100)/107;
        	double storage = expectedTotRev - (others+totalBilableAmtFBA+totalBilableAmtRT);
        	double totRev = storage + others + totalBilableAmtFBA + totalBilableAmtRT;
        	logger.info("ExpectedToTRev:"+expectedTotRev+"storage:"+storage+"totRev:"+totRev);
        	otherGst = others * gstFigure;
        	fbaGst = totalBilableAmtFBA * gstFigure;
        	rtGst = totalBilableAmtRT * gstFigure;
			//if (storage < 0) storage = 0;
        	storageGst = storage * gstFigure;
        	totalGst = storageGst + otherGst + fbaGst + rtGst;
        	
    		//totalRevenue = others + totalBilableAmtFBA + totalBilableAmtRT + netAmount;
    		JSONObject  obj1  = new JSONObject();
			obj1.put("clientTitle",c.getClientTitle());
			obj1.put("storage",storage);
			obj1.put("othersChrg",others);
			obj1.put("totalBilableAmtFBA",totalBilableAmtFBA);
			obj1.put("totalBilableAmtRT",totalBilableAmtRT);
			
			double showGst = 0.00;
			showGst = totalGst;
			//if (totRev == 0) showGst = 0; 
			obj1.put("gst",showGst);
			obj1.put("totalRevenue",totRev);
			list.add(obj1);
			
			storageType = "";
			othersChrg = 0.0f;
			totalRevenue = 0.0;
			totalBilableAmtRT = 0.0;
	    	totalBilableAmtFBA = 0.0;	
	    	totalBilableAmt = 0.0;
	    	others = 0.0;
	    	netAmount = 0.0;
		}
    	
    	response.put("data",list);
    	return response;
    }
    

}

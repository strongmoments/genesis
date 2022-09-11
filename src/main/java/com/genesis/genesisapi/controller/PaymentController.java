package com.genesis.genesisapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genesis.genesisapi.model.BillByInvoice;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.Payment;
import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.repository.BillingRepo;
import com.genesis.genesisapi.repository.ClientInfoRepo;
import com.genesis.genesisapi.repository.PaymentRepo;

@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClientInfoRepo clientRepo;
	
	@Autowired
	private BillingRepo billingRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	@RequestMapping(value="/outstandingBillEnquiry",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject outstandingBillEnquiry(@RequestParam(value = "clientId",required = false) String clientId){
        JSONObject  responsejson  = new JSONObject();
        
        //String clientId = "1";
        Long clientId1 = Long.valueOf(clientId);
        ClientInfo clientInfo = clientRepo.findByCientId(clientId1);
        
        //List<Billing> billingdata = billingRepo.getBillingInfoByCLientId(clientId1);
        List<Object> billingdata = billingRepo.getBillingInfoByCLientIdGroup(clientId1);
        logger.info("Records successfully retrieved from Billing table based on Client Id");
    	List<BillByInvoice> billInvoice= new ArrayList<BillByInvoice>();
    	
    	for (int i = 0; i < billingdata.size(); i++) {
    		Object[] object = (Object[]) billingdata.get(i);
    		System.out.println("Value of Total Amount:"+(Double) object[0]);
    		System.out.println("Value of Total Amount:"+(String) object[1]);
    		BillByInvoice b = new BillByInvoice();
    		b.setTotal_amount((Double) object[0]);
    		b.setInvoice_no((String) object[1]);
    		billInvoice.add(b);
    	}
    	
        JSONArray list = new  JSONArray();
        Date lastBillDate = null;
        Double lastBillPay = 0.00;
        Double ttlInvoiceAmount = 0.00;
        Double ttlBalanceAmount = 0.00;
        for (BillByInvoice model : billInvoice){
        	
        	List<Payment> paymentdata = paymentRepo.findPaymentByInvoiceNoDistinct(model.getInvoice_no());
        	logger.info("Records successfully retrieved from Payment table based on Invoice No");
        	List<Billing> billingInfo = billingRepo.getBillingInfoByInvoiceNoGroup(model.getInvoice_no());
        	logger.info("Records successfully retrieved from Billing table based on Invoice No group");
        	System.out.println("Lis<Payment> length: "+paymentdata.size());
        	if(paymentdata.size()==0){
        		JSONObject  obj  = new JSONObject();
    	        obj.put("invoiceNo", billingInfo.get(0).getInvoiceNo());
    	        obj.put("invoiceDate", billingInfo.get(0).getInvoiceDate());
    	        obj.put("invoiceAmount", model.getTotal_amount());
    	        obj.put("balanceAmount", model.getTotal_amount());
    	        list.add(obj);
    	        ttlInvoiceAmount += model.getTotal_amount();
    	        ttlBalanceAmount += model.getTotal_amount();//Because no Payment made in Payment Table.
    	        
        	}
        	else
        	{
        		for(Payment modeldata : paymentdata) {
            		JSONObject  obj  = new JSONObject();
        	        obj.put("invoiceNo", modeldata.getInvoiceNo());
        	        obj.put("invoiceDate", billingInfo.get(0).getInvoiceDate());
        	        obj.put("invoiceAmount", model.getTotal_amount());
        	        obj.put("balanceAmount", modeldata.getBalance());
        	        list.add(obj);
        	        ttlInvoiceAmount += model.getTotal_amount();
        	        ttlBalanceAmount += modeldata.getBalance();
        	        if(lastBillDate == null || lastBillDate.before(modeldata.getPaymentDate())) {
        	        	lastBillDate = modeldata.getPaymentDate();
        	        	lastBillPay = (double) modeldata.getPaidAmount();
        	        }
            	}
        	}
        	
        }
        
        JSONObject  obj1  = new JSONObject();
        obj1.put("lastBillDate", lastBillDate);
        obj1.put("lastBillPay", lastBillPay);
        obj1.put("ttlInvoiceAmount", ttlInvoiceAmount);
        obj1.put("ttlBalanceAmount", ttlBalanceAmount);
        
        JSONObject  obj2  = new JSONObject();
        obj2.put("clientId", clientInfo.getClientTitle());
        obj2.put("contactPhone", clientInfo.getContactPersonPhoneNumber());
        obj2.put("contactMobile", clientInfo.getContactPersonMobileNumbere());
        
        responsejson.put("data",list);
        responsejson.put("data1",obj1);
        responsejson.put("data2",obj2);
        return responsejson;
    }

}

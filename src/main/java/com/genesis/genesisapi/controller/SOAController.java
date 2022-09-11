package com.genesis.genesisapi.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/soa")
@CrossOrigin("*")
public class SOAController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClientInfoRepo clientRepo;
	
	@Autowired
	private BillingRepo billingRepo;
	
	@Autowired
	private PaymentRepo paymentRepo;
	
	
	@RequestMapping(value="/soaEnquiry",method={RequestMethod.GET,RequestMethod.POST})
    public JSONObject outstandingBillEnquiry(@RequestParam(value = "fromClient",required = false) String fromClient,
			@RequestParam(value = "toClient",required = false) String toClient){
        //JSONObject  responsejson  = new JSONObject();
		long daysOverDue = 0L;
        //String clientId = "1";
		Long fromClient1 = Long.valueOf(fromClient);
		Long toClient1 = Long.valueOf(toClient);
		
        
        List<ClientInfo> clientInfo = clientRepo.getClientBetweenClient(fromClient1, toClient1);
        logger.info("Records successfully retrieved from Client Info table based on from and to client Id");
        
        JSONObject  responsejson  = new JSONObject();
        JSONArray list1 = new  JSONArray();
        JSONArray listCurrent = new  JSONArray();
        
        for(ClientInfo model1 : clientInfo) {
        	//List<Billing> billingdata = billingRepo.getBillingInfoByCLientId(model1.getClientInfoId());
        	
        	List<Object> billingdata = billingRepo.getBillingInfoByCLientIdGroup(model1.getClientInfoId());
        	logger.info("Records successfully retrieved from Billing table based on Client Id Group.");
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
        	Double current=0.0;
        	Double overdue30=0.0;
        	Double overdue60=0.0;
        	Double overdue90=0.0;
        	Double overdue120=0.0;
        	Double overdueMore120=0.0;
        	
        	JSONObject  objCurrent  = new JSONObject();
            
            
            for (BillByInvoice model : billInvoice){
            	
            	//List<Payment> paymentdata = paymentRepo.findPaymentByInvoiceNo(model.getInvoice_no());
            	List<Payment> paymentdata = paymentRepo.findPaymentByInvoiceNoDistinct(model.getInvoice_no());
            	logger.info("Records successfully retrieved from payment table based on distinct Invoice No.");
            	List<Billing> billingInfo = billingRepo.getBillingInfoByInvoiceNoGroup(model.getInvoice_no());
            	logger.info("Records successfully retrieved from Billing table based on Invoice No group.");
            	
            	System.out.println("List<Payment> length: "+paymentdata.size());
            	if(paymentdata.size()==0){
            		try{
            			
            			System.out.println("instantPayDt:"+new Date());
            			System.out.println("To date value:"+billingInfo.get(0).getInvoiceDate());
            			Date date2 = new Date();
            			Date date1 = addDays(billingInfo.get(0).getInvoiceDate(),30);
            			long timeDiff = (date2.getTime() - date1.getTime());
            			long diff = TimeUnit.MILLISECONDS.toDays(timeDiff);
            			if(diff<0)
            				daysOverDue=0;
            			else
            				daysOverDue = diff;
                		System.out.println("Difference in days:"+daysOverDue);
            		}catch(Exception e){
            			System.out.println(e.getMessage());
            		}
            		
            		if(daysOverDue==0){
            			current += model.getTotal_amount();
            		}else if(daysOverDue>0 && daysOverDue<31){
            			overdue30 += model.getTotal_amount();
            		}else if(daysOverDue>30 && daysOverDue<61){
            			overdue60 += model.getTotal_amount();
            		}else if(daysOverDue>60 && daysOverDue<91){
            			overdue90 += model.getTotal_amount();
            		}else if(daysOverDue>90 && daysOverDue<121){
            			overdue120 += model.getTotal_amount();
            		}else if(daysOverDue>120){
            			overdueMore120 += model.getTotal_amount();
            		}
            		JSONObject  obj  = new JSONObject();
            		obj.put("invoiceDate",billingInfo.get(0).getInvoiceDate()) ;
        	        obj.put("invoiceNo", billingInfo.get(0).getInvoiceNo());
        	        obj.put("creditTerms", 30);
        	        obj.put("documentDate", "");
        	        obj.put("paymentCreditNote", "");
        	        //obj.put("daysOverDue", daysOverDue.getDays());
        	        obj.put("daysOverDue", 0);
        	        obj.put("debit", 0);
        	        obj.put("credit", model.getTotal_amount());
        	        obj.put("balanceAmount", model.getTotal_amount());
        	        list.add(obj);
        	        
            	}
            	else{
            		for(Payment modeldata : paymentdata) {
                		try{
                			
                			System.out.println("instantPayDt:"+modeldata.getPaymentDate());
                			System.out.println("To date value:"+billingInfo.get(0).getInvoiceDate());
                			Date date2 = modeldata.getPaymentDate();
                			Date date1 = addDays(billingInfo.get(0).getInvoiceDate(),30);
                			long timeDiff = (date2.getTime() - date1.getTime());
                			long diff = TimeUnit.MILLISECONDS.toDays(timeDiff);
                			if(diff<0)
                				daysOverDue=0;
                			else
                				daysOverDue = diff;
                    		System.out.println("Difference in days:"+daysOverDue);
                		}catch(Exception e){
                			System.out.println(e.getMessage());
                		}
                		
                		if(daysOverDue==0){
                			current += modeldata.getBalance();
                		}else if(daysOverDue>0 && daysOverDue<31){
                			overdue30 += modeldata.getBalance();
                		}else if(daysOverDue>30 && daysOverDue<61){
                			overdue60 += modeldata.getBalance();
                		}else if(daysOverDue>60 && daysOverDue<91){
                			overdue90 += modeldata.getBalance();
                		}else if(daysOverDue>90 && daysOverDue<121){
                			overdue120 += modeldata.getBalance();
                		}else if(daysOverDue>120){
                			overdueMore120 += modeldata.getBalance();
                		}
                	    
                		JSONObject  obj  = new JSONObject();
                		obj.put("invoiceDate",billingInfo.get(0).getInvoiceDate()) ;
            	        obj.put("invoiceNo", modeldata.getInvoiceNo());
            	        obj.put("creditTerms", 30);
            	        obj.put("documentDate", modeldata.getPaymentDate());
            	        obj.put("paymentCreditNote",modeldata.getCnPnId());
            	        //obj.put("daysOverDue", daysOverDue.getDays());
            	        obj.put("daysOverDue", daysOverDue);
            	        obj.put("debit", model.getTotal_amount()-modeldata.getBalance());
            	        obj.put("credit", model.getTotal_amount());
            	        obj.put("balanceAmount", modeldata.getBalance());
            	        list.add(obj);
            	        
                	}
            	}
            	
            }
            objCurrent.put("current", current);
            objCurrent.put("overdue30", overdue30);
            objCurrent.put("overdue60", overdue60);
            objCurrent.put("overdue90", overdue90);
            objCurrent.put("overdue120", overdue120);
            objCurrent.put("overdueMore120", overdueMore120);
            listCurrent.add(objCurrent);
            list1.add(list);
            current=0.0;
        	overdue30=0.0;
        	overdue60=0.0;
        	overdue90=0.0;
        	overdue120=0.0;
        	overdueMore120=0.0;
        }
        
        JSONArray listClient = new JSONArray();
        for(ClientInfo c: clientInfo ){
            JSONObject  obj1  = new JSONObject();
            
            obj1.put("clientId", c.getClientTitle());
            obj1.put("contactPhone", c.getContactPersonPhoneNumber());
            obj1.put("contactMobile", c.getContactPersonMobileNumbere());
            listClient.add(obj1);
        }
        
        
        responsejson.put("data", list1);
        responsejson.put("data1", listClient);
        responsejson.put("data2", listCurrent);
        return responsejson;
    }
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
				
		return cal.getTime();
	}
}

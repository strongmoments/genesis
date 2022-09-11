package com.genesis.genesisapi.service;

import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ChargeItems;
import com.genesis.genesisapi.repository.BillingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {

    @Autowired
    private BillingRepo billingRepo;

    public List<Billing> getBilling(){
        return billingRepo.findAll();
    }

    public Billing getBilling(Long billingId){
        return billingRepo.findById(billingId).get();
    }

    public Billing saveBilling(Billing billing){
        return billingRepo.save(billing);
    }
}

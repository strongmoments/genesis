package com.genesis.genesisapi.service;

import com.genesis.genesisapi.model.ChargeItems;
import com.genesis.genesisapi.model.OtherCharges;
import com.genesis.genesisapi.repository.ChargeItemsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargeItemsService {

    @Autowired
    private ChargeItemsRepo chargeItemsRepo;

    public List<ChargeItems> getChargeItems(){
        return chargeItemsRepo.findAll();
    }

    public ChargeItems getChargeItems(Long chargeItemsId){
        return chargeItemsRepo.findById(chargeItemsId).get();
    }

    public ChargeItems saveChargeItems(ChargeItems chargeItems){
        return chargeItemsRepo.save(chargeItems);
    }
}

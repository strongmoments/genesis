package com.genesis.genesisapi.service;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.repository.AccountRepo;
import com.genesis.genesisapi.repository.DeliveryListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryListRepo deliveryListRepo;

    public List<DeliveryList> getAllDeliveyList(Boolean b){
        return deliveryListRepo.findByisDeleted(b);
    }

    public DeliveryList getDeliveryList(Boolean b, Long deliveryListId){
        return deliveryListRepo.findByisDeletedAndOutgoingInventoryId(b, deliveryListId);
    }

    public List<DeliveryList> getListOfDeliveryList(Long clientId, Boolean b){
        return deliveryListRepo.findByClientInfoClientInfoIdAndIsDeleted(clientId, b);
    }

    public DeliveryList saveDeliveryList(DeliveryList deliveryList){
        return deliveryListRepo.save(deliveryList);
    }
}

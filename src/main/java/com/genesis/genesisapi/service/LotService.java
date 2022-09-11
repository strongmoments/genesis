package com.genesis.genesisapi.service;

import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.repository.LotInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LotService {

    @Autowired
    private LotInfoRepo lotInfoRepo;

    public List<LotInfo> getAllLotInfo(Boolean b){
        return lotInfoRepo.findByisDeleted(b);
    }

    public LotInfo getLotInfo(Boolean b, Long deliveryListId){
        return lotInfoRepo.findByisDeletedAndLotId(b, deliveryListId);
    }

    public List<LotInfo> getLotInfoWithin24Hours(Long wsoId, Date date){
        return lotInfoRepo.fingByWsoInfoId(wsoId, date);
    }

    public LotInfo saveLotInfo(LotInfo deliveryList){
        return lotInfoRepo.save(deliveryList);
    }

    public List<LotInfo> findLotIdAndNo() {
        return lotInfoRepo.findLotIdAndNo();
    }
    
    public Float findTotalUnitWeightLotByWsoId(Long wsoId) {
        return lotInfoRepo.findTotalUnitWeightLotByWsoId(wsoId);
    }
}
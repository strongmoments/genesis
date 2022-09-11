package com.genesis.genesisapi.service;

import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.repository.WSOInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WSOService {

    @Autowired
    private WSOInfoRepo wsoInfoRepo;

    public List<WSOInfo> getAllWSOInfo(Boolean b){
        return wsoInfoRepo.findByisDeleted(b);
    }

    public WSOInfo getWSOInfo(Boolean b, Long deliveryListId){
        return wsoInfoRepo.findByisDeletedAndWsoId(b, deliveryListId);
    }

    public WSOInfo saveWSOInfo(WSOInfo wsoInfo){
        return wsoInfoRepo.save(wsoInfo);
    }

    public List<WSOInfo> findWsoIdAndNo() {
        return wsoInfoRepo.findWsoIdAndNo();
    }
    
   public List<WSOInfo> loadAllWsoByclientAndTallyAproved(List<Long> tallySheetIds){
	   return wsoInfoRepo.loadAllWsoByclientAndTallyAproved (tallySheetIds);
   }
}
package com.genesis.genesisapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.repository.TallysheetRepo;

@Service
public class TallsheetService {

    @Autowired
    private TallysheetRepo tallysheetRepo;

    public List<TallySheet> getAllTallysheet(Boolean b){
        return tallysheetRepo.findByisDeleted(b);
    }

    public TallySheet getTallysheet(Boolean b, Long tallysheetId){
        return tallysheetRepo.findByisDeletedAndTallysheetId(b, tallysheetId);
    }
    
    public List<TallySheet> getTallysheet(ClientInfo clientInfo){
        return tallysheetRepo.findByClientInfo(clientInfo);
    }

    public TallySheet saveTallysheet(TallySheet tallysheet){
        return tallysheetRepo.save(tallysheet);
    }

    public List<TallySheet> findTallysheetIdAndNo(){
        return tallysheetRepo.findTallysheetNoAndId();
    }
}

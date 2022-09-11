package com.genesis.genesisapi.service;


import com.genesis.genesisapi.model.OtherCharges;
import com.genesis.genesisapi.repository.OtherChargesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherChargesService {

    @Autowired
    private OtherChargesRepo otherChargesRepo;

    public List<OtherCharges> getOtherCharges(){
        return otherChargesRepo.findAll();
    }

    public OtherCharges getOtherCharges(Long otherChargesId){
        return otherChargesRepo.findById(otherChargesId).get();
    }

    public OtherCharges saveOtherCharges(OtherCharges otherCharges){
        return otherChargesRepo.save(otherCharges);
    }
    
    public List<OtherCharges> getOtherChargeByClient(Long clientId){
    	 return otherChargesRepo.getOtherChargeByClient(clientId);
    }
    
    public void  deleteModel(List<OtherCharges> modelList){
    	for( OtherCharges modal  : modelList){
    		otherChargesRepo.delete(modal);
    	}
   }

   public List<OtherCharges>  getOtherChargesBetweenClient(Long clientId1,  Long clientId2){
	   return otherChargesRepo.getOtherChargesBetweenClient(clientId1, clientId2);
   }
   
   public List<OtherCharges>  getAllFormNoByClient(Long clientId1){
	   return otherChargesRepo.getAllFormNoByClient(clientId1);
   }
   
   public List<OtherCharges>  getAllFormNoByClientIdAndFormNu(Long clientId1, String formNum){
	   return otherChargesRepo.getAllFormNoByClientIdAndFormNu(clientId1, formNum);
	}
   
   public String  findTop1OrderByFormnoDesc(){
	   List<String> model = otherChargesRepo.findTop1OrderByFormnoDesc();
	   if(!model.isEmpty()){
		   return model.get(0);   
	   }
	   return null;
	}

   
    
    
}

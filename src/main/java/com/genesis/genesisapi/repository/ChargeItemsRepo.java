package com.genesis.genesisapi.repository;

import org.springframework.stereotype.Repository;

import com.genesis.genesisapi.model.ChargeItems;
import com.genesis.genesisapi.model.OtherCharges;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ChargeItemsRepo extends JpaRepository<ChargeItems,Long> {

}

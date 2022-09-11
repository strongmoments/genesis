package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ChargeItems;
import com.genesis.genesisapi.model.OtherCharges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeItemsRepo extends JpaRepository<ChargeItems,Long> {

}

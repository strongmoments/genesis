package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.WSOInfo;
import com.genesis.genesisapi.model.WarehouseInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WarehouseInfoRepo extends JpaRepository<WarehouseInfo,Long> {
	 	@Query("Select t from  warehouse_info as t")
	    List<WarehouseInfo> findAllWareHouse();
}

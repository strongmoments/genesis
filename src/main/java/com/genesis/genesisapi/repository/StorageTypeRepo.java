package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.model.StorageType;
import com.genesis.genesisapi.model.WSOInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository

public interface StorageTypeRepo extends JpaRepository<StorageType,Long> {
	
	@Query("Select c from Storage_type c where c.storageTypeName = :storageTypeName ")
    StorageType getStorageTypeByName(@Param("storageTypeName") String storageTypeName);
	
}

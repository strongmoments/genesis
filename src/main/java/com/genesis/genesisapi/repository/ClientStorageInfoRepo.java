package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.model.WSOInfo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientStorageInfoRepo extends JpaRepository<ClientStorageInfo,Long> {
    @Query("Select c from client_storage_info c where c.isDeleted = 0 AND c.clientInfo.clientInfoId = :id")
    List<ClientStorageInfo> findByClientId(@Param("id") Long clientId);

    @Query("Select count(c) from client_storage_info c where c.isDeleted = 0 AND c.clientInfo.clientInfoId = :id")
    Long findCoutnByClientId(@Param("id") Long clientId);

    @Query("Select c from client_storage_info c where c.isDeleted = 0 AND c.clientStorageId = :id")
    ClientStorageInfo findByClientStorageId(@Param("id") Long clientStorageId);
    
    @Query("Select c from client_storage_info c where  c.clientInfo.clientInfoId BETWEEN :clientId1  AND :clientId2  and  c.storageType.storageTypeId IN :storageId  and c.lastBillDate < :todayDate")
    List<ClientStorageInfo> findByClientIdAndStoregeTypeId(@Param("clientId1") Long clientId1, @Param("clientId2") Long clientId2,  @Param("storageId") List <Long> storageId , @Param("todayDate") Date todayDate);
    
    @Query("Select c from client_storage_info c where  c.clientInfo.clientInfoId =:clientId1  and  c.storageType.storageTypeId =:storageId")
    List<ClientStorageInfo> getClientStorageByclientAndStorageId(@Param("clientId1") Long clientId1, @Param("storageId") Long storageId);
    
    @Query(value = "SELECT * FROM genesis.client_storage_info where storage_type_storage_type_id IN :storageId and storage_start_date < (SELECT DATE_ADD(LAST_DAY(curdate()),INTERVAL 1 DAY)) and storage_end_date>(LAST_DAY(DATE_ADD(LAST_DAY(curdate()),INTERVAL 1 DAY))) ;", nativeQuery = true)
	List<ClientStorageInfo> getClientStorageInfoOfNextMonth(@Param("storageId") List <Long> storageId);
}

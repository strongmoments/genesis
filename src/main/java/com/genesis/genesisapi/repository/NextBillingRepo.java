package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
@Repository
public interface NextBillingRepo extends JpaRepository<TallySheet,Long> {
	@Query(value = "SELECT client_info_client_info_id FROM genesis.client_storage_info where storage_start_date < (SELECT DATE_ADD(LAST_DAY(curdate()),INTERVAL 1 DAY)) and storage_end_date>(LAST_DAY(DATE_ADD(LAST_DAY(curdate()),INTERVAL 1 DAY))) ;", nativeQuery = true)
	List<BigInteger> getClientStorageInfoOfNextMonth();
	
	@Query(value ="select tallysheet_id from genesis.tallysheet where client_info_client_info_id in (:clientList);", nativeQuery = true)
	List<Long> getTallySheetIds(@Param("clientList") List<Long> clientList);
	
	@Query("Select t from tallysheet t where t.clientInfo.clientInfoId = :clientId ")
    List<TallySheet> findTallysheetByClientId(@Param("clientId") Long clientId);
	
	/*@Query("SELECT  ((t.totalWsoWeight/1000)* t.invoiceRate) + (((t.totalWsoWeight/1000)* t.invoiceRate)/100* t.gst) FROM wsoInfo t where (t.tallysheet.tallysheetId in (:tallysheetList) ) and (t.storageClass.storageTypeId between 1 and 5)")
	List<Float> getWsoInfoAmount(@Param("tallysheetList") List<Long> tallysheetList);*/
	
	
	@Query("Select t from wsoInfo t where (t.tallysheet.tallysheetId in (:tallysheetList) ) and (t.storageClass.storageTypeId between 1 and 5)")
	List<WSOInfo> getWsoIdList(@Param("tallysheetList") List<Long> tallysheetList);
	
	
	@Query("Select t from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId in (:wsoList)")
    List<LotInfo> findLotByWsoId(@Param("wsoList") List<Long> wsoList);
	
	@Query("select t.currentQuantity from lotInfo t where lotId = :lotId")
	Integer getLotCurrentQty(@Param("lotId") Long lotId);
}

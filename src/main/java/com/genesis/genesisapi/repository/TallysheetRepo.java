package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.TallySheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface TallysheetRepo extends JpaRepository<TallySheet,Long> {


    List<TallySheet> findByisDeleted(Boolean isDeleted);
    TallySheet findByisDeletedAndTallysheetId(Boolean isDeleted, Long tallysheetId);
    
    List<TallySheet> findByClientInfo(ClientInfo clientInfo);

    @Query("Select tallysheetNumber from tallysheet")
    List<TallySheet> findTallysheetNoAndId();

    @Query("Select t from tallysheet t where t.clientInfo.clientInfoId = :id ")
    List<TallySheet> findTallysheetByClientId(@Param("id") Long clientId);
    
    @Query("Select t from tallysheet t where t.clientInfo.clientInfoId = :id AND t.storageDate <= :date1")
    List<TallySheet> findTallysheetByClientIdAndDate(@Param("id") Long clientId, @Param("date1") Date date1);

    @Query("Select t from tallysheet t where t.verify = 0 AND t.clientInfo.clientInfoId = :id")
    List<TallySheet> findTallysheetByVerifyAndClientId(@Param("id") Long clientId);

    @Query("Select t from tallysheet t where t.clientInfo.clientInfoId BETWEEN :clientId1 AND :clientId2")
    List<TallySheet> findTallysheetByBetweenClientId(@Param("clientId1") Long clientId1, @Param("clientId2") Long clientId2);
   
    TallySheet findTop1ByOrderByTallysheetIdDesc();
   
    @Query("Select t from tallysheet t where t.tallysheetId= :id")
    List<TallySheet> getTallysheetDetail(@Param("id") Long clientId);
    
    @Query("Select t from tallysheet t where t.clientInfo.clientInfoId = :id AND t.tallysheetId =:tallyId")
    List<TallySheet> findTallysheetByClientId(@Param("id") Long clientId, @Param("tallyId") Long tallysheetId);
    
    @Query("Select t from tallysheet t where t.clientInfo.clientInfoId = :id AND t.verify='1'")
    List<TallySheet> getApprovedTallySheetByClient(@Param("id") Long clientId);
    
    @Query("Select t from tallysheet t where t.verify='1' AND  t.clientInfo.clientInfoId BETWEEN :clientId1 AND :clientId2")
    List<TallySheet> getApprovedTallySheetBetweenClient(@Param("clientId1") Long clientId1, @Param("clientId2") Long clientId2);
    
    @Query("Select t from tallysheet t where  t.verify='1'")
    List<TallySheet> findAllVerifiedTallySheet();
    
    @Query("Select count(t) from tallysheet t ")
    Long findCountTallysheet();
    
    List<TallySheet> findBytallysheetNumberIgnoreCaseContaining(String tallysheetNumber);
    
}

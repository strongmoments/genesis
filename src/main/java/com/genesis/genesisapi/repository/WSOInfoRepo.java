package com.genesis.genesisapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.WSOInfo;

public interface WSOInfoRepo extends JpaRepository<WSOInfo,Long> {

    List<WSOInfo> findByisDeleted(Boolean isDeleted);
    WSOInfo findByisDeletedAndWsoId(Boolean isDeleted, Long wsoId);

    @Query("Select wsoNo from wsoInfo")
    List<WSOInfo> findWsoIdAndNo();
    
    @Query("Select t from wsoInfo t where t.isDeleted=0 AND t.wsoNo = :wsoNo")
    WSOInfo findWsoInfoByWsoNo(@Param("wsoNo") String wsoNo);
    
    WSOInfo findTop1ByOrderByWsoIdDesc();

    @Query("Select t from wsoInfo t where t.tallysheet.tallysheetId = :id AND t.tallysheet.verify='1' ")
    List<WSOInfo> findWsoByTallysheetId(@Param("id") Long tallyId);
    
    @Query("Select t from wsoInfo t where t.isDeleted=0 AND t.tallysheet.tallysheetId = :id ")
    List<WSOInfo> findWsoByTallysheet(@Param("id") Long tallyId);
    
    @Query("Select t from wsoInfo t where t.isDeleted=0 AND t.tallysheet.tallysheetId = :id AND t.wsoNo LIKE %:wso%")
    List<WSOInfo> findWsoByTallysheetAndWso(@Param("id") Long tallyId, @Param("wso") String wsoNo);

    @Query("Select count(t) from wsoInfo t where t.isDeleted=0 AND t.tallysheet.tallysheetId = :id ")
    Long findCountWsoByTallysheet(@Param("id") Long tallyId);
    
    @Query("Select t from wsoInfo t where t.tallysheet.tallysheetId = :id AND t.billStartDate <= :date")
    List<WSOInfo> findWsoByTallysheetIdAndDate(@Param("id") Long tallyId, @Param("date") Date date);
    
    @Query("Select t from wsoInfo t where t.tallysheet.tallysheetId = :id AND  t.tallysheet.verify='1' AND t.billEndDate <= :dat AND t.storageClass.storageTypeId NOT IN :leaseType ")
    List<WSOInfo> findAllWSObyTallySheet(@Param("id") Long  tallySheetId, @Param("dat") Date  dat, @Param("leaseType") List<Long> leaseStorageType);
    
    @Query("Select t from wsoInfo t ")
    List<WSOInfo> loadAllWsos();
    
    @Query("Select t from wsoInfo t where t.tallysheet.tallysheetId IN :id")
    List<WSOInfo> loadAllWsoByclientAndTallyAproved(@Param("id") List<Long> clientId);
    
    @Query("Select t from wsoInfo t where t.tallysheet.tallysheetId IN :id  And t.wsoId NOT IN :wsoId")
    List<WSOInfo> loadAllWsoByclientAndTallyAprovedAndExcept(@Param("id") List<Long> clientId, @Param("wsoId") List<Long> wsoId);
    
    @Query("Select t from wsoInfo t where t.tallysheet.tallysheetId IN :id")
    List<WSOInfo> loadWsoReports(@Param("id") List<Long> clientId);
      
    @Query("SELECT  C.clientTitle, B.fromDate, B.toDate, B.invoiceNo,  sum(B.netAmount)  FROM   billing  B  JOIN  B.tallysheet T  JOIN T.clientInfo  C   WHERE   T.tallysheetId = B.tallysheet.tallysheetId    AND T.clientInfo.clientInfoId  = C.clientInfoId  AND  B.storageClass.storageTypeId  IN :storateTypeId   AND B.tallysheet.tallysheetId  IN :tallySheetList  GROUP BY B.invoiceNo ")
    List<Object> loadBillByClientAndBillType(@Param("storateTypeId") List<Long> storateTypeId,  @Param("tallySheetList") List<Long> tallySheetList);
    
   
    @Query("SELECT  C.clientTitle, B.fromDate, B.toDate, B.invoiceNo,  sum(B.netAmount), sum(B.gst) , B.invoiceDate  FROM   billing  B  JOIN  B.clientInfo  C   WHERE  B.storageClass.storageTypeId  IN :storateTypeId   AND  C.clientInfoId    BETWEEN :fromClient AND :toClientInfo    GROUP BY B.invoiceNo ")
    List<Object> loadLeaasBillAndBillType(@Param("storateTypeId") List<Long> storateTypeId,  @Param("fromClient") Long fromClient, @Param("toClientInfo") Long toClient );
   
    @Query("SELECT  C.clientTitle, B.fromDate, B.toDate, B.invoiceNo,  sum(B.netAmount), sum(B.gst), B.invoiceDate  FROM   billing  B  JOIN  B.clientInfo  C   WHERE  B.storageClass.storageTypeId  IN :storateTypeId  GROUP BY B.invoiceNo ")
    List<Object> loadLeaasBillAndBillTypeAllClient(@Param("storateTypeId") List<Long> storateTypeId );

    @Query("Select count(t) from wsoInfo t ")
    Long findCountWsoInfo();
    
    List<WSOInfo> findByWsoNoIgnoreCaseContaining(String wsoNo);
    
    @Query("Select d from wsoInfo d where d.wsoId Between :fromWsoId And :toWsoId")
    List<WSOInfo> findWsoByWsoId(@Param("fromWsoId") Long fromWsoId, @Param("toWsoId") Long toWsoId);
    
    @Query("Select d from deliveryList d where d.wsoInfo.wsoId = :wsoId  ORDER BY CAST(SUBSTRING(d.dlNo,LOCATE('D00000',d.dlNo)+1) as int)")
    List<DeliveryList> findDeliveryListByWsoId(@Param("wsoId") Long wsoId);
    
    @Query("Select t from wsoInfo t where t.tallysheet.clientInfo.clientInfoId = :clientId AND t.tallysheet.verify='1' AND t.tallysheet.storageDate <=:date")
    List<WSOInfo> loadWsoByClientId(@Param("clientId") Long clientId,@Param("date") Date date);
}

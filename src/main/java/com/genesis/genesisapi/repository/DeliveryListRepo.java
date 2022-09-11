package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.ClientInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.WSOInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DeliveryListRepo extends JpaRepository<DeliveryList,Long> {
	
    List<DeliveryList> findByisDeleted(Boolean isDeleted);
    DeliveryList findByisDeletedAndOutgoingInventoryId(Boolean isDeleted, Long outgoingInventoryId);
    List<DeliveryList> findByClientInfoClientInfoIdAndIsDeleted(Long clientInfoId, Boolean b);

    @Query("select vehicleNo from deliveryList d where d.clientInfo.clientInfoId = ?1")
    List<DeliveryList> findVehicleNoByClientInfoClientInfoId(Long clientInfoId);

    @Query("select nricOfReceiver from deliveryList d where d.clientInfo.clientInfoId = ?1")
    List<DeliveryList> findNricByClientInfoClientInfoId(Long clientInfoId);

    @Query("select d from deliveryList d where d.clientInfo.clientInfoId = ?1 order by d.dlNo asc")
    List<DeliveryList> findDlByClientInfoClientInfoId(Long clientInfoId);
    
    @Query("select nameOfReceiver from deliveryList d where d.clientInfo.clientInfoId = ?1")
    List<DeliveryList> findNameOfReceiverByClientInfoClientInfoId(Long clientId);
    
    @Query("select d  from deliveryList d where d.dlNo = ?1")
    List<DeliveryList> findByDlNo(String dlNo);

    @Query("select d  from deliveryList d order by d.dlNo desc")
    List<DeliveryList> findTop1ByOrderByOutgoingInventoryIdDesc();
    
    @Query("Select t from deliveryList t where t.outgoingInventoryId = :id ")
    List<DeliveryList> findWsoByDeliveryList(@Param("id") Long deliveryId);
    
    @Query("select d  from deliveryList d group by d.dlNo")
    List<DeliveryList> getAllDeliveryByDlNo();

    @Query("select d  from deliveryList d group by d.dlNo HAVING d.dlNo LIKE %:dlno%")
    List<DeliveryList> getAllDeliveryByDlNoAndDlno(@Param("dlno") String dlNo);
    
    @Query("select d  from deliveryList d where d.dlNo = :id")
    List<DeliveryList> getAllLotByDlNo(@Param("id") String dlNo);
    
    @Query("SELECT d FROM deliveryList d WHERE d.clientInfo.clientInfoId = :fromClient And d.dateOfDelivery BETWEEN :fromDate AND :toDate AND d.verify = 1 ORDER BY CAST(SUBSTRING(d.dlNo,LOCATE('D00000',d.dlNo)+1) as int)")
    List<DeliveryList> getDeliveryBetweenDate(@Param("fromClient") Long fromClient, @Param("fromDate") Date fromDate,  @Param("toDate") Date toDate );

    @Query("SELECT d FROM deliveryList d WHERE d.verify = 1 And d.dlNo IN :dlNoss")
    List<DeliveryList> getDeliveryIndl(@Param("dlNoss") List<String> dlNoss);
    
    @Query("SELECT d FROM clientInfo d WHERE d.clientInfoId BETWEEN :fromClient AND :toClient ")
    List<ClientInfo> getClientBetweenClient(@Param("fromClient") Long fromClient,  @Param("toClient") Long toClient);
    
    @Query("SELECT d FROM deliveryList d WHERE d.lotInfo.lotId = :lotId And d.dateOfDelivery > :date1 ")
    List<DeliveryList> getDeliveryByLotAndDate(@Param("lotId") Long lotId, @Param("date1") Date date1);

    @Query("SELECT d FROM deliveryList d WHERE d.wsoInfo.wsoId = :wsoId And d.dateOfDelivery Between :date1 AND :date2")
    List<DeliveryList> getDeliveryByWsoAndDate(@Param("wsoId") Long wsoId, @Param("date1") Date date1, @Param("date2") Date date2);
    

    @Query("SELECT d FROM deliveryList d WHERE d.wsoInfo.wsoId = :wsoId And d.verify = 1 And d.dateOfDelivery <=:date1")
    List<DeliveryList> getDeliveryByWsoAndDate2(@Param("wsoId") Long wsoId, @Param("date1") Date date1);
    
    @Query("SELECT sum(d.totalQty) FROM deliveryList d WHERE d.wsoInfo.wsoId = :wsoId And d.verify = 1 And d.dateOfDelivery <=:date1")
    Integer getDeliveryByWsoAndDate3(@Param("wsoId") Long wsoId, @Param("date1") Date date1);
    
    @Query("SELECT d FROM deliveryList d WHERE d.wsoInfo.wsoId = :wsoId And d.verify = 1")
    List<DeliveryList> getDeliveryByWsoId(@Param("wsoId") Long wsoId);
    
    @Query("SELECT d FROM deliveryList d WHERE d.lotInfo.lotId = :lotId")
    List<DeliveryList> getDeliveryByLotId(@Param("lotId") Long lotId);
    
    @Query("SELECT d FROM deliveryList d WHERE d.lotInfo.lotId = :lotId AND d.verify = 1 AND d.dateOfDelivery <=:date")
    List<DeliveryList> getDeliveryByLotIdANDDate(@Param("lotId") Long lotId,@Param("date") Date date);
    
    @Query("SELECT d FROM deliveryList d WHERE d.lotInfo.lotId = :lotId AND d.verify = 1 AND d.dateOfDelivery > :date AND d.dateOfDelivery <= now()")
    List<DeliveryList> getDeliveryByLotIdANDBetweenCurentAndGivenDate(@Param("lotId") Long lotId,@Param("date") Date date);
    
    @Query("SELECT sum(d.totalQty) FROM deliveryList d WHERE d.lotInfo.lotId = :lotId AND d.verify = 1 AND d.dateOfDelivery > :date AND d.dateOfDelivery <= now()")
    Integer getSumOfDeliveryByLotIdANDBetweenCurentAndGivenDate(@Param("lotId") Long lotId,@Param("date") Date date);
    
    @Query("SELECT d FROM deliveryList d WHERE d.lotInfo.lotId = :lotId")
    List<DeliveryList> getDeliveryByLotId1(@Param("lotId") Long lotId);
    
    @Query("SELECT d FROM deliveryList d WHERE d.lotInfo.lotId = :lotId And d.verify = 1 And d.dateOfDelivery Between :FromDate AND :ToDate")
    List<DeliveryList> getDeliveryByWsoAndBetweenDates(@Param("lotId") Long lotId, @Param("FromDate") Date FromDate, @Param("ToDate") Date ToDate);
}

package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.OtherCharges;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OtherChargesRepo extends JpaRepository<OtherCharges,Long> {

	@Query("Select t from other_charges  t where t.clientInfo.clientInfoId = :id  ")
	List<OtherCharges>  getOtherChargeByClient(@Param("id") Long clientId);
	
	@Query("Select t from other_charges  t where t.verifyInd ='yes' AND t.billGenerated='0' AND t.clientInfo.clientInfoId BETWEEN :clientId1 AND :clientId2 ")
	List<OtherCharges>  getOtherChargesBetweenClient(@Param("clientId1") Long clientId1, @Param("clientId2") Long clientId2);

	@Query("Select t.formNo  from other_charges  t where t.clientInfo.clientInfoId = :id group by formNo  ")
	public List<OtherCharges>  getAllFormNoByClient( @Param("id") Long clientId1);
	
	
	@Query("Select t  from other_charges  t where t.clientInfo.clientInfoId = :id and t.formNo = :formNo ")
	public List<OtherCharges>  getAllFormNoByClientIdAndFormNu( @Param("id") Long clientId1,@Param("formNo") String formNum);
	
	@Query("Select distinct t.formNo   from other_charges  t ORDER BY t.otherChargesId DESC  ")
    public List<String> findTop1OrderByFormnoDesc();

	@Query("Select t  from other_charges  t where t.clientInfo.clientInfoId = :id and t.isDeleted = 0 ")
	public List<OtherCharges> findByClientId(@Param("id") Long clientId1);
	
	@Query(value = "Select sum(bilable_amount) from other_charges where client_info_client_info_id = :clientId AND from_date >= :fromDate AND to_date <= :toDate", nativeQuery = true)
    Double getBilableAmt(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query(value = "Select sum(bilable_amount) from other_charges where client_info_client_info_id = :clientId AND from_date >= :fromDate AND to_date <= :toDate AND charge_items_charge_item_id=13", nativeQuery = true)
    Double getBilableAmtRT(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query(value = "Select sum(bilable_amount) from other_charges where client_info_client_info_id = :clientId AND from_date >= :fromDate AND to_date <= :toDate AND charge_items_charge_item_id=15", nativeQuery = true)
    Double getBilableAmtFBA(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
    /*
    List<OtherCharges> findByisDeleted(Boolean isDeleted);
    OtherCharges findByisDeletedAndOutgoingInventoryId(Boolean isDeleted, Long outgoingInventoryId);
    List<OtherCharges> findByClientInfoClientInfoIdAndIsDeleted(Long clientInfoId, Boolean b);
    */
}

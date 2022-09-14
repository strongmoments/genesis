package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.LotInfo;
import com.genesis.genesisapi.model.TallySheet;
import com.genesis.genesisapi.model.WSOInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface LotInfoRepo extends JpaRepository<LotInfo,Long> {

    List<LotInfo> findByisDeleted(Boolean isDeleted);

    LotInfo findByisDeletedAndLotId(Boolean isDeleted, Long lotId);

    @Query("SELECT l, w FROM lotInfo l LEFT JOIN l.wsoInfo w WHERE w.wsoId = ?1 AND l.productionDate > ?2")
    //@Query("select l FROM lotInfo l where l.production_date > :date")
    List<LotInfo> fingByWsoInfoId(Long wsoId, Date date);

    @Query("Select lotNo from lotInfo")
    List<LotInfo> findLotIdAndNo();

    @Query("Select t from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
    List<LotInfo> findLotByWsoId(@Param("id") Long wsoId);

    @Query("Select count(t) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
    Long findCountLotByWsoId(@Param("id") Long wsoId);
    
    @Query("Select t from lotInfo t where t.lotId = :id")
    List<LotInfo> findLotDetailByLotId(@Param("id") Long lotId);

    @Query("Select count(t) from lotInfo t ")
    Long findCountLotInfo();

	LotInfo findByLotNo(String lotNo);
	
	LotInfo findByWsoInfoWsoIdAndLotNo(Long wsoId, String lotNo);
	
	@Query("Select distinct(t.description) from lotInfo t where t.wsoInfo.wsoId IN :wsoIdList order by t.description asc")
    List<String> findDistinctLotByWsoIdList(@Param("wsoIdList") List<Long> wsoIdList);
	
	@Query("Select t from lotInfo t where t.wsoInfo.wsoId IN :wsoIdList group by t.description")
    List<LotInfo> findDistinctLotDetailByWsoIdList(@Param("wsoIdList") List<Long> wsoIdList);
	
	@Query("Select t from lotInfo t where t.wsoInfo.wsoId IN :wsoIdList AND t.description = :description")
	List<LotInfo> findLotDetailByWsoIdList(@Param("wsoIdList") List<Long> wsoIdList, @Param("description") String description);
	
	@Query("Select sum(t.unitNetWeightInKg * t.lotQuantity) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
    Float findTotalUnitWeightLotByWsoId(@Param("id") Long wsoId);
	
	@Query("Select sum(t.unitNetWeightInKg * t.lotQuantity) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId in (select a.wsoId from wsoInfo a where a.tallysheet.tallysheetId =:tallyId) ")
	Float getTallysheetGrandTotal(@Param("tallyId") Long tallyId);
	
	@Query("Select sum(t.unitNetWeightInKg * t.currentQuantity) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
	Float findTotCurrWtLotByWsoId(@Param("id") Long wsoId);
	
	@Query("Select sum(t.unitNetWeightInKg * t.lotQuantity) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
	Float findTotLotByWsoId(@Param("id") Long wsoId);
	
	@Query("Select sum(t.currentQuantity) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
	Integer findTotCurrQtyLotByWsoId(@Param("id") Long wsoId);
	
	@Query("Select sum(t.initialQuantity) from lotInfo t where t.isDeleted = 0 AND t.wsoInfo.wsoId = :id")
	Integer findTotQtyLotByWsoId(@Param("id") Long wsoId);
	
}

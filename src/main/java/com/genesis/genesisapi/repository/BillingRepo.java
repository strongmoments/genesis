package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.BillByInvoice;
import com.genesis.genesisapi.model.Billing;
import com.genesis.genesisapi.model.ClientStorageInfo;
import com.genesis.genesisapi.model.DeliveryList;
import com.genesis.genesisapi.model.WSOInfo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepo extends JpaRepository<Billing, Long> {
	@Query("Select t from billing t  ")
    List<Billing> loadAllBill();

	Billing findTop1ByOrderByBillingIdDesc();

	@Query("Select t from billing t where t.invoiceNo =:invoiceNum")
    List<Billing> getBillDetailByInvoiceNum(@Param("invoiceNum") String invoiceNum);
	
	@Query("Select t from billing t where t.wsoInfo.wsoId =:id")
    List<Billing> getBillDetailByWsoId(@Param("id") Long wsoId);
	
	@Query("Select t from billing t where t.storageClass.storageTypeId  IN:storageList")
    List<Billing> getLeaseBillDetail(@Param("storageList") List<Long> storageList);
	
	@Query("SELECT  C.clientTitle, B.invoiceDate,  B.invoiceNo, sum(B.gst),  sum(B.netAmount)  FROM   billing  B  JOIN  B.clientInfo C   WHERE   C.clientInfoId    BETWEEN :fromClient AND :toClientInfo  AND  B.storageClass.storageTypeId  IS NULL GROUP BY B.invoiceNo")
	//@Query("Select t from billing t where t.storageClass.storageTypeId is NULL")
    List<Object> getOtherBillDetail(@Param("fromClient") Long fromClient,  @Param("toClientInfo") Long toClientInfo );
	
	@Query("SELECT  C.clientTitle, B.invoiceDate,  B.invoiceNo, sum(B.gst),  sum(B.netAmount)  , B.formNo  FROM   billing  B  JOIN  B.clientInfo C   WHERE   B.storageClass.storageTypeId  IS NULL GROUP BY B.invoiceNo")
    List<Object> getOtherBillAllDetail();

	@Query("SELECT  B.storageClass.storageTypeName, sum(B.netAmount) - sum(B.gst), sum(B.handlingCharge), sum(B.gst),B.invoiceNo  FROM   billing  B WHERE B.invoiceDate BETWEEN :fromDate And :toDate  GROUP BY B.storageClass.storageTypeId ")
    List<Object> getStorageReport(@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query("SELECT  B.ChargeItems.chargeItem, sum(B.netAmount) - sum(B.gst), sum(B.handlingCharge), sum(B.gst),B.invoiceNo  FROM   billing  B WHERE B.invoiceDate BETWEEN :fromDate And :toDate  GROUP BY B.ChargeItems.chargeItemId ")
    List<Object> getChargeItemReport(@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	/*@Query("SELECT  B.storageClass.storageTypeName, sum(B.netAmount), sum(B.handlingCharge), sum(B.gst),B.invoiceNo  FROM   billing  B WHERE B.invoiceDate BETWEEN :fromDate And :toDate  GROUP BY B.storageClass.storageTypeId ")
    List<Object> getStorageReport(@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query("SELECT  B.ChargeItems.chargeItem, sum(B.netAmount), sum(B.handlingCharge), sum(B.gst),B.invoiceNo  FROM   billing  B WHERE B.invoiceDate BETWEEN :fromDate And :toDate  GROUP BY B.ChargeItems.chargeItemId ")
    List<Object> getChargeItemReport(@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);*/

	/*@Query("Select t from billing t where t.clientInfo.clientInfoId = :clientId AND t.billPaid = 0 ")
    List<Billing> getBillForPayment(@Param("clientId") Long clientId);*/
	
	@Query("Select t.clientInfo.clientTitle, t.invoiceNo, sum(t.netAmount) from billing t where t.clientInfo.clientInfoId = :clientId AND t.billPaid = 0 GROUP BY t.invoiceNo")
    List<Object> getBillForPayment(@Param("clientId") Long clientId);
	
	
	@Query("Select t from billing t where t.clientInfo.clientInfoId = :clientId AND t.billPaid = 0 ")
    List<Billing> getBillingInfoByCLientId(@Param("clientId") Long clientId);
	
	@Query("Select t from billing t WHERE t.clientInfo.clientInfoId BETWEEN :fromClient AND :toClient AND t.billPaid = 0 ")
    List<Billing> getBillingInfoBetweenCLientId(@Param("fromClient") Long fromClient,  @Param("toClient") Long toClient);
	
	@Query("UPDATE billing t set t.billPaid = '1' where t.invoiceNo = :invoiceNO")
	public void updatePaidBilling(@Param("invoiceNO") String invoiceNo);
	
	@Query("UPDATE billing t set t.billPaid = '1' where t.invoiceNo = :invoiceNO")
	public void updateUnPaidBilling(@Param("invoiceNO") String invoiceNo);
	
	@Query("Select t from billing t where t.invoiceNo = :invoiceNO")
    List<Billing> getBillingInfoByInvoiceNo(@Param("invoiceNO") String invoiceNo);
	
	@Query("Select t from billing t where t.invoiceNo = :invoiceNO group by invoiceNo")
    List<Billing> getBillingInfoByInvoiceNoGroup(@Param("invoiceNO") String invoiceNo);
	
	@Query("Select sum(t.netAmount),t.invoiceNo from billing t where t.clientInfo.clientInfoId = :clientId AND t.billPaid = 0 group by invoiceNo")
    List<Object> getBillingInfoByCLientIdGroup(@Param("clientId") Long clientId);
	
	@Query("Select t.invoiceNo,t.invoiceDate,sum(t.netAmount)-sum(t.gst),sum(t.gst),sum(t.netAmount) from billing t where t.clientInfo.clientInfoId = :clientId AND t.invoiceDate BETWEEN :fromDate And :toDate group by invoiceNo")
    List<Object> getBillingInfoByCLientIdAndBetweenDatesForInvList(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query("Select t.invoiceNo,t.invoiceDate,sum(t.netAmount)-sum(t.gst),sum(t.gst),sum(t.netAmount) from billing t where t.clientInfo.clientInfoId = :clientId AND t.invoiceDate BETWEEN :fromDate And :toDate GROUP BY t.storageClass.storageTypeId")
    List<Object> getBillingInfoByCLientIdAndBetweenDates(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query("Select t.handlingCharge,sum(t.netAmount) from billing t where t.clientInfo.clientInfoId = :clientId AND t.invoiceDate BETWEEN :fromDate And :toDate group by t.invoiceNo")
	List<Object> getBillingByCLientIdAndBetweenDates(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query("Select t.invoiceNo,t.invoiceDate,sum(t.netAmount)-sum(t.gst),sum(t.gst),sum(t.netAmount) from billing t where t.clientInfo.clientInfoId = :clientId AND t.billPaid = 1 AND t.invoiceDate BETWEEN :fromDate And :toDate group by invoiceNo")
    List<Object> getInvoiceDates(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	@Query("Select t.storageClass.storageTypeName,t.invoiceNo from billing t where t.clientInfo.clientInfoId = :clientId AND t.billPaid = 1 AND t.invoiceDate BETWEEN :fromDate And :toDate group by invoiceNo")
    List<Object> getStorageType(@Param("clientId") Long clientId,@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
}

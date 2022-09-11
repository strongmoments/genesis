package com.genesis.genesisapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.genesis.genesisapi.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

	@Query("Select p from payment p where p.billing.billingId = :billId")
	List<Payment> findPaymentByBillId(@Param("billId") Long billId);

	@Query("SELECT p FROM payment p where p.invoiceNo = :invoiceNo ORDER BY p.paymentId DESC")
	List<Payment> findLastPaymentByInvoiceNo(@Param("invoiceNo") String invoiceNo);

	@Query("Select p from payment p where p.invoiceNo = :invoiceNo")
	List<Payment> findPaymentByInvoiceNo(@Param("invoiceNo") String invoiceNo);

	@Query("Select p from payment p where (p.invoiceNo = :invoiceNo) and (p.balance=(select min(c.balance) from payment c where c.invoiceNo = :invoiceNo))")
	List<Payment> findPaymentByInvoiceNoDistinct(@Param("invoiceNo") String invoiceNo);

	@Query("Select p from payment p where p.paymentType = :ptype ORDER BY p.cnPnId DESC ")
	List<Payment> findLatestcnPnNo(@Param("ptype") String paymentType);

	@Query("Select p from payment p where p.invoiceNo in(:invoiceNo)")
	List<Payment> findPaymentByInvoiceNoList(@Param("invoiceNo") List<String> invoiceNo);

	/*@Query("SELECT p.paymentDate, sum(p.paidAmount), p.invoiceNo FROM payment p WHERE YEAR(p.paymentDate) = :year AND MONTH(p.paymentDate) = :month GROUP BY p.invoiceNo")
	List<Object> findPaymentByMonthAndYear(@Param("month") int month, @Param("year") int year);*/
	
	@Query("SELECT p.paymentDate, sum(p.paidAmount), p.invoiceNo FROM payment p WHERE p.paymentDate BETWEEN :fromDate And :toDate GROUP BY p.invoiceNo ORDER BY p.paymentDate")
	List<Object> findPaymentBetweenDates(@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);
	
	/*@Query("SELECT p.paymentDate, sum(p.paidAmount), p.invoiceNo FROM payment p WHERE p.paymentDate BETWEEN :fromDate And :toDate GROUP BY p.invoiceNo, p.billing.clientInfo.clientTitle ORDER BY p.paymentDate")
	List<Object> findPaymentBetweenDatesGrpByCient(@Param("fromDate") Date fromDate, @Param("toDate") Date  toDate);*/
}

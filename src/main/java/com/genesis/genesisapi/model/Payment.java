package com.genesis.genesisapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Table
@Entity(name="payment")
public class Payment {

	@Id
	@GeneratedValue
	@Column(name = "payment_id", length = 20)
	private long paymentId;
	
	@Column(name = "invoice_no", length = 500)
    private String invoiceNo;
	
	@Column(name = "cnPnId", length = 100)
    private String cnPnId;
	
	@Column(name = "paymentType", length = 5)
    private String paymentType;
	
	@Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
	
	@Column(name = "paid_amount")
    private float paidAmount;

    @Column(name = "balance")
    private float balance;
    
    @Column(name = "payment_user", length = 500)
    private String paymentUser;
    
    @Column(name = "credit", length = 500)
    private String credit;
    
    @Column(name = "credit_note", length = 500)
    private String creditNote;
   
    @ManyToOne
    private Billing billing;

	public Payment() {
	}

	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCnPnId() {
		return cnPnId;
	}

	public void setCnPnId(String cnPnId) {
		this.cnPnId = cnPnId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public float getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(float paidAmount) {
		this.paidAmount = paidAmount;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public String getPaymentUser() {
		return paymentUser;
	}

	public void setPaymentUser(String paymentUser) {
		this.paymentUser = paymentUser;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCreditNote() {
		return creditNote;
	}

	public void setCreditNote(String creditNote) {
		this.creditNote = creditNote;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public Payment(long paymentId, String invoiceNo, String cnPnId, String paymentType, Date paymentDate,
			float paidAmount, float balance, String paymentUser, String credit, String creditNote, Billing billing) {
		super();
		this.paymentId = paymentId;
		this.invoiceNo = invoiceNo;
		this.cnPnId = cnPnId;
		this.paymentType = paymentType;
		this.paymentDate = paymentDate;
		this.paidAmount = paidAmount;
		this.balance = balance;
		this.paymentUser = paymentUser;
		this.credit = credit;
		this.creditNote = creditNote;
		this.billing = billing;
	}

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", invoiceNo=" + invoiceNo + ", cnPnId=" + cnPnId + ", paymentType="
				+ paymentType + ", paymentDate=" + paymentDate + ", paidAmount=" + paidAmount + ", balance=" + balance
				+ ", paymentUser=" + paymentUser + ", credit=" + credit + ", creditNote=" + creditNote + ", billing="
				+ billing + "]";
	}

}

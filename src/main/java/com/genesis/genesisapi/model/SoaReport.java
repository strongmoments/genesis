package com.genesis.genesisapi.model;

public class SoaReport {
	
	private String invoiceDate;
	private String invoiceNo;
	private String creditTerms;
	private String documentDate;
	private String paymentCreditNote;
	private String daysOverDue;
	private String debit;
	private String credit;
	private String balanceAmount;
	
	
	
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getCreditTerms() {
		return creditTerms;
	}
	public void setCreditTerms(String creditTerms) {
		this.creditTerms = creditTerms;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}
	public String getPaymentCreditNote() {
		return paymentCreditNote;
	}
	public void setPaymentCreditNote(String paymentCreditNote) {
		this.paymentCreditNote = paymentCreditNote;
	}
	public String getDaysOverDue() {
		return daysOverDue;
	}
	public void setDaysOverDue(String daysOverDue) {
		this.daysOverDue = daysOverDue;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
}

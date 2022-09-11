package com.genesis.genesisapi.model;

public class PymtBillingReport {
	private String clientTitle;
	private String receivedDate;
	private String receivedAmt;
	private String invoiceNo;
	private String status;
	private String subTotal;
	private String ttlAmt;
	private String verify;
	
	public String getClientTitle() {
		return clientTitle;
	}

	public void setClientTitle(String clientTitle) {
		this.clientTitle = clientTitle;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getReceivedAmt() {
		return receivedAmt;
	}

	public void setReceivedAmt(String receivedAmt) {
		this.receivedAmt = receivedAmt;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getTtlAmt() {
		return ttlAmt;
	}

	public void setTtlAmt(String ttlAmt) {
		this.ttlAmt = ttlAmt;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	@Override
	public String toString() {
		return "PymtBillingReport [clientTitle=" + clientTitle + ", receivedDate=" + receivedDate + ", receivedAmt="
				+ receivedAmt + ", invoiceNo=" + invoiceNo + ", status=" + status + ", subTotal=" + subTotal
				+ ", ttlAmt=" + ttlAmt + ", verify=" + verify + "]";
	}

}

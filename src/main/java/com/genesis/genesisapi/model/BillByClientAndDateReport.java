package com.genesis.genesisapi.model;

public class BillByClientAndDateReport {
	public BillByClientAndDateReport() {
		
	}
	private String invNo;
	private String invDate;
	private String client;
	private String subTotal;
	private String gst;
	private String total;
	private String totalsubTotal;
	private String totalGst;
	private String totalTotal;
	public String getInvNo() {
		return invNo;
	}
	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}
	public String getInvDate() {
		return invDate;
	}
	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalsubTotal() {
		return totalsubTotal;
	}
	public void setTotalsubTotal(String totalsubTotal) {
		this.totalsubTotal = totalsubTotal;
	}
	public String getTotalGst() {
		return totalGst;
	}
	public void setTotalGst(String totalGst) {
		this.totalGst = totalGst;
	}
	public String getTotalTotal() {
		return totalTotal;
	}
	public void setTotalTotal(String totalTotal) {
		this.totalTotal = totalTotal;
	}
}

package com.genesis.genesisapi.model;

import java.util.Date;

public class CommonBilling {

	private String wsoNo;
	private String des;
	private Date fromDate;
	private Date toDate;
	private String rate;
	private String gst;
	private String amount;
	private String weight;
	private String subTotal;
	public String ttlAmount;
	public String getWsoNo() {
		return wsoNo;
	}
	public void setWsoNo(String wsoNo) {
		this.wsoNo = wsoNo;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getTtlAmount() {
		return ttlAmount;
	}
	public void setTtlAmount(String ttlAmount) {
		this.ttlAmount = ttlAmount;
	}
	
	

	
}

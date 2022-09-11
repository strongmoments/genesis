package com.genesis.genesisapi.model;

import java.util.Date;

public class leaseBilling {
	
	private String storage;
	private String fromDate;
	private String toDate;
	private String rate;
	private String gst;
	private String amount;
	public String ttlAmount;
	
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTtlAmount() {
		return ttlAmount;
	}
	public void setTtlAmount(String ttlAmount) {
		this.ttlAmount = ttlAmount;
	}

	
}

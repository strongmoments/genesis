package com.genesis.genesisapi.model;

public class OtherChargesBilling {

	private String chargeItem;
	private String billableUnit;
	private String rate;
	private String subTotal;
	private String gst;
	private String amount;
	public String ttlAmount;
	
	public String getChargeItem() {
		return chargeItem;
	}
	public void setChargeItem(String chargeItem) {
		this.chargeItem = chargeItem;
	}
	public String getBillableUnit() {
		return billableUnit;
	}
	public void setBillableUnit(String billableUnit) {
		this.billableUnit = billableUnit;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
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

package com.genesis.genesisapi.model;

import java.lang.reflect.Field;

public class FinanceReport {

	private String chargeItems;
	private String revenue;
	private String totalAmount;
	private String totalGst;
	private String total;
	private String grandCreditAmount;
	private String grandCreditGst;
	
	public String getTotalGst() {
		return totalGst;
	}
	public void setTotalGst(String totalGst) {
		this.totalGst = totalGst;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getChargeItems() {
		return chargeItems;
	}
	public void setChargeItems(String chargeItems) {
		this.chargeItems = chargeItems;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	public String getGrandCreditAmount() {
		return grandCreditAmount;
	}
	public void setGrandCreditAmount(String grandCreditAmount) {
		this.grandCreditAmount = grandCreditAmount;
	}
	public String getGrandCreditGst() {
		return grandCreditGst;
	}
	public void setGrandCreditGst(String grandCreditGst) {
		this.grandCreditGst = grandCreditGst;
	}
	
}

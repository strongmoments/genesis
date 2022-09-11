package com.genesis.genesisapi.model;

public class CustListReport {
	public CustListReport() {
		
	}
	private String clientTitle;
	private String storageType;
	private String handOthersChrg;
	private String totalBilableAmtFBA;
	private String totalBilableAmtRT;
	private String gst;
	private String totalRevenue;
	
	public String getClientTitle() {
		return clientTitle;
	}
	public void setClientTitle(String clientTitle) {
		this.clientTitle = clientTitle;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	public String getHandOthersChrg() {
		return handOthersChrg;
	}
	public void setHandOthersChrg(String handOthersChrg) {
		this.handOthersChrg = handOthersChrg;
	}
	public String getTotalBilableAmtFBA() {
		return totalBilableAmtFBA;
	}
	public void setTotalBilableAmtFBA(String totalBilableAmtFBA) {
		this.totalBilableAmtFBA = totalBilableAmtFBA;
	}
	public String getTotalBilableAmtRT() {
		return totalBilableAmtRT;
	}
	public void setTotalBilableAmtRT(String totalBilableAmtRT) {
		this.totalBilableAmtRT = totalBilableAmtRT;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(String totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	
}

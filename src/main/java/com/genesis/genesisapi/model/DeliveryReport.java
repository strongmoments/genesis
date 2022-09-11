package com.genesis.genesisapi.model;

public class DeliveryReport {
	private String dlNo;
	private String dateOfDelivery;
	private String lotNo;
	private String packagesOut;
	private String wsoNo;
	private String clientName;
	private String totalWsoWeight;
	private String storageDate;
	public String getDlNo() {
		return dlNo;
	}
	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}
	public String getDateOfDelivery() {
		return dateOfDelivery;
	}
	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getPackagesOut() {
		return packagesOut;
	}
	public void setPackagesOut(String packagesOut) {
		this.packagesOut = packagesOut;
	}
	public String getWsoNo() {
		return wsoNo;
	}
	public void setWsoNo(String wsoNo) {
		this.wsoNo = wsoNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getTotalWsoWeight() {
		return totalWsoWeight;
	}
	public void setTotalWsoWeight(String totalWsoWeight) {
		this.totalWsoWeight = totalWsoWeight;
	}
	public String getStorageDate() {
		return storageDate;
	}
	public void setStorageDate(String storageDate) {
		this.storageDate = storageDate;
	}
}

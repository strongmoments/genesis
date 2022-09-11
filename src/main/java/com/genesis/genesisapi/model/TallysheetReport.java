package com.genesis.genesisapi.model;

public class TallysheetReport {

	private String wsoNo;
	private String remarks;
	private String des;
	private String lotWeight;
	private String lotQuantity;
	private Integer pallets;
	private String storage;
	private String ttllotWeight;
	private String ttllotQuantity;
	private Integer ttlpallets;
	
	public String getTtllotWeight() {
		return ttllotWeight;
	}
	public void setTtllotWeight(String ttllotWeight) {
		this.ttllotWeight = ttllotWeight;
	}
	public String getTtllotQuantity() {
		return ttllotQuantity;
	}
	public void setTtllotQuantity(String ttllotQuantity) {
		this.ttllotQuantity = ttllotQuantity;
	}
	public Integer getTtlpallets() {
		return ttlpallets;
	}
	public void setTtlpallets(Integer ttlpallets) {
		this.ttlpallets = ttlpallets;
	}
	public String getWsoNo() {
		return wsoNo;
	}
	public void setWsoNo(String wsoNo) {
		this.wsoNo = wsoNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getLotWeight() {
		return lotWeight;
	}
	public void setLotWeight(String lotWeight) {
		this.lotWeight = lotWeight;
	}
	public String getLotQuantity() {
		return lotQuantity;
	}
	public void setLotQuantity(String lotQuantity) {
		this.lotQuantity = lotQuantity;
	}
	public Integer getPallets() {
		return pallets;
	}
	public void setPallets(Integer pallets) {
		this.pallets = pallets;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
}

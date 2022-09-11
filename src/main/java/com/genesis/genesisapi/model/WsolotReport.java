package com.genesis.genesisapi.model;

public class WsolotReport {

	private String wsoNo;
	private String lotNo;
	private String des;
	private String lotWeight;
	private Integer lotQuantity;
	private Integer pallets;
	
	private String ttllotWeight;
	private Integer ttllotQuantity;
	private Integer ttlpallets;
	
	
	public String getTtllotWeight() {
		return ttllotWeight;
	}
	public void setTtllotWeight(String ttllotWeight) {
		this.ttllotWeight = ttllotWeight;
	}
	public Integer getTtllotQuantity() {
		return ttllotQuantity;
	}
	public void setTtllotQuantity(Integer ttllotQuantity) {
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
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
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
	public Integer getLotQuantity() {
		return lotQuantity;
	}
	public void setLotQuantity(Integer lotQuantity) {
		this.lotQuantity = lotQuantity;
	}
	public Integer getPallets() {
		return pallets;
	}
	public void setPallets(Integer pallets) {
		this.pallets = pallets;
	}
	
	
}

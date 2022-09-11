package com.genesis.genesisapi.model;

public class DeliverylistReport {

	private String wsoNo;
	private String lotNo;
	private String des;
	private Integer lotQuantity;
	private Integer ttllotQuantity;
	
	
	public Integer getTtllotQuantity() {
		return ttllotQuantity;
	}
	public void setTtllotQuantity(Integer ttllotQuantity) {
		this.ttllotQuantity = ttllotQuantity;
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
	public Integer getLotQuantity() {
		return lotQuantity;
	}
	public void setLotQuantity(Integer lotQuantity) {
		this.lotQuantity = lotQuantity;
	}

}

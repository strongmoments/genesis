package com.genesis.genesisapi.model;

public class LotByClientReport {
	public LotByClientReport() {
		
	}
	
	private String lotDesc;
	private String lotNo;
	private String wsoNo;
	private String date;
	private String initialQty;
	private String currentQty;
	private String prodDate;
	private String expDate;
	private String unitKg;
	private String unitQtyLot;
	private String storageClass;
	public String getLotDesc() {
		return lotDesc;
	}
	public void setLotDesc(String lotDesc) {
		this.lotDesc = lotDesc;
	}
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getWsoNo() {
		return wsoNo;
	}
	public void setWsoNo(String wsoNo) {
		this.wsoNo = wsoNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getInitialQty() {
		return initialQty;
	}
	public void setInitialQty(String initialQty) {
		this.initialQty = initialQty;
	}
	public String getCurrentQty() {
		return currentQty;
	}
	public void setCurrentQty(String currentQty) {
		this.currentQty = currentQty;
	}
	public String getProdDate() {
		return prodDate;
	}
	public void setProdDate(String prodDate) {
		this.prodDate = prodDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getUnitKg() {
		return unitKg;
	}
	public void setUnitKg(String unitKg) {
		this.unitKg = unitKg;
	}
	public String getUnitQtyLot() {
		return unitQtyLot;
	}
	public void setUnitQtyLot(String unitQtyLot) {
		this.unitQtyLot = unitQtyLot;
	}
	public String getStorageClass() {
		return storageClass;
	}
	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}
	
	
}

package com.genesis.genesisapi.model;

public class DateInventoryReport {
	private String productDes;
	private String productCode;
	private String wsoNo;
	private String storageClass;
	private String storageDate;
	private Integer initialQty;
	private Integer currentQty;
	private String productDate;
	private String expiryDate;
	private String netWt;
	private String grossWt;
	
	private Integer ttlinitialQty;
	private Integer ttlcurrentQty;
	
	
	public Integer getTtlinitialQty() {
		return ttlinitialQty;
	}
	public void setTtlinitialQty(Integer ttlinitialQty) {
		this.ttlinitialQty = ttlinitialQty;
	}
	public Integer getTtlcurrentQty() {
		return ttlcurrentQty;
	}
	public void setTtlcurrentQty(Integer ttlcurrentQty) {
		this.ttlcurrentQty = ttlcurrentQty;
	}
	public String getProductDes() {
		return productDes;
	}
	public void setProductDes(String productDes) {
		this.productDes = productDes;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getWsoNo() {
		return wsoNo;
	}
	public void setWsoNo(String wsoNo) {
		this.wsoNo = wsoNo;
	}
	public String getStorageClass() {
		return storageClass;
	}
	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}
	public String getStorageDate() {
		return storageDate;
	}
	public void setStorageDate(String storageDate) {
		this.storageDate = storageDate;
	}
	public Integer getInitialQty() {
		return initialQty;
	}
	public void setInitialQty(Integer initialQty) {
		this.initialQty = initialQty;
	}
	public Integer getCurrentQty() {
		return currentQty;
	}
	public void setCurrentQty(Integer currentQty) {
		this.currentQty = currentQty;
	}
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getNetWt() {
		return netWt;
	}
	public void setNetWt(String netWt) {
		this.netWt = netWt;
	}
	public String getGrossWt() {
		return grossWt;
	}
	public void setGrossWt(String grossWt) {
		this.grossWt = grossWt;
	}
	
}

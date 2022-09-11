package com.genesis.genesisapi.model;

public class WsoEnquiryReport {

	private String lotNo;
	private String dlNo;
	private String date;
	private int packagesOut;
	public String getLotNo() {
		return lotNo;
	}
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	public String getDlNo() {
		return dlNo;
	}
	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getPackagesOut() {
		return packagesOut;
	}
	public void setPackagesOut(int packagesOut) {
		this.packagesOut = packagesOut;
	}
	
	
}

package com.genesis.genesisapi.model;

import java.util.Date;
import java.util.List;

public class BillingTest {
	String invoiceNo;
	String clientTitle;
	String tallyNo;
	Date storageDate;
	Date deliveryDate;
	String dlNo;
	String vehicleNo;
	String nricNo;
	String nameOfRec;
	String exVessel;
	Float unstuffTemp;
	Float tempRec;
	
	Date invoiceDate;
	Double totalAmount;
	public List<leaseBilling> leaseDetail ;
	public List<CommonBilling> commonDetail ;
	public List<OtherChargesBilling> otherDetail ;
	public List<TallysheetReport> tallyDetail ;
	public List<DeliverylistReport> deliveryDetail ;
	
	
	public List<DeliverylistReport> getDeliveryDetail() {
		return deliveryDetail;
	}

	public void setDeliveryDetail(List<DeliverylistReport> deliveryDetail) {
		this.deliveryDetail = deliveryDetail;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDlNo() {
		return dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getNricNo() {
		return nricNo;
	}

	public void setNricNo(String nricNo) {
		this.nricNo = nricNo;
	}

	public String getNameOfRec() {
		return nameOfRec;
	}

	public void setNameOfRec(String nameOfRec) {
		this.nameOfRec = nameOfRec;
	}

	public String getTallyNo() {
		return tallyNo;
	}

	public void setTallyNo(String tallyNo) {
		this.tallyNo = tallyNo;
	}

	public Date getStorageDate() {
		return storageDate;
	}

	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}

	public String getExVessel() {
		return exVessel;
	}

	public void setExVessel(String exVessel) {
		this.exVessel = exVessel;
	}

	public Float getUnstuffTemp() {
		return unstuffTemp;
	}

	public void setUnstuffTemp(Float unstuffTemp) {
		this.unstuffTemp = unstuffTemp;
	}

	public Float getTempRec() {
		return tempRec;
	}

	public void setTempRec(Float tempRec) {
		this.tempRec = tempRec;
	}

	public List<TallysheetReport> getTallyDetail() {
		return tallyDetail;
	}

	public void setTallyDetail(List<TallysheetReport> tallyDetail) {
		this.tallyDetail = tallyDetail;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getClientTitle() {
		return clientTitle;
	}

	public void setClientTitle(String clientTitle) {
		this.clientTitle = clientTitle;
	}

	
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public List<leaseBilling> getLeaseDetail() {
		return leaseDetail;
	}

	public void setLeaseDetail(List<leaseBilling> leaseDetail) {
		this.leaseDetail = leaseDetail;
	}

	public List<CommonBilling> getCommonDetail() {
		return commonDetail;
	}

	public void setCommonDetail(List<CommonBilling> commonDetail) {
		this.commonDetail = commonDetail;
	}

	public List<OtherChargesBilling> getOtherDetail() {
		return otherDetail;
	}

	public void setOtherDetail(List<OtherChargesBilling> otherDetail) {
		this.otherDetail = otherDetail;
	}
	
	
}

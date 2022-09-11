package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity(name="client_storage_info")
@Where(clause = "is_deleted='false'")
public class ClientStorageInfo {

    @Id
    @GeneratedValue
    @Column(name = "client_storage_id", length = 20)
    private long clientStorageId;

    @Column(name = "storage_start_date")
    @Temporal(TemporalType.DATE)
    private Date storageStartDate;

    @Column(name = "storage_end_date")
    @Temporal(TemporalType.DATE)
    private Date storageEndDate;

    @Column(name = "monthly_rate")
    private float monthlyRate;

    @Column(name = "handling_charges")
    private float handlingCharges;
    
    
    @Column(name = "first_billing")
    private boolean isfirstBilling;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "last_bill_date")
    @Temporal(TemporalType.DATE)
    private Date lastBillDate;

    @Column(name = "next_bill_date")
    @Temporal(TemporalType.DATE)
    private Date nextBillDate;

    @ManyToOne
    //@JsonBackReference
    private StorageType storageType;

    @ManyToOne
    @JsonBackReference
    private ClientInfo clientInfo;

    @ManyToOne
    @JsonBackReference
    private WarehouseInfo warehouseInfo;

    public ClientStorageInfo() {
    }

    public ClientStorageInfo(long clientStorageId, Date storageStartDate, Date storageEndDate, float monthlyRate, float handlingCharges, Date lastBillDate, Date nextBillDate, boolean isDeleted, StorageType storageType, ClientInfo clientInfo, WarehouseInfo warehouseInfo) {
        this.clientStorageId = clientStorageId;
        this.storageStartDate = storageStartDate;
        this.storageEndDate = storageEndDate;
        this.monthlyRate = monthlyRate;
        this.handlingCharges = handlingCharges;
        this.lastBillDate = lastBillDate;
        this.nextBillDate = nextBillDate;
        this.storageType = storageType;
        this.clientInfo = clientInfo;
        this.warehouseInfo = warehouseInfo;
        this.isDeleted = isDeleted();
    }

    public long getClientStorageId() {
        return clientStorageId;
    }

    public void setClientStorageId(long clientStorageId) {
        this.clientStorageId = clientStorageId;
    }

    public Date getStorageStartDate() {
        return storageStartDate;
    }

    public void setStorageStartDate(Date storageStartDate) {
        this.storageStartDate = storageStartDate;
    }

    public Date getStorageEndDate() {
        return storageEndDate;
    }

    public void setStorageEndDate(Date storageEndDate) {
        this.storageEndDate = storageEndDate;
    }

    public float getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(float monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public Date getLastBillDate() {
        return lastBillDate;
    }

    public void setLastBillDate(Date lastBillDate) {
        this.lastBillDate = lastBillDate;
    }

    public Date getNextBillDate() {
        return nextBillDate;
    }

    public void setNextBillDate(Date nextBillDate) {
        this.nextBillDate = nextBillDate;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public WarehouseInfo getWarehouseInfo() {
        return warehouseInfo;
    }

    public void setWarehouseInfo(WarehouseInfo warehouseInfo) {
        this.warehouseInfo = warehouseInfo;
    }

    public float getHandlingCharges() {
        return handlingCharges;
    }

    public void setHandlingCharges(float handlingCharges) {
        this.handlingCharges = handlingCharges;
    }

	public boolean isIsfirstBilling() {
		return isfirstBilling;
	}

	public void setIsfirstBilling(boolean isfirstBilling) {
		this.isfirstBilling = isfirstBilling;
	}

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

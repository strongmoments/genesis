package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Table
@Entity(name="wsoInfo")
@Where(clause = "is_deleted='false'")
public class WSOInfo {

    @Id
    @GeneratedValue
    @Column(name = "wso_id", length = 20)
    private long wsoId;

    @Column(name = "wso_no", length = 500)
    private String wsoNo;

    @Column(name = "total_wso_weight")
    private float totalWsoWeight;
    
    @Transient
    private String transWsoWt;

    @Column(name = "cargo", length = 500)
    private String cargo;

    @Column(name = "total_no_of_pallets")
    private int totalNoOfPallets;

    @Column(name = "gst")
    private float gst;

    @Column(name = "invoice_rate")
    private float invoiceRate;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "remarks", length = 500)
    private String remarks;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "bill_start_date")
    @Temporal(TemporalType.DATE)
    private Date billStartDate;

    @Column(name = "bill_end_date")
    @Temporal(TemporalType.DATE)
    private Date billEndDate;

    
    @Column(name = "first_bill_date")
    @Temporal(TemporalType.DATE)
    private Date firstBillDate;

    @Column(name = "next_bill_date")
    @Temporal(TemporalType.DATE)
    private Date nextBillDate;
    
    @Column(name = "first_billing")
    private boolean isfirstBilling;

    @OneToMany(mappedBy = "wsoInfo")
    @JsonIgnore
    private Set<LotInfo> lotInfo;

    @OneToMany(mappedBy = "wsoInfo")
    @JsonIgnore
    private Set<DeliveryList> deliveryLists;

    @ManyToOne
    @JsonBackReference
    private TallySheet tallysheet;

    @OneToMany(mappedBy = "wsoInfo")
    @JsonIgnore
    private Set<Billing> billings;
    
    @ManyToOne
    private StorageType storageClass;

    public WSOInfo() {
    }

    public WSOInfo(String wsoNo, float totalWsoWeight, Date firstBillDate, Date billStartDate, String cargo, String transWsoWt, int totalNoOfPallets,float invoiceRate, String description, String remarks, boolean isDeleted, Date nextBillDate, TallySheet tallysheet, Set<LotInfo> lotInfo, Set<DeliveryList> deliveryLists, Set<Billing> billings,StorageType storageClass) {
        this.wsoNo = wsoNo;
        this.totalWsoWeight = totalWsoWeight;
        this.cargo = cargo;
        this.totalNoOfPallets = totalNoOfPallets;
        this.billStartDate = billStartDate;
        this.firstBillDate = firstBillDate;
        this.invoiceRate = invoiceRate;
        this.description = description;
        this.remarks = remarks;
        this.isDeleted = isDeleted;
        this.nextBillDate = nextBillDate;
        this.tallysheet = tallysheet;
        this.lotInfo = lotInfo;
        this.deliveryLists = deliveryLists;
        this.billings = billings;
        this.storageClass = storageClass;
        this.transWsoWt = transWsoWt;
        
    }

    public long getWsoId() {
        return wsoId;
    }

    public void setWsoId(long wsoId) {
        this.wsoId = wsoId;
    }

    public String getWsoNo() {
        return wsoNo;
    }

    public void setWsoNo(String wsoNo) {
        this.wsoNo = wsoNo;
    }

    public float getTotalWsoWeight() {
        return totalWsoWeight;
    }

    public void setTotalWsoWeight(float totalWsoWeight) {
        this.totalWsoWeight = totalWsoWeight;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getTotalNoOfPallets() {
        return totalNoOfPallets;
    }

    public void setTotalNoOfPallets(int totalNoOfPallets) {
        this.totalNoOfPallets = totalNoOfPallets;
    }

    public float getGst() {
        return gst;
    }

    public void setGst(float gst) {
        this.gst = gst;
    }

    public float getInvoiceRate() {
        return invoiceRate;
    }

    public void setInvoiceRate(float invoiceRate) {
        this.invoiceRate = invoiceRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public TallySheet getTallysheet() {
        return tallysheet;
    }

    public void setTallysheet(TallySheet tallysheet) {
        this.tallysheet = tallysheet;
    }

    public Set<LotInfo> getLotInfo() {
        return lotInfo;
    }

    public void setLotInfo(Set<LotInfo> lotInfo) {
        this.lotInfo = lotInfo;
    }

    public Set<DeliveryList> getDeliveryLists() {
        return deliveryLists;
    }

    public void setDeliveryLists(Set<DeliveryList> deliveryLists) {
        this.deliveryLists = deliveryLists;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<Billing> getBillings() {
        return billings;
    }

    public void setBillings(Set<Billing> billings) {
        this.billings = billings;
    }

    public Date getBillStartDate() {
        return billStartDate;
    }

    public void setBillStartDate(Date billStartDate) {
        this.billStartDate = billStartDate;
    }

    public Date getFirstBillDate() {
        return firstBillDate;
    }

    public void setFirstBillDate(Date firstBillDate) {
        this.firstBillDate = firstBillDate;
    }

    public Date getNextBillDate() {
        return nextBillDate;
    }

    public void setNextBillDate(Date nextBillDate) {
        this.nextBillDate = nextBillDate;
    }

	public Date getBillEndDate() {
		return billEndDate;
	}

	public void setBillEndDate(Date billEndDate) {
		this.billEndDate = billEndDate;
	}

	public boolean isIsfirstBilling() {
		return isfirstBilling;
	}

	public void setIsfirstBilling(boolean isfirstBilling) {
		this.isfirstBilling = isfirstBilling;
	}

	public StorageType getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(StorageType storageClass) {
		this.storageClass = storageClass;
	}

	public String getTransWsoWt() {
		return transWsoWt;
	}

	public void setTransWsoWt(String transWsoWt) {
		this.transWsoWt = transWsoWt;
	}
	
}


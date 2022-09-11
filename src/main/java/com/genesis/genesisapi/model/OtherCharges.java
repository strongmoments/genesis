package com.genesis.genesisapi.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "other_charges")
@Table
@Where(clause = "is_deleted='false'")
public class OtherCharges {

    @Id
    @GeneratedValue
    @Column(name = "other_charges_id", length = 20)
    private long otherChargesId;

    @Column(name = "verify_ind", length = 500)
    private String verifyInd;

    @Column(name = "warehouse_name", length = 500)
    private String warehouseName;

    @Column(name = "form_no", length = 500)
    private String formNo;

    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Column(name = "from_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    @Temporal(TemporalType.TIME)
    private Date fromTime;

    @Column(name = "to_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    @Temporal(TemporalType.TIME)
    private Date ToTime;

    @Column(name = "bilable_unit")
    private float bilableUnit;

    @Column(name = "bilable_rate")
    private float bilableRate;

    @Column(name = "bilable_amount")
    private double bilableAmount;
    
    @Column(name = "bill_genereted")
    private boolean billGenerated;

    @Column(name = "naration", length = 100)
    private String naration;

    @Column(name = "total_amount")
    private double totalAmount;

    /*@Column(name = "gst")
    private float gst;*/

    @Column(name = "grand_total_amount")
    private double grandTotalAmount;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    private ChargeItems chargeItems;

    @ManyToOne
    private AccountInfo accountInfo;

    @ManyToOne
    private ClientInfo clientInfo;

    public OtherCharges() {
    }

    public OtherCharges(String verifyInd, String warehouseName, String formNo, Date fromDate, Date toDate, Date fromTime, Date toTime, float bilableUnit, float bilableRate, double bilableAmount, String naration, double totalAmount, /*float gst,*/boolean isDeleted, double grandTotalAmount, ChargeItems chargeItems, AccountInfo accountInfo, ClientInfo clientInfo) {
        this.verifyInd = verifyInd;
        this.warehouseName = warehouseName;
        this.formNo = formNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        ToTime = toTime;
        this.bilableUnit = bilableUnit;
        this.bilableRate = bilableRate;
        this.bilableAmount = bilableAmount;
        this.naration = naration;
        this.totalAmount = totalAmount;
        this.isDeleted = isDeleted;
        /*this.gst = gst;*/
        this.grandTotalAmount = grandTotalAmount;
        this.chargeItems = chargeItems;
        this.accountInfo = accountInfo;
        this.clientInfo = clientInfo;
    }

    public long getOtherChargesId() {
        return otherChargesId;
    }

    public void setOtherChargesId(long otherChargesId) {
        this.otherChargesId = otherChargesId;
    }

    public String getVerifyInd() {
        return verifyInd;
    }

    public void setVerifyInd(String verifyInd) {
        this.verifyInd = verifyInd;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return ToTime;
    }

    public void setToTime(Date toTime) {
        ToTime = toTime;
    }

    public float getBilableUnit() {
        return bilableUnit;
    }

    public void setBilableUnit(float bilableUnit) {
        this.bilableUnit = bilableUnit;
    }

    public float getBilableRate() {
        return bilableRate;
    }

    public void setBilableRate(float bilableRate) {
        this.bilableRate = bilableRate;
    }

    public double getBilableAmount() {
        return bilableAmount;
    }

    public void setBilableAmount(double bilableAmount) {
        this.bilableAmount = bilableAmount;
    }

    public String getNaration() {
        return naration;
    }

    public void setNaration(String naration) {
        this.naration = naration;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /*public float getGst() {
        return gst;
    }

    public void setGst(float gst) {
        this.gst = gst;
    }*/

    public double getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public void setGrandTotalAmount(double grandTotalAmount) {
        this.grandTotalAmount = grandTotalAmount;
    }

    public ChargeItems getChargeItems() {
        return chargeItems;
    }

    public void setChargeItems(ChargeItems chargeItems) {
        this.chargeItems = chargeItems;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

	public boolean isBillGenerated() {
		return billGenerated;
	}

	public void setBillGenerated(boolean billGenerated) {
		this.billGenerated = billGenerated;
	}

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

package com.genesis.genesisapi.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Set;

@Entity(name = "billing")
@Table
public class Billing {

    @Id
    @GeneratedValue
    @Column(name = "billing_id", length = 20)
    private Long billingId;

    @Column(name = "invoice_no", length = 500)
    private String invoiceNo;

    @Column(name = "from_date")
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Column(name = "billing_quantity")
    private float billingQuantity;

    @Column(name = "billing_rate")
    private float billingRate;

    @Column(name = "billing_weight")
    private float billingWeight;

    @Column(name = "gst")
    private float gst;
    
    @Column(name = "handling_charge")
    private float handlingCharge;
    
    @Column(name = "net_amount")
    private double netAmount;
    
    @Column(name = "bill_paid")
    private boolean billPaid = false;

    @Column(name = "invoice_date")
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

    @ManyToOne
    private TallySheet tallysheet;

    @ManyToOne
    private WSOInfo wsoInfo;
    
    @Override
	public String toString() {
		return "Billing [billingId=" + billingId + ", invoiceNo=" + invoiceNo + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", billingQuantity=" + billingQuantity + ", billingRate=" + billingRate + ", billingWeight="
				+ billingWeight + ", gst=" + gst + ", handlingCharge=" + handlingCharge + ", netAmount=" + netAmount
				+ ", billPaid=" + billPaid + ", invoiceDate=" + invoiceDate + ", tallysheet=" + tallysheet
				+ ", wsoInfo=" + wsoInfo + ", clientInfo=" + clientInfo + ", storageClass=" + storageClass
				+ ", ChargeItems=" + ChargeItems + ", formNo=" + formNo + ", naration=" + naration + "]";
	}

	@ManyToOne
    private ClientInfo clientInfo;
    
    @ManyToOne
    private StorageType storageClass;
    
    @ManyToOne
    private ChargeItems ChargeItems;

    @Column(name = "form_no", length = 100)
    private String formNo;
    
    @Column(name = "naration", length = 500)
    private String naration;
   

	public Billing() {
    }

    public Billing(String invoiceNo, Date fromDate, Date toDate, float billingQuantity, float billingRate, boolean billPaid,float billingWeight, float gst, double netAmount, Date invoiceDate, TallySheet tallysheet, WSOInfo wsoInfo, ClientInfo clientInfo ) {
        this.invoiceNo = invoiceNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.billingQuantity = billingQuantity;
        this.billingRate = billingRate;
        this.billingWeight = billingWeight;
        this.gst = gst;
        this.netAmount = netAmount;
        this.invoiceDate = invoiceDate;
        this.tallysheet = tallysheet;
        this.wsoInfo = wsoInfo;
        this.clientInfo = clientInfo;
        this.billPaid = billPaid;
    }

    public Long getBillingId() {
        return billingId;
    }

    public void setBillingId(Long billingId) {
        this.billingId = billingId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public float getBillingQuantity() {
        return billingQuantity;
    }
    
    public String getNaration() {
		return naration;
	}

	public void setNaration(String naration) {
		this.naration = naration;
	}

    public void setBillingQuantity(float billingQuantity) {
        this.billingQuantity = billingQuantity;
    }

    public float getBillingRate() {
        return billingRate;
    }

    public void setBillingRate(float billingRate) {
        this.billingRate = billingRate;
    }

    public float getBillingWeight() {
        return billingWeight;
    }

    public void setBillingWeight(float billingWeight) {
        this.billingWeight = billingWeight;
    }

    public float getGst() {
        return gst;
    }

    public void setGst(float gst) {
        this.gst = gst;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public TallySheet getTallysheet() {
        return tallysheet;
    }

    public void setTallysheet(TallySheet tallysheet) {
        this.tallysheet = tallysheet;
    }

    public WSOInfo getWsoInfo() {
        return wsoInfo;
    }

    public void setWsoInfo(WSOInfo wsoInfo) {
        this.wsoInfo = wsoInfo;
    }

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}

	public float getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(float handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	public StorageType getStorageClass() {
		return storageClass;
	}

	public void setStorageClass(StorageType storageClass) {
		this.storageClass = storageClass;
	}

	public ChargeItems getChargeItems() {
		return ChargeItems;
	}

	public void setChargeItems(ChargeItems chargeItems) {
		ChargeItems = chargeItems;
	}
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	public boolean isBillPaid() {
		return billPaid;
	}

	public void setBillPaid(boolean billPaid) {
		this.billPaid = billPaid;
	}
	
	
    
}

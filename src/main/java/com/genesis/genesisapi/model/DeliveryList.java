package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table
@Entity(name = "deliveryList")
public class DeliveryList {

    @Id
    @GeneratedValue
    @Column(name = "outgoing_inventory_id", length = 20)
    private long outgoingInventoryId;

    @Column(name = "date_of_delivery")
    @Temporal(TemporalType.DATE)
    private Date dateOfDelivery;

    @Column(name = "dl_no", length = 500)
    private String dlNo;

    @Column(name = "time_of_issue")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    @Temporal(TemporalType.TIME)
    private Date timeOfIssue;

    @Column(name = "name_of_receiver", length = 500)
    private String nameOfReceiver;

    @Column(name = "vehicle_no", length = 500)
    private String vehicleNo;

    @Column(name = "nric_of_receiver", length = 500)
    private String nricOfReceiver;

    @Column(name = "verify")
    private boolean verify;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @Column(name = "total_qty")
    private Integer totalQty;
    @ManyToOne
    private Account account;

    @ManyToOne
    private WSOInfo wsoInfo;

    @ManyToOne
    private ClientInfo clientInfo;

    @ManyToOne
    private LotInfo lotInfo;
    
    
    
    @Override
	public String toString() {
		return "DeliveryList [outgoingInventoryId=" + outgoingInventoryId + ", dateOfDelivery=" + dateOfDelivery
				+ ", dlNo=" + dlNo + ", timeOfIssue=" + timeOfIssue + ", nameOfReceiver=" + nameOfReceiver
				+ ", vehicleNo=" + vehicleNo + ", nricOfReceiver=" + nricOfReceiver + ", verify=" + verify
				+ ", isDeleted=" + isDeleted + ", totalQty=" + totalQty + ", account=" + account + ", wsoInfo="
				+ wsoInfo + ", clientInfo=" + clientInfo + ", lotInfo=" + lotInfo + "]";
	}

	public DeliveryList(){
    }

    public DeliveryList(Date dateOfDelivery, String dlNo, Date timeOfIssue, String nameOfReceiver, String vehicleNo, String nricOfReceiver, boolean verify, boolean isDeleted, Account account, WSOInfo wsoInfo, ClientInfo clientInfo) {
        this.dateOfDelivery = dateOfDelivery;
        this.dlNo = dlNo;
        this.timeOfIssue = timeOfIssue;
        this.nameOfReceiver = nameOfReceiver;
        this.vehicleNo = vehicleNo;
        this.nricOfReceiver = nricOfReceiver;
        this.verify = verify;
        this.isDeleted = isDeleted;
        this.account = account;
        this.wsoInfo = wsoInfo;
        this.clientInfo = clientInfo;
    }

    public long getOutgoingInventoryId() {
        return outgoingInventoryId;
    }

    public void setOutgoingInventoryId(long outgoingInventoryId) {
        this.outgoingInventoryId = outgoingInventoryId;
    }

    public Date getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(Date dateOfDelinery) {
        this.dateOfDelivery = dateOfDelinery;
    }

    public String getDlNo() {
        return dlNo;
    }

    public void setDlNo(String dlNo) {
        this.dlNo = dlNo;
    }

    public Date getTimeOfIssue() {
        return timeOfIssue;
    }

    public void setTimeOfIssue(Date timeOfIssue) {
        this.timeOfIssue = timeOfIssue;
    }

    public String getNameOfReceiver() {
        return nameOfReceiver;
    }

    public void setNameOfReceiver(String nameOfReceiver) {
        this.nameOfReceiver = nameOfReceiver;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getNricOfReceiver() {
        return nricOfReceiver;
    }

    public void setNricOfReceiver(String nricOfReceiver) {
        this.nricOfReceiver = nricOfReceiver;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

	public LotInfo getLotInfo() {
		return lotInfo;
	}

	public void setLotInfo(LotInfo lotInfo) {
		this.lotInfo = lotInfo;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}
	
	
    
}

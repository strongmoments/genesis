package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Table
@Entity(name = "tallysheet")
@Where(clause = "is_deleted='false'")
public class TallySheet {

    @Id
    @GeneratedValue
    @Column(name = "tallysheet_id", length = 20)
    private Long tallysheetId;

    @Column(name = "tally_sheet_number", length = 500)
    private String tallysheetNumber;

    @Column(name = "ref_dry")
    @Enumerated(EnumType.STRING)
    private DryRef refDry;

    @Column(name = "storage_date")
    @Temporal(TemporalType.DATE)
    private Date storageDate;

    @Column(name = "lorry_container")
    @Enumerated(EnumType.STRING)
    private LorryContainer lorryContainer;

    @Column(name = "containerNo")
    private String containerNo;

    @Column(name = "ex_vessel", length = 500)
    private String exVessel;

    @Column(name = "temp_recorded")
    private float tempRecorded;

    @Column(name = "temp_unstudd_unload")
    private float tempUnstuddUnload;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    /*@Column(name = "no_of_overtime_men")
    private int noOfOvertimeMen;

    @Column(name = "overtime_start_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm", timezone = "")
    @Temporal(TemporalType.TIME)
    private Date overtimeStartTime;

    @Column(name = "overtime_end_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    @Temporal(TemporalType.TIME)
    private Date overtimeEndTime;

    @Column(name = "overtime_hours")
    private int overtimeHours;

    @Column(name = "overtime_minutes")
    private int overtimeMinutes;

    @Column(name = "fork_lift_no")
    private int forkLiftNo;

    @Column(name = "fork_lift_start_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    @Temporal(TemporalType.TIME)
    private Date forkLiftStartTime;

    @Column(name = "fork_lift_end_time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm")
    @Temporal(TemporalType.TIME)
    private Date forkLiftEndTime;

    @Column(name = "fork_lift_hours")
    private int forkLiftHours;

    @Column(name = "fork_lift_minutes")
    private int forkLiftMinutes;

    @Column(name = "transport")
    private int transport;

    *//*@Column(name = "gst")
    private float gst;*//*

    @Column(name = "holiday_sunday_indicator")
    private boolean holidaySundayIndicator;

    @Column(name = "manpower_charge")
    private float manpowerCharge;
*/
    @Column(name = "grnd_ttl_qty")
    private float grndTtlQty;

    @Column(name = "ops_name")
    private String opsName;
    
    /*@Column(name = "other_type")
    private String otherType;
*/
	@Column(name = "grnd_tot")
    private float grndTot;

    @Column(name = "verify")
    private boolean verify;

   // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "tallysheet")
    
    @JsonIgnore
    @OneToMany(mappedBy = "tallysheet")
    private Set<Billing> billings;

    @ManyToOne
    //@JsonIgnore
    private ClientInfo clientInfo;

    @OneToMany(mappedBy = "tallysheet")
    @JsonManagedReference
    private List<WSOInfo> wsoInfoList;

    public TallySheet() {
    }

    public TallySheet(Long tallysheetId, String tallysheetNumber, /*String warehouseName,*/ DryRef refDry, Date storageDate, LorryContainer lorryContainer, String measurement, String exVessel, float tempRecorded, float tempUnstuddUnload, boolean isDeleted, /*int noOfOvertimeMen, Date overtimeStartTime, Date overtimeEndTime, int overtimeHours, int overtimeMinutes, int forkLiftNo, Date forkLiftStartTime, Date forkLiftEndTime, int forkLiftHours, int forkLiftMinutes, int transport, *//*float gst,*//* boolean holidaySundayIndicator, int manpowerCharge, */float grndTtlQty, String opsName,/*String otherType ,*/float grndTot, boolean verify, Set<Billing> billings, ClientInfo clientInfo, List<WSOInfo> wsoInfoList, String containerNo) {
        this.tallysheetId = tallysheetId;
        this.tallysheetNumber = tallysheetNumber;
        /*this.warehouseName = warehouseName;*/
        this.refDry = refDry;
        this.storageDate = storageDate;
        this.lorryContainer = lorryContainer;
        this.containerNo = containerNo;
        this.exVessel = exVessel;
        this.tempRecorded = tempRecorded;
        this.tempUnstuddUnload = tempUnstuddUnload;
        this.isDeleted = isDeleted;
        /*this.noOfOvertimeMen = noOfOvertimeMen;
        this.overtimeStartTime = overtimeStartTime;
        this.overtimeEndTime = overtimeEndTime;
        this.overtimeHours = overtimeHours;
        this.overtimeMinutes = overtimeMinutes;
        this.forkLiftNo = forkLiftNo;
        this.forkLiftStartTime = forkLiftStartTime;
        this.forkLiftEndTime = forkLiftEndTime;
        this.forkLiftHours = forkLiftHours;
        this.forkLiftMinutes = forkLiftMinutes;
        this.transport = transport;
        *//*this.gst = gst;*//*
        this.holidaySundayIndicator = holidaySundayIndicator;
        this.manpowerCharge = manpowerCharge;
        */this.grndTtlQty = grndTtlQty;
        this.opsName = opsName;
        /*this.otherType = otherType;
        */this.grndTot = grndTot;
        this.verify = verify;
        this.billings = billings;
        this.clientInfo = clientInfo;
        this.wsoInfoList = wsoInfoList;
    }

    public Long getTallysheetId() {
        return tallysheetId;
    }

    public void setTallysheetId(Long tallysheetId) {
        this.tallysheetId = tallysheetId;
    }

    public String getTallysheetNumber() {
        return tallysheetNumber;
    }

    public void setTallysheetNumber(String tallysheetNumber) {
        this.tallysheetNumber = tallysheetNumber;
    }

    /*public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }*/

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    public DryRef getRefDry() {
        return refDry;
    }

    public void setRefDry(DryRef refDry) {
        this.refDry = refDry;
    }

    public LorryContainer getLorryContainer() {
        return lorryContainer;
    }

    public void setLorryContainer(LorryContainer lorryContainer) {
        this.lorryContainer = lorryContainer;
    }

    

    public String getExVessel() {
        return exVessel;
    }

    public void setExVessel(String exVessel) {
        this.exVessel = exVessel;
    }

    public float getTempRecorded() {
        return tempRecorded;
    }

    public void setTempRecorded(float tempRecorded) {
        this.tempRecorded = tempRecorded;
    }

    public float getTempUnstuddUnload() {
        return tempUnstuddUnload;
    }

    public void setTempUnstuddUnload(float tempUnstuddUnload) {
        this.tempUnstuddUnload = tempUnstuddUnload;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    /*public int getNoOfOvertimeMen() {
        return noOfOvertimeMen;
    }

    public void setNoOfOvertimeMen(int noOfOvertimeMen) {
        this.noOfOvertimeMen = noOfOvertimeMen;
    }

    public Date getOvertimeStartTime() {
        return overtimeStartTime;
    }

    public void setOvertimeStartTime(Date overtimeStartTime) {
        this.overtimeStartTime = overtimeStartTime;
    }

    public Date getOvertimeEndTime() {
        return overtimeEndTime;
    }

    public void setOvertimeEndTime(Date overtimeEndTime) {
        this.overtimeEndTime = overtimeEndTime;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public int getOvertimeMinutes() {
        return overtimeMinutes;
    }

    public void setOvertimeMinutes(int overtimeMinutes) {
        this.overtimeMinutes = overtimeMinutes;
    }

    public int getForkLiftNo() {
        return forkLiftNo;
    }

    public void setForkLiftNo(int forkLiftNo) {
        this.forkLiftNo = forkLiftNo;
    }

    public Date getForkLiftStartTime() {
        return forkLiftStartTime;
    }

    public void setForkLiftStartTime(Date forkLiftStartTime) {
        this.forkLiftStartTime = forkLiftStartTime;
    }

    public Date getForkLiftEndTime() {
        return forkLiftEndTime;
    }

    public void setForkLiftEndTime(Date forkLiftEndTime) {
        this.forkLiftEndTime = forkLiftEndTime;
    }

    public int getForkLiftHours() {
        return forkLiftHours;
    }

    public void setForkLiftHours(int forkLiftHours) {
        this.forkLiftHours = forkLiftHours;
    }

    public int getForkLiftMinutes() {
        return forkLiftMinutes;
    }

    public void setForkLiftMinutes(int forkLiftMinutes) {
        this.forkLiftMinutes = forkLiftMinutes;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public boolean isHolidaySundayIndicator() {
        return holidaySundayIndicator;
    }

    public void setHolidaySundayIndicator(boolean holidaySundayIndicator) {
        this.holidaySundayIndicator = holidaySundayIndicator;
    }

    public float getManpowerCharge() {
        return manpowerCharge;
    }

    public void setManpowerCharge(float manpowerCharge) {
        this.manpowerCharge = manpowerCharge;
    }
*/
    public float getGrndTtlQty() {
        return grndTtlQty;
    }

    public void setGrndTtlQty(float grndTtlQty) {
        this.grndTtlQty = grndTtlQty;
    }

    public String getOpsName() {
        return opsName;
    }

    public void setOpsName(String opsName) {
        this.opsName = opsName;
    }

    public float getGrndTot() {
        return grndTot;
    }
    
   /* public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
*/
    public void setGrndTot(float grndTot) {
        this.grndTot = grndTot;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public Set<Billing> getBillings() {
        return billings;
    }

    public void setBillings(Set<Billing> billings) {
        this.billings = billings;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    /*public float getGst() {
        return gst;
    }

    public void setGst(float gst) {
        this.gst = gst;
    }*/

    public List<WSOInfo> getWsoInfoList() {
        return wsoInfoList;
    }

    public void setWsoInfoList(List<WSOInfo> wsoInfoList) {
        this.wsoInfoList = wsoInfoList;
    }

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
    
    
}

package com.genesis.genesisapi.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity(name = "lotInfo")
@Where(clause = "is_deleted='false'")
public class LotInfo {

    @Id
    @GeneratedValue
    @Column(name = "lot_id", length = 20)
    private long lotId;

    @Column(name = "lot_no", length = 500)
    private String lotNo;

    @Column(name = "description")
    private String description;

    @Column(name = "expiry_date")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Column(name = "production_date")
    @Temporal(TemporalType.DATE)
    private Date productionDate;

    @Column(name = "lot_quantity")
    private int lotQuantity;

    @Column(name = "unit_quantity")
    private int unitQuantity;

    @Column(name = "room_no", length = 500)
    private String roomNo;

    @Column(name = "unit_net_weight_in_kg")
    private float unitNetWeightInKg;

    @Column(name = "unit_weight_in_kg")
    private float unitWeightInKg;

    @Column(name = "unit_gross_weight_in_kg")
    private float unitGrossWeightInKg;
    
    @Column(name = "lot_initial_qty")
    private Integer initialQuantity;

    @Column(name = "lot_current_qty")
    private Integer currentQuantity;

    
    @ManyToOne
    private WSOInfo wsoInfo;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public LotInfo() {
    }

    public LotInfo(String lotNo, String description, Date expiryDate, Date productionDate, int lotQuantity, int unitQuantity, String roomNo, float unitNetWeightInKg, float unitWeightInKg, float unitGrossWeightInKg, WSOInfo wsoInfo, boolean isDeleted) {
        this.lotNo = lotNo;
        this.description = description;
        this.expiryDate = expiryDate;
        this.productionDate = productionDate;
        this.lotQuantity = lotQuantity;
        this.unitQuantity = unitQuantity;
        this.roomNo = roomNo;
        this.unitNetWeightInKg = unitNetWeightInKg;
        this.unitWeightInKg = unitWeightInKg;
        this.unitGrossWeightInKg = unitGrossWeightInKg;
        this.wsoInfo = wsoInfo;
        this.isDeleted = isDeleted;
    }

    @Override
	public String toString() {
		return "LotInfo [lotId=" + lotId + ", lotNo=" + lotNo + ", description=" + description + ", expiryDate="
				+ expiryDate + ", productionDate=" + productionDate + ", lotQuantity=" + lotQuantity + ", unitQuantity="
				+ unitQuantity + ", roomNo=" + roomNo + ", unitNetWeightInKg=" + unitNetWeightInKg + ", unitWeightInKg="
				+ unitWeightInKg + ", unitGrossWeightInKg=" + unitGrossWeightInKg + ", initialQuantity="
				+ initialQuantity + ", currentQuantity=" + currentQuantity + ", wsoInfo=" + wsoInfo + ", isDeleted="
				+ isDeleted + "]";
	}

	public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getPackages() {
        return description;
    }

    public void setPackages(String description) {
        this.description = description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getLotQuantity() {
        return lotQuantity;
    }

    public void setLotQuantity(int lotQuantity) {
        this.lotQuantity = lotQuantity;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public float getUnitNetWeightInKg() {
        return unitNetWeightInKg;
    }

    public void setUnitNetWeightInKg(float unitNetWeightInKg) {
        this.unitNetWeightInKg = unitNetWeightInKg;
    }

    public float getUnitWeightInKg() {
        return unitWeightInKg;
    }

    public void setUnitWeightInKg(float unitWeightInKg) {
        this.unitWeightInKg = unitWeightInKg;
    }

    public float getUnitGrossWeightInKg() {
        return unitGrossWeightInKg;
    }

    public void setUnitGrossWeightInKg(float unitGrossWeightInKg) {
        this.unitGrossWeightInKg = unitGrossWeightInKg;
    }

    public WSOInfo getWsoInfo() {
        return wsoInfo;
    }

    public void setWsoInfo(WSOInfo wsoInfo) {
        this.wsoInfo = wsoInfo;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public  Integer getInitialQuantity() {
		return initialQuantity;
	}

    public  void setInitialQuantity(Integer initialQuantity) {
		this.initialQuantity = initialQuantity;
	}

	public Integer getCurrentQuantity() {
		return currentQuantity;
	}

	public  void setCurrentQuantity(Integer currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(int unitQuantity) {
        this.unitQuantity = unitQuantity;
    }
}


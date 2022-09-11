package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name="warehouse_info")
public class WarehouseInfo {

    @Id
    @GeneratedValue
    @Column(name = "warehouse_info_id", length = 20)
    private long warehouseInfoId;

    @Column(name = "warehouse_name", length = 500)
    private String warehouseName;

    @Column(name = "applicable_gst")
    private float applicableGst;

    @OneToMany(mappedBy = "warehouseInfo")
    @JsonIgnore
    private Set<ClientStorageInfo> clientStorageInfos;

    public WarehouseInfo() {
    }

    public WarehouseInfo(long warehouseInfoId, String warehouseName, float applicableGst, Set<ClientStorageInfo> clientStorageInfos) {
        this.warehouseInfoId = warehouseInfoId;
        this.warehouseName = warehouseName;
        this.applicableGst = applicableGst;
        this.clientStorageInfos = clientStorageInfos;
    }

    public long getWarehouseInfoId() {
        return warehouseInfoId;
    }

    public void setWarehouseInfoId(long warehouseInfoId) {
        this.warehouseInfoId = warehouseInfoId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public float getApplicableGst() {
        return applicableGst;
    }

    public void setApplicableGst(float applicableGst) {
        this.applicableGst = applicableGst;
    }

    public Set<ClientStorageInfo> getClientStorageInfos() {
        return clientStorageInfos;
    }

    public void setClientStorageInfos(Set<ClientStorageInfo> clientStorageInfos) {
        this.clientStorageInfos = clientStorageInfos;
    }
}

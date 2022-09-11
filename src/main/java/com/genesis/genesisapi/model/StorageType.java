package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name="Storage_type")
public class StorageType {

    @Id
    @GeneratedValue
    @Column(name = "storage_type_id", length = 20)
    private long storageTypeId;

    @Column(name = "storage_type_name", length = 500)
    private String storageTypeName;

    @OneToMany(mappedBy = "storageType")
    @JsonIgnore
    private Set<ClientStorageInfo> clientStorageInfos;

    public StorageType() {
    }

    public StorageType(long storageTypeId, String storageTypeName, Set<ClientStorageInfo> clientStorageInfos) {
        this.storageTypeId = storageTypeId;
        this.storageTypeName = storageTypeName;
        this.clientStorageInfos = clientStorageInfos;
    }

    public long getStorageTypeId() {
        return storageTypeId;
    }

    public void setStorageTypeId(long storageTypeId) {
        this.storageTypeId = storageTypeId;
    }

    public String getStorageTypeName() {
        return storageTypeName;
    }

    public void setStorageTypeName(String storageTypeName) {
        this.storageTypeName = storageTypeName;
    }

    public Set<ClientStorageInfo> getClientStorageInfos() {
        return clientStorageInfos;
    }

    public void setClientStorageInfos(Set<ClientStorageInfo> clientStorageInfos) {
        this.clientStorageInfos = clientStorageInfos;
    }
}

package com.genesis.genesisapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "role")
@Table
public class Role {

    @Id @GeneratedValue
    @Column(name = "role_id", length = 20)
    private long roleId;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "label", length = 100)
    private String label;

    @Column(name = "ordinal", length = 11)
    private int ordinal;

    @Column(name = "effective_start_date")
    @Temporal(TemporalType.DATE)
    private Date effectiveStartDate;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    public Role() {
    }

    public Role(String code, String label, int ordinal, Date effectiveStartDate, Date expirationDate, Date creationDate, boolean isDeleted) {
        this.code = code;
        this.label = label;
        this.ordinal = ordinal;
        this.effectiveStartDate = effectiveStartDate;
        this.expirationDate = expirationDate;
        this.creationDate = creationDate;
        this.isDeleted = isDeleted;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}

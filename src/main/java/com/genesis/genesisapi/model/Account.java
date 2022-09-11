package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Entity(name = "account")
@Table
@Where(clause = "is_deleted='false'")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "account_id", length = 20)
    private Long accountId;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_credentials_expired")
    private Boolean isCredentialsExpired;

    @Column(name = "is_account_locked")
    private Boolean isAccountLocked;

    @Column(name = "createdBy", length = 100)
    private String createdBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "creationDate")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "updatedBy", length = 100)
    private String updatedBy;

    @Column(name = "updation_date")
    @Temporal(TemporalType.DATE)
    private Date updationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "AccountRole",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "account")
    @JsonIgnore
    private Set<DeliveryList> deliveryLists;

    public Account() {
    }

    public Account(String username, String password, boolean isEnabled, boolean isCredentialsExpired, boolean isAccountLocked, String createdBy, boolean isDeleted, Date creationDate, String updatedBy, Date updationDate, Set<Role> roles, Set<DeliveryList> deliveryLists) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isCredentialsExpired = isCredentialsExpired;
        this.isAccountLocked = isAccountLocked;
        this.createdBy = createdBy;
        this.isDeleted = isDeleted;
        this.creationDate = creationDate;
        this.updatedBy = updatedBy;
        this.updationDate = updationDate;
        this.roles = roles;
        this.deliveryLists = deliveryLists;
    }

    public Account(Account account){
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.isEnabled = account.isEnabled();
        this.isCredentialsExpired = account.isCredentialsExpired();
        this.isAccountLocked = account.isAccountLocked();
        this.createdBy = account.getCreatedBy();
        this.isDeleted = account.isDeleted();
        this.creationDate = account.getCreationDate();
        this.updatedBy = account.getUpdatedBy();
        this.updationDate = account.getUpdationDate();
        this.roles = account.getRoles();
    }


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }

    public boolean isAccountLocked() {
        return isAccountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        isAccountLocked = accountLocked;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdationDate() {
        return updationDate;
    }

    public void setUpdationDate(Date updationDate) {
        this.updationDate = updationDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<DeliveryList> getDeliveryLists() {
        return deliveryLists;
    }

    public void setDeliveryLists(Set<DeliveryList> deliveryLists) {
        this.deliveryLists = deliveryLists;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", isCredentialsExpired=" + isCredentialsExpired +
                ", isAccountLocked=" + isAccountLocked +
                ", createdBy='" + createdBy + '\'' +
                ", isDeleted=" + isDeleted +
                ", creationDate=" + creationDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updationDate=" + updationDate +
                ", roles=" + roles +
                ", deliveryLists=" + deliveryLists +
                '}';
    }
}

package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "accountInfo")
@Where(clause = "is_deleted='false'")
public class AccountInfo {

    @Id @GeneratedValue
    @Column(name = "account_info_id", length = 20)
    private long accountInfoId;

    @Column(name = "firstName", length = 45)
    private String firstName;

    @Column(name = "middleName", length = 45)
    private String middleName;

    @Column(name = "lastName", length = 45)
    private String lastName;

    @Column(name = "linkToProfilePic", length = 145)
    private String linkToProfilePic;

    @Column(name = "emailId", length = 65)
    private String emailId;

    @Column(name = "last_successful_login", length = 45)
    private String lastSuccessfulLogin;

    @Column(name = "last_unsuccessful_login", length = 45)
    private String lastUnsuccessfulLogin;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "accountInfo")
    @JsonIgnore
    private List<OtherCharges> otherChargesList;

    @OneToOne
    private Account account;

    public AccountInfo() {
    }

    public AccountInfo(String firstName, String middleName, String lastName, String linkToProfilePic, String emailId, String lastSuccessfulLogin, String lastUnsuccessfulLogin, boolean isDeleted, List<OtherCharges> otherChargesList, Account account) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.linkToProfilePic = linkToProfilePic;
        this.emailId = emailId;
        this.lastSuccessfulLogin = lastSuccessfulLogin;
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
        this.isDeleted = isDeleted;
        this.otherChargesList = otherChargesList;
        this.account = account;
    }

    public long getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(long accountInfoId) {
        this.accountInfoId = accountInfoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLinkToProfilePic() {
        return linkToProfilePic;
    }

    public void setLinkToProfilePic(String linkToProfilePic) {
        this.linkToProfilePic = linkToProfilePic;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getLastSuccessfulLogin() {
        return lastSuccessfulLogin;
    }

    public void setLastSuccessfulLogin(String lastSuccessfulLogin) {
        this.lastSuccessfulLogin = lastSuccessfulLogin;
    }

    public String getLastUnsuccessfulLogin() {
        return lastUnsuccessfulLogin;
    }

    public void setLastUnsuccessfulLogin(String lastUnsuccessfulLogin) {
        this.lastUnsuccessfulLogin = lastUnsuccessfulLogin;
    }

    public List<OtherCharges> getOtherChargesList() {
        return otherChargesList;
    }

    public void setOtherChargesList(List<OtherCharges> otherChargesList) {
        this.otherChargesList = otherChargesList;
    }
}

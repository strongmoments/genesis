package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Table
@Entity(name = "clientInfo")
@Where(clause = "is_deleted='false'")
public class ClientInfo {

    @Id
    @GeneratedValue
    @Column(name = "client_info_id", length = 20)
    private Long clientInfoId;

    @Column(name = "client_title", length = 500)
    private String clientTitle;

    @Column(name = "is_client_active")
    private Boolean isClientActive;

    @Column(name = "client_address1", length = 1000)
    private String clientAddress1;

    @Column(name = "client_address2", length = 1000)
    private String clientAddress2;

    @Column(name = "client_city", length = 500)
    private String clientCity;

    @Column(name = "client_state", length = 500)
    private String clientState;

    @Column(name = "client_country", length = 500)
    private String clientCountry;

    @Column(name = "client_zip", length = 500)
    private String clientZip;

    @Column(name = "contact_person_first_name", length = 500)
    private String contactPersonFirstName;

    @Column(name = "contact_person_middle_name", length = 500)
    private String contactPersonMiddleName;

    @Column(name = "contact_person_last_name", length = 500)
    private String contactPersonLastName;

    @Column(name = "contact_person_phone_number", length = 500)
    private String contactPersonPhoneNumber;

    @Column(name = "contact_person_mobile_number", length = 500)
    private String contactPersonMobileNumbere;

    @Column(name = "is_contact_person_active")
    private Boolean isContactPersonActive;

    @Column(name = "contact_person_active_date")
    @Temporal(TemporalType.DATE)
    private Date contactPersonActiveDate;

    @Column(name = "contact_person_inactive_date")
    @Temporal(TemporalType.DATE)
    private Date contactPersonInactiveDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted=false;

    @OneToMany(mappedBy = "clientInfo")
    @JsonIgnore
    private Set<DeliveryList> deliveryLists;

    @OneToMany(mappedBy = "clientInfo")
    @JsonIgnore
    private Set<TallySheet> tallysheets;

    @OneToMany(mappedBy = "clientInfo")
    @JsonIgnore
    private List<OtherCharges> otherChargesList;

    @OneToMany(mappedBy = "clientInfo")
    @JsonIgnore
    private Set<ClientStorageInfo> clientStorageInfos;

    public ClientInfo() {
    }

    public ClientInfo(String clientTitle, Boolean isClientActive, String clientAddress1, String clientAddress2, String clientCity, String clientState, String clientCountry, String clientZip, String contactPersonFirstName, String contactPersonMiddleName, String contactPersonLastName, String contactPersonPhoneNumber, String contactPersonMobileNumbere, boolean isContactPersonActive, Date contactPersonActiveDate, Date contactPersonInactiveDate, boolean isDeleted, Set<DeliveryList> deliveryLists, Set<TallySheet> tallysheets, List<OtherCharges> otherChargesList, Set<ClientStorageInfo> clientStorageInfos) {
        this.clientTitle = clientTitle;
        this.isClientActive = isClientActive;
        this.clientAddress1 = clientAddress1;
        this.clientAddress2 = clientAddress2;
        this.clientCity = clientCity;
        this.clientState = clientState;
        this.clientCountry = clientCountry;
        this.clientZip = clientZip;
        this.contactPersonFirstName = contactPersonFirstName;
        this.contactPersonMiddleName = contactPersonMiddleName;
        this.contactPersonLastName = contactPersonLastName;
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
        this.contactPersonMobileNumbere = contactPersonMobileNumbere;
        this.isContactPersonActive = isContactPersonActive;
        this.contactPersonActiveDate = contactPersonActiveDate;
        this.contactPersonInactiveDate = contactPersonInactiveDate;
        this.isDeleted = isDeleted;
        this.deliveryLists = deliveryLists;
        this.tallysheets = tallysheets;
        this.otherChargesList = otherChargesList;
        this.clientStorageInfos = clientStorageInfos;
    }

    public Long getClientInfoId() {
        return clientInfoId;
    }

    public void setClientInfoId(Long clientInfoId) {
        this.clientInfoId = clientInfoId;
    }

    public String getClientTitle() {
        return clientTitle;
    }

    public void setClientTitle(String clientTitle) {
        this.clientTitle = clientTitle;
    }

    public Boolean isClientActive() {
        return isClientActive;
    }

    public void setClientActive(Boolean clientActive) {
        isClientActive = clientActive;
    }

    public String getClientAddress1() {
        return clientAddress1;
    }

    public void setClientAddress1(String clientAddress1) {
        this.clientAddress1 = clientAddress1;
    }

    public String getClientAddress2() {
        return clientAddress2;
    }

    public void setClientAddress2(String clientAddress2) {
        this.clientAddress2 = clientAddress2;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    public String getClientCountry() {
        return clientCountry;
    }

    public void setClientCountry(String clientCountry) {
        this.clientCountry = clientCountry;
    }

    public String getClientZip() {
        return clientZip;
    }

    public void setClientZip(String clientZip) {
        this.clientZip = clientZip;
    }

    public String getContactPersonFirstName() {
        return contactPersonFirstName;
    }

    public void setContactPersonFirstName(String contactPersonFirstName) {
        this.contactPersonFirstName = contactPersonFirstName;
    }

    public String getContactPersonMiddleName() {
        return contactPersonMiddleName;
    }

    public void setContactPersonMiddleName(String contactPersonMiddleName) {
        this.contactPersonMiddleName = contactPersonMiddleName;
    }

    public String getContactPersonLastName() {
        return contactPersonLastName;
    }

    public void setContactPersonLastName(String contactPersonLastName) {
        this.contactPersonLastName = contactPersonLastName;
    }

    public String getContactPersonPhoneNumber() {
        return contactPersonPhoneNumber;
    }

    public void setContactPersonPhoneNumber(String contactPersonPhoneNumber) {
        this.contactPersonPhoneNumber = contactPersonPhoneNumber;
    }

    public String getContactPersonMobileNumbere() {
        return contactPersonMobileNumbere;
    }

    public void setContactPersonMobileNumbere(String contactPersonMobileNumbere) {
        this.contactPersonMobileNumbere = contactPersonMobileNumbere;
    }

    public Boolean isContactPersonActive() {
        return isContactPersonActive;
    }

    public void setContactPersonActive(Boolean contactPersonActive) {
        isContactPersonActive = contactPersonActive;
    }

    public Date getContactPersonActiveDate() {
        return contactPersonActiveDate;
    }

    public void setContactPersonActiveDate(Date contactPersonActiveDate) {
        this.contactPersonActiveDate = contactPersonActiveDate;
    }

    public Date getContactPersonInactiveDate() {
        return contactPersonInactiveDate;
    }

    public void setContactPersonInactiveDate(Date contactPersonInactiveDate) {
        this.contactPersonInactiveDate = contactPersonInactiveDate;
    }

    public Set<DeliveryList> getDeliveryLists() {
        return deliveryLists;
    }

    public void setDeliveryLists(Set<DeliveryList> deliveryLists) {
        this.deliveryLists = deliveryLists;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<OtherCharges> getOtherChargesList() {
        return otherChargesList;
    }

    public void setOtherChargesList(List<OtherCharges> otherChargesList) {
        this.otherChargesList = otherChargesList;
    }

    public Set<TallySheet> getTallysheets() {
        return tallysheets;
    }

    public void setTallysheets(Set<TallySheet> tallysheets) {
        this.tallysheets = tallysheets;
    }

    public Boolean getClientActive() {
        return isClientActive;
    }

    public Boolean getContactPersonActive() {
        return isContactPersonActive;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Set<ClientStorageInfo> getClientStorageInfos() {
        return clientStorageInfos;
    }

    public void setClientStorageInfos(Set<ClientStorageInfo> clientStorageInfos) {
        this.clientStorageInfos = clientStorageInfos;
    }
}

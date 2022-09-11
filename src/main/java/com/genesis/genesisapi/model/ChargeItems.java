package com.genesis.genesisapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "charge_items")
@Table
public class ChargeItems {

    @Id
    @GeneratedValue
    @Column(name = "charge_item_id", length = 20)
    private long chargeItemId;

    @Column(name = "charge_item", length = 500)
    private String chargeItem;

    @ManyToOne
    private OtherCharges otherCharges;

    public ChargeItems() {
    }

    public ChargeItems(String chargeItem, OtherCharges otherCharges) {
        this.chargeItem = chargeItem;
        this.otherCharges = otherCharges;
        /*this.otherChargesList = otherChargesList;*/
    }

    public long getChargeItemId() {
        return chargeItemId;
    }

    public void setChargeItemId(long chargeItemId) {
        this.chargeItemId = chargeItemId;
    }

    public String getChargeItem() {
        return chargeItem;
    }

    public void setChargeItem(String chargeItem) {
        this.chargeItem = chargeItem;
    }

    public OtherCharges getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(OtherCharges otherCharges) {
        this.otherCharges = otherCharges;
    }

   /* public List<OtherCharges> getOtherChargesList() {
        return otherChargesList;
    }

    public void setOtherChargesList(List<OtherCharges> otherChargesList) {
        this.otherChargesList = otherChargesList;
    }*/
}

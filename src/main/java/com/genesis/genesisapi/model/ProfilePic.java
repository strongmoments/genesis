package com.genesis.genesisapi.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Table
@Entity(name = "profilePic")
public class ProfilePic {

	@Id
    @GeneratedValue
    @Column(name = "profile_pic_id", length = 20)
    private Long profilePicId;

    @Column(name = "profile_pic_name", length = 500)
    private String profilePicName;
    
    @Column(name = "profile_pic_type", length = 500)
    private String profilePicType;
    
    @Column(name="pic_image")
    private Blob picImage;
    
    public ProfilePic(){
    	super();
    }
	public ProfilePic(Long profilePicId, String profilePicName, String profilePicType, Blob picImage) {
		super();
		this.profilePicId = profilePicId;
		this.profilePicName = profilePicName;
		this.profilePicType = profilePicType;
		this.picImage = picImage;
	}

	public Long getProfilePicId() {
		return profilePicId;
	}

	public void setProfilePicId(Long profilePicId) {
		this.profilePicId = profilePicId;
	}

	public String getProfilePicName() {
		return profilePicName;
	}

	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}

	public String getProfilePicType() {
		return profilePicType;
	}

	public void setProfilePicType(String profilePicType) {
		this.profilePicType = profilePicType;
	}

	public Blob getPicImage() {
		return picImage;
	}

	public void setPicImage(Blob picImage) {
		this.picImage = picImage;
	}
    
    
}

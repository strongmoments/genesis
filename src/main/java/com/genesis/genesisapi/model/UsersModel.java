package com.genesis.genesisapi.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
@Table(name = "USERS")
public class UsersModel {
	
	@Id
	@GeneratedValue
	@Column(name = "SNO", nullable = false)
	private Long sno;
	
	@Column(name = "FIRST_NAME")
	private String fName;
	
	@Column(name = "LAST_NAME")
	private String lName;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "USERID")
	private String userId;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "MOBILENO")
	private String mobileNo;

	@Column(name = "DOB")
	private Long dob;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "EMAILID")
	private String emailId;
	
	@Column(name = "CREATION_DATE")
	private Long creationDate;
	
	@Column(name = "CLIENTID")
	private Long clientId;
	
	@Column(name = "RESET_TOKEN")
	private String resetToken;
	
	@Column(name="RESET_TOKEN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date tokenDate;
	
	@OneToOne
	private ProfilePic profilePic;
	
	public UsersModel(Long sno, String fName, String lName, String name, String userId, String password,
			String mobileNo, Long dob, String gender, String emailId, Long creationDate, Long clientId,
			String resetToken, Date tokenDate, ProfilePic profilePic) {
		super();
		this.sno = sno;
		this.fName = fName;
		this.lName = lName;
		this.name = name;
		this.userId = userId;
		this.password = password;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.gender = gender;
		this.emailId = emailId;
		this.creationDate = creationDate;
		this.clientId = clientId;
		this.resetToken = resetToken;
		this.tokenDate = tokenDate;
		this.profilePic = profilePic;
	}
	public UsersModel(){
		super();
	}
	
	public Long getSno() {
		return sno;
	}

	public void setSno(Long sno) {
		this.sno = sno;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Date getTokenDate() {
		return tokenDate;
	}

	public void setTokenDate(Date tokenDate) {
		this.tokenDate = tokenDate;
	}

	public ProfilePic getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(ProfilePic profilePic) {
		this.profilePic = profilePic;
	}

	
}

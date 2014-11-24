/**
 * 
 *平安金融科技
 * Copyright (c) 2011-2014 PingAn,Inc.All Rights Reserved.
 */
package org.sunpei.dao;

import java.io.Serializable;

/**
 * this is the bean
 * @author peter.sun 
 * if you have question ,please send to hisp@163.com
 * 
 */
public class CustomerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户ID*/
	private String custId;
	
	/** 用户昵称*/
	private String nickName;
	
	private String nation;// 民族
	
	private String address;// 地址
	
	private String issue;// 签发机构
	
	private String expire;//有效日期

	/** 用户头像地址*/
	private String avatarUrl;
	
	private String clientNo;
	
	private String partyNo;
	
	private String alias;
	
	private String name;
	
	private String sex;
	
	private String birthDate;
	
	private String idType;
	
	private String idNo;
	
	/** 是否平安用户 Y,N*/
	private String isPaCustomer;
	
	private String mobileNo;
	
	private String email;

	/** 是否基金客户*/
	private String isFundCustomer;
	
	/** 是否基金关联*/
	private String isFundRelate;
	
	private String imgId;
	
	
	
	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
	
	
	public String getIsFundRelate() {
		return isFundRelate;
	}

	public void setIsFundRelate(String isFundRelate) {
		this.isFundRelate = isFundRelate;
	}

	/**
	 * Getter method for property <tt>custId</tt>.
	 * 
	 * @return property value of custId
	 */
	public String getCustId() {
		return custId;
	}

	
	/**
	 * Setter method for property <tt>custId</tt>.
	 * 
	 * @param custId value to be assigned to property custId
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * Getter method for property <tt>clientNo</tt>.
	 * 
	 * @return property value of clientNo
	 */
	public String getClientNo() {
		return clientNo;
	}

	/**
	 * Setter method for property <tt>clientNo</tt>.
	 * 
	 * @param clientNo value to be assigned to property clientNo
	 */
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	/**
	 * Getter method for property <tt>partyNo</tt>.
	 * 
	 * @return property value of partyNo
	 */
	public String getPartyNo() {
		return partyNo;
	}

	/**
	 * Setter method for property <tt>partyNo</tt>.
	 * 
	 * @param partyNo value to be assigned to property partyNo
	 */
	public void setPartyNo(String partyNo) {
		this.partyNo = partyNo;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>sex</tt>.
	 * 
	 * @return property value of sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Setter method for property <tt>sex</tt>.
	 * 
	 * @param sex value to be assigned to property sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Getter method for property <tt>birthDate</tt>.
	 * 
	 * @return property value of birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * Setter method for property <tt>birthDate</tt>.
	 * 
	 * @param birthDate value to be assigned to property birthDate
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Getter method for property <tt>idType</tt>.
	 * 
	 * @return property value of idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * Setter method for property <tt>idType</tt>.
	 * 
	 * @param idType value to be assigned to property idType
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * Getter method for property <tt>idNo</tt>.
	 * 
	 * @return property value of idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * Setter method for property <tt>idNo</tt>.
	 * 
	 * @param idNo value to be assigned to property idNo
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * Getter method for property <tt>isPaCustomer</tt>.
	 * 
	 * @return property value of isPaCustomer
	 */
	public String getIsPaCustomer() {
		return isPaCustomer;
	}

	/**
	 * Setter method for property <tt>isPaCustomer</tt>.
	 * 
	 * @param isPaCustomer value to be assigned to property isPaCustomer
	 */
	public void setIsPaCustomer(String isPaCustomer) {
		this.isPaCustomer = isPaCustomer;
	}

	/**
	 * Getter method for property <tt>mobileNo</tt>.
	 * 
	 * @return property value of mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * Setter method for property <tt>mobileNo</tt>.
	 * 
	 * @param mobileNo value to be assigned to property mobileNo
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * Getter method for property <tt>email</tt>.
	 * 
	 * @return property value of email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter method for property <tt>email</tt>.
	 * 
	 * @param email value to be assigned to property email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	

	/**
	 * Getter method for property <tt>nickName</tt>.
	 * 
	 * @return property value of nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Setter method for property <tt>nickName</tt>.
	 * 
	 * @param nickName value to be assigned to property nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Getter method for property <tt>avatarUrl</tt>.
	 * 
	 * @return property value of avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * Setter method for property <tt>avatarUrl</tt>.
	 * 
	 * @param avatarUrl value to be assigned to property avatarUrl
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Getter method for property <tt>isFundCustomer</tt>.
	 * 
	 * @return property value of isFundCustomer
	 */
	public String getIsFundCustomer() {
		return isFundCustomer;
	}

	/**
	 * Setter method for property <tt>isFundCustomer</tt>.
	 * 
	 * @param isFundCustomer value to be assigned to property isFundCustomer
	 */
	public void setIsFundCustomer(String isFundCustomer) {
		this.isFundCustomer = isFundCustomer;
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerInfo [custId=" + custId + ", nickName=" + nickName
				+ ", avatarUrl=" + avatarUrl + ", clientNo=" + clientNo
				+ ", partyNo=" + partyNo + ", alias=" + alias + ", name="
				+ name + ", sex=" + sex + ", birthDate=" + birthDate
				+ ", idType=" + idType + ", idNo=" + idNo + ", isPaCustomer="
				+ isPaCustomer + ", mobileNo=" + mobileNo + ", email=" + email
				+ ", isFundCustomer=" + isFundCustomer + "]";
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}


	
	
}

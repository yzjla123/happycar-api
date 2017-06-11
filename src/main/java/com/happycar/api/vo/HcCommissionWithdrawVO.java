package com.happycar.api.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

public class HcCommissionWithdrawVO implements Serializable {
	
	private int id;
	private Date addTime;
	private String alipayAccount;
	private String alipayName;
	private float amount;
	private int memberId;
	private String remark;
	private int status;
	private Date updateTime;
	private Date transferTime;

	public HcCommissionWithdrawVO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getAlipayAccount() {
		return this.alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getAlipayName() {
		return this.alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
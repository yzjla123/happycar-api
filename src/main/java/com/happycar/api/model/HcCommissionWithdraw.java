package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_commission_withdraw database table.
 * 
 */
@Entity
@Table(name="hc_commission_withdraw")
@NamedQuery(name="HcCommissionWithdraw.findAll", query="SELECT h FROM HcCommissionWithdraw h")
public class HcCommissionWithdraw implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="alipay_account")
	private String alipayAccount;

	@Column(name="alipay_name")
	private String alipayName;

	private float amount;

	@Column(name="member_id")
	private int memberId;

	private String remark;

	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="transfer_time")
	private Date transferTime;

	public HcCommissionWithdraw() {
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

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

}
package com.happycar.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the hc_signup database table.
 * 
 */
@Entity
@Table(name="hc_signup")
public class HcSignup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private Float amount;

	@Column(name="coupon_amount")
	private Float couponAmount;

	@Column(name="coupon_id")
	private Integer couponId;

	@Column(name="tuition_id")
	private Integer tuitionId;

	@Column(name="member_id")
	private Integer memberId;

	@Column(name="order_no")
	private String orderNo;

	@Column(name="pay_amount")
	private Float payAmount;

	@Column(name="pay_channel")
	private Integer payChannel;

	@Column(name="pay_type")
	private Integer payType;

	private Integer status;

	@Column(name="referee_phone")
	private String refereePhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
	
	@OneToOne
	@JoinColumn(name="member_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcMember member;
	
	@OneToOne
	@JoinColumn(name="tuition_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcTuition tuition;

	public HcSignup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Float getAmount() {
		return this.amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getCouponAmount() {
		return this.couponAmount;
	}

	public void setCouponAmount(Float couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getTuitionId() {
		return this.tuitionId;
	}

	public void setTuitionId(Integer tuitionId) {
		this.tuitionId = tuitionId;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Float getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayChannel() {
		return this.payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}


	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public HcMember getMember() {
		return member;
	}

	public void setMember(HcMember member) {
		this.member = member;
	}

	public HcTuition getTuition() {
		return tuition;
	}

	public void setTuition(HcTuition tuition) {
		this.tuition = tuition;
	}

	public String getRefereePhone() {
		return refereePhone;
	}

	public void setRefereePhone(String refereePhone) {
		this.refereePhone = refereePhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
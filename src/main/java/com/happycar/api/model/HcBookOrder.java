package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_book_order database table.
 * 
 */
@Entity
@Table(name="hc_book_order")
@NamedQuery(name="HcBookOrder.findAll", query="SELECT h FROM HcBookOrder h")
public class HcBookOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="member_id")
	private Integer memberId;

	@Column(name="pay_amount")
	private Integer payAmount;

	@Column(name="pay_happy_coin")
	private Integer payHappyCoin;

	@Column(name="book_order_no")
	private String bookOrderNo;

	private Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public HcBookOrder() {
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

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getPayAmount() {
		return this.payAmount;
	}

	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPayHappyCoin() {
		return this.payHappyCoin;
	}

	public void setPayHappyCoin(Integer payHappyCoin) {
		this.payHappyCoin = payHappyCoin;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBookOrderNo() {
		return bookOrderNo;
	}

	public void setBookOrderNo(String bookOrderNo) {
		this.bookOrderNo = bookOrderNo;
	}

}
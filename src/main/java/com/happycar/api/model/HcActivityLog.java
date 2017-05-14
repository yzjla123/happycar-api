package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the hc_activity_log database table.
 * 
 */
@Entity
@Table(name="hc_activity_log")
@NamedQuery(name="HcActivityLog.findAll", query="SELECT h FROM HcActivityLog h")
public class HcActivityLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private Float amount;

	@Column(name="member_id")
	private int memberId;

	private String name;

	public HcActivityLog() {
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

	public Float getAmount() {
		return this.amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
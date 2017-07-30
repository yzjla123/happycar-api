package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_learn_progress database table.
 * 
 */
@Entity
@Table(name="hc_learn_progress")
@NamedQuery(name="HcLearnProgress.findAll", query="SELECT h FROM HcLearnProgress h")
public class HcLearnProgress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="member_id")
	private Integer memberId;

	private Integer subject1;

	private Integer subject2;

	private Integer subject3;

	private Integer subject4;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public HcLearnProgress() {
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

	public Integer getSubject1() {
		return this.subject1;
	}

	public void setSubject1(Integer subject1) {
		this.subject1 = subject1;
	}

	public Integer getSubject2() {
		return this.subject2;
	}

	public void setSubject2(Integer subject2) {
		this.subject2 = subject2;
	}

	public Integer getSubject3() {
		return this.subject3;
	}

	public void setSubject3(Integer subject3) {
		this.subject3 = subject3;
	}

	public Integer getSubject4() {
		return this.subject4;
	}

	public void setSubject4(Integer subject4) {
		this.subject4 = subject4;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
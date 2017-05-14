package com.happycar.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the hc_book database table.
 * 
 */
@Entity
@Table(name="hc_book")
@NamedQuery(name="HcBook.findAll", query="SELECT h FROM HcBook h")
public class HcBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String comment;

	@Column(name="member_id")
	private Integer memberId;

	@Column(name="schedule_id")
	private Integer scheduleId;

	private Integer star;

	private Integer status;
	
	private Date date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="penalty_amount")
	private Float penaltyAmount;
	
	@Column(name="coach_id")
	private Integer coachId;
	
	@OneToOne
	@JoinColumn(name="member_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcMember member;
	
	@OneToOne
	@JoinColumn(name="schedule_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcSchedule schedule;

	public HcBook() {
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

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
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

	public Float getPenaltyAmount() {
		return this.penaltyAmount;
	}

	public void setPenaltyAmount(Float penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public HcMember getMember() {
		return member;
	}

	public void setMember(HcMember member) {
		this.member = member;
	}

	public HcSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(HcSchedule schedule) {
		this.schedule = schedule;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

}
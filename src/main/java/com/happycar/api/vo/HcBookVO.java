package com.happycar.api.vo;

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


public class HcBookVO implements Serializable {

	private Integer id;

	private String comment;

	private Integer memberId;

	private Integer scheduleId;

	private Integer star;

	private Integer status;
	
	private Date date;

	private Float penaltyAmount;
	
	private Integer coachId;
	
	private HcCoachVO coach; 
	
	private HcScheduleVO schedule; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getPenaltyAmount() {
		return penaltyAmount;
	}

	public void setPenaltyAmount(Float penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	public HcCoachVO getCoach() {
		return coach;
	}

	public void setCoach(HcCoachVO coach) {
		this.coach = coach;
	}

	public HcScheduleVO getSchedule() {
		return schedule;
	}

	public void setSchedule(HcScheduleVO schedule) {
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
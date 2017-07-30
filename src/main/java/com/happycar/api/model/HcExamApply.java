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
 * The persistent class for the hc_exam_apply database table.
 * 
 */
@Entity
@Table(name="hc_exam_apply")
@NamedQuery(name="HcExamApply.findAll", query="SELECT h FROM HcExamApply h")
public class HcExamApply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="exam_addr")
	private String examAddr;

	@Column(name="exam_remark")
	private String examRemark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="exam_time")
	private Date examTime;

	@Column(name="member_id")
	private Integer memberId;

	@Column(name="reject_reson")
	private String rejectReson;

	private Integer status;

	@Column(name="subject_type")
	private Integer subjectType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
	
	@OneToOne
	@JoinColumn(name="member_id",insertable=false,updatable=false)
	@NotFound
	private HcMember member;
	
	public HcExamApply() {
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

	public String getExamAddr() {
		return this.examAddr;
	}

	public void setExamAddr(String examAddr) {
		this.examAddr = examAddr;
	}

	public String getExamRemark() {
		return this.examRemark;
	}

	public void setExamRemark(String examRemark) {
		this.examRemark = examRemark;
	}

	public Date getExamTime() {
		return this.examTime;
	}

	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getRejectReson() {
		return this.rejectReson;
	}

	public void setRejectReson(String rejectReson) {
		this.rejectReson = rejectReson;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSubjectType() {
		return this.subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public HcMember getMember() {
		return member;
	}

	public void setMember(HcMember member) {
		this.member = member;
	}

}
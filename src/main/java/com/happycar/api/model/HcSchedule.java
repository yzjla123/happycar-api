package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the hc_paiban database table.
 * 
 */
@Entity
@Table(name="hc_schedule")
public class HcSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="book_num")
	private Integer bookNum;

	@Column(name="car_num")
	private Integer carNum;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="coach_id")
	private Integer coachId;

	@Column(name="member_num")
	private Integer memberNum;

	private Integer status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	private String time1;
	
	private String time2;
	
	@Column(name="subject_type")
	private Integer subjectType;
	
	@OneToOne
	@JoinColumn(name="coach_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcCoach coach;
	
	@Column(name="driving_license_type")
	private String drivingLicenseType;
	public HcSchedule() {
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

	public Integer getBookNum() {
		return this.bookNum;
	}

	public void setBookNum(Integer bookNum) {
		this.bookNum = bookNum;
	}

	public Integer getCarNum() {
		return this.carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCoachId() {
		return this.coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public Integer getMemberNum() {
		return this.memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
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

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public Integer getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}

	public HcCoach getCoach() {
		return coach;
	}

	public void setCoach(HcCoach coach) {
		this.coach = coach;
	}

	public String getDrivingLicenseType() {
		return drivingLicenseType;
	}

	public void setDrivingLicenseType(String drivingLicenseType) {
		this.drivingLicenseType = drivingLicenseType;
	}

}
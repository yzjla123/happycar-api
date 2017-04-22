package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the hc_coach database table.
 * 
 */
@Entity
@Table(name="hc_coach")
public class HcCoach implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="img_url")
	private String imgUrl;

	private String name;

	private String phone;

	@Column(name="school_id")
	private Integer schoolId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="school_age")
	private Integer schoolAge;

	@Column(name="is_deleted")
	private Integer isDeleted;

	@Column(name="car_num")
	private Integer carNum;
	
	@OneToOne
	@JoinColumn(name="school_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcSchool school;
	
	@Transient
	private Integer totalMember;
	
	@Transient
	private Integer learningMember;	

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

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public HcSchool getSchool() {
		return school;
	}

	public void setSchool(HcSchool school) {
		this.school = school;
	}

	public Integer getCarNum() {
		return carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}

	public Integer getTotalMember() {
		return totalMember;
	}

	public void setTotalMember(Integer totalMember) {
		this.totalMember = totalMember;
	}

	public Integer getLearningMember() {
		return learningMember;
	}

	public void setLearningMember(Integer learningMember) {
		this.learningMember = learningMember;
	}

}
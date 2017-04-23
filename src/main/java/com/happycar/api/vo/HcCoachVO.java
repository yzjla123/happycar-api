package com.happycar.api.vo;

import java.io.Serializable;

import com.happycar.api.model.HcSchool;

public class HcCoachVO implements Serializable {

	private Integer id;

	private String imgUrl;

	private String name;

	private String phone;

	private Integer schoolId;

	private Integer schoolAge;

	private Integer carNum;
	
	private HcSchoolVO school;
	
	private Double distance;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getSchoolAge() {
		return schoolAge;
	}

	public void setSchoolAge(Integer schoolAge) {
		this.schoolAge = schoolAge;
	}

	public Integer getCarNum() {
		return carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}

	public HcSchoolVO getSchool() {
		return school;
	}

	public void setSchool(HcSchoolVO school) {
		this.school = school;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
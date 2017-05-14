package com.happycar.api.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.happycar.api.model.HcSchool;

public class HcCoachVO implements Serializable {

	private Integer id;

	private String pic;

	private String name;

	private String phone;

	private Integer schoolId;

	private Integer schoolAge;

	private Integer carNum;
	
	private HcSchoolVO school;
	
	private Double distance;

	private Integer star;
	
	private String drivingLicenseType;
	
	private Integer age;
	
	private Float passingRate;
	
	private String licensePlate;
	
	private String vehicleBrand;
	
	private String addr;
	
	private String idcard;
	
	private List<HcCommentVO> comments = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Float getPassingRate() {
		return passingRate;
	}

	public void setPassingRate(Float passingRate) {
		this.passingRate = passingRate;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getDrivingLicenseType() {
		return drivingLicenseType;
	}

	public void setDrivingLicenseType(String drivingLicenseType) {
		this.drivingLicenseType = drivingLicenseType;
	}

	public List<HcCommentVO> getComments() {
		return comments;
	}

	public void setComments(List<HcCommentVO> comments) {
		this.comments = comments;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
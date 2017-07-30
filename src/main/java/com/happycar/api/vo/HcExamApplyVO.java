package com.happycar.api.vo;

import java.util.Date;

public class HcExamApplyVO {
	
	private Integer id;
	private String memberName;
	private Date addTime;
	private Integer status;
	
	public HcExamApplyVO() {
		// TODO Auto-generated constructor stub
	}
	
	public HcExamApplyVO(Integer id, String memberName, Date addTime) {
		super();
		this.id = id;
		this.memberName = memberName;
		this.addTime = addTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

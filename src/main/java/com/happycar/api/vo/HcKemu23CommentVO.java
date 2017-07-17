package com.happycar.api.vo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;

import java.util.Date;


public class HcKemu23CommentVO implements Serializable {
	private Integer id;
	
	private Date addTime;

	private String content;

	private Integer kemu23Id;

	private String memberId;

	private Date updateTime;

	private Integer zanNum;
	
	private String memberName;
	
	private String memberPic;
	

	public HcKemu23CommentVO() {
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getKemu23Id() {
		return this.kemu23Id;
	}

	public void setKemu23Id(Integer kemu23Id) {
		this.kemu23Id = kemu23Id;
	}

	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getZanNum() {
		return this.zanNum;
	}

	public void setZanNum(Integer zanNum) {
		this.zanNum = zanNum;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPic() {
		return memberPic;
	}

	public void setMemberPic(String memberPic) {
		this.memberPic = memberPic;
	}

}
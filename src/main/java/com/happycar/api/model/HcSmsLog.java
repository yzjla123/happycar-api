package com.happycar.api.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the sms_log database table.
 * 系统参数
 */
@Entity
@Table(name="hc_sms_log")
public class HcSmsLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String content;
	private String phone;
	private Integer status;
	private Date addTime;
	private Date updateTime;

	public HcSmsLog() {
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="content")
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="status")
	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="add_time")
	public Date getAddTime() {
		return addTime;
	}


	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
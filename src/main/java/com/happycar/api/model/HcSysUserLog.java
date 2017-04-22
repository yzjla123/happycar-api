package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_sys_user_log database table.
 * 
 */
@Entity
@Table(name="hc_sys_user_log")
@NamedQuery(name="HcSysUserLog.findAll", query="SELECT h FROM HcSysUserLog h")
public class HcSysUserLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Lob
	private String content;

	private String operation;

	@Column(name="user_behavior")
	private String userBehavior;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="user_type")
	private Integer userType;

	public HcSysUserLog() {
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

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUserBehavior() {
		return this.userBehavior;
	}

	public void setUserBehavior(String userBehavior) {
		this.userBehavior = userBehavior;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
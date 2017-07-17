package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the hc_kemu23_comment database table.
 * 
 */
@Entity
@Table(name="hc_kemu23_comment")
public class HcKemu23Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String content;

	@Column(name="kemu23_id")
	private Integer kemu23Id;

	@Column(name="member_id")
	private Integer memberId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="zan_num")
	private Integer zanNum;
	
	@OneToOne
	@JoinColumn(name="member_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcMember member;

	public HcKemu23Comment() {
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

	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
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

	public HcMember getMember() {
		return member;
	}

	public void setMember(HcMember member) {
		this.member = member;
	}

}
package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the hc_message_center database table.
 * 
 */
@Entity
@Table(name="hc_message_center")
@NamedQuery(name="HcMessageCenter.findAll", query="SELECT h FROM HcMessageCenter h")
public class HcMessageCenter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String content;

	@Column(name="is_read")
	private int isRead;

	@Column(name="member_id")
	private int memberId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	private String url;
	
	@Column(name="is_deleted")
	private int isDeleted;
	
	private String no;
	
	@OneToOne
	@JoinColumn(name="member_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcMember member;

	public HcMessageCenter() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public int getIsRead() {
		return this.isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public HcMember getMember() {
		return member;
	}

	public void setMember(HcMember member) {
		this.member = member;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
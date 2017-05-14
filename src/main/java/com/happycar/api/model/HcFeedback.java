package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_feedback database table.
 * 
 */
@Entity
@Table(name="hc_feedback")
@NamedQuery(name="HcFeedback.findAll", query="SELECT h FROM HcFeedback h")
public class HcFeedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String content;

	@Column(name="member_id")
	private int memberId;

	public HcFeedback() {
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

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

}
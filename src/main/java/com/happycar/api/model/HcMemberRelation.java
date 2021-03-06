package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the hc_member_relation database table.
 * 
 */
@Entity
@Table(name="hc_member_relation")
@NamedQuery(name="HcMemberRelation.findAll", query="SELECT h FROM HcMemberRelation h")
public class HcMemberRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private int cid;

	private int level;

	private int pid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@OneToOne
	@JoinColumn(name="cid",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcMember childMember;
	
	@OneToOne
	@JoinColumn(name="pid",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcMember parentMember;
	
	public HcMemberRelation() {
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

	public int getCid() {
		return this.cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPid() {
		return this.pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public HcMember getChildMember() {
		return childMember;
	}

	public void setChildMember(HcMember childMember) {
		this.childMember = childMember;
	}

	public HcMember getParentMember() {
		return parentMember;
	}

	public void setParentMember(HcMember parentMember) {
		this.parentMember = parentMember;
	}

}
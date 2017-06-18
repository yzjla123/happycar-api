package com.happycar.api.vo;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;
public class HcMemberRelationVO implements Serializable {

	private int id;
	private int cid;
	private int level;
	private int pid;
	private String childPic;
	private String childName;
	private String parentPic;
	private String parentName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getChildPic() {
		return childPic;
	}
	public void setChildPic(String childPic) {
		this.childPic = childPic;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getParentPic() {
		return parentPic;
	}
	public void setParentPic(String parentPic) {
		this.parentPic = parentPic;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
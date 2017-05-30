package com.happycar.api.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

public class HcUpdateVO implements Serializable {

	private int id;

	private int isForce;

	private String type;

	private String url;

	private String version;
	
	private String title;
	
	private String note;
	
	/**
	 * 0 无需升级,1需要升级
	 */
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsForce() {
		return isForce;
	}

	public void setIsForce(int isForce) {
		this.isForce = isForce;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


}
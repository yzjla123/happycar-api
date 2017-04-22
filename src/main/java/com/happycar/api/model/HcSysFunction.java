package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hc_sys_function database table.
 * 
 */
@Entity
@Table(name="hc_sys_function")
@NamedQuery(name="HcSysFunction.findAll", query="SELECT h FROM HcSysFunction h")
public class HcSysFunction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String code;

	private String description;

	@Column(name="menu_rank")
	private String menuRank;

	private String name;

	@Column(name="parent_id")
	private Integer parentId;

	private String remarks;

	private String status;

	public HcSysFunction() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMenuRank() {
		return this.menuRank;
	}

	public void setMenuRank(String menuRank) {
		this.menuRank = menuRank;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hc_sys_role_right database table.
 * 
 */
@Entity
@Table(name="hc_sys_role_right")
@NamedQuery(name="HcSysRoleRight.findAll", query="SELECT h FROM HcSysRoleRight h")
public class HcSysRoleRight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="right_id")
	private Integer rightId;

	@Column(name="role_id")
	private Integer roleId;

	public HcSysRoleRight() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRightId() {
		return this.rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hc_sys_role_menu database table.
 * 
 */
@Entity
@Table(name="hc_sys_role_menu")
@NamedQuery(name="HcSysRoleMenu.findAll", query="SELECT h FROM HcSysRoleMenu h")
public class HcSysRoleMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="menu_id")
	private Integer menuId;

	@Column(name="role_id")
	private Integer roleId;

	public HcSysRoleMenu() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
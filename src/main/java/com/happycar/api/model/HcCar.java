package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hc_car database table.
 * 
 */
@Entity
@Table(name="hc_car")
public class HcCar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="coach_id")
	private Integer coachId;

	private String no;

	public HcCar() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getcoachId() {
		return this.coachId;
	}

	public void setcoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

}
package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_model_test_question database table.
 * 
 */
@Entity
@Table(name="hc_model_test_question")
@NamedQuery(name="HcModelTestQuestion.findAll", query="SELECT h FROM HcModelTestQuestion h")
public class HcModelTestQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String answer;

	@Column(name="is_wrong")
	private Integer isWrong;

	@Column(name="model_test_id")
	private Integer modelTestId;

	@Column(name="theory_id")
	private Integer theoryId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public HcModelTestQuestion() {
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

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getIsWrong() {
		return this.isWrong;
	}

	public void setIsWrong(Integer isWrong) {
		this.isWrong = isWrong;
	}

	public Integer getModelTestId() {
		return this.modelTestId;
	}

	public void setModelTestId(Integer modelTestId) {
		this.modelTestId = modelTestId;
	}

	public Integer getTheoryId() {
		return this.theoryId;
	}

	public void setTheoryId(Integer theoryId) {
		this.theoryId = theoryId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
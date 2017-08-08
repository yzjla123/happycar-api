package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;


/**
 * The persistent class for the hc_member database table.
 * 
 */
@Entity
@Table(name="hc_member")
@NamedQuery(name="HcMember.findAll", query="SELECT h FROM HcMember h")
public class HcMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String idcard;

	private String name;

	private String phone;
	
	private String addr;

	private Integer progress;
	
	private Float amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="coach_id")
	private Integer coachId;
	
	@Column(name="signup_id")
	private Integer signupId;
	
	@Column(name="signup_date")
	private Date signupDate;
	
	private Float commission;
	
	@Column(name="referee_phone")
	private String refereePhone;
	
	@OneToOne
	@JoinColumn(name="coach_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private HcCoach coach;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
	
	private String pic;
	
	@Column(name = "exam_phone")
	private String examPhone;
	
	@Column(name = "happy_coin")
	private Integer happyCoin;
	
	@Column(name="driving_license_type")
	private String drivingLicenseType;
	public HcMember() {
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

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public HcCoach getCoach() {
		return coach;
	}

	public void setCoach(HcCoach coach) {
		this.coach = coach;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getSignupId() {
		return signupId;
	}

	public void setSignupId(Integer signupId) {
		this.signupId = signupId;
	}

	public Date getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public String getRefereePhone() {
		return refereePhone;
	}

	public void setRefereePhone(String refereePhone) {
		this.refereePhone = refereePhone;
	}

	public String getExamPhone() {
		return examPhone;
	}

	public void setExamPhone(String examPhone) {
		this.examPhone = examPhone;
	}

	public Integer getHappyCoin() {
		return happyCoin;
	}

	public void setHappyCoin(Integer happyCoin) {
		this.happyCoin = happyCoin;
	}

	public String getDrivingLicenseType() {
		return drivingLicenseType;
	}

	public void setDrivingLicenseType(String drivingLicenseType) {
		this.drivingLicenseType = drivingLicenseType;
	}
}
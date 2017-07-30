package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_article database table.
 * 
 */
@Entity
@Table(name="hc_article")
@NamedQuery(name="HcArticle.findAll", query="SELECT h FROM HcArticle h")
public class HcArticle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Lob
	private String content;

	@Column(name="is_deleted")
	private Integer isDeleted;

	private String tag;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="subject_id")
	private Integer subjectId;
	
	private Integer type;
	
	private String code;
	
	@Column(name="share_title")
	private String shareTitle;
	
	@Column(name="share_content")
	private String shareContent;
	
	@Column(name="share_pic")
	private String sharePic;
	
	@Column(name="share_happy_coin")
	private Integer shareHappyCoin;
	
	@Column(name="share_day_times")
	private Integer shareDayTimes;

	public HcArticle() {
	}

	public HcArticle(Integer id, String title, Integer subjectId) {
		super();
		this.id = id;
		this.title = title;
		this.subjectId = subjectId;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getSharePic() {
		return sharePic;
	}

	public void setSharePic(String sharePic) {
		this.sharePic = sharePic;
	}

	public Integer getShareHappyCoin() {
		return shareHappyCoin;
	}

	public void setShareHappyCoin(Integer shareHappyCoin) {
		this.shareHappyCoin = shareHappyCoin;
	}

	public Integer getShareDayTimes() {
		return shareDayTimes;
	}

	public void setShareDayTimes(Integer shareDayTimes) {
		this.shareDayTimes = shareDayTimes;
	}

}
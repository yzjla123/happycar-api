package com.happycar.api.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hc_advertisement database table.
 * 
 */
@Entity
@Table(name="hc_advertisement")
@NamedQuery(name="HcAdvertisement.findAll", query="SELECT h FROM HcAdvertisement h")
public class HcAdvertisement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	private String content;

	private String pic;

	private String title;

	private Integer type;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	private String url;
	
	@Column(name="is_deleted")
	private Integer isDeleted;
	
	private String thumbnail;

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
	
	public HcAdvertisement() {
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

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
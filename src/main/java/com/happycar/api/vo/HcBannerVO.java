package com.happycar.api.vo;

import java.io.Serializable;

public class HcBannerVO implements Serializable {

	private String pic;
	private String href;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}

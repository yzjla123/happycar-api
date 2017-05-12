package com.happycar.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qiniu.util.Auth;

@Service
public class QiniuService {
	@Value("${qiniu_accessKey}")
	private String accessKey;
	@Value("${qiniu_secretKey}")
	private String secretKey;
	@Value("${qiniu_bucket}")
	private String bucket;
	
	public String getUploadToken(){
		Auth auth = Auth.create(accessKey, secretKey);
		String uploadToken = auth.uploadToken(bucket);
		return uploadToken;
	}
	
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	
	

}

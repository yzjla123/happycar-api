package com.happycar.api.exception;

import com.happycar.api.contant.ErrorCode;

public class BussinessException extends RuntimeException{
	
	private int erorCode;

	public BussinessException() {
		super();
	}
	
	public BussinessException(String message) {
		super(message);
	}
	
	public BussinessException(int erorCode,String message) {
		super(message);
		this.erorCode = erorCode;
	}
	
	public BussinessException(ErrorCode erorCode) {
		super(erorCode.getMsg());
		this.erorCode = erorCode.getErrorCode();
	}

	public int getErorCode() {
		return erorCode;
	}

	public void setErorCode(int erorCode) {
		this.erorCode = erorCode;
	}

}

package com.happycar.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

public class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
    private static HttpClient client = null;
    
	public static String getContent(String urlStr)  {
		StringBuilder sb = new StringBuilder();
		try{
			URL url = new URL(urlStr);
		    URLConnection urlConnection = url.openConnection();                                                    // 打开连接
		    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8")); // 获取输入流
		    String line = null;
		    
		    while ((line = br.readLine()) != null) {
		        sb.append(line + "\n");
		    }
		}catch(Exception e){
			
		}
		return sb.toString();
	}
	// 构造单例
    private HttpUtil() {

	MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
	HttpConnectionManagerParams params = new HttpConnectionManagerParams();
	// 默认连接超时时间
	params.setConnectionTimeout(5000);
	// 默认读取超时时间
	params.setSoTimeout(10000);
	// 默认单个host最大连接数
	params.setDefaultMaxConnectionsPerHost(100);// very important!!
	// 最大总连接数
	params.setMaxTotalConnections(256);// very important!!
	httpConnectionManager.setParams(params);

	client = new HttpClient(httpConnectionManager);

	client.getParams().setConnectionManagerTimeout(3000);
	// client.getParams().setIntParameter("http.socket.timeout", 10000);
	// client.getParams().setIntParameter("http.connection.timeout", 5000);
    }

    private static class ClientUtilInstance {
	private static final HttpUtil ClientUtil = new HttpUtil();
    }

    public static HttpUtil getInstance() {
	return ClientUtilInstance.ClientUtil;
    }

    /**
     * 发送http GET请求，并返回http响应字符串
     * 
     * @param urlstr
     *            完整的请求url字符串
     * @return
     */
    public String doGetRequest(String urlstr) {
	String response = "";

	HttpMethod httpmethod = new GetMethod(urlstr);
	try {
	    int statusCode = client.executeMethod(httpmethod);
	    InputStream _InputStream = null;
	    if (statusCode == HttpStatus.SC_OK) {
		_InputStream = httpmethod.getResponseBodyAsStream();
	    }
	    if (_InputStream != null) {
		response = GetResponseString(_InputStream, "UTF-8");
	    }
	} catch (HttpException e) {
	    logger.error("获取响应错误，原因：" + e.getMessage());
	    e.printStackTrace();
	} catch (IOException e) {
	    logger.error("获取响应错误，原因1：" + e.getMessage());
	    e.printStackTrace();
	} finally {
	    httpmethod.releaseConnection();

	}
	return response;
    }

    /**
     * 
     * @param _InputStream
     * @param Charset
     * @return
     */
    public String GetResponseString(InputStream _InputStream, String Charset) {
	String response = "";
	try {
	    if (_InputStream != null) {
		StringBuffer buffer = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(_InputStream, Charset);
		Reader in = new BufferedReader(isr);
		int ch;
		while ((ch = in.read()) > -1) {
		    buffer.append((char) ch);
		}
		response = buffer.toString();
		buffer = null;
	    }
	} catch (Exception e) {
	    logger.error("获取响应错误，原因：" + e.getMessage());
	    response = response + e.getMessage();
	    e.printStackTrace();
	}
	return response;
    }

    public static void main(String[] args) {
	String url = "http://120.76.233.25:8081/obd/find_car?device_id=E61643190094&request_no=123&request_time=1468219121469&sign=5b71724aa37a2c391614758727c539ed";
	System.out.println(new Date());
	HttpUtil HttpUtil = getInstance();
	System.out.println(HttpUtil.doGetRequest(url));
	System.out.println(new Date());
    }
}

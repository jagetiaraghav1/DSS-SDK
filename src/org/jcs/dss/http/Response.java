package org.jcs.dss.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
	
	private int statusCode;
	private String statusMsg;
	private Map<String, List<String>> headers;
	private InputStream data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	
	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}
	
	public InputStream getData() {
		return data;
	}
	
	public void setData(InputStream data) {
		this.data = data;
	}
	
}

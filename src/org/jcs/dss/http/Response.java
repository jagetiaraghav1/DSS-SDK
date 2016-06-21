package org.jcs.dss.http;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
///Stores the response returned from JCS DSS Server
public class Response {

	private int statusCode;
	private String statusMsg;
	private Map<String, List<String>> headers;
	private InputStream data;
	///Returns Status Code
	/**
	 * 
	 * @return statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	///Sets Status Code
	/**
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	///Return Response Message
	/**
	 * 
	 * @return statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}
	///Sets Response Message
	/**
	 * 
	 * @param statusMsg
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	/// Returns List of Headers
	/**
	 *  
	 * @return  Map<String, List<String>> :  Headers List
	 */
	public Map<String, List<String>> getHeaders() {
		return headers;
	}
	/// Returns List of Headers
	/**
	 * 
	 * @param Map<String, List<String>>  : Headers List
	 */
	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}
	/// Returns InputSteam
	/**
	 * 
	 * @return InputSteam
	 */
	public InputStream getData() {
		return data;
	}
	///Sets InputStream
	/**
	 * 
	 * @param InputStream
	 */
	public void setData(InputStream data) {
		this.data = data;
	}

}

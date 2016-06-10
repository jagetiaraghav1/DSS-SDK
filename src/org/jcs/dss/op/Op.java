package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public abstract class Op {

	protected DssConnection conn;
	protected String httpMethod;
	protected Map<String, String> httpHeaders;
	protected String opPath;
	protected String queryStr;
	protected String queryStrForSignature;
	protected String filePath;
	private static String JcsCopySource;
	protected int expiryTime;
	protected String uploadId;
	protected String partNumber;
	protected String multipartUpload;

	public  Op(DssConnection conn) {
		this.conn = conn;
		httpHeaders = new HashMap<String, String>();
		httpHeaders.put("Authorization", "");
		httpHeaders.put("Date", "");
		queryStr = "";
		opPath = "";
	}

	public abstract Object processResult(Object result);

	public Response makeRequest() throws Exception {
		String date = Utils.getCurTimeInGMTString();
		DssAuth authentication = new DssAuthBuilder()
				.httpMethod(httpMethod)
				.accessKey(conn.getAccessKey())
				.secretKey(conn.getSecretKey())
				.path(opPath)
				.dateStr(date)
				.queryStr(queryStr)
				.build();
		String signature = authentication.getSignature();
		//System.out.println(signature);
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		String request_url = conn.getHost() + opPath;
		//System.out.println(request_url);
		if(queryStr != ""){
			request_url += '?' + queryStr;  
		}
		Response resp =  Request.request(httpMethod,request_url,httpHeaders);


		return resp;
	}

	public static String getJcsCopySource() {
		return JcsCopySource;
	}

	public void setJcsCopySource(String jcsCopySource) {
		JcsCopySource = jcsCopySource;
	}



}

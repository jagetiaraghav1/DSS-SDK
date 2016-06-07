package org.jcs.dss.op;

import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;


public class GetPresignedURLOp extends ObjectOp {
	private int expiry;

	public GetPresignedURLOp(DssConnection conn, String bucketName, String objectName,int Expiry) {
		super(conn, bucketName, objectName);
		// TODO Auto-generated constructor stub
		httpMethod = "GET";
		expiry =Expiry;
		
		
	}
	
	public void Execute() throws Exception {
		expiryTime = (int) (System.currentTimeMillis()/1000)+expiry;
		

		 MakeRequest();
	}
	public void MakeRequest() throws Exception {
		String date = Utils.getCurTimeInGMTString();
		DssAuth authentication = new DssAuthBuilder()
									.httpMethod(httpMethod)
									.accessKey(conn.getAccessKey())
									.secretKey(conn.getSecretKey())
									.path(opPath)
									.dateStr(date)
									.expiryTime(expiryTime)
									.build();
		String signature = authentication.getSignature();	
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		String request_url = conn.getHost() + opPath;
        request_url = request_url + "?JCSAccessKeyId="+ conn.getAccessKey() + "&Expires=" + Integer.toString(expiryTime) + "&Signature=" + signature;
        System.out.println("ok!!!");
		//Response resp =  Request.request(httpMethod,request_url,httpHeaders);

		//return resp;
	}

	
	

}

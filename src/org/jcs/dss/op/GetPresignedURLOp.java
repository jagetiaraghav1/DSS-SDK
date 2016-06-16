package org.jcs.dss.op;

import java.net.URL;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;


public class GetPresignedURLOp extends ObjectOp {
	private int expiry;
	private int expiryTime;

	public GetPresignedURLOp(DssConnection conn, String bucketName, String objectName,int Expiry) {
		super(conn, bucketName, objectName);
		httpMethod = "GET";
		expiry =Expiry;
	}

	public URL Execute() throws Exception {
		expiryTime = (int) (System.currentTimeMillis()/1000)+expiry;
		URL url = MakeRequest();
		return url;
	}
	public URL MakeRequest() throws Exception {
		String date = Utils.getCurTimeInGMTString();
		DssAuth authentication = new DssAuthBuilder()
				.httpMethod(httpMethod)
				.accessKey(conn.getAccessKey())
				.secretKey(conn.getSecretKey())
				.path(opPath)
				.dateStr(date)
				.expiryTime(expiryTime)
				.useTimeInSeconds(true)
				.build();
		String signature = authentication.getSignature();	
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		String request_url = conn.getHost() + opPath;
		request_url = request_url + "?AWSAccessKeyId="+ conn.getAccessKey() + "&Expires=" + Integer.toString(expiryTime) + "&Signature=" + signature;
		URL url = new URL(request_url);
		return url;
	}
}

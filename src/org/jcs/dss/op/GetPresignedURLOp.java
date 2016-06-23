package org.jcs.dss.op;

import java.net.URL;
import java.net.URLEncoder;

import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

///Class to get presigned url
public class GetPresignedURLOp extends ObjectOp {
	private int expiry;
	private int expiryTime;
	///Constructors
	public GetPresignedURLOp(DssConnection conn, String bucketName, String objectName,int Expiry) {
		super(conn, bucketName, objectName);
		httpMethod = "GET";
		expiry =Expiry;
	}
	////// Defines expiry time and gets presigned URL
	/**
	 * 
	 * @return URL : Presigned URL
	 * @throws Exception
	 */
	public URL Execute() throws Exception {
		expiryTime = (int) (System.currentTimeMillis()/1000)+expiry;
		URL url = MakeRequest();
		return url;
	}
	///This method first gets signature, sets httpHeaders and returns pre-signed URL
	/**
	 * @return URL : Presigned URL
	 */
	public URL MakeRequest() throws Exception {
		String date = Utils.getCurTimeInGMTString();
		///Creating object of DssAuth to get signature
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
		System.out.println(signature);
		//Assigning headers
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		String path = Utils.getEncodedURL(opPath);
		String request_url = conn.getHost() + path;
		String EncodedSignature = URLEncoder.encode(signature, "UTF-8");
		//Generating Presigned URL
		request_url = request_url + "?AWSAccessKeyId="+ conn.getAccessKey() + "&Expires=" + Integer.toString(expiryTime) + "&Signature=" + EncodedSignature;
		URL url = new URL(request_url);
		return url;
	}
}

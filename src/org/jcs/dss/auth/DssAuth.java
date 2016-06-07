package org.jcs.dss.auth;
import java.io.UnsupportedEncodingException;
import Decoder.BASE64Encoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DssAuth {

	private String httpMethod;
	private String accessKey;
	private String secretKey;
	private String path;
	private String queryStr;
	private String contentType;
	private boolean useTimeInSeconds;
	private String dateStr;
	private int expiryTime;
	private String version = "V2";

	public DssAuth(String httpMethod, String accessKey, String secretKey,
			String path, String queryStr, String contentType, String dateStr,
			boolean useTimeInSeconds, int expiryTime) {

		this.httpMethod = httpMethod;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.path = path;
		this.queryStr = queryStr;
		this.contentType = contentType;
		this.dateStr = dateStr;
		this.useTimeInSeconds = useTimeInSeconds;
		this.expiryTime = expiryTime;
	}

	public String getCannonicalStr() {
		
		//return "GET\n\n\nMon, 30 May 2016 14:15:19 GMT\n/";
		
		String cannonicalStr = "";
		String md5Checksum = "";
		//String dateStr = Utils.getCurTimeInGMTString();

		if (useTimeInSeconds) {
			dateStr = Integer.toString(expiryTime);
		}
		path = this.getPathForCannonicalString();

		cannonicalStr += httpMethod;
		cannonicalStr += "\n" + md5Checksum;

		cannonicalStr += "\n" + contentType;

		cannonicalStr += "\n" + dateStr;
		cannonicalStr += "\n" + path;
		return cannonicalStr;
		

	}

	public String getSignature() throws UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException {
		
		String secret = secretKey;
		byte[] secretBytes = secret.getBytes("UTF-8");
		String cannonicalStr = getCannonicalStr();
	//	System.out.println(cannonicalStr);
	
		byte[] cannonicalStrBytes = cannonicalStr.getBytes("UTF-8");
		SecretKeySpec keySpec = new SecretKeySpec(secretBytes, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(keySpec);
		byte[] result = mac.doFinal(cannonicalStrBytes);
		BASE64Encoder encoder = new BASE64Encoder();
		String b64_hmac = (encoder.encode(result));
		String auth = "";

		if (useTimeInSeconds) {
			auth = b64_hmac;
		} else {
			auth = ("AWS " + accessKey + ":" + b64_hmac);
		}
		//System.out.println(auth);
		return auth;
	}

	
	public String getPathForCannonicalString() {

		if (queryStr != "") {
			path += '?' + queryStr;
		}
		return path;
	}

}

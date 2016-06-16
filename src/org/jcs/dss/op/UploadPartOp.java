package org.jcs.dss.op;

import java.io.InputStream;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public class UploadPartOp extends ObjectOp{

	private InputStream inStream = null;

	public UploadPartOp(DssConnection conn, String bucketName, String objectName,String uploadId,String partNumber, InputStream inStream) {
		super(conn, bucketName, objectName);
		httpMethod ="PUT";
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "partNumber="+ partNumber+"&uploadId="+ uploadId;
		queryStrForSignature = "partNumber="+ partNumber+"&uploadId="+ uploadId;
		this.inStream = inStream;
	}


	@Override
	public Response execute() throws Exception {
		return makeRequest();
	}

	@Override
	public Response makeRequest() throws Exception {
		String date = Utils.getCurTimeInGMTString();
		DssAuth authentication = new DssAuthBuilder()
				.httpMethod(httpMethod)
				.accessKey(conn.getAccessKey())
				.secretKey(conn.getSecretKey())
				.path(opPath)
				.dateStr(date)
				.contentType("application/octet-stream")
				.queryStr(queryStr)
				.build();
		String signature = authentication.getSignature();
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		httpHeaders.put("Content-Length", Integer.toString(inStream.available()));
		httpHeaders.put("Content-Type", "application/octet-stream");
		String request_url = conn.getHost() + opPath;
		if(queryStr != ""){
			request_url += '?' + queryStr;  
		}
		Response resp =  Request.Put(httpMethod,request_url,httpHeaders,inStream);
		return resp;
	}
}

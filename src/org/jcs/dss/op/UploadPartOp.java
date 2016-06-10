package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public class UploadPartOp extends ObjectOp{

	
	public UploadPartOp(DssConnection conn, String bucketName, String objectName,String uploadId,String partNumber, String filePath) {
		super(conn, bucketName, objectName);
		httpMethod ="PUT";
		this.partNumber=partNumber;
		this.uploadId=uploadId;
		this.filePath=filePath;
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "partNumber="+ partNumber+"&uploadId="+ uploadId;
		queryStrForSignature = "partNumber="+ partNumber+"&uploadId="+ uploadId;
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
		InputStream object = new FileInputStream(filePath);

		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		httpHeaders.put("Content-Length", Integer.toString(object.available()));
		httpHeaders.put("Content-Type", "application/octet-stream");

		String request_url = conn.getHost() + opPath;
		if(queryStr != ""){
			request_url += '?' + queryStr;  
		}
		Response resp =  Request.Put(request_url,httpHeaders,object);

		return resp;
	}

}

package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public class CompleteMPUploadOp extends ObjectOp{

	public CompleteMPUploadOp(DssConnection conn, String bucketName, String objectName,String uploadId,String multipartUpload) {
		super(conn, bucketName, objectName);
		// TODO Auto-generated constructor stub
		httpMethod ="POST";
		this.uploadId=uploadId;
		this.multipartUpload = multipartUpload;
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "&uploadId="+ uploadId;
		queryStrForSignature = "&uploadId="+ uploadId;
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
				.contentType("text/xml")
				.queryStr(queryStr)
				.build();
		String signature = authentication.getSignature();
		InputStream object = new FileInputStream(multipartUpload);

		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		httpHeaders.put("Content-Length", Integer.toString(object.available()));
		httpHeaders.put("Content-Type", "text/xml");

		String request_url = conn.getHost() + opPath;
		if(queryStr != ""){
			request_url += '?' + queryStr;  
		}
		Response resp =  Request.Put(request_url,httpHeaders,object);

		return resp;
	}

}

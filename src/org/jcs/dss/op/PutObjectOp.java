package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.PutObjectResult;
import org.jcs.dss.utils.Utils;

public class PutObjectOp extends ObjectOp{
	
	private String filePath;
	public PutObjectOp(DssConnection conn,String bucketName, String objectName,String filepath) throws FileNotFoundException {
		super(conn,bucketName,objectName);
		filePath= filepath;
		httpMethod="PUT";
		opPath = '/' + bucketName + '/' + objectName;
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
		Response resp =  Request.Put(httpMethod,request_url,httpHeaders,object);

		return resp;
	}

	@Override
	public Object processResult(Object resp){

		String ETag = null;
		String Date = null;
		for (Map.Entry<String, List<String>> headers : ((Response) resp).getHeaders().entrySet()) {
			String key = new String();
			if(headers.getKey()!=null)
				key = headers.getKey();
			List<String> valueList = headers.getValue();
			if(key.contentEquals("ETag") )
			{
				ETag=valueList.get(0).substring(1, valueList.get(0).length()-1);

			}
			else if(key.contentEquals("Date")){
				Date = valueList.get(0);
			}
		}
		PutObjectResult putObject = new PutObjectResult(ETag,Date);

		return putObject;
	}
}

package org.jcs.dss.op;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

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
		queryStr = "uploadId="+ uploadId;
		queryStrForSignature = "uploadId="+ uploadId;
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
		InputStream object = new ByteArrayInputStream(multipartUpload.getBytes(StandardCharsets.UTF_8));
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		httpHeaders.put("Content-Length", Integer.toString(object.available()));
		httpHeaders.put("Content-Type", "text/xml");

		String request_url = conn.getHost() + opPath;
		if(queryStr != ""){
			request_url += '?' + queryStr;  
		}
		Response resp =  Put(request_url,httpHeaders,object);

		return resp;
	}
	
	
	public static Response Put(String Url,Map<String, String> HttpHeader,InputStream Data) throws InvalidKeyException, NoSuchAlgorithmException, IOException{

		URL RequestUrl = new URL(Url);
		HttpURLConnection Connection = (HttpURLConnection)RequestUrl.openConnection();
		Connection.setDoOutput(true);
			Connection.setRequestMethod("POST");
		for(Entry<String, String> entry : HttpHeader.entrySet()) {
			Connection.setRequestProperty(entry.getKey(), entry.getValue());
		}

		Connection.connect();
		DataOutputStream out = new DataOutputStream(Connection.getOutputStream());

		byte[] buffer = new byte[4096];
		int bytesRead=Data.read(buffer);
		while ( bytesRead != -1) {
			//String str = new String(buffer, "UTF-8");
			//System.out.println(str);
			out.write(buffer,0,bytesRead);
			bytesRead=Data.read(buffer);
		}


		Data.close();
		out.close();
		

		Response response = new Response();
		response.setStatusCode(Connection.getResponseCode());
		response.setStatusMsg(Connection.getResponseMessage());
		response.setHeaders(Connection.getHeaderFields());
		
		response.setData(Connection.getInputStream());
		Connection.disconnect();
		return response;

	}

}

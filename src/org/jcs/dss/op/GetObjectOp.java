package org.jcs.dss.op;

import java.io.*;
import java.net.HttpURLConnection;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public class GetObjectOp extends ObjectOp {
	private String filePath;

	public GetObjectOp(DssConnection conn, String bucketName, String objectName,String filepath) {
		super(conn, bucketName, objectName);
		filePath= filepath;
		httpMethod="GET";
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
				.build();
		String signature = authentication.getSignature();

		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		String request_url = conn.getHost() + opPath;
		Response resp = Request.request("GET", request_url,httpHeaders);
		return resp;
	}

	@Override
	public Object processResult(Object resp) throws IOException{
		int responseCode = ((Response) resp).getStatusCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream inputStream = ((Response) resp).getData();
			String saveFilePath = filePath;
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
			String message = "File Downloaded";
			return message;
		} else {
			String errormessage = "No file to download. Server replied HTTP code: " + responseCode;
			return errormessage;
		}
	}
}

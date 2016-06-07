package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class GetObjectOp extends ObjectOp {

	public GetObjectOp(DssConnection conn, String bucketName, String objectName,String filepath) {
		super(conn, bucketName, objectName);

		filePath= filepath;
		httpMethod="GET";
		opPath = '/' + bucketName + '/' + objectName;
		// TODO Auto-generated constructor stub
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
		Response resp =  Get(request_url,httpHeaders,filePath);

		return resp;
	}

	public static Response Get(String Url,Map<String, String> HttpHeader,String Path) throws InvalidKeyException, NoSuchAlgorithmException, IOException{

		URL RequestUrl = new URL(Url);
		HttpURLConnection Connection = (HttpURLConnection)RequestUrl.openConnection();
		Connection.setRequestMethod("GET");
		
		for(Entry<String, String> entry : HttpHeader.entrySet()) {
			Connection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		
		Connection.connect();
		int responseCode = Connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {

			InputStream inputStream = Connection.getInputStream();
			String saveFilePath = Path;
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		Response response = new Response();
		response.setStatusCode(responseCode);
		response.setStatusMsg(Connection.getResponseMessage());
		response.setHeaders(Connection.getHeaderFields());
		response.setData(Connection.getInputStream());
		Connection.disconnect();
		return response;
	}


}

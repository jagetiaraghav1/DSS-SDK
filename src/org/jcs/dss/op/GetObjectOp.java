package org.jcs.dss.op;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;
/// Class to download object file from request key to desired path
public class GetObjectOp extends ObjectOp {
	private String filePath;
	///Constructors
	public GetObjectOp(DssConnection conn, String bucketName, String objectName,String filepath) {
		super(conn, bucketName, objectName);
		filePath= filepath;
		httpMethod="GET";
		opPath = '/' + bucketName + '/' + objectName;
	}
	/// Executes the method requested by user
	/**
	 * 
	 * @return Response : Gets response object returned from makeRequest()
	 * @throws Exception
	 */
	
	public String Execute() throws Exception {
		return MakeRequest();
	}
	///This method first gets signature, sets httpHeaders and then gets Response object
	/**
	 * @return Response : response object by calling request method under Request class
	 * @throws Exception
	 */
	
	public String MakeRequest() throws Exception {
		String date = Utils.getCurTimeInGMTString();
		///Creating object of DssAuth to get signature
		DssAuth authentication = new DssAuthBuilder()
				.httpMethod(httpMethod)
				.accessKey(conn.getAccessKey())
				.secretKey(conn.getSecretKey())
				.path(opPath)
				.dateStr(date)
				.build();
		String signature = authentication.getSignature();
		//Assigning headers
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		String path = Utils.getEncodedURL(opPath);
		String request_url = conn.getHost() + path;
		//Calling Request.request method to get inputStream
		//Response resp = Request.request("GET", request_url,httpHeaders);
		
		return request_url;
	}

	@Override
	///Process final step to download file to desired path
	/**
	 * @param Response : Response message got from Request.request()
	 * @return String : Message for successful or unsuccessful file download
	 * @throws IOException
	 */
	public Object processResult(Object resp) throws IOException{
		
		URL requestUrl = new URL((String) resp);
		HttpURLConnection Connection = (HttpURLConnection) requestUrl.openConnection();
		Connection.setDoOutput(true);
		Connection.setDoInput(true);
		//Setting HTTP Method
		Connection.setRequestMethod("GET");
		// Setting request headers
		for(Entry<String, String> entry : httpHeaders.entrySet()) {
			Connection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		//Checks if server has returned message OK 
			InputStream inputStream = Connection.getInputStream();
			String saveFilePath = filePath;
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			int bytesRead = -1;
			//Reads 4096 bytes at a time till the end of file and writes it using FileOutputStream.write() to the desired location
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
			Connection.disconnect();
			//Success message
			String message = "File Downloaded";
			return message;
	}
}

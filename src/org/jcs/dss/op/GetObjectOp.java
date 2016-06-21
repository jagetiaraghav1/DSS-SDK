package org.jcs.dss.op;

import java.io.*;
import java.net.HttpURLConnection;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
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
	@Override
	public Response execute() throws Exception {
		return makeRequest();
	}
	///This method first gets signature, sets httpHeaders and then gets Response object
	/**
	 * @return Response : response object by calling request method under Request class
	 * @throws Exception
	 */
	@Override
	public Response makeRequest() throws Exception {
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
		Response resp = Request.request("GET", request_url,httpHeaders);
		return resp;
	}

	@Override
	///Process final step to download file to desired path
	/**
	 * @param Response : Response message got from Request.request()
	 * @return String : Message for successful or unsuccessful file download
	 * @throws IOException
	 */
	public Object processResult(Object resp) throws IOException{
		int responseCode = ((Response) resp).getStatusCode();
		//Checks if server has returned message OK 
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream inputStream = ((Response) resp).getData();
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
			//Success message
			String message = "File Downloaded";
			return message;
		} else {
			//Failure message
			String errormessage = "No file to download. Server replied HTTP code: " + responseCode;
			return errormessage;
		}
	}
}

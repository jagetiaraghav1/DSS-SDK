package org.jcs.dss.http;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Request {

	public static Response request(String method, String url, Map<String, String> headers) throws Exception {

		URL requestUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod(method);

		for(Entry<String, String> entry : headers.entrySet()) {
			connection.setRequestProperty(entry.getKey(), entry.getValue());
			//System.out.println(entry.getKey()+"   "+entry.getValue());
		}
				connection.connect();

		Response resp = new Response();

		resp.setStatusCode(connection.getResponseCode());
	//	System.out.println(resp.getStatusCode());
		resp.setStatusMsg(connection.getResponseMessage());
//		System.out.println(resp.getStatusMsg());

		resp.setHeaders(connection.getHeaderFields());
		/*for (Map.Entry<String, List<String>> me : resp.getHeaders().entrySet()) {
			  String key = me.getKey();
			  List<String> valueList = me.getValue();
			  System.out.println("Key: " + key);
			  System.out.print("Values: ");
			  for (String s : valueList) {
			    System.out.print(s + " ");
			  }}*/
		resp.setData(connection.getInputStream());

		return resp;

	}


	public static Response Put(String Url,Map<String, String> HttpHeader,InputStream Data) throws InvalidKeyException, NoSuchAlgorithmException, IOException{

		URL RequestUrl = new URL(Url);
		HttpURLConnection Connection = (HttpURLConnection)RequestUrl.openConnection();
		Connection.setDoOutput(true);
			Connection.setRequestMethod("PUT");
		for(Entry<String, String> entry : HttpHeader.entrySet()) {
			Connection.setRequestProperty(entry.getKey(), entry.getValue());
		}

		Connection.connect();
		DataOutputStream out = new DataOutputStream(Connection.getOutputStream());

		byte[] buffer = new byte[4096];
		int bytesRead=Data.read(buffer);

		while ( bytesRead != -1) {
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



package org.jcs.dss.http;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;		
import javax.net.ssl.*;
import org.jcs.dss.main.Config;
import org.jcs.dss.main.DssConnection;

/// Class to make connection to JCS DSS Server
public class Request {
	private static final Logger logger= Logger.getLogger( DssConnection.class.getName() );
	///To create connection and to store response in resp object of Response class
	/**
	 * @param method : HTTP method
	 * @param url : Url of the server
	 * @param headers 
	 * @return resp : An object of Response class
	 * @throws Exception
	 */
	public static Response request(String method, String url, Map<String, String> headers) throws Exception {
		// Create a trust manager that does not validate certificate chains
		if(!Config.isSecure())
		{
			TrustManager[] trustAllCerts = new TrustManager[] 
					{	new X509TrustManager() 
					{
						public java.security.cert.X509Certificate[] getAcceptedIssuers() 
						{
							return new X509Certificate[0];
						}
						public void checkClientTrusted(X509Certificate[] certs, String authType) 
						{
						}
						public void checkServerTrusted(X509Certificate[] certs, String authType) 
						{
						}
					}
					};

			// Install the all-trusting trust manager
			SSLContext sc;
			try 
			{
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} 
			catch (NoSuchAlgorithmException e) 
			{
				e.printStackTrace();
			} 
			catch (KeyManagementException e) {
				e.printStackTrace();
			}
		}
		URL requestUrl = new URL(url);

		logger.info("URL : " + requestUrl);
		HttpURLConnection Connection = (HttpURLConnection) requestUrl.openConnection();
		Connection.setDoOutput(true);
		Connection.setDoInput(true);
		//Setting HTTP Method
		Connection.setRequestMethod(method);
		logger.info("HTTP Method : " + method);
		// Setting request headers
		for(Entry<String, String> entry : headers.entrySet()) {
			Connection.setRequestProperty(entry.getKey(), entry.getValue());
			logger.info("Headers(Given to server) : "+ entry.getKey() + " : " + entry.getValue());
		}
		//Creating connection
		//Connection.connect();
		Response resp = new Response ();
		//If Operation succeed 
		if (Connection.getResponseCode() == 200 || Connection.getResponseCode() == 204) {
			resp.setStatusCode(Connection.getResponseCode());
			resp.setStatusMsg(Connection.getResponseMessage());
			resp.setHeaders(Connection.getHeaderFields());
			for (Map.Entry<String, List<String>> header : ((Response) resp).getHeaders().entrySet()) {
				String key = new String();
				if(header.getKey()!=null)
					key = header.getKey();
				else
					key = "null";
				List<String> valueList = header.getValue();
				for(int j=0;j<valueList.size();j++){
					logger.info("Headers (Recieved from Server) : "+ key +" : " + valueList.get(j) );
				}
			}
			resp.setData(Connection.getInputStream());
			BufferedReader input = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
			String XML= input.readLine();
			resp.setXMLString(XML);
		}
		//If operation fails throw ErrorException 
		else {
			throw (new ErrorResponse(Connection.getResponseCode(),Connection.getResponseMessage(),Connection.getErrorStream()));
		}
		Connection.disconnect();
		return resp;

	}

	///To create connection, to upload object to the server and to store response in resp object of Response class
	/**
	 * 
	 * @param httpMethod
	 * @param Url : Url of the server
	 * @param HttpHeader
	 * @param Data : InputStream of the file to be upload
	 * @return : resp : An object of Response class
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws ErrorResponse
	 */
	public static Response Put(String httpMethod,String Url,Map<String, String> HttpHeader,InputStream Data) throws InvalidKeyException, NoSuchAlgorithmException, IOException, ErrorResponse{

		// Create a trust manager that does not validate certificate chains
		if(!Config.isSecure())
		{
			TrustManager[] trustAllCerts = new TrustManager[] 
					{	new X509TrustManager() 
					{
						public java.security.cert.X509Certificate[] getAcceptedIssuers() 
						{
							return new X509Certificate[0];
						}
						public void checkClientTrusted(X509Certificate[] certs, String authType) 
						{
						}
						public void checkServerTrusted(X509Certificate[] certs, String authType) 
						{
						}
					}
					};

			// Install the all-trusting trust manager
			SSLContext sc;
			try 
			{
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} 
			catch (NoSuchAlgorithmException e) 
			{
				e.printStackTrace();
			} 
			catch (KeyManagementException e) {
				e.printStackTrace();
			}
		}
		URL RequestUrl = new URL(Url);
		logger.info("URL : " + RequestUrl);
		HttpURLConnection Connection = (HttpURLConnection)RequestUrl.openConnection();
		Connection.setDoOutput(true);
		//Setting HTTP Method
		Connection.setRequestMethod(httpMethod);
		logger.info("HTTP Method : " + httpMethod);
		// Setting request headers
		for(Entry<String, String> entry : HttpHeader.entrySet()) {
			Connection.setRequestProperty(entry.getKey(), entry.getValue());
			logger.info("Headers(Given to server) : "+ entry.getKey() + " : " + entry.getValue());
		}
		//Creating connection
		//Connection.connect();
		//Uploading Data in smaller parts of 4096 byte by reading data in buffer and then writing till the end of file
		DataOutputStream out = new DataOutputStream(Connection.getOutputStream());
		byte[] buffer = new byte[4096];
		int bytesRead=Data.read(buffer);
		while ( bytesRead != -1) {
			out.write(buffer,0,bytesRead);
			bytesRead=Data.read(buffer);
		}
		Data.close();
		out.close();
		Response resp = new Response();
		//If Operation succeed 
		if (Connection.getResponseCode() == 200 || Connection.getResponseCode() == 204) {
			resp.setStatusCode(Connection.getResponseCode());
			resp.setStatusMsg(Connection.getResponseMessage());
			resp.setHeaders(Connection.getHeaderFields());
			for (Map.Entry<String, List<String>> header : ((Response) resp).getHeaders().entrySet()) {
				String key = new String();
				if(header.getKey()!=null)
					key = header.getKey();
				else
					key = "null";
				List<String> valueList = header.getValue();
				for(int j=0;j<valueList.size();j++){
					logger.info("Headers (Recieved from Server) : "+ key +" : " + valueList.get(j) );
				}
			}
			resp.setData(Connection.getInputStream());
			BufferedReader input = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
			String XML= input.readLine();
			resp.setXMLString(XML);
		} 
		//If operation fails throw ErrorException 
		else {
			throw (new ErrorResponse(Connection.getResponseCode(),Connection.getResponseMessage(),Connection.getErrorStream()));
		}
		Connection.disconnect();
		return resp;
	}
}



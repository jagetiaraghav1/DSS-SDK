package org.jcs.dss.op;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.CompleteMultipartUploadResult;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
///Class to execute Complete multipart upload
public class CompleteMPUploadOp extends ObjectOp{

	private String multipartUpload;
	///Constructors
	public CompleteMPUploadOp(DssConnection conn, String bucketName, String objectName,String uploadId,String multipartUpload) {
		super(conn, bucketName, objectName);
		httpMethod ="POST";
		this.multipartUpload = multipartUpload;
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "uploadId="+ uploadId;
		queryStrForSignature = "uploadId="+ uploadId;
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
	 * @return Response : response object by calling put method under request class
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
				.contentType("text/xml")
				.queryStr(queryStr)
				.build();
		String signature = authentication.getSignature();
		// Creating inputstream of Xml string, named by multipartUpload
		InputStream object = new ByteArrayInputStream(multipartUpload.getBytes(StandardCharsets.UTF_8));
		//Assigning headers
		httpHeaders.put("Authorization", signature);
		httpHeaders.put("Date", date);
		httpHeaders.put("Content-Length", Integer.toString(object.available()));
		httpHeaders.put("Content-Type", "text/xml");
		String path = Utils.getEncodedURL(opPath);
		String request_url = conn.getHost() + path;
		if(queryStr != ""){
			request_url += '?' + queryStr;  
		}
		//Calling Request.put to complete multipart upload
		Response resp =  Request.Put(httpMethod,request_url,httpHeaders,object);
		return resp;
	}

	@Override
	///This method extracts information such as key, ETag etc from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.put()
	 * @return CompleteMultipartUploadResult : A class that contains all the information about a successful uploaded object
	 * @throws IOException
	 */
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		//Creating an object for class CompleteMultipartUploadResult
		CompleteMultipartUploadResult completeMPup = new  CompleteMultipartUploadResult(null,null, null);
		//Parsing XML using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			//Type casting a string to a Document
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CompleteMultipartUploadResult");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				// Extracting parameters from XML String and putting in completeMPup object
				completeMPup = new CompleteMultipartUploadResult(eElement.getElementsByTagName("Bucket").item(0).getTextContent(),
						eElement.getElementsByTagName("Key").item(0).getTextContent(),
						eElement.getElementsByTagName("ETag").item(0).getTextContent());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return completeMPup;
	}
}

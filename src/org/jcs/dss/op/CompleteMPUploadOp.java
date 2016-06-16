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

public class CompleteMPUploadOp extends ObjectOp{

	private String multipartUpload;

	public CompleteMPUploadOp(DssConnection conn, String bucketName, String objectName,String uploadId,String multipartUpload) {
		super(conn, bucketName, objectName);
		httpMethod ="POST";
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
		Response resp =  Request.Put(httpMethod,request_url,httpHeaders,object);
		return resp;
	}

	@Override
	public Object processResult(Object resp) throws IOException{
		InputStream data = ((Response) resp).getData();
		BufferedReader input = new BufferedReader(new InputStreamReader(data));
		String XML= input.readLine();
		CompleteMultipartUploadResult completeMPup = new  CompleteMultipartUploadResult(null,null, null);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CompleteMultipartUploadResult");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
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

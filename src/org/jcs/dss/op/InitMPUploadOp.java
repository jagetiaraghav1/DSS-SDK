package org.jcs.dss.op;

import java.io.*;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.InitiateMultipartUploadResult;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
/// Class to Initiate MultiPart Upload 
public class InitMPUploadOp extends ObjectOp{
	///Constructors
	public InitMPUploadOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod = "POST";
		queryStr = "uploads";
		queryStrForSignature="uploads";
	}

	///This method extracts information such as key, UploadId etc from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.put()
	 * @return InitiateMultipartUploadResult : A class that contains all the information about a initiated MultiPart upload
	 * @throws IOException
	 */

	@Override
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		//Creating an object for class InitiateMultipartUploadResult
		InitiateMultipartUploadResult imitMPupload = new  InitiateMultipartUploadResult(null,null, null);
		//Parsing XML using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Type casting a string to a Document
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("InitiateMultipartUploadResult");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				// Extracting parameters from XML String and putting in imitMPupload object
				imitMPupload = new InitiateMultipartUploadResult(eElement.getElementsByTagName("Bucket").item(0).getTextContent(),
						eElement.getElementsByTagName("Key").item(0).getTextContent(),
						eElement.getElementsByTagName("UploadId").item(0).getTextContent());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return imitMPupload;
	}
}

package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
///Class to list all the on-going MultiPart uploads associated with the requested bucket
public class ListMPUploadsOp extends BucketOp {
	///Constructors
	public ListMPUploadsOp(DssConnection conn, String bucketName) throws UnsupportedEncodingException {
		super(conn, bucketName);
		httpMethod = "GET";
		queryStr = "uploads";
		queryStrForSignature="uploads";
	}
	///This method extracts information such as key, ETag etc from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.request()
	 * @return CompleteMultipartUploadResult : A class that contains all the information about a successful uploaded object
	 * @throws IOException
	 */
	@Override
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		//Creating an object for class MultipartUploadListing
		MultipartUploadListing multipartUploadList = new MultipartUploadListing(null,null,null,null,null);
		//Creating an List object for class MultipartUpload 
		List<MultipartUpload> multipartUpload = new ArrayList<MultipartUpload>();
		//Parsing XML using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String initiator=null;
		String owner=null;

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Type casting a string to a Document
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList List = doc.getElementsByTagName("Initiator");
			Node Node = List.item(0);
			if (Node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element = (Element) Node;
				// Extracting Initiator's ID from XML String and storing in local variable
				initiator = Element.getElementsByTagName("ID").item(0).getTextContent();
			}

			NodeList List1 = doc.getElementsByTagName("Owner");
			Node Node1 = List1.item(0);
			if (Node1.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element1 = (Element) Node1;
				// Extracting owner's ID from XML String and storing in local variable
				owner = Element1.getElementsByTagName("ID").item(0).getTextContent();
			}
			NodeList nList = doc.getElementsByTagName("Upload");
			// Running loop to get complete list of all multipart uploads
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// Extracting parameters from XML String and putting in multipartUp object
					MultipartUpload	multipartUp = new MultipartUpload(eElement.getElementsByTagName("Key").item(0).getTextContent(),
							eElement.getElementsByTagName("UploadId").item(0).getTextContent(),
							initiator,
							owner,
							eElement.getElementsByTagName("StorageClass").item(0).getTextContent(),
							eElement.getElementsByTagName("Initiated").item(0).getTextContent());
					//Adding information about each on-going multipart upload to multipartUpload object to make a list
					multipartUpload.add(multipartUp);
				}
			}
			NodeList List2 = doc.getElementsByTagName("ListMultipartUploadsResult");
			Node Node2 = List2.item(0);
			if (Node2.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element2 = (Element) Node2;
				// Extracting common parameters from XML String and putting in multipartUploadList object 
				multipartUploadList = new MultipartUploadListing(Element2.getElementsByTagName("Bucket").item(0).getTextContent(),
						Element2.getElementsByTagName("NextKeyMarker").item(0).getTextContent(),
						Element2.getElementsByTagName("NextUploadIdMarker").item(0).getTextContent(),
						Element2.getElementsByTagName("MaxUploads").item(0).getTextContent(),
						multipartUpload);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return multipartUploadList;
	}
}

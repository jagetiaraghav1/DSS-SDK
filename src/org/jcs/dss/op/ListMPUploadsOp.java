package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class ListMPUploadsOp extends BucketOp {

	public ListMPUploadsOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod = "GET";
		queryStr = "uploads";
		queryStrForSignature="uploads";
	}

	@Override
	public Object processResult(Object resp) throws IOException{
		InputStream data = ((Response) resp).getData();
		BufferedReader input = new BufferedReader(new InputStreamReader(data));
		String XML= input.readLine();
		MultipartUploadListing multipartUploadList = new MultipartUploadListing(null,null,null,null,null);
		List<MultipartUpload> multipartUpload = new ArrayList<MultipartUpload>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String initiator=null;
		String owner=null;

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList List = doc.getElementsByTagName("Initiator");
			Node Node = List.item(0);
			if (Node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element = (Element) Node;
				initiator = Element.getElementsByTagName("ID").item(0).getTextContent();
			}

			NodeList List1 = doc.getElementsByTagName("Owner");
			Node Node1 = List1.item(0);
			if (Node1.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element1 = (Element) Node1;
				owner = Element1.getElementsByTagName("ID").item(0).getTextContent();
			}
			NodeList nList = doc.getElementsByTagName("Upload");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					MultipartUpload	multipartUp = new MultipartUpload(eElement.getElementsByTagName("Key").item(0).getTextContent(),
							eElement.getElementsByTagName("UploadId").item(0).getTextContent(),
							initiator,
							owner,
							eElement.getElementsByTagName("StorageClass").item(0).getTextContent(),
							eElement.getElementsByTagName("Initiated").item(0).getTextContent());
					multipartUpload.add(multipartUp);
				}
			}
			NodeList List2 = doc.getElementsByTagName("ListMultipartUploadsResult");
			Node Node2 = List2.item(0);
			if (Node2.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element2 = (Element) Node2;
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

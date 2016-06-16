package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class ListPartOp extends ObjectOp{
	public ListPartOp(DssConnection conn, String bucketName, String objectName,String uploadId) {
		super(conn, bucketName, objectName);
		httpMethod ="GET";
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "uploadId="+uploadId;
		queryStrForSignature = "uploadId="+uploadId;
	}

	@Override
	public Object processResult(Object resp) throws IOException{
		InputStream data = ((Response) resp).getData();
		BufferedReader input = new BufferedReader(new InputStreamReader(data));
		String xml= input.readLine();
		PartListing partList = new PartListing(null,null,null,null,null,null,null,null,null);
		List<PartSummary> Partdetails = new ArrayList<PartSummary>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String owner=null;

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(xml)));
			doc.getDocumentElement().normalize();

			NodeList List1 = doc.getElementsByTagName("Owner");
			Node Node1 = List1.item(0);
			if (Node1.getNodeType() == Node.ELEMENT_NODE) {
				Element Element1 = (Element) Node1;
				owner = Element1.getElementsByTagName("ID").item(0).getTextContent();
			}

			NodeList nList = doc.getElementsByTagName("Part");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					PartSummary	partSummary = new PartSummary(eElement.getElementsByTagName("LastModified").item(0).getTextContent(),
							eElement.getElementsByTagName("PartNumber").item(0).getTextContent(),
							eElement.getElementsByTagName("ETag").item(0).getTextContent(),
							eElement.getElementsByTagName("Size").item(0).getTextContent());
					Partdetails.add(partSummary);
				}
			}

			NodeList List2 = doc.getElementsByTagName("ListPartsResult");
			Node Node2 = List2.item(0);
			if (Node2.getNodeType() == Node.ELEMENT_NODE) {
				Element Element2 = (Element) Node2;
				partList = new PartListing(Element2.getElementsByTagName("Bucket").item(0).getTextContent(),
						Element2.getElementsByTagName("Key").item(0).getTextContent(),
						Element2.getElementsByTagName("UploadId").item(0).getTextContent(),
						Element2.getElementsByTagName("StorageClass").item(0).getTextContent(),
						Element2.getElementsByTagName("PartNumberMarker").item(0).getTextContent(),
						Element2.getElementsByTagName("NextPartNumberMarker").item(0).getTextContent(),
						Element2.getElementsByTagName("MaxParts").item(0).getTextContent(),
						owner,
						Partdetails);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return partList;
	}
}

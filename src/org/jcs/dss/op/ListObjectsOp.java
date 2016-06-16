package org.jcs.dss.op;

import java.io.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.DssObject;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class ListObjectsOp extends BucketOp {

	public ListObjectsOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod = "GET";
	}

	@Override
	public Object processResult(Object resp) throws IOException{
		InputStream data = ((Response) resp).getData();
		BufferedReader input = new BufferedReader(new InputStreamReader(data));
		String XML= input.readLine();
		List<DssObject> DssObjectList = new ArrayList<DssObject>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String Bucket = null;
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();

			NodeList List = doc.getElementsByTagName("ListBucketResult");
			Node Node = List.item(0);
			if (Node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element = (Element) Node;
				Bucket = Element.getElementsByTagName("Name").item(0).getTextContent();
			}
			NodeList nList = doc.getElementsByTagName("Contents");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() ==org.w3c.dom.Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					DssObject temp1 = new DssObject(Bucket,
							eElement.getElementsByTagName("Key").item(0).getTextContent(),
							eElement.getElementsByTagName("LastModified").item(0).getTextContent(),
							eElement.getElementsByTagName("Size").item(0).getTextContent(),
							eElement.getElementsByTagName("ID").item(0).getTextContent());
					DssObjectList.add(temp1);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return DssObjectList;
	}
}

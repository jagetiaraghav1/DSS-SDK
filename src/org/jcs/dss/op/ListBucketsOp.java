package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.Bucket;
import org.jcs.dss.main.DssConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ListBucketsOp extends BucketOp {

	public ListBucketsOp(DssConnection conn) {
		super(conn, null);
		httpMethod = "GET";
		opPath = "/";
	}
	@Override
	public Object processResult(Object resp) throws IOException{
		InputStream data = ((Response) resp).getData();
		BufferedReader input = new BufferedReader(new InputStreamReader(data));
		String XML=input.readLine();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		List<Bucket> BucketList= new ArrayList<Bucket>();
		String owner = null;
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList List1 = doc.getElementsByTagName("Owner");
			Node Node1 = List1.item(0);
			if (Node1.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element1 = (Element) Node1;
				owner = Element1.getElementsByTagName("ID").item(0).getTextContent();
			}
			NodeList nList = doc.getElementsByTagName("Bucket");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Bucket buck = new Bucket(eElement.getElementsByTagName("Name").item(0).getTextContent(),
							eElement.getElementsByTagName("CreationDate").item(0).getTextContent(),
							owner);
					BucketList.add(buck);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return BucketList;
	}
}

package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.DssObject;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
/// Class to get List of all Objects associated with the requested bucket
public class ListObjectsOp extends BucketOp {
	///Constructors
	public ListObjectsOp(DssConnection conn, String bucketName) throws UnsupportedEncodingException {
		super(conn, bucketName);
		httpMethod = "GET";
	}
	///This method extracts information such as Size, Key etc about each object from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.request()
	 * @return List<DssObject> : List of all DSS Objects associated with the requested bucket
	 * @throws IOException
	 */
	@Override
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		//Creating an List object for class DssObject
		List<DssObject> DssObjectList = new ArrayList<DssObject>();
		//Parsing XML using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String Bucket = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Type casting a string to a Document
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList List = doc.getElementsByTagName("ListBucketResult");
			Node Node = List.item(0);
			if (Node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element = (Element) Node;
				// Extracting bucket name from XML String and storing in local variable
				Bucket = Element.getElementsByTagName("Name").item(0).getTextContent();
			}
			NodeList nList = doc.getElementsByTagName("Contents");
			// Running for loop to get complete list of objects
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() ==org.w3c.dom.Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// Extracting parameters from XML String and adding in DssObjectList object
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

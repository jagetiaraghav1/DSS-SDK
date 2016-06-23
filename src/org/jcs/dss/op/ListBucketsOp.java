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
/// Class to get List of all Buckets associated with the this account
public class ListBucketsOp extends BucketOp {
	///Constructors
	public ListBucketsOp(DssConnection conn) throws UnsupportedEncodingException {
		super(conn, null);
		httpMethod = "GET";
		opPath = "/";
	}
	///This method extracts information such as Owner, Bucket name and creation date from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.request()
	 * @return List<Bucket> : List of all DSS buckets associated with this account
	 * @throws IOException
	 */
	@Override
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		//Parsing XML using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//Creating an List object for class Bucket
		List<Bucket> BucketList= new ArrayList<Bucket>();
		String owner = null;
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			//Type casting a string to a Document
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList List1 = doc.getElementsByTagName("Owner");
			Node Node1 = List1.item(0);
			if (Node1.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element Element1 = (Element) Node1;	
				// Extracting owner's ID from XML String and storing in local variable
				owner = Element1.getElementsByTagName("ID").item(0).getTextContent();
			}
			NodeList nList = doc.getElementsByTagName("Bucket");
			// Running for loop to get complete list
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					// Extracting parameters from XML String and putting adding in BucketList object
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

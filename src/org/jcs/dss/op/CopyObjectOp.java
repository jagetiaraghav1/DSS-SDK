package org.jcs.dss.op;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.CopyObjectResult;
import org.jcs.dss.main.DssConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
///Class to execute method for copying Object from one bucket to another
public class CopyObjectOp extends ObjectOp{
	private String JcsCopyPath;
	///Constructors
	public CopyObjectOp(DssConnection conn,String bucketName, String objectName,String  JcsCopyPath) {
		super(conn,bucketName, objectName);
		httpMethod="PUT";
		opPath = "/" + bucketName + "/" + objectName;
		this.JcsCopyPath = JcsCopyPath;
	}
	/// Executes the method requested by user
	/**
	 * 
	 * @return Response : Gets response object returned from putHeaders()
	 * @throws Exception
	 */
	@Override
	public Response execute() throws Exception {
		return putHeaders();
	}
	/// This method assign additional headers which are not present in Op.makeRequests()
	/**
	 * 
	 * @return Response : Gets response object returned from makeRequest()
	 * @throws Exception
	 */
	public Response putHeaders() throws Exception {
		httpHeaders.put("x-amz-metadata-directive", "COPY");
		httpHeaders.put("x-amz-copy-source", JcsCopyPath);
		return makeRequest();
	}
	///This method extracts information about newly copied object such as LastModified and ETag from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.put()
	 * @return CopyObjectResult : A class that contains information about a newly copied object
	 * @throws IOException
	 */
	@Override
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		//Creating an object for class CopyObjectResult
		CopyObjectResult copyObject = new CopyObjectResult(null,null);
		//Parsing XML using DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			//Type casting a string to a Document
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CopyObjectResult");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				// Extracting parameters from XML String and putting in copyObject object
				copyObject = new CopyObjectResult(
						eElement.getElementsByTagName("ETag").item(0).getTextContent(),
						eElement.getElementsByTagName("LastModified").item(0).getTextContent()
						);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return copyObject;
	}

}

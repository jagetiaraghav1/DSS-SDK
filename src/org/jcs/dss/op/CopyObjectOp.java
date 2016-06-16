package org.jcs.dss.op;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class CopyObjectOp extends ObjectOp{
	private String JcsCopyPath;
	public CopyObjectOp(DssConnection conn,String bucketName, String objectName,String  JcsCopyPath) {
		super(conn,bucketName, objectName);
		httpMethod="PUT";
		opPath = "/" + bucketName + "/" + objectName;
		this.JcsCopyPath = JcsCopyPath;
	}

	@Override
	public Response execute() throws Exception {
		return putHeaders();
	}

	public Response putHeaders() throws Exception {
		httpHeaders.put("x-amz-metadata-directive", "COPY");
		httpHeaders.put("x-amz-copy-source", JcsCopyPath);
		return makeRequest();
	}

	@Override
	public Object processResult(Object resp) throws IOException{
		InputStream data = ((Response) resp).getData();
		BufferedReader in = new BufferedReader(new InputStreamReader(data));
		String XML= in.readLine();
		CopyObjectResult copyObject = new CopyObjectResult(null,null);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(XML)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CopyObjectResult");
			Node nNode = nList.item(0);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

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

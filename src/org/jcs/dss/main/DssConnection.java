package org.jcs.dss.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jcs.dss.http.Response;
import org.jcs.dss.op.CopyObjectOp;
import org.jcs.dss.op.CreateBucketOp;
import org.jcs.dss.op.DeleteBucketOp;
import org.jcs.dss.op.DeleteObjectOp;
import org.jcs.dss.op.GetObjectOp;
import org.jcs.dss.op.GetPresignedURLOp;
import org.jcs.dss.op.HeadBucketOp;
import org.jcs.dss.op.HeadObjectOp;
import org.jcs.dss.op.ListBucketsOp;
import org.jcs.dss.op.ListObjectsOp;
import org.jcs.dss.op.Op;
import org.jcs.dss.op.PutObjectOp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DssConnection {

	private String accessKey;
	private String secretKey;
	private String host;

	public DssConnection(String accessKey, String secretKey, String host) {

		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.host = host;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public String getHost() {
		return host;
	}

	public Bucket createBucket(String bucketName) throws Exception {

		CreateBucketOp op = new CreateBucketOp(this, bucketName);
		op.execute();
		return new Bucket(bucketName, null);

	}

	public List<Bucket> listBuckets() throws Exception {

		ListBucketsOp op = new ListBucketsOp(this);
		Response resp = op.execute();
		InputStream data = resp.getData();

		BufferedReader in = new BufferedReader(new InputStreamReader(data));

		String inputLine=in.readLine();
		String Line = inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		List<Bucket> BucketList= new ArrayList<Bucket>();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(Line)));

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Bucket");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Bucket temp1 = new Bucket(eElement.getElementsByTagName("Name").item(0).getTextContent(),eElement.getElementsByTagName("CreationDate").item(0).getTextContent());
					BucketList.add(temp1);

				}
			}

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}

		return BucketList;

	}

	public List<DssObject> listObjects(String bucketName) throws Exception {

		ListObjectsOp op = new ListObjectsOp(this, bucketName);
		Response resp = op.execute();
		InputStream data = resp.getData();
		BufferedReader in = new BufferedReader(new InputStreamReader(data));
		String inputLine= in.readLine();
		String Line = inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();

		List<DssObject> DssObjectList = new ArrayList<DssObject>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(Line)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Contents");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					DssObject temp1 = new DssObject(eElement.getElementsByTagName("Key").item(0).getTextContent(),
							eElement.getElementsByTagName("LastModified").item(0).getTextContent(),
							eElement.getElementsByTagName("Size").item(0).getTextContent(),
							eElement.getElementsByTagName("ID").item(0).getTextContent());
					DssObjectList.add(temp1);
				}
			}

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return DssObjectList;

	}

	public void deleteBucket(String bucketName) throws Exception {

		DeleteBucketOp op = new DeleteBucketOp(this, bucketName);
		op.execute();

	}

	public void uploadObjectFromFileName(String bucketName, String objectName,
			String filePath) throws Exception {

		PutObjectOp op = new PutObjectOp(this,bucketName,objectName,filePath);
		Response resp = op.execute();
	}

	public void downloadObjectToFileName(String bucketName, String objectName,
			String filePath) throws Exception {
		GetObjectOp op = new GetObjectOp(this, bucketName,objectName,filePath);
		Response resp = op.execute();
	}

	public void deleteObject(String bucketName, String objectName) throws Exception {
		DeleteObjectOp op = new DeleteObjectOp(this,bucketName,objectName);
		op.execute();
	}

	/*public void copyObject(String bucketName, String objectName,String copySource) throws Exception{
		CopyObjectOp op = new CopyObjectOp(this,bucketName,objectName,copySource);
		Response resp = op.execute();
		System.out.println(resp.getStatusMsg());
	}

	public void headBucket(String bucketName) throws Exception {
		HeadBucketOp op = new HeadBucketOp(this,bucketName);

	}


	public void headObject(String bucketName, String objectName) throws Exception {
		HeadObjectOp op = new HeadObjectOp(this,bucketName,objectName);

	}

	public void getPresignedURLOp(String bucketName, String objectName,int expirytime) throws Exception {
		GetPresignedURLOp op = new GetPresignedURLOp(this,bucketName,objectName,expirytime);
		op.Execute();

	}*/

}

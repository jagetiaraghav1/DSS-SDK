package org.jcs.dss.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jcs.dss.http.Response;
import org.jcs.dss.op.CompleteMPUploadOp;
import org.jcs.dss.op.CopyObjectOp;
import org.jcs.dss.op.CreateBucketOp;
import org.jcs.dss.op.DeleteBucketOp;
import org.jcs.dss.op.DeleteObjectOp;
import org.jcs.dss.op.GetObjectOp;
import org.jcs.dss.op.GetPresignedURLOp;
import org.jcs.dss.op.HeadBucketOp;
import org.jcs.dss.op.HeadObjectOp;
import org.jcs.dss.op.InitMPUploadOp;
import org.jcs.dss.op.ListBucketsOp;
import org.jcs.dss.op.ListMPUploadsOp;
import org.jcs.dss.op.ListObjectsOp;
import org.jcs.dss.op.ListPartOp;
import org.jcs.dss.op.Op;
import org.jcs.dss.op.PutObjectOp;
import org.jcs.dss.op.UploadPartOp;
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

	public void copyObject(String bucketName, String objectName,String copySource) throws Exception{
		CopyObjectOp op = new CopyObjectOp(this,bucketName,objectName,copySource);
		Response resp = op.execute();
		System.out.println(resp.getStatusMsg());
	}

	public Response headBucket(String bucketName) throws Exception {
		HeadBucketOp op = new HeadBucketOp(this,bucketName);
		Response resp =op.execute();
		return resp;


	}


	public Response headObject(String bucketName, String objectName) throws Exception {
		HeadObjectOp op = new HeadObjectOp(this,bucketName,objectName);
		Response resp =op.execute();
		return resp;

	}

	public void getPresignedURL(String bucketName, String objectName,int expirytime) throws Exception {
		GetPresignedURLOp op = new GetPresignedURLOp(this,bucketName,objectName,expirytime);
		URL url = op.Execute();
		System.out.println("{DownloadUrl:"+url +"}");
		

	}
	
	public InitiateMultipartUploadResult initMPUpload(String bucketName, String objectName) throws Exception {
		InitMPUploadOp op = new InitMPUploadOp(this,bucketName,objectName);
		
		Response resp = op.execute();
		InputStream data = resp.getData();
		BufferedReader in = new BufferedReader(new InputStreamReader(data));
		String inputLine= in.readLine();
		String Line = inputLine;
		System.out.println(Line);
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
		
		InitiateMultipartUploadResult temp1 = new  InitiateMultipartUploadResult(null,null, null);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc= db.parse(new InputSource(new StringReader(Line)));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("InitiateMultipartUploadResult");
				Node nNode = nList.item(0);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					 temp1 = new InitiateMultipartUploadResult(eElement.getElementsByTagName("Bucket").item(0).getTextContent(),
							eElement.getElementsByTagName("Key").item(0).getTextContent(),
							eElement.getElementsByTagName("UploadId").item(0).getTextContent());
				}
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return temp1;

	}
	
	public void listMPUploads(String bucketName) throws Exception {
		ListMPUploadsOp op = new ListMPUploadsOp(this,bucketName);
		Response resp = op.execute();
		System.out.println(resp.getStatusMsg());
		InputStream data = resp.getData();
		BufferedReader in = new BufferedReader(new InputStreamReader(data));
		String inputLine= in.readLine();
		String Line = inputLine;
		System.out.println(Line);
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}
	
	public List<UploadPartResult> uploadPart(String bucketName, String objectName,String uploadId,
			String filePath,int partSize) throws Exception {

		
		int partNumber = 1;
		File f = new File(filePath);
		//System.out.println(f.getParent());
		//System.out.println(f.getName());
		int partCounter = 1;

		byte[] buffer = new byte[partSize];
		List<UploadPartResult> uploadPartResult = new ArrayList<UploadPartResult>();

		try (BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f))) {
			String name = f.getName();

			int tmp = 0;
			while ((tmp = bis.read(buffer)) > 0) {
				File newFile = new File(f.getParent(), name + "."
						+ Integer.toString(partCounter++));
				try (FileOutputStream out = new FileOutputStream(newFile)) {
					out.write(buffer, 0, tmp);
			UploadPartOp op= new UploadPartOp(this,"my-new-bucket", "hello", "2~rsr0thorLXq_t9Ib-cQ_o1VDNNud3vS", Integer.toString(partCounter-1), newFile.getParent()+"/"+newFile.getName());
					Response resp = op.execute();
					
					String ETag = null;
					for (Map.Entry<String, List<String>> headers : resp.getHeaders().entrySet()) {
						   String key = new String();
						   if(headers.getKey()!=null)
						   key = headers.getKey();

						  List<String> valueList = headers.getValue();
						  if(key.contentEquals("ETag") )
						  {
						    ETag=valueList.get(0).substring(1, valueList.get(0).length()-1);

						  }}
					UploadPartResult upload =new UploadPartResult(ETag,Integer.toString(partNumber));
					partNumber++;
					uploadPartResult.add(upload);
					newFile.delete();
				}
			}
		}

		
		
		/*UploadPartOp op = new UploadPartOp(this,bucketName,objectName,uploadId,partNumber,filePath);
		Response resp = op.execute();
		
		String ETag = null;
		List<UploadPartResult> uploadPartResult = new ArrayList<UploadPartResult>();
		for (Map.Entry<String, List<String>> headers : resp.getHeaders().entrySet()) {
			   String key = new String();
			   if(headers.getKey()!=null)
			   key = headers.getKey();

			  List<String> valueList = headers.getValue();
			  if(key.contentEquals("ETag") )
			  {
			    ETag=valueList.get(0).substring(1, valueList.get(0).length()-1);

			  }}
		UploadPartResult upload =new UploadPartResult(ETag,partNumber);
		uploadPartResult.add(upload);*/
		return uploadPartResult;
		
	}

	
	public void listPart(String bucketName, String objectName,String uploadId) throws Exception {

		ListPartOp op = new ListPartOp(this,bucketName,objectName,uploadId);
		Response resp = op.execute();
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getStatusMsg());
		InputStream data = resp.getData();
		BufferedReader in = new BufferedReader(new InputStreamReader(data));
		String inputLine= in.readLine();
		String Line = inputLine;
		System.out.println(Line);
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}
	
	public void completeMultiPart(String bucketName, String objectName,String uploadId,String multipartUpload) throws Exception {

		CompleteMPUploadOp op = new CompleteMPUploadOp(this,bucketName,objectName,uploadId,multipartUpload);
		Response resp = op.execute();
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getStatusMsg());
		InputStream data = resp.getData();
		/*BufferedReader in = new BufferedReader(new InputStreamReader(data));
		String inputLine= in.readLine();
		String Line = inputLine;
		System.out.println(Line);
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();*/
	}

}

package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.UploadPartResult;
///Class to break file into small parts of size requested by users to execute UploadPart API
public class PartCreationForUploadPart{

	private String bucketName;
	private String ObjectName;
	private String uploadId;
	private String filePath;
	private int partSize;
	private DssConnection conn;
	///Constructors
	public PartCreationForUploadPart(DssConnection conn, String bucketName, String objectName, String uploadId, String filePath,int partSize) 
	{
		this.bucketName = bucketName;
		this.ObjectName = objectName;
		this.filePath = filePath;
		this.uploadId = uploadId;
		this.partSize = partSize;
		this.conn = conn;
	}
	///This method breaks file into smaller parts and creates its InputStream and send to to UploadPartOp class to upload it on server and further extracts ETag and part number corresponding to each part
	/**
	 * @return List<UploadPartResult> : List of ETag and part number associated with each uploaded parts
	 * @throws Exception
	 */
	public List<UploadPartResult> createPart() throws Exception{
		int partNumber = 1;
		File f = new File(filePath);
		//Creating buffer array equivalent to part size requested by user
		byte[] buffer = new byte[partSize];	
		//Creating a List object for class UploadPartResult
		List<UploadPartResult> uploadPartResult = new ArrayList<UploadPartResult>();
		try (BufferedInputStream fileRead = new BufferedInputStream(
				new FileInputStream(f))) {
			int tmp = 0;
			// Reading files in parts and storing in byte array till the end of file
			while ((tmp = fileRead.read(buffer)) > 0) {
				{
					//Creating inputStream of each part
					InputStream partStream = new ByteArrayInputStream(buffer,0,tmp);
					//Calling UploadPartOp to upload parts one by one
					UploadPartOp op= new UploadPartOp(conn,bucketName,ObjectName , uploadId, 
							Integer.toString(partNumber), partStream);
					Response resp = op.execute();
					String ETag = null;
					//Extracting ETag from headers returned from server
					for (Map.Entry<String, List<String>> headers : resp.getHeaders().entrySet()) {
						String key = new String();
						if(headers.getKey()!=null)
							key = headers.getKey();
						List<String> valueList = headers.getValue();
						if(key.contentEquals("ETag") )
						{
							ETag=valueList.get(0).substring(1, valueList.get(0).length()-1);
						}
					}
					//Creating list of ETag and part number
					UploadPartResult upload =new UploadPartResult(ETag,Integer.toString(partNumber));
					partNumber++;
					uploadPartResult.add(upload);
				} 
			} 			
		}
		return uploadPartResult;
	}

}

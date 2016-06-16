package org.jcs.dss.op;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.UploadPartResult;

public class PartCreationForUploadPart{

	private String bucketName;
	private String ObjectName;
	private String uploadId;
	private String filePath;
	private int partSize;
	private DssConnection conn;

	public PartCreationForUploadPart(DssConnection conn, String bucketName, String objectName, String uploadId, String filePath,int partSize) 
	{
		this.bucketName = bucketName;
		this.ObjectName = objectName;
		this.filePath = filePath;
		this.uploadId = uploadId;
		this.partSize = partSize;
		this.conn = conn;
	}

	public List<UploadPartResult> createPart() throws Exception{
		int partNumber = 1;
		File f = new File(filePath);	
		byte[] buffer = new byte[partSize];	
		List<UploadPartResult> uploadPartResult = new ArrayList<UploadPartResult>();
		try (BufferedInputStream fileRead = new BufferedInputStream(
				new FileInputStream(f))) {
			int tmp = 0;
			while ((tmp = fileRead.read(buffer)) > 0) {
				{
					InputStream partStream = new ByteArrayInputStream(buffer,0,tmp);
					UploadPartOp op= new UploadPartOp(conn,bucketName,ObjectName , uploadId, 
							Integer.toString(partNumber), partStream);
					Response resp = op.execute();
					System.out.println(partNumber);
					String ETag = null;
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
					UploadPartResult upload =new UploadPartResult(ETag,Integer.toString(partNumber));
					partNumber++;
					uploadPartResult.add(upload);
				} 
			} 			
		}
		return uploadPartResult;
	}

}

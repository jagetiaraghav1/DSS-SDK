package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class CancelMPUploadOp extends ObjectOp{

	public CancelMPUploadOp(DssConnection conn, String bucketName, String objectName,String uploadId) {
		super(conn, bucketName, objectName);
		// TODO Auto-generated constructor stub
		this.uploadId = uploadId;
		httpMethod = "DELETE";
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "uploadId="+ uploadId;
		queryStrForSignature = "uploadId="+ uploadId;
	}

}

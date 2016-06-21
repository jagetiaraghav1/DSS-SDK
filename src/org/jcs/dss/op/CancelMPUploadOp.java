package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;
///Sets constructors like httpMethod, queryString, etc to Cancel a multipart upload
public class CancelMPUploadOp extends ObjectOp{
	///Constructors
	public CancelMPUploadOp(DssConnection conn, String bucketName, String objectName,String uploadId) {
		super(conn, bucketName, objectName);
		httpMethod = "DELETE";
		opPath = "/"+bucketName+"/"+objectName;
		queryStr = "uploadId="+ uploadId;
		queryStrForSignature = "uploadId="+ uploadId;
	}

}

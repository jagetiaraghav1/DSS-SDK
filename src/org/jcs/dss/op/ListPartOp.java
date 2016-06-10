package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class ListPartOp extends ObjectOp{

	public ListPartOp(DssConnection conn, String bucketName, String objectName,String uploadId) {
		super(conn, bucketName, objectName);
		httpMethod ="GET";
		this.uploadId=uploadId;
		 opPath = "/"+bucketName+"/"+objectName;
		 queryStr = "uploadId="+uploadId;
		 queryStrForSignature = "uploadId="+uploadId;
	}

}

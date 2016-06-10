package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class ListMPUploadsOp extends BucketOp {

	public ListMPUploadsOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		
		httpMethod = "GET";
		queryStr = "uploads";
		queryStrForSignature="uploads";
	}

}

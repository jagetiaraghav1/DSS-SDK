package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class DeleteBucketOp extends BucketOp {
	
	public DeleteBucketOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod ="DELETE";
	}

}

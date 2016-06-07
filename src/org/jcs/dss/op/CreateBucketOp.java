package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class CreateBucketOp extends BucketOp{

	public CreateBucketOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod ="PUT";
	}

}

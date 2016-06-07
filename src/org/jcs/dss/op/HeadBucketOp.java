package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class HeadBucketOp extends BucketOp{

	public HeadBucketOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod ="HEAD";
	}

}

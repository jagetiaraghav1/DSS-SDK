package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class ListObjectsOp extends BucketOp {

	public ListObjectsOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod = "GET";
	}
}

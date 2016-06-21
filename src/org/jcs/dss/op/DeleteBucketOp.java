package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;
///Class to Delete an existing bucket
public class DeleteBucketOp extends BucketOp {
	/// Constructors
	public DeleteBucketOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod ="DELETE";
	}
}

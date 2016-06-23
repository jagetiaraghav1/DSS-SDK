package org.jcs.dss.op;

import java.io.UnsupportedEncodingException;

import org.jcs.dss.main.DssConnection;
///Class to Delete an existing bucket
public class DeleteBucketOp extends BucketOp {
	/// Constructors
	public DeleteBucketOp(DssConnection conn, String bucketName) throws UnsupportedEncodingException {
		super(conn, bucketName);
		httpMethod ="DELETE";
	}
}

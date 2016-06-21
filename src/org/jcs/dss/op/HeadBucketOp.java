package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;
/// Class to execute head bucket API
public class HeadBucketOp extends BucketOp{
	///Constructors
	public HeadBucketOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod ="HEAD";
	}

}

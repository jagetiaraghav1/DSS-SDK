package org.jcs.dss.op;

import java.io.UnsupportedEncodingException;

import org.jcs.dss.main.DssConnection;
/// Class to execute head bucket API
public class HeadBucketOp extends BucketOp{
	///Constructors
	public HeadBucketOp(DssConnection conn, String bucketName) throws UnsupportedEncodingException {
		super(conn, bucketName);
		httpMethod ="HEAD";
	}

}

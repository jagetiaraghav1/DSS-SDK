package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;
///Class to execute head object API
public class HeadObjectOp extends ObjectOp{
	///Constructors
	public HeadObjectOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod = "HEAD";
	}

}

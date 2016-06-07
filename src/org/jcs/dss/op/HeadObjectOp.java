package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class HeadObjectOp extends ObjectOp{

	public HeadObjectOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod = "HEAD";
	}

}

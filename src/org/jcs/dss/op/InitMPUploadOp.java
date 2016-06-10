package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class InitMPUploadOp extends ObjectOp{

	public InitMPUploadOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod = "POST";
		queryStr = "uploads";
		queryStrForSignature="uploads";
	}

}

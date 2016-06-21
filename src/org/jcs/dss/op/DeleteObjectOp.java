package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;
///Class to Delete an existing key inside the requested bucket
public class DeleteObjectOp extends ObjectOp{
	///Constructors
	public DeleteObjectOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod="DELETE";
		opPath = '/' + bucketName + '/' + objectName;
	}

}

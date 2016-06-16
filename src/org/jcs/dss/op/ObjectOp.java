package org.jcs.dss.op;

import java.io.IOException;

import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;

public class ObjectOp extends Op {

	private String bucketName;
	private String objectName;

	public ObjectOp(DssConnection conn, String bucketName, String objectName) {
		super(conn);
		this.bucketName=bucketName;
		this.objectName = objectName;
		opPath = '/' + this.bucketName + '/' + this.objectName;
	}

	public Response execute() throws Exception {
		return makeRequest();	
	}

	public Object processResult(Object result) throws IOException{
		return result;
	}
}




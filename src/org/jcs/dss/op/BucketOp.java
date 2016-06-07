package org.jcs.dss.op;

import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;


public class BucketOp extends Op {
	
	private String bucketName;

	
	public BucketOp(DssConnection conn, String bucketName) {
		super(conn);
		this.bucketName = bucketName;
		if(bucketName != null) {
		this.opPath = '/' + this.bucketName;
		}

	}

	public Response execute() throws Exception {	
		
		return makeRequest();
	}
	
	public Object processResult(Object result){
		return result;
	}	
}




package org.jcs.dss.op;

import org.jcs.dss.main.Bucket;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public class CreateBucketOp extends BucketOp{

	public CreateBucketOp(DssConnection conn, String bucketName) {
		super(conn, bucketName);
		httpMethod ="PUT";
	}
	
	@Override
	public Object processResult(Object bucketName){
		String date = Utils.getCurTimeInGMTString();
		Bucket bucket = new Bucket ((String)bucketName, date,null);
		return bucket;
	}
	

}

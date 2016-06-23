package org.jcs.dss.op;

import java.io.UnsupportedEncodingException;

import org.jcs.dss.main.Bucket;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;
///Class to create a new bucket
public class CreateBucketOp extends BucketOp{
	///Constructors
	public CreateBucketOp(DssConnection conn, String bucketName) throws UnsupportedEncodingException {
		super(conn, bucketName);
		httpMethod ="PUT";
	}
	/// Class to put creation date and return the bucket object
	/**
	 * @param BucketName : Name of new bucket entered by user
	 * @return Bucket : An Object of bucket class having details of bucket name and creation date
	 */
	@Override
	public Object processResult(Object bucketName){
		//Getting current date 
		String date = Utils.getCurTimeInGMTString();
		Bucket bucket = new Bucket ((String)bucketName, date,null);
		return bucket;
	}


}

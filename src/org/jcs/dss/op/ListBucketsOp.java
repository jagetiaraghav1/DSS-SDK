package org.jcs.dss.op;

import org.jcs.dss.main.DssConnection;

public class ListBucketsOp extends BucketOp {

	public ListBucketsOp(DssConnection conn) {
		super(conn, null);
		httpMethod = "GET";
		opPath = "/";
	}
}

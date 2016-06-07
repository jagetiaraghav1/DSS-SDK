package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;

public class CopyObjectOp extends ObjectOp{


	public CopyObjectOp(DssConnection conn,String bucketName, String objectName,String  JcsCopyPath) {
		super(conn,bucketName, objectName);
		// TODO Auto-generated constructor stub
		httpMethod="PUT";
		opPath = "/" + bucketName + "/" + objectName;
		JcsCopySource =JcsCopyPath;
		System.out.println(opPath);
	}

	@Override
	public Response execute() throws Exception {
		return putHeaders();
	}

	public Response putHeaders() throws Exception {

		httpHeaders.put("x-jcs-metadata-directive", "COPY");
		httpHeaders.put("x-jcs-copy-source", JcsCopySource);





		return makeRequest();
	}


}

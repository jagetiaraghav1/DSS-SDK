package org.jcs.dss.op;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.jcs.dss.auth.DssAuth;
import org.jcs.dss.auth.DssAuthBuilder;
import org.jcs.dss.http.Request;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.utils.Utils;

public class CopyObjectOp extends ObjectOp{


	public CopyObjectOp(DssConnection conn,String bucketName, String objectName,String  JcsCopyPath) {
		super(conn,bucketName, objectName);
		httpMethod="PUT";
		opPath = "/" + bucketName + "/" + objectName;
		setJcsCopySource(JcsCopyPath);
	}

	@Override
	public Response execute() throws Exception {
		return putHeaders();
	}

	public Response putHeaders() throws Exception {

		httpHeaders.put("x-amz-metadata-directive", "COPY");
		httpHeaders.put("x-amz-copy-source", getJcsCopySource());
		return makeRequest();
	}

}

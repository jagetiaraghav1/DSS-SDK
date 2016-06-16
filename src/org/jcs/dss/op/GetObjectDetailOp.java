package org.jcs.dss.op;

import java.util.List;
import java.util.Map;

import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.Objectdata;

public class GetObjectDetailOp extends ObjectOp{

	public GetObjectDetailOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod="GET";
		opPath = '/' + bucketName + '/' + objectName;
	}
	
	@Override
	public Object processResult(Object resp){
		String ETag = null;
		String contentLength = null;
		String contentType = null;
		String lastModified = null;
		for (Map.Entry<String, List<String>> headers : ((Response) resp).getHeaders().entrySet()) {
			String key = new String();
			if(headers.getKey()!=null)
				key = headers.getKey();
			List<String> valueList = headers.getValue();
			if(key.contentEquals("ETag")){
				ETag=valueList.get(0).substring(1, valueList.get(0).length()-1);
			}
			else if(key.contentEquals("Content-Length")){
				contentLength = valueList.get(0);
			}
			else if(key.contentEquals("Content-Type")){
				contentType = valueList.get(0);
			}else if(key.contentEquals("Last-Modified")){
				lastModified = valueList.get(0);
			}
		}
		Objectdata objectMetadata = new Objectdata(ETag,contentLength,lastModified,contentType);
		return objectMetadata;
	}
}

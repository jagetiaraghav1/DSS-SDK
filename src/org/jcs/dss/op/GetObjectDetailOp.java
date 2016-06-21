package org.jcs.dss.op;

import java.util.List;
import java.util.Map;

import org.jcs.dss.http.Response;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.Objectdata;
///Class to get details regarding ETag, content Length etc of the requested object

public class GetObjectDetailOp extends ObjectOp{
	///Constructors
	public GetObjectDetailOp(DssConnection conn, String bucketName, String objectName) {
		super(conn, bucketName, objectName);
		httpMethod="GET";
		opPath = '/' + bucketName + '/' + objectName;
	}

	@Override
	///This method extracts information from headers got from server
	/**
	 * @param Response : Response message got from Request.request()
	 * @return Objectdata : Object of a class containing information about requested key
	 */
	public Object processResult(Object resp){
		String ETag = null;
		String contentLength = null;
		String contentType = null;
		String lastModified = null;
		//Extracting values of ETag, ContentLength etc from headers and storing in local variable
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
		//Creating an object for class Objectdata and assigning values
		Objectdata objectdata = new Objectdata(ETag,contentLength,lastModified,contentType);
		return objectdata;
	}
}

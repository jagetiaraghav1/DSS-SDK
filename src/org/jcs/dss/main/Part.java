package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement (name = "CompleteMultipartUpload")
public class Part {

	private List<UploadPartResult> upload = new ArrayList<UploadPartResult>();
	
	public Part() {}  
	public Part( List<UploadPartResult> upload) {  
	    super();  
	   /* this.id = id;  
	    this.questionname = questionname;  */
	    this.upload = upload;  
	}  
	@XmlElement (name = "Part") 
	public List<UploadPartResult> getUploadPartResult() {  
	    return upload;  
	}  
	public void setUploadPartResult(List<UploadPartResult> upload) {  
	    this.upload = upload;  
	}  
	
}

package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement (name = "CompleteMultipartUpload")
public class Part {
	
	@XmlElement (name = "Part") 
	private List<UploadPartResult> upload ;
	
	public Part() {}  
	public Part( List<UploadPartResult> upload) {  
	    super();  
	    this.upload = new ArrayList<UploadPartResult>();
	    this.upload = upload;  
	}  
	
//	public List<UploadPartResult> getUploadPartResult() {  
//	    return upload;  
//	}  
//	void setUploadPartResult(List<UploadPartResult> upload) {  
//	    this.upload = upload;  
//	}  
	
}

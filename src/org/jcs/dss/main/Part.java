package org.jcs.dss.main;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/// For internal use only. 
@XmlRootElement (name = "CompleteMultipartUpload")
public class Part {

	@XmlElement (name = "Part") 
	private List<UploadPartResult> upload ;

	public Part() {}  
	///Constructors
	public Part( List<UploadPartResult> upload) {  
		super();  
		this.upload = new ArrayList<UploadPartResult>();
		this.upload = upload;  
	}  	
}

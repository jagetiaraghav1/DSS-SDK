package org.jcs.dss.main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType(propOrder={"partNumber", "ETag"})
public class UploadPartResult {
	
	@XmlElement(name = "ETag")
	private String ETag;
	@XmlElement(name="PartNumber")
	private String partNumber;
	
	public UploadPartResult(){}
	
	public UploadPartResult(String ETag, String PartNumber) {
		super();
		this.ETag= ETag;
		this.partNumber = PartNumber;
	}

	public String getETag() {
		return ETag;
	}
	public void setEtag(String ETag) {
		this.ETag = ETag;
	}

	public String getpartNumber() {
		return partNumber;
	}
	public void setPartNumber(String PartNumber) {
		this.partNumber = PartNumber;
	}
	
}

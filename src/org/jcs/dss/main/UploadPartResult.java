package org.jcs.dss.main;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
//@XmlType(propOrder={"PartNumber", "ETag"})
public class UploadPartResult {
	@XmlElement

	private String ETag;
	@XmlElement(name="PartNumber")
	private String PartNumber;
	public UploadPartResult(){}
	
	public UploadPartResult(String ETag, String partNumber) {
		super();
		this.ETag= ETag;
		this.PartNumber = partNumber;
	}

	public String getETag() {
		return ETag;
	}
	public void setEtag(String ETag) {
		this.ETag = ETag;
	}

	public String getPartNumber() {
		return PartNumber;
	}
	public void setPartNumber(String partNumber) {
		this.PartNumber = partNumber;
	}
	
}

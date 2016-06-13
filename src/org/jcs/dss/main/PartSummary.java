package org.jcs.dss.main;

public class PartSummary {

	private String ETag;
	private String LastModified;
	private String PartNumber;
	private String Size;
	public PartSummary(String LastModified,String PartNumber,String ETag,String Size) {
		super();
		this.ETag = ETag;
		this.LastModified = LastModified;
		this.PartNumber=PartNumber;
		this.Size = Size;
	}
	
	public String getETag() {
		return ETag;
	}
	public void setETag(String ETag) {
		this.ETag = ETag;
	}
	public String getLastModified() {
		return LastModified;
	}
	public void setLastModified(String LastModified) {
		this.LastModified = LastModified;
	}
	
	public String getPartNumber() {
		return PartNumber;
	}
	public void setPartNumber(String PartNumber) {
		this.PartNumber = PartNumber;
	}
	
	public String getSize() {
		return Size;
	}
	public void setSize(String Size) {
		this.Size = Size;
	}
}

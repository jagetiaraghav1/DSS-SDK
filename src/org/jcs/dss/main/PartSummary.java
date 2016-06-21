package org.jcs.dss.main;
///Contains information about each part in a multipart upload, such as part number, size, etc.
public class PartSummary {

	private String ETag;
	private String LastModified;
	private String PartNumber;
	private String Size;
	//Constructors
	public PartSummary(String LastModified,String PartNumber,String ETag,String Size) {
		super();
		this.ETag = ETag;
		this.LastModified = LastModified;
		this.PartNumber=PartNumber;
		this.Size = Size;
	}
	///Returns the entity tag generated from the part content.
	/**
	 * 
	 * @return ETag
	 */
	public String getETag() {
		return ETag;
	}
	///Sets the entity tag generated from the part content.
	/**
	 * 
	 * @param ETag
	 */
	public void setETag(String ETag) {
		this.ETag = ETag;
	}
	///Returns the date this part was last modified.
	/**
	 * 
	 * @return LastModified
	 */
	public String getLastModified() {
		return LastModified;
	}
	///Sets the date this part was last modified.
	/**
	 * 
	 * @param LastModified
	 */
	public void setLastModified(String LastModified) {
		this.LastModified = LastModified;
	}
	///Returns the part number of a part in the multipart upload.
	/**
	 * 
	 * @return PartNumber
	 */
	public String getPartNumber() {
		return PartNumber;
	}
	///Sets the part number of a part in the multipart upload.
	/**
	 * 
	 * @param PartNumber
	 */
	public void setPartNumber(String PartNumber) {
		this.PartNumber = PartNumber;
	}
	///Returns the size of this part, in bytes.
	/**
	 * 
	 * @return Size
	 */
	public String getSize() {
		return Size;
	}
	///Sets the size of this part, in bytes.
	/**
	 * 
	 * @param Size
	 */
	public void setSize(String Size) {
		this.Size = Size;
	}
}

package org.jcs.dss.main;
///Contains the data returned by JCS DSS from the copyObject operation. 
/**
 * Use this class to access information about the new copied object created from the copyObject request, 
 * such as its ETag and lastModifiedDate 
 */
public class CopyObjectResult {
	private String ETag;
	private String lastModifiedDate;
	///Constructors
	public CopyObjectResult(String ETag, String lastModifiedDate) {
		super();
		this.ETag= ETag;
		this.lastModifiedDate = lastModifiedDate;
	}
	/// Returns the server-side ETag value for the newly copied object.
	/**
	 * 
	 * @return ETag
	 */
	public String getETag() {
		return ETag;
	}
	/// Sets the server-side ETag value for the newly copied object.
	/**
	 * 
	 * @param ETag
	 */
	public void setEtag(String ETag) {
		this.ETag = ETag;
	}

	///Returns the date the newly copied object was last modified.
	/**
	 * 
	 * @return LastModifiedDate
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	///Sets the date the newly copied object was last modified.
	/**
	 * 
	 * @param LastModifiedDate
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}

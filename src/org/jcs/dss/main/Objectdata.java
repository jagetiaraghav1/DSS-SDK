package org.jcs.dss.main;
///Represents an object stored in JCS DSS.
/**
 * This object contains the data content, such as content type, ETag, etc.
 */
public class Objectdata {

	String ETag;
	String contentLength;
	String contentType;
	String lastModified;
	///Constructors
	public  Objectdata(String ETag,String contentLength,String lastModified,String contentType) {
		super();
		this.ETag = ETag;
		this.contentLength = contentLength;
		this.lastModified=lastModified;
		this.contentType=contentType;
	}
	/// Returns the server-side ETag value for the requested object.
	/**
	 * 
	 * @return ETag
	 */
	public String getETag() {
		return ETag;
	}

	/// Sets the server-side ETag value for the requested object.
	/**
	 * 
	 * @param ETag
	 */
	public void setETag(String ETag) {
		this.ETag = ETag;
	}

	/// Returns the content-length of the specified object.
	/**
	 * 
	 * @return ContentLength
	 */
	public String getContentLength() {
		return contentLength;
	}
	/// Sets the content-length of the specified object.
	/**
	 * 
	 * @param ContentLength
	 */
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}
	/// Returns the date this object was last modified
		/**
		 * 
		 * @return LastModifiedDate
		 */
	public String getLastModified() {
		return lastModified;
	}
	/// Sets the date this object was last modified
		/**
		 * 
		 * @param LastModifiedDate
		 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	/// Sets the content-type of the specified object.
		/**
		 * 
		 * @return ContentType
		 */
	public String getContentType() {
		return contentType;
	}
	/// Returns the content-type of the specified object.
		/**
		 * 
		 * @param ContentType
		 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}

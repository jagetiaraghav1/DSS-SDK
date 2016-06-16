package org.jcs.dss.main;
///Returns a list of summary information about the objects in the specified bucket.
public class DssObject {

	String name;
	String bucket;
	String size;
	String ownerId;
	String lastModified;
	/// Constructors
	public DssObject(String bucket,String name,String lastModified,String size,String ownerId) {
		super();
		this.name = name;
		this.bucket = bucket;
		this.lastModified=lastModified;
		this.ownerId=ownerId;
		this.size=size;
	}
	///Returns the size of this object in bytes
	/**
	 * 
	 * @return Size
	 */
	public String getSize() {
		return size;
	}
	/// Sets the size of this object in bytes
	/**
	 * 
	 * @param Size
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/// Returns the name of the JCS DSS bucket containing the list of objects listed in DssObject
	/**
	 * 
	 * @return BucketName.
	 */
	public String getBucket() {
		return bucket;
	}
	/// Sets the name of the JCS DSS bucket containing the list of objects listed in DssObject
	/**
	 * 
	 * @param BucketName 
	 */
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	/// Returns the key of the objects present in requested bucket
	/**
	 * 
	 * @return ObjectName
	 */
	public String getName() {
		return name;
	}
	/// Sets the key of the objects present in requested bucket
	/**
	 * 
	 * @param ObjectName
	 */
	public void setName(String name) {
		this.name = name;
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
	/// Returns the owner of this object.
	/**
	 * 
	 * @return Owner
	 */
	public String getOwnerId() {
		return ownerId;
	}
	/// Sets the owner of this object.
	/**
	 * 
	 * @param Owner
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}

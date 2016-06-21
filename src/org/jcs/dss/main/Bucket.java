package org.jcs.dss.main;
/// Represents a DSS bucket
/**
 * 
 *	 Every object stored in JCS DSS is contained within a bucket. 
 *	 Within a bucket, any name can be used for objects. However, bucket names must be unique across all of JCS DSS.  
 *	 Ownership of the bucket is retained as long as the owner has an JCS DSS account.
 * 
 */
public class Bucket {

	private String name;
	private String creationDate;
	private String Owner;
	///Constructors
	public Bucket(String name, String creationDate, String Owner) {
		super();
		this.name = name;
		this.creationDate = creationDate;
		this.Owner = Owner;
	}
	/// Returns name of the bucket
	/**
	 * 
	 * @return BucketName : The name of the bucket.
	 */
	public String getName() {
		return name;
	}
	///Sets the name of the bucket
	/**
	 * 
	 * @param BucketName : The name for the bucket.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/// Returns creation date of the bucket
	/**
	 * 
	 * @return CreationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}
	/// Set Creation Date of the bucket
	/**
	 * 
	 * @param CreationDate.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	/// Returns Owner of the Owner of the bucket
	/**
	 * 
	 * @return Owner
	 */
	public String getOwner() {
		return Owner;
	}
	/// Sets Owner of the Owner of the bucket
	/**
	 * 
	 * @param Owner
	 */
	public void setOwner(String Owner) {
		this.Owner = Owner;
	}
}

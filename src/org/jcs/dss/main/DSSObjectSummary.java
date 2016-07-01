package org.jcs.dss.main;

import javax.xml.bind.annotation.XmlElement;

public class DSSObjectSummary {
	@XmlElement(name ="Key")
	private String name;
	@XmlElement(name ="Size")
	private String size;
	@XmlElement(name ="Owner")
	private Owner ownerId;
	@XmlElement(name ="LastModified")
	private String lastModified;
	@XmlElement(name ="ETag")
	private String ETag;

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
	void setSize(String size) {
		this.size = size;
	}
	///Returns the ETag of this object 
	/**
	 * 
	 * @return ETag
	 */
	public String getETag() {
		return ETag;
	}
	/// Sets the ETag of this object
	/**
	 * 
	 * @param ETag
	 */
	void setETag(String ETag) {
		this.ETag = ETag;
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

	void setName(String name) {
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
	void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	/// Returns the owner of this object.
	/**
	 * 
	 * @return Owner
	 */
	public String getOwner() {
		return ownerId.getDisplayName();
	}
	/// Sets the owner of this object.
	/**
	 * 
	 * @param Owner
	 */
	void setOwner(Owner ownerId) {
		this.ownerId = ownerId;
	}
}

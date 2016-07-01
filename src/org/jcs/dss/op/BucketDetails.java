package org.jcs.dss.op;

import javax.xml.bind.annotation.XmlElement;

public class BucketDetails {
	@XmlElement (name ="Name")
	private String name;
	@XmlElement (name ="Creation")
	private String creationDate;
	public String getName() {
		return name;
	}
	///Sets the name of the bucket
	/**
	 * 
	 * @param BucketName : The name for the bucket.
	 */
	 void setName(String name) {
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
	 void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
}

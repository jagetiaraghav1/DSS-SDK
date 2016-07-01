package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ListBucketResult")
///Returns a list of summary information about the objects in the specified bucket.
public class DssObject {
	@XmlElement(name = "Name")
	private String bucket;
	@XmlElement(name = "MaxKeys")
	private String MaxKeys;
	@XmlElement (name ="Contents")
	private List<DSSObjectSummary> DSSObjectSummary =  new ArrayList<DSSObjectSummary>();
	/*/// Constructors
	public DssObject(String bucket,String name,String lastModified,String size,String ownerId) {
		super();
		this.name = name;
		this.bucket = bucket;
		this.lastModified=lastModified;
		this.ownerId=ownerId;
		this.size=size;
	}*/
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
	void setBucket(String bucket) {
		this.bucket = bucket;
	}

	/// Returns the Max Keys
	/**
	 * 
	 * @return MaxKeys
	 */
	public String getMaxKeys() {
		return MaxKeys;
	}
	/// Sets the Max Keys
	/**
	 * 
	 * @param MaxKeys 
	 */
	void setMaxKeys(String MaxKeys) {
		this.MaxKeys = MaxKeys;
	}


	/// Returns the name of the JCS DSS bucket containing the list of objects listed in DssObject
	/**
	 * 
	 * @return BucketName.
	 */
	public List<DSSObjectSummary> getDSSObjectSummary() {
		return DSSObjectSummary;
	}
	/// Sets the name of the JCS DSS bucket containing the list of objects listed in DssObject
	/**
	 * 
	 * @param BucketName 
	 */
	void setDSSObjectSummary(List<DSSObjectSummary> DSSObjectSummary) {
		this.DSSObjectSummary = DSSObjectSummary;
	}

}

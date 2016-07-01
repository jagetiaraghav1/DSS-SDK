package org.jcs.dss.main;

import javax.xml.bind.annotation.XmlElement;

///The MultipartUpload contains all the information about each individual multipart upload like their key, uploadID etc.

public class MultipartUpload {
	@XmlElement (name = "Initiated")
	private String initiated;
	@XmlElement (name = "Key")
	private String key;
	@XmlElement (name = "UploadId")
	private String uploadId;
	@XmlElement (name = "Initiator")
	private Initiator initiator;
	@XmlElement (name = "Owner")
	private Owner owner;
	@XmlElement (name = "StorageClass")
	private String storageClass;
	///Returns the user who initiated this multipart upload.
	/**
	 * 
	 * @return initiator
	 */
	public String getInitiator() {
		return initiator.getInitiator();
	}
	/// Sets the user who initiated this multipart upload.
	/**
	 * 
	 * @param initiator
	 */
	void setInitiator(Initiator initiator) {
		this.initiator = initiator;
	}
	///Returns the key by which this upload is stored.
	/**
	 * 
	 * @return key
	 */
	public String getKey() {
		return key;
	}
	///Sets the key by which this upload is stored.
	/**
	 * 
	 * @param key
	 */
	void setKey(String key) {
		this.key = key;
	}
	///Returns the unique ID of this multipart upload.
	/**
	 * 
	 * @return UploadID
	 */
	public String getUploadId() {
		return uploadId;
	}
	///Sets the unique ID of this multipart upload.
	/**
	 * 
	 * @param uploadId
	 */
	void setUpoadId(String uploadId) {
		this.uploadId = uploadId;
	}
	///Returns the owner of this multipart upload.
	/**
	 * 
	 * @return Owner
	 */
	public String getOwner() {
		return owner.getDisplayName();
	}
	/// Sets the owner of this multipart upload.
	/**
	 * 
	 * @param owner
	 */
	void setOwner(Owner owner) {
		this.owner = owner;
	}
	///Returns the storage class indicating how the data in this multipart upload will be stored.
	/**
	 * 
	 * @return storageClass
	 */
	public String getStorageClass() {
		return storageClass;
	}
	///Sets the storage class indicating how the data in this multipart upload will be stored.
	/**
	 * 
	 * @param storageClass
	 */
	void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}
	///Returns the date at which this upload was initiated.
	/**
	 * 
	 * @return initiated
	 */
	public String getInitiated() {
		return initiated;
	}
	///Sets the date at which this upload was initiated.
	/**
	 * 
	 * @param initiated
	 */
	void setInitiated(String initiated) {
		this.initiated = initiated;
	}
}

package org.jcs.dss.main;
///The MultipartUpload contains all the information about each individual multipart upload like their key, uploadID etc.

public class MultipartUpload {

	private String initiated;
	private String key;
	private String uploadId;
	private String initiator;
	private String owner;
	private String storageClass;
	///Constructors
	public MultipartUpload( String key,String uploadId, String initiator, String owner, String storageClass,String initiated) {
		super();
		this.key = key;
		this.uploadId=uploadId;
		this.initiator = initiator;
		this.owner = owner;
		this.storageClass= storageClass;
		this.initiated = initiated;
	}
	///Returns the user who initiated this multipart upload.
	/**
	 * 
	 * @return initiator
	 */
	public String getInitiator() {
		return initiator;
	}
	/// Sets the user who initiated this multipart upload.
	/**
	 * 
	 * @param initiator
	 */
	public void setInitiator(String initiator) {
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
	public void setKey(String key) {
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
	public void setUpoadId(String uploadId) {
		this.uploadId = uploadId;
	}
	///Returns the owner of this multipart upload.
	/**
	 * 
	 * @return Owner
	 */
	public String getOwner() {
		return owner;
	}
	/// Sets the owner of this multipart upload.
	/**
	 * 
	 * @param owner
	 */
	public void setOwner(String owner) {
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
	public void setStorageClass(String storageClass) {
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
	public void setInitiated(String initiated) {
		this.initiated = initiated;
	}
}

package org.jcs.dss.main;

public class MultipartUpload {

	private String initiated;
	private String key;
	private String uploadId;
	private String initiator;
	private String owner;
	private String storageClass;
	
	public MultipartUpload( String key,String uploadId, String initiator, String owner, String storageClass,String initiated) {
		super();
		this.key = key;
		this.uploadId=uploadId;
		this.initiator = initiator;
		this.owner = owner;
		this.storageClass= storageClass;
		this.initiated = initiated;
	}
	
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getUploadId() {
		return uploadId;
	}
	public void setUpoadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getStorageClass() {
		return storageClass;
	}
	public void setStorageClass(String storageClass) {
		this.storageClass = storageClass;
	}
	
	public String getInitiated() {
		return initiated;
	}
	public void setInitiated(String initiated) {
		this.initiated = initiated;
	}
	
}

package org.jcs.dss.main;

public class InitiateMultipartUploadResult {

	private String bucketName;
	private String key;
	private String uploadId;
	
	public InitiateMultipartUploadResult(String bucketName, String key,String uploadId) {
		super();
		this.bucketName = bucketName;
		this.key = key;
		this.uploadId=uploadId;
	}
	
	public String getbucketName() {
		return bucketName;
	}
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
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
	
}

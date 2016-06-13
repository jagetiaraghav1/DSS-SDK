package org.jcs.dss.main;

public class CompleteMultipartUploadResult {
	
	private String bucketName;
	private String key;
	private String ETag;
	
	public CompleteMultipartUploadResult(String bucketName, String key,String ETag) {
		super();
		this.bucketName = bucketName;
		this.key = key;
		this.ETag=ETag;
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
	
	public String getETag() {
		return ETag;
	}
	public void setETag(String ETag) {
		this.ETag = ETag;
	}


}

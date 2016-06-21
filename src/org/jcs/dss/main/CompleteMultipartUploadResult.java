package org.jcs.dss.main;
///The CompleteMultipartUploadResult contains all the information returns from the CompleteMultipartUpload method.
public class CompleteMultipartUploadResult {

	private String bucketName;
	private String key;
	private String ETag;
///Constructors
	public CompleteMultipartUploadResult(String bucketName, String key,String ETag) {
		super();
		this.bucketName = bucketName;
		this.key = key;
		this.ETag=ETag;
	}
	///Returns the name of the bucket containing the completed multipart object.
	/**
	 * 
	 * @return BucketName
	 */
	public String getbucketName() {
		return bucketName;
	}
	///Sets the name of the bucket containing the completed multipart object.
	/**
	 * 
	 * @param BucketName
	 */
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	///Returns the key by which the newly created object is stored.
	/**
	 * 
	 * @return Key
	 */
	public String getKey() {
		return key;
	}
	///Sets the key of the newly created object.
	/**
	 * 
	 * @param Key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	///Returns the entity tag identifying the new object.
	/**
	 * @return ETag
	 */
	public String getETag() {
		return ETag;
	}
	///Returns the entity tag identifying the new object.
	/**
	 * 
	 * @param ETag
	 */
	public void setETag(String ETag) {
		this.ETag = ETag;
	}
}

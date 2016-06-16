package org.jcs.dss.main;
///Contains the results of initiating a multipart upload (initMPUpload), particularly the unique ID of the new multipart upload.
public class InitiateMultipartUploadResult {

	private String bucketName;
	private String key;
	private String uploadId;
	/// Constructors
	public InitiateMultipartUploadResult(String bucketName, String key,String uploadId) {
		super();
		this.bucketName = bucketName;
		this.key = key;
		this.uploadId=uploadId;
	}
	///Returns the name of the bucket in which the new multipart upload was initiated.
	/**
	 * @return BucketName
	 */
	public String getbucketName() {
		return bucketName;
	}
	///Sets the name of the bucket in which the new multipart upload was initiated.
	/**
	 * 
	 * @param BucketName
	 */
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	///Returns the object key for which the multipart upload was initiated.
	/**
	 * @return Key
	 */
	public String getKey() {
		return key;
	}
	///Sets the object key for which the multipart upload was initiated.
	/**
	 * 
	 * @param Key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	///Returns the initiated multipart upload ID.
	/**
	 * 
	 * @return UploadID
	 */
	public String getUploadId() {
		return uploadId;
	}
	///Sets the initiated multipart upload ID.
	/**
	 * 
	 * @param UploadID
	 */
	public void setUpoadId(String uploadId) {
		this.uploadId = uploadId;
	}
}

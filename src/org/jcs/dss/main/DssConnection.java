package org.jcs.dss.main;
import java.net.URL;
import java.util.List;
import org.jcs.dss.http.Response;
import org.jcs.dss.op.*;
///Provides an interface to the client for accessing the JCS DSS web service.
/**

  		 The JCS DSS java-sdk  provides a interface that can be used to store and retrieve any amount of data, at any time, from anywhere on the web.

 */

public class DssConnection {

	private String accessKey;
	private String secretKey;
	private String host;
	private static boolean isSecure;
	/// Constructors
	public DssConnection(String accessKey, String secretKey, String host,boolean isSecure) {
		DssConnection.isSecure = isSecure;
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.host = host;
	}
	/// Returns if the connection is secure or not
		/**
		 * 
		 * @return String : isSecure	
		 */
		public static boolean getIsSecure() {
			return isSecure;
		}
	/// Returns the Access key entered by client
	/**
	 * 
	 * @return String : AccessKey	
	 */
	public String getAccessKey() {
		return accessKey;
	}
	/// Returns the Secret key entered by client
	/**
	 * 
	 * @return String : SecretKey	
	 */
	public String getSecretKey() {
		return secretKey;
	}
	/// Returns the Host address entered by client
	/**
	 * 
	 * @return String : Host	
	 */
	public String getHost() {
		return host;
	}

	/// Creates a new DSS bucket
	/**
	 * 
	 * @param BucketName : Name of the DSS bucket to be created. 
	 * @return Bucket 
	 * @throws Exception
	 */
	public Bucket createBucket(String bucketName) throws Exception {
		CreateBucketOp op = new CreateBucketOp(this, bucketName);
		op.execute();
		Bucket bucket = (Bucket) op.processResult(bucketName);
		return bucket;
	}
	/// Returns the List of DSS buckets owned by authenticated sender
	/**
	 * 
	 * @return List<Bucket> : List of all buckets.
	 * @throws Exception
	 */
	public List<Bucket> listBuckets() throws Exception {
		ListBucketsOp op = new ListBucketsOp(this);
		Response resp = op.execute();
		@SuppressWarnings("unchecked")
		List<Bucket> bucketList = (List<Bucket>) op.processResult(resp);
		return bucketList;
	}
	/// Returns the List of objects and its information associated with the requested bucket
	/**
	 * 
	 * @param BucketName : Name of DSS bucket whose objects are need to be listed.
	 * @return List<DssObject> : List of all DSS objects and their information associated with the requested bucket.
	 * @throws Exception
	 */
	public List<DssObject> listObjects(String bucketName) throws Exception {
		ListObjectsOp op = new ListObjectsOp(this, bucketName);
		Response resp = op.execute();
		@SuppressWarnings("unchecked")
		List<DssObject> objectList = (List<DssObject>) op.processResult(resp);
		return objectList;
	}
	///Deletes the requested bucket
	/**
	 * 
	 * @param BucketName : Name of the DSS bucket need to be deleted.
	 * 
	 * @throws Exception
	 */
	public void deleteBucket(String bucketName) throws Exception {
		DeleteBucketOp op = new DeleteBucketOp(this, bucketName);
		op.execute();
	}
	///Uploads a new file in the specified DSS bucket 
	/**
	 * 
	 * @param BucketName : Name of the DSS bucket to where data need to be uploaded.
	 * @param ObjectName : Sets the key under which to store the new object.
	 * @param FilePath : Path of the file from where file is need to be uploaded.
	 * @return PutObjectResult : Returns details related to uploaded object.
	 * @throws Exception
	 */
	public PutObjectResult uploadObjectFromFileName(String bucketName, String objectName,
			String filePath) throws Exception {
		PutObjectOp op = new PutObjectOp(this,bucketName,objectName,filePath);
		Response resp = op.execute();	
		PutObjectResult putObject = (PutObjectResult) op.processResult(resp);
		return putObject;
	}
	///Downloads the requested object from the specified bucket to the requested file path
	/**
	 * 
	 * @param BucketName : Name of the DSS bucket from where data need to be download.
	 * @param ObjectName : Sets the key under which object is stored.
	 * @param FilePath : Path of the file where file is need to be download.
	 * @throws Exception
	 */
	public void downloadObjectToFileName(String bucketName, String objectName,
			String filePath) throws Exception {
		GetObjectOp op = new GetObjectOp(this, bucketName,objectName,filePath);
		String resp = op.Execute();
		op.processResult(resp);
	}
	///Returns the information associated with the specified object in the requested bucket
	/**
	 * 
	 * @param BucketName : Name of the bucket where the requested object exists.
	 * @param ObjectName : Key of the Object whose information needed to be fetched
	 * @return Objectdata : Returns details related to object.
	 * @throws Exception
	 */
	public Objectdata getObjectDetail(String bucketName, String objectName) throws Exception {
		GetObjectDetailOp op = new GetObjectDetailOp(this, bucketName,objectName);
		Response resp = op.execute();
		Objectdata objectdetail =  (Objectdata) op.processResult(resp);
		return objectdetail;
	}
	///Deletes the specified object in the specified bucket
	/**
	 * 
	 * @param BucketName : Name of the bucket where the requested object exists.
	 * @param ObjectName : Key of the Object to remove.
	 * @throws Exception
	 */
	public void deleteObject(String bucketName, String objectName) throws Exception {
		DeleteObjectOp op = new DeleteObjectOp(this,bucketName,objectName);
		op.execute();
	}
	///Copies object from specified source to the new destination
	/**
	 * By default, all object metadata for the source object, storage-class are copied to the new destination object.
	 * 
	 * Note : CopySource must be of the form bucketName/objectName or else operation will fail.
	 * @param BucketName : Name of the bucket where object need to be copied.
	 * @param objectName : Key of the newly copied object.
	 * @param copySource : Path of the object where original data is present. 
	 * @return CopyObjectResult : Returns the data related to newly copied object.
	 * @throws Exception
	 */
	public CopyObjectResult copyObject(String bucketName, String objectName,String copySource) throws Exception{
		CopyObjectOp op = new CopyObjectOp(this,bucketName,objectName,copySource);
		Response resp = op.execute();
		CopyObjectResult copyObject = (CopyObjectResult) op.processResult(resp);
		return copyObject;
	}
	///Performs head bucket operation to determine if the requested bucket exists and if you have permission to access it
	/**
	 * 
	 * @param BucketName : Name of the bucket to execute headBucket operation. 
	 * @return Response
	 * @throws Exception
	 */
	public Response headBucket(String bucketName) throws Exception {
		HeadBucketOp op = new HeadBucketOp(this,bucketName);
		Response resp =op.execute();
		return resp;
	}
	///Performs head object operation to determine if the requested object in the specified bucket exists and if you have permission to access it
	/**
	 * 
	 * @param BucketName : Name of the bucket where the requested object exists.
	 * @param ObjectName : Key of the object to execute headObject operation.
	 * @return Response
	 * @throws Exception
	 */
	public Response headObject(String bucketName, String objectName) throws Exception {
		HeadObjectOp op = new HeadObjectOp(this,bucketName,objectName);
		Response resp =op.execute();
		return resp;
	}
	///Returns a URL for accessing a DSS resource
	/**
	 * 
	 * @param BucketName : Name of the bucket where the requested object exists.
	 * @param ObjectName : Key in the specified bucket under which the desired object is stored.
	 * @param Expirytime : The time at which the returned pre-signed URL will expire.
	 * @return URL : A pre-signed URL which expires at the specified time, and can be used to allow anyone to download the specified object from DSS, without exposing the owner's JCS secret access key.
	 * @throws Exception
	 */
	public URL getPresignedURL(String bucketName, String objectName,int expirytime) throws Exception {
		GetPresignedURLOp op = new GetPresignedURLOp(this,bucketName,objectName,expirytime);
		URL url = op.Execute();
		return url;
	}
	///Initiates a multipart upload and returns an InitiateMultipartUploadResult which contains an upload ID
	/**
	 * 
	 * @param BucketName : Name of the bucket in which to create the new multipart upload.
	 * @param ObjectName : Key by which to store the new multipart upload.
	 * @return InitiateMultipartUploadResult : Returns bucket name,key and upload ID related to multipart upload.
	 * @throws Exception
	 */
	public InitiateMultipartUploadResult initMPUpload(String bucketName, String objectName) throws Exception {
		InitMPUploadOp op = new InitMPUploadOp(this,bucketName,objectName);
		Response resp = op.execute();
		InitiateMultipartUploadResult initMultipart = (InitiateMultipartUploadResult) op.processResult(resp);
		return initMultipart;
	}
	///List in-progress multipart Uploads associated with the specified bucket and their details 
	/**
	 * 
	 * @param BucketName : Name of the bucket whose in-progress multipart Uploads need to be listed.
	 * @return MultipartUploadListing : Details related to in-progress multipart Uploads.
	 * @throws Exception
	 */
	public MultipartUploadListing listMPUploads(String bucketName) throws Exception {
		ListMPUploadsOp op = new ListMPUploadsOp(this,bucketName);
		Response resp = op.execute();
		MultipartUploadListing multipartUploadList = (MultipartUploadListing) op.processResult(resp);
		return multipartUploadList;
	}
	///Uploads a part in a multipart upload. You must initiate a multipart upload before you can upload any part. 
	/**
	 * 
	 * @param BucketName : Name of the bucket containing the existing, initiated multipart upload. 
	 * @param ObjectName : Key of the initiated multipart upload.
	 * @param UploadId : ID of the existing, initiated multipart upload with which this new part will be associated.
	 * @param FilePath : Path of the file which is to be uploaded.
	 * @param PartSize : Size of this part, in bytes.
	 * @return List<UploadPartResult> : Returns list of ETag and part number for each part uploaded.
	 * @throws Exception
	 */
	public List<UploadPartResult> uploadPart(String bucketName, String objectName,String uploadId,
			String filePath,int partSize) throws Exception {	
		PartCreationForUploadPart uploadPart = new PartCreationForUploadPart(this, bucketName,  objectName,uploadId,filePath,partSize);
		List<UploadPartResult>  uploadList= uploadPart.createPart();
		return uploadList;
	}
	///Lists the parts that have been uploaded for the specific multipart upload which is identified by upload ID
	/**
	 * 
	 * @param BucketName : Name of the bucket containing the multipart upload.
	 * @param ObjectName : Key of the associated multipart upload whose parts are being listed.
	 * @param UploadId : ID of the multipart upload whose parts are being listed.
	 * @return PartListing : Returns details of all parts and details of associted multipart upload.
	 * @throws Exception
	 */
	public PartListing listPart(String bucketName, String objectName,String uploadId) throws Exception {
		ListPartOp op = new ListPartOp(this,bucketName,objectName,uploadId);
		Response resp = op.execute();
		PartListing partList = (PartListing) op.processResult(resp);
		return partList;
	}
	///Completes a multipart upload by assembling previously uploaded parts in the order of the part number specified
	/**
	 * 
	 * @param BucketName : Name of the bucket containing the multipart upload to complete.
	 * @param ObjectName : Key under which the multipart upload to complete is stored.
	 * @param UploadId : ID of the multipart upload to complete.
	 * @param MultipartUpload : XML string containing ETag and part number
	 * @return CompleteMultipartUploadResult : Returns the details related to completed multipart upload.
	 * @throws Exception
	 */
	public CompleteMultipartUploadResult completeMultiPart(String bucketName, String objectName,String uploadId,String multipartUpload) throws Exception {
		CompleteMPUploadOp op = new CompleteMPUploadOp(this,bucketName,objectName,uploadId,multipartUpload);
		Response resp = op.execute();
		CompleteMultipartUploadResult completeMPUpload = (CompleteMultipartUploadResult) op.processResult(resp);
		return completeMPUpload;
	}
	///Cancels a multipart upload
	/**
	 * 
	 * @param BucketName : Name of the bucket containing the multipart upload to cancel.
	 * @param ObjectName : Key of the multipart upload to cancel.
	 * @param UploadId : ID of the multipart upload to cancel.
	 * @throws Exception
	 */
	public void cancelMPUpload(String bucketName, String objectName,String uploadId) throws Exception {
		CancelMPUploadOp op = new CancelMPUploadOp(this,bucketName,objectName,uploadId);
		op.execute();
	}
}

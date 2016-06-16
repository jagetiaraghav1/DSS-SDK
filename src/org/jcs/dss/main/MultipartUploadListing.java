package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;
///The List<MultipartUploadListing> contains all the information about the listMPUploads method.
public class MultipartUploadListing {

	private String bucketName;
	private String nextKeyMarker;
	private String nextUploadIdMarker ;
	private String MaxUploads;
	private List<MultipartUpload> multipartup = new ArrayList<MultipartUpload>();
	///Constructors
	public MultipartUploadListing(String bucketName, String nextKeyMarker,String nextUploadIdMarker,String MaxUploads,List<MultipartUpload> multipartup) {
		super();
		this.bucketName = bucketName;
		this.MaxUploads = MaxUploads;
		this.nextUploadIdMarker=nextUploadIdMarker;
		this.nextKeyMarker = nextKeyMarker;
		this.multipartup = multipartup;
	}
	///Returns the name of the bucket containing the listed multipart uploads.
	/**
	 * @return BucketName
	 */
	public String getbucketName() {
		return bucketName;
	}
	///Sets the name of the bucket containing the listed multipart uploads.
	/**
	 * 
	 * @param BucketName
	 */
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	///Returns the optional maximum number of uploads to be listed.
	/**
	 * 
	 * @return MaxUploads
	 */
	public String getMaxUploads() {
		return MaxUploads;
	}
	///Sets the optional maximum number of uploads to be listed.
	/**
	 * 
	 * @param MaxUploads
	 */
	public void setMaxUploads(String MaxUploads) {
		this.MaxUploads = MaxUploads;
	}
	///Returns the next upload ID marker that should be used in the next request to get the next page of results.
	/**
	 * 
	 * @return NextUploadIdMarker
	 */
	public String getNextUploadIdMarker() {
		return nextUploadIdMarker;
	}
	///Sets the next upload ID marker that should be used in the next request to get the next page of results.
	/**
	 * 
	 * @param NextUploadIdMarker
	 */
	public void setNextUploadIdMarker(String nextUploadIdMarker) {
		this.nextUploadIdMarker = nextUploadIdMarker;
	}
	///Returns the next key marker that should be used in the next request to get the next page of results.
	/**
	 * 
	 * @return NextKeyMarker
	 */
	public String getNextKeyMarker() {
		return nextKeyMarker;
	}
	///Sets the next key marker that should be used in the next request to get the next page of results.
	/**
	 * 
	 * @param NextKeyMarker
	 */
	public void setNextKeyMarker(String nextKeyMarker) {
		this.nextKeyMarker = nextKeyMarker;
	}
	///Returns the list of multipart uploads.
	/**
	 * @return Multipartup : List of multipart uploads.
	 */

	public List<MultipartUpload> getMultipartUploads() {
		return multipartup;
	}
	///Sets the list of multipart uploads.
	/**
	 * 
	 * @param multipartup : List of multipart uploads.
	 */
	public void setMultipartUploads(List<MultipartUpload> multipartup) {
		this.multipartup = multipartup;
	}
}

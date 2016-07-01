package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
///The List<MultipartUploadListing> contains all the information about the listMPUploads method.
@XmlRootElement(name = "ListMultipartUploadsResult")
public class MultipartUploadListing {
	
	@XmlElement (name = "Bucket")
	private String bucketName;
	@XmlElement (name = "NextKeyMarker")
	private String nextKeyMarker;
	@XmlElement (name = "NextUploadIdMarker")
	private String nextUploadIdMarker ;
	@XmlElement (name = "MaxUploads")
	private String MaxUploads;
	@XmlElement (name="Upload")
	private List<MultipartUpload> multipartup = new ArrayList<MultipartUpload>();

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
	void setbucketName(String bucketName) {
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
	void setMaxUploads(String MaxUploads) {
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
	void setNextUploadIdMarker(String nextUploadIdMarker) {
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
	void setNextKeyMarker(String nextKeyMarker) {
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
	 void setMultipartUploads(List<MultipartUpload> multipartup) {
		this.multipartup = multipartup;
	}
}

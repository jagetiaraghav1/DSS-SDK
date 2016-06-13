package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;

public class MultipartUploadListing {
	
	private String bucketName;
	private String nextKeyMarker;
	private String nextUploadIdMarker ;
	private String MaxUploads;
	private List<MultipartUpload> multipartup = new ArrayList<MultipartUpload>();
	
	public MultipartUploadListing(String bucketName, String nextKeyMarker,String nextUploadIdMarker,String MaxUploads,List<MultipartUpload> multipartup) {
		super();
		this.bucketName = bucketName;
		this.MaxUploads = MaxUploads;
		this.nextUploadIdMarker=nextUploadIdMarker;
		this.nextKeyMarker = nextKeyMarker;
		this.multipartup = multipartup;
	}
	
	public String getbucketName() {
		return bucketName;
	}
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getMaxUploads() {
		return MaxUploads;
	}
	public void setMaxUploads(String MaxUploads) {
		this.MaxUploads = MaxUploads;
	}
	
	public String getNextUploadIdMarker() {
		return nextUploadIdMarker;
	}
	public void setNextUploadIdMarker(String nextUploadIdMarker) {
		this.nextUploadIdMarker = nextUploadIdMarker;
	}
	public String getNextKeyMarker() {
		return nextKeyMarker;
	}
	public void setNextKeyMarker(String nextKeyMarker) {
		this.nextKeyMarker = nextKeyMarker;
	}
	
	public List<MultipartUpload> getMultipartUploads() {
		return multipartup;
	}
	public void setMultipartUploads(List<MultipartUpload> multipartup) {
		this.multipartup = multipartup;
	}
	
	

}

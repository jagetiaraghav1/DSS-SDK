package org.jcs.dss.main;

import java.util.ArrayList;
import java.util.List;

public class PartListing {

	private String bucketName;
	private String PartNumberMarker;
	private String NextPartNumberMarker ;
	private String MaxParts;
	private String Key;
	private String UploadId;
	private String StorageClass;
	private String owner;
	private List<PartSummary> partDetails = new ArrayList<PartSummary>();
	
	public PartListing(String bucketName, String Key,String UploadId,String StorageClass,String PartNumberMarker, String NextPartNumberMarker, String MaxParts,String owner,List<PartSummary> partDetails) {
		super();
		this.bucketName = bucketName;
		this.Key = Key;
		this.UploadId=UploadId;
		this.StorageClass = StorageClass;
		this.PartNumberMarker = PartNumberMarker;
		this.NextPartNumberMarker = NextPartNumberMarker;
		this.MaxParts = MaxParts;
		this.owner = owner;
		this.partDetails = partDetails;
	}
	
	public String getbucketName() {
		return bucketName;
	}
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getMaxParts() {
		return MaxParts;
	}
	public void setMaxParts(String MaxParts) {
		this.MaxParts = MaxParts;
	}
	
	public String getKey() {
		return Key;
	}
	public void setKey(String Key) {
		this.Key = Key;
	}
	public String getUploadId() {
		return UploadId;
	}
	public void setUploadId(String UploadId) {
		this.UploadId = UploadId;
	}
	
	public String getPartNumberMarker() {
		return PartNumberMarker;
	}
	public void setPartNumberMarker(String PartNumberMarker) {
		this.PartNumberMarker = PartNumberMarker;
	}
	
	public String getNextPartNumberMarker() {
		return NextPartNumberMarker;
	}
	public void setNextPartNumberMarker(String NextPartNumberMarker) {
		this.NextPartNumberMarker = NextPartNumberMarker;
	}
	
	public String getStorageClass() {
		return StorageClass;
	}
	public void setStorageClass(String StorageClass) {
		this.StorageClass = StorageClass;
	}
	
	public List<PartSummary> getParts() {
		return partDetails;
	}
	public void setParts(List<PartSummary> partDetails) {
		this.partDetails = partDetails;
	}
	
	

}

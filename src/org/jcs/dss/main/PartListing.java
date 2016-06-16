package org.jcs.dss.main;
import java.util.ArrayList;
import java.util.List;
///The List<PartListing> contains all the information about the ListParts method.
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
	///Constructors
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
	///Returns the name of the bucket containing the listed parts.
	/**
	 * @return bucketName
	 */
	public String getbucketName() {
		return bucketName;
	}
	///Sets the name of the bucket containing the listed parts
	/**
	 * 
	 * @param bucketName
	 */
	public void setbucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	///Returns the optional max parts value to limit how many parts are listed.
	/**
	 * 
	 * @return MaxParts
	 */
	public String getMaxParts() {
		return MaxParts;
	}
	///Sets the optional max parts value to limit how many parts are listed.
	/**
	 * 
	 * @param MaxParts
	 */
	public void setMaxParts(String MaxParts) {
		this.MaxParts = MaxParts;
	}
	///Returns the key value to identify which multipart upload contains the parts to list.
	/**
	 * 
	 * @return Key
	 */
	public String getKey() {
		return Key;
	}
	///Sets the key value to identify which multipart upload contains the parts to list.
	/**
	 * 
	 * @param Key
	 */
	public void setKey(String Key) {
		this.Key = Key;
	}
	///Returns the upload ID value used to identify which multipart upload contains the parts to list
	/**
	 * .
	 * @return UploadId
	 */
	public String getUploadId() {
		return UploadId;
	}
	///Sets the upload ID value used to identify which multipart upload contains the parts to list.
	/**
	 * 
	 * @param UploadId
	 */
	public void setUploadId(String UploadId) {
		this.UploadId = UploadId;
	}
	///Returns the part number marker to specify where in the results to begin listing parts.
	/**
	 * 
	 * @return PartNumberMarker
	 */
	public String getPartNumberMarker() {
		return PartNumberMarker;
	}
	///Sets the optional part number marker to specify where in the results to begin listing parts.
	/**
	 * 
	 * @param PartNumberMarker
	 */
	public void setPartNumberMarker(String PartNumberMarker) {
		this.PartNumberMarker = PartNumberMarker;
	}
	///Returns the next part number marker.
	/**
	 * 
	 * @return NextPartNumberMarker
	 */
	public String getNextPartNumberMarker() {
		return NextPartNumberMarker;
	}
	///Sets the next part number marker.
	/**
	 * 
	 * @param NextPartNumberMarker
	 */
	public void setNextPartNumberMarker(String NextPartNumberMarker) {
		this.NextPartNumberMarker = NextPartNumberMarker;
	}
	///Returns the class of storage used for the parts in the associated multipart upload.
	/**
	 * 
	 * @return StorageClass
	 */
	public String getStorageClass() {
		return StorageClass;
	}
	///Sets the class of storage used for the parts in the associated multipart upload.
	/**
	 * 
	 * @param StorageClass
	 */
	public void setStorageClass(String StorageClass) {
		this.StorageClass = StorageClass;
	}
	///Returns ID of the user who owns the associated multipart upload.
	/**
	 * 
	 * @return Owner
	 */
	public String getOwner() {
		return owner;
	}
	///Sets the user who owns the associated multipart upload.
	/**
	 * 
	 * @param owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	///Returns the list of parts and detail of each individual part.
	/**
	 * 
	 * @return partDetails : List of parts
	 */
	public List<PartSummary> getParts() {
		return partDetails;
	}
	///Sets the list of parts and detail of each individual part.
	/**
	 * 
	 * @param partDetails : List of parts
	 */
	public void setParts(List<PartSummary> partDetails) {
		this.partDetails = partDetails;
	}



}

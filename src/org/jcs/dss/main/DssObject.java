package org.jcs.dss.main;

public class DssObject {

	String name;
	Bucket bucket;
	String size;
	String ownerId;
	String lastModified;
	
	
	public DssObject(String name,String lastModified,String size,String ownerId) {
		super();
		this.name = name;
		this.bucket = bucket;
		this.lastModified=lastModified;
		this.ownerId=ownerId;
		this.size=size;
	}
	
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	public Bucket getBucket() {
		return bucket;
	}
	
	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	
}

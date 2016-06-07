package org.jcs.dss.main;

public class Bucket {
	
	private String name;
	private String creationDate;
	
	public Bucket(String name, String creationDate) {
		super();
		this.name = name;
		this.creationDate = creationDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	

}

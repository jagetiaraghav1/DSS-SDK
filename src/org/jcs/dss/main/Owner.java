package org.jcs.dss.main;

import javax.xml.bind.annotation.XmlElement;

public class Owner {
	@XmlElement (name = "DisplayName")
	private String DisplayName;

	///Returns the owner's name of this multipart upload.
	/**
	 * 
	 * @return DisplayName
	 */
	public String getDisplayName() {
		return DisplayName;
	}
	///Sets the owner's name of this multipart upload.
	/**
	 * 
	 * @param DisplayName
	 */
	void setDisplayName(String DisplayName) {
		this.DisplayName = DisplayName;
	}

}

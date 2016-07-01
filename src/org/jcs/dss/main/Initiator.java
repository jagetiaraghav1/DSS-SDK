package org.jcs.dss.main;

import javax.xml.bind.annotation.XmlElement;

public class Initiator {

	@XmlElement (name ="DisplayName")
	private String initiator;
	///Returns the user who initiated this multipart upload.
	/**
	 * 
	 * @return initiator
	 */
	public String getInitiator() {
		return initiator;
	}
	/// Sets the user who initiated this multipart upload.
	/**
	 * 
	 * @param initiator
	 */
	void setInitiator(String initiator) {
		this.initiator = initiator;
	}
}

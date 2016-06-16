package org.jcs.dss.main;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType(propOrder={"partNumber", "ETag"})
///Contains the details returned from JCS DSS after calling the UploadPart operation.
public class UploadPartResult {

	@XmlElement(name = "ETag")
	private String ETag;
	@XmlElement(name="PartNumber")
	private String partNumber;

	public UploadPartResult(){}
	///Constructors
	public UploadPartResult(String ETag, String PartNumber) {
		super();
		this.ETag= ETag;
		this.partNumber = PartNumber;
	}
	///Returns the entity tag of the newly uploaded part.
	/***
	 * 
	 * @return ETag
	 */
	public String getETag() {
		return ETag;
	}
	///Sets the entity tag of the newly uploaded part.
	/**
	 * 
	 * @param ETag
	 */
	public void setEtag(String ETag) {
		this.ETag = ETag;
	}
	///Returns the part number of the newly uploaded part.
	/**
	 * 
	 * @return PartNumber
	 */
	public String getpartNumber() {
		return partNumber;
	}
	///Sets the part number of the newly uploaded part.
	/**
	 * 
	 * @param PartNumber
	 */
	public void setPartNumber(String PartNumber) {
		this.partNumber = PartNumber;
	}	
}

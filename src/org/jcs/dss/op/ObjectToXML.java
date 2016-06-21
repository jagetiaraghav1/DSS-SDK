package org.jcs.dss.op;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.*;
import org.jcs.dss.main.Part;
import org.jcs.dss.main.UploadPartResult;
///Class to generate XML string to be use in complete multi-part upload
public class ObjectToXML {
	///Generation of string using JAXB marshalling
	/**
	 * 
	 * @param Uploadpart : List of ETag and part number of all uploaded parts
	 * @return xmlString : XML String to be used in complete Multi-part upload
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static String GenrateXML(List<UploadPartResult> Uploadpart) throws JAXBException, IOException {
		StringWriter xml = new StringWriter();
		JAXBContext jaxbContext = JAXBContext.newInstance(Part.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		Part part = new Part(Uploadpart);
		jaxbMarshaller.marshal(part, xml);
		String xmlString = xml.toString();
		return xmlString;
	}
}

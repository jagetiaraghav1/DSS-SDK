package org.jcs.dss.op;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.*;
import org.jcs.dss.main.Part;
import org.jcs.dss.main.UploadPartResult;
public class ObjectToXML {

	public static String marshalingExample(List<UploadPartResult> Uploadpart) throws JAXBException, IOException {
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

package org.jcs.dss.op;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jcs.dss.main.Part;
import org.jcs.dss.main.UploadPartResult;
public class ObjectToXML {
		

	public static void marshalingExample(List<UploadPartResult> Uploadpart) throws JAXBException, FileNotFoundException {
	    JAXBContext jaxbContext = JAXBContext.newInstance(Part.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    Part part = new Part(Uploadpart);
	    jaxbMarshaller.marshal(part, new File("/home/raghav/Desktop/question.xml"));  
	    
	}
}

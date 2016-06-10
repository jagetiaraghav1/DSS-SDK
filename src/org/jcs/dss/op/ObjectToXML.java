package org.jcs.dss.op;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.transform.Result;

import org.jcs.dss.main.Part;
import org.jcs.dss.main.UploadPartResult;
public class ObjectToXML {
		

	public static String marshalingExample(List<UploadPartResult> Uploadpart) throws JAXBException, IOException {
		StringWriter sw = new StringWriter();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Part.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    Part part = new Part(Uploadpart);
	    jaxbMarshaller.marshal(part, new File("/home/raghav/Desktop/question1.xml"));  
	    jaxbMarshaller.marshal(part, sw);
		String xmlString = sw.toString();
		
	/*	BufferedReader br = new BufferedReader(new FileReader(new File("/home/raghav/Desktop/question1.xml")));
		String line;
		StringBuilder sb = new StringBuilder();

		while((line=br.readLine())!= null){
		    sb.append(line.trim());
		}*/
		//xmlString.replace()
		/*xmlString.replaceAll("\n",""); 
		xmlString.replaceAll("\t","");*/

		//System.out.println(xmlString);
			return xmlString;
	}
}

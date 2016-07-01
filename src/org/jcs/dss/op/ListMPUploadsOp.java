package org.jcs.dss.op;

import java.io.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
///Class to list all the on-going MultiPart uploads associated with the requested bucket
public class ListMPUploadsOp extends BucketOp {
	///Constructors
	public ListMPUploadsOp(DssConnection conn, String bucketName) throws UnsupportedEncodingException {
		super(conn, bucketName);
		httpMethod = "GET";
		queryStr = "uploads";
		queryStrForSignature="uploads";
	}
	///This method extracts information such as key, ETag etc from InputStream got from Server
	/**
	 * @param Response : Response message got from Request.request()
	 * @return CompleteMultipartUploadResult : A class that contains all the information about a successful uploaded object
	 * @throws IOException
	 */
	@Override
	public Object processResult(Object resp) throws IOException{
		String XML = ((Response) resp).getXMLString();
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(MultipartUploadListing.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//Filter Namespace
			final SAXParserFactory sax = SAXParserFactory.newInstance();
			sax.setNamespaceAware(false);
			final XMLReader reader = sax.newSAXParser().getXMLReader();
			final Source er = new SAXSource(reader, new InputSource(new StringReader(XML)));
			
			return (MultipartUploadListing) jaxbUnmarshaller.unmarshal(er);
			
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println("JAXB Error");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
		
	}	
}


package org.jcs.dss.examples;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


import org.jcs.dss.main.Bucket;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.DssObject;
import org.jcs.dss.main.InitiateMultipartUploadResult;
import org.jcs.dss.main.UploadPartResult;
import org.jcs.dss.op.ObjectToXML;
import org.jcs.dss.http.Response;
public class DssExample {

	public static void main(String[] args) throws Exception {
		// To connect to the Dss Server.Takes access key, secret key and host of the resource
		DssConnection conn = new DssConnection
				("Y7PUMEKBZORIP07W2A1W",
						"OfKjGgFzcrRgi7JSyCaM3qZc8fqWXshFYMGyXQ7b",
						"http://192.168.56.111:7480");

		//Creates a bucket. Takes bucket name as input
		/*conn.createBucket("my-new-bucket");*/

		//List all the buckets. First create a List array and call listBuckets() and it will store list of buckets in that array
		List<Bucket> listbucket = new ArrayList<Bucket>();
		listbucket = conn.listBuckets();
		for (int i = 0; i < listbucket.size(); i++) {
			System.out.println(listbucket.get(i).getName());
		}

		// Uploads object to the bucket.Takes bucketname, filename to be stored in bucket, and path of the file
		//conn.uploadObjectFromFileName("my-new-bucket", "file.odt","/home/raghav/Desktop/DSS-SDK_explain.odt");


		//List all the objects in a bucket. First create a List array and call listObjects("bucketname") and it will store list of objects in that array 
		List<DssObject> dssobjects = new ArrayList<DssObject>();
		dssobjects = conn.listObjects("my-new-bucket");
		for (int i = 0; i < dssobjects.size(); i++) {
			System.out.println(dssobjects.get(i).getName());
		}

		/*	//Download object from the bucket.Takes bucketname, filename which need to be download from bucket, and path of the file where file need to be saved
		conn.downloadObjectToFileName("my-bucket","file.mp4","/home/raghav/Desktop/filedownload.mp4");



		//Deletes the object from the bucket. Takes bucket name and object
		conn.deleteObject("my-new-bucket", "file.mp4");

		// Deletes the bucket. Takes bucket name as input
		conn.deleteBucket("my-bucket");*/
		//conn.copyObject("my-new-bucket", "copyTest.mp4", "my-bucket/file.mp4");
		//conn.headBucket("my-new-bucket");
		/*Response resp =conn.headObject("my-new-bucket", "file.odt");
		System.out.println(resp.getStatusCode());*/


		//conn.getPresignedURLOp("my-new-bucket", "file.odt", 600);

/*		InitiateMultipartUploadResult initMPUploadOp = new InitiateMultipartUploadResult(null,null,null);
		initMPUploadOp = conn.initMPUpload("my-bucket", "MultipartUp");

		System.out.println(initMPUploadOp.getUploadId());
*/



		/*	File f = new File("/home/raghav/Desktop/video1.mp4");
		System.out.println(f.getParent());
		System.out.println(f.getName());
		int partCounter = 1;

		int sizeOfFiles = 1024 * 1024;// 1MB
		byte[] buffer = new byte[sizeOfFiles];

		try (BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f))) {
			String name = f.getName();

			int tmp = 0;
			while ((tmp = bis.read(buffer)) > 0) {
				File newFile = new File(f.getParent(), name + "."
						+ Integer.toString(partCounter++));
				try (FileOutputStream out = new FileOutputStream(newFile)) {
					out.write(buffer, 0, tmp);
					List<UploadPartResult> Uploadpart = new ArrayList<UploadPartResult>();
					Uploadpart = conn.uploadPart("my-new-bucket", "hello", "2~rsr0thorLXq_t9Ib-cQ_o1VDNNud3vS", Integer.toString(partCounter-1), newFile.getParent()+"/"+newFile.getName(),sizeOfFiles);
					for (int i = 0; i < Uploadpart.size(); i++) {
						System.out.println(Uploadpart.size());
						System.out.println(Uploadpart.get(i).getETag());
					}	
					newFile.delete();
				}
			}
		}*/
		int sizeOfFiles = 1024 * 1024;

		List<UploadPartResult> Uploadpart = new ArrayList<UploadPartResult>();

		Uploadpart = conn.uploadPart("my-new-bucket", "hello", "2~rsr0thorLXq_t9Ib-cQ_o1VDNNud3vS", "/home/raghav/Desktop/video1.mp4",sizeOfFiles);
		for (int i = 0; i < Uploadpart.size(); i++) {
			System.out.println(Uploadpart.get(i).getPartNumber());
			System.out.println(Uploadpart.get(i).getETag());
		}	
		//Uploadpart.clear();

	    ObjectToXML xml = new ObjectToXML();
		xml.marshalingExample(Uploadpart);
		//conn.uploadPart("my-new-bucket", "hello", "2~rsr0thorLXq_t9Ib-cQ_o1VDNNud3vS", "3", "/home/raghav/Desktop/video1.mp4");
		conn.listPart("my-new-bucket", "hello", "2~rsr0thorLXq_t9Ib-cQ_o1VDNNud3vS");
		//conn.listMPUploadsOp("my-bucket");
		System.out.println("Done!!!");



	}
}
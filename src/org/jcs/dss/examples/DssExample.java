package org.jcs.dss.examples;

import java.util.ArrayList;
import java.util.List;
import org.jcs.dss.main.*;
public class DssExample {

	public static void main(String[] args) throws Exception {
		// To connect to the Dss Server.Takes access key, secret key and host of the resource
		DssConnection conn = new DssConnection
				("Y7PUMEKBZORIP07W2A1W",
						"OfKjGgFzcrRgi7JSyCaM3qZc8fqWXshFYMGyXQ7b",
						"http://192.168.56.111:7480");

		//Creates a bucket. Takes bucket name as input
		Bucket buck = conn.createBucket("my-new-bucket");
		System.out.println(buck.getCreationDate());
		//List all the buckets. First create a List array and call listBuckets() and it will store list of buckets in that array
		List<Bucket> listbucket = new ArrayList<Bucket>();
		listbucket = conn.listBuckets();
		for (int i = 0; i < listbucket.size(); i++) {
			System.out.println(listbucket.get(i).getName());
			System.out.println(listbucket.get(i).getOwner());
		}

		// Uploads object to the bucket.Takes bucketname, filename to be stored in bucket, and path of the file
		/*PutObjectResult put = conn.uploadObjectFromFileName("my-new-bucket", "file.odt","/home/raghav/Desktop/DSS-SDK_explain.odt");
		System.out.println(put.getETag());
*/
		//List all the objects in a bucket. First create a List array and call listObjects("bucketname") and it will store list of objects in that array 
	/*	List<DssObject> dssobjects = new ArrayList<DssObject>();
		dssobjects = conn.listObjects("my-new-bucket");
		for (int i = 0; i < dssobjects.size(); i++) {
			System.out.println(dssobjects.get(i).getBucket());
		}
*/
			//Download object from the bucket.Takes bucketname, filename which need to be download from bucket, and path of the file where file need to be saved
	//	conn.downloadObjectToFileName("my-bucket","file.mp4","/home/raghav/Desktop/filedownload.mp4");
		//System.out.println(obmeta.getContentType());
		/*Objectdata ob = conn.getObjectDetail("my-new-bucket", "file.odt");
		System.out.println(ob.getContentLength());
		

		//Deletes the object from the bucket. Takes bucket name and object

		// Deletes the bucket. Takes bucket name as input
		conn.deleteBucket("my-bucket");*/
		/*CopyObjectResult copyObject = conn.copyObject("my-new-bucket", "copyTest.mp4", "my-bucket/file.mp4");
		System.out.println(copyObject.getETag());*/
	/*	Response resp =conn.headBucket("my-new-bucket");
		System.out.println(resp.getStatusCode());*/

		/*resp =conn.headObject("my-new-bucket", "file.odt");
		System.out.println(resp.getStatusCode());*/


		//conn.getPresignedURLOp("my-new-bucket", "file.odt", 600);
		//conn.deleteObject("my-new-bucket", "ubuntu-16.04-desktop-amd64.iso");
/*
		String bucketName = "my-new-bucket";
		String key = "Testcaseupload";
		String path = "/home/raghav/Desktop/video.mp4";
		InitiateMultipartUploadResult initMPUploadOp = new InitiateMultipartUploadResult(null,null,null);
		initMPUploadOp = conn.initMPUpload(bucketName, key);

		System.out.println("initMP_uploadid:  "+initMPUploadOp.getUploadId());
		System.out.println("initMP_bucketName:  "+initMPUploadOp.getbucketName());
		System.out.println("initMP_Key:  "+initMPUploadOp.getKey());


		String Uploadid = initMPUploadOp.getUploadId();

		int sizeOfFiles = 6024 * 1024;

		List<UploadPartResult> Uploadpart = new ArrayList<UploadPartResult>();

		Uploadpart = conn.uploadPart(bucketName, key, Uploadid, path,sizeOfFiles);
		for (int i = 0; i < Uploadpart.size(); i++) {
			//System.out.println(Uploadpart.get(i).getpartNumber());
			//System.out.println(Uploadpart.get(i).getETag());
		}	
	    ObjectToXML xml = new ObjectToXML();
		String xmlString = xml.marshalingExample(Uploadpart);
		
		PartListing partList = new PartListing(null,null,null,null,null,null,null,null,null);
		partList = conn.listPart(bucketName, key, Uploadid);
		System.out.println("PartList_bucket"+partList.getbucketName());
		List<PartSummary> partSummary = new ArrayList<PartSummary>();
		partSummary = partList.getParts();
		for (int i = 0; i < partSummary.size(); i++) {
			System.out.println("partSize:   "+partSummary.size());
			System.out.println("============");
			System.out.println(partSummary.get(i).getETag());
			System.out.println(partSummary.get(i).getLastModified());

		}	
		
		
		
		MultipartUploadListing multipartUpload= conn.listMPUploads(bucketName);
		System.out.println(multipartUpload.getbucketName());
		System.out.println(multipartUpload.getNextUploadIdMarker());
		List<MultipartUpload> Multipart= new ArrayList<MultipartUpload>();
		Multipart = multipartUpload.getMultipartUploads();
		for (int i = 0; i < Multipart.size(); i++) {
			System.out.println(Multipart.get(i).getKey());
			System.out.println(Multipart.get(i).getUploadId());

		}	
		//conn.cancelMPUpload("my-new-bucket",key,Uploadid);

		System.out.println("=======");
		CompleteMultipartUploadResult compmultipart = new CompleteMultipartUploadResult(null,null,null);
		compmultipart = conn.completeMultiPart(bucketName, key, Uploadid, xmlString);
		System.out.println(compmultipart.getbucketName());
		System.out.println(compmultipart.getETag());
		System.out.println(compmultipart.getKey());

		*/
		System.out.println("Done!!!");



	}
}
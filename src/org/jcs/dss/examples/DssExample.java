package org.jcs.dss.examples;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.jcs.dss.http.Response;
import org.jcs.dss.main.*;
import org.jcs.dss.op.ObjectToXML;
public class DssExample {
	private static Scanner scan;

	public static void main(String[] args) throws Exception {
		// To connect to the Dss Server.Takes access key, secret key and host of the resource
		DssConnection conn = new DssConnection
				("Y7PUMEKBZORIP07W2A1W",
						"OfKjGgFzcrRgi7JSyCaM3qZc8fqWXshFYMGyXQ7b",
						"http://192.168.56.111:7480",false);

		String Bucket = "my-hello-bucket";
		String Key = "newObject";
		String UploadPath = "/home/raghav/Desktop/video1.mp4";
		String DownloadPath = "/home/raghav/Desktop/download.mp4";	
		String JCSCopySource = "";
		int ExpiryTime = 600;
		int SizeOfFiles = 6024*1024;
		int c = 0;
		int num =1 ;
		scan = new Scanner(System.in);

		for(int j = 0;j<1000;j++){
			while(num!=-1){
				System.out.print("Enter number: ");
				num = scan.nextInt()	;

			//System.out.println("Test Number ====================================================="+ j);
		/*	for(int k =1; k<15;k++){
				if(k!=7 && k!=8 && k!=9 )
					num = k;
				else
					num =0;*/

				switch(num){
				case 0:
					System.out.println("proceed further");
					break;
				case 1 :
					Bucket bucket = conn.createBucket(Bucket);
					System.out.println("Create Bucket API : ");
					System.out.println("Bucket Name : " + bucket.getName());
					System.out.println("Bucket Creation Date : " + bucket.getCreationDate());
					break;
				case 2 :
					List<Bucket> listbuckets = new ArrayList<Bucket>();
					listbuckets = conn.listBuckets();
					System.out.println("List Bucket API : ");
					for (int i = 0; i < listbuckets.size(); i++) {
						System.out.println("{"+(i+1)+"} : ");
						System.out.println("Bucket Name : " +listbuckets.get(i).getName());
						System.out.println("Bucket Owner : " +listbuckets.get(i).getOwner());
						System.out.println("Bucket Creation Date : "+listbuckets.get(i).getCreationDate());
					}
					break;
				case 3 :
					PutObjectResult PutObject = conn.uploadObjectFromFileName(Bucket, Key,UploadPath);
					System.out.println("Put Object API : ");
					System.out.println("ETag : "+PutObject.getETag());
					System.out.println("Upload Date : "+PutObject.getUploadDate());
					break;
				case 4 :
					List<DssObject> Dssobjects = new ArrayList<DssObject>();
					Dssobjects = conn.listObjects(Bucket);
					System.out.println("List Object API : ");
					System.out.println("Bucket Name" +Dssobjects.get(0).getBucket());
					for (int i = 0; i < Dssobjects.size(); i++) {
						System.out.println("{"+(i+1)+"} : ");
						System.out.println("Object Name : " + Dssobjects.get(i).getName());
						System.out.println("Object Owner : "+Dssobjects.get(i).getOwnerId());
						System.out.println("Last Modified Date : " +Dssobjects.get(i).getLastModified());
						System.out.println("Object Size : " +Dssobjects.get(i).getSize());
					}
					break;
				case 5:
					System.out.println("Download Object API");
					conn.downloadObjectToFileName(Bucket,Key,DownloadPath);
					break;
				case 6:
					Objectdata objectdata = conn.getObjectDetail(Bucket, Key);
					System.out.println("Get Object API : ");
					System.out.println("Content Type :"+objectdata.getContentType());
					System.out.println("ETag :"+objectdata.getETag());
					System.out.println("Last Modified :"+objectdata.getLastModified());
					System.out.println("Content Length :"+ objectdata.getContentLength());
					break;
				case 7:
					System.out.println("Delete Object API");
					conn.deleteObject(Bucket, Key);
					break;
				case 8:
					System.out.println("Delete Bucket API");
					conn.deleteBucket(Bucket);
					break;
				case 9:
					CopyObjectResult CopyObject = conn.copyObject(Bucket, Key,JCSCopySource);
					System.out.println("Copy Object API : ");
					System.out.println("ETag : "+CopyObject.getETag());
					System.out.println("Last Modified : " +CopyObject.getLastModifiedDate());
					break;
				case 10:
					Response response =conn.headBucket(Bucket);
					System.out.println("Head Bucket API : ");
					System.out.println("Respnse Code : "+ response.getStatusCode());
					System.out.println("Response Message : "+response.getStatusMsg());
					break;
				case 11:
					Response Obresponse =conn.headObject(Bucket, Key);
					System.out.println("Head Object API : ");
					System.out.println("Respnse Code : "+ Obresponse.getStatusCode());
					System.out.println("Response Message : "+Obresponse.getStatusMsg());
					break;
				case 12 : 
					URL Url = conn.getPresignedURL(Bucket, Key, ExpiryTime);
					System.out.println("Get Presigned URL : ");
					System.out.println("Download URL : "+Url);
					break;
				case 13 :
					InitiateMultipartUploadResult InitMPUploadOp = new InitiateMultipartUploadResult(null,null,null);
					InitMPUploadOp = conn.initMPUpload(Bucket, Key);
					System.out.println("Initiate MultiPart Upload API : ");
					System.out.println("Upload ID : "+InitMPUploadOp.getUploadId());
					System.out.println("Bucket Name : "+InitMPUploadOp.getbucketName());
					System.out.println("Key : "+InitMPUploadOp.getKey());
					String	UploadId = InitMPUploadOp.getUploadId();
					System.out.println("===================================================");

					List<UploadPartResult> uploadpart = new ArrayList<UploadPartResult>();
					System.out.println("Upload Part API : ");
					uploadpart = conn.uploadPart(Bucket, Key, UploadId, UploadPath,SizeOfFiles);
					for (int i = 0; i < uploadpart.size(); i++) {
						System.out.println("{"+(i+1)+"} : ");
						System.out.println("Partnumber  : "+uploadpart.get(i).getpartNumber());
						System.out.println("ETag   : "+uploadpart.get(i).getETag());
					}

					System.out.println("===================================================");


					String	XmlString = ObjectToXML.GenrateXML(uploadpart);
					PartListing PartList = new PartListing(null,null,null,null,null,null,null,null,null);
					System.out.println("List Part API : ");
					PartList = conn.listPart(Bucket, Key, UploadId);
					System.out.println("Bucket Name :  "+PartList.getbucketName());
					System.out.println("Key : "+PartList.getKey());
					System.out.println("Maximum Parts :  "+PartList.getMaxParts());
					System.out.println("Next Part Number Marker :  "+PartList.getNextPartNumberMarker());
					System.out.println("Owner Name :  "+PartList.getOwner());
					System.out.println("Part Marker Number :  "+PartList.getPartNumberMarker());
					System.out.println("Storage Class :  "+PartList.getStorageClass());
					System.out.println("Upload Id :  "+PartList.getUploadId());

					List<PartSummary> PartSummary = new ArrayList<PartSummary>();
					PartSummary = PartList.getParts();
					for (int i = 0; i < PartSummary.size(); i++) {
						System.out.println("{"+(i+1)+"} : ");
						System.out.println("Part Size : "+PartSummary.get(i).getSize());
						System.out.println("ETag :"+PartSummary.get(i).getETag());
						System.out.println("Last Modified : "+PartSummary.get(i).getLastModified());
						System.out.println("PartNumber : "+PartSummary.get(i).getPartNumber());
					}	
					System.out.println("===================================================");
					/*System.out.print("Press 1 to complete multipart upload or 2 to abort: ");
					c = scan.nextInt();*/
					c=1;
					if(c==1){
						CompleteMultipartUploadResult completemultipart = new CompleteMultipartUploadResult(null,null,null);
						completemultipart = conn.completeMultiPart(Bucket, Key, UploadId, XmlString);
						System.out.println("Complete MultiPart Upload API : ");
						System.out.println("Bucket Name : "+completemultipart.getbucketName());
						System.out.println("ETag : "+completemultipart.getETag());
						System.out.println("Key : "+completemultipart.getKey());
					}
					else if (c==2)
						conn.cancelMPUpload(Bucket,Key,UploadId);
					else
						System.out.println("Invalid Operation");
					break;
				case 14 : 

					MultipartUploadListing MultipartUpload= conn.listMPUploads(Bucket);
					System.out.println("List MultiPart Upload API : ");
					System.out.println("Bucket Name : "+ MultipartUpload.getbucketName());
					System.out.println("Max Uploads : " +MultipartUpload.getMaxUploads());
					System.out.println("Next Key Marker : "+MultipartUpload.getNextKeyMarker());
					System.out.println("Next Upload Id Marker : "+MultipartUpload.getNextUploadIdMarker());
					List<MultipartUpload> multipart= new ArrayList<MultipartUpload>();
					multipart = MultipartUpload.getMultipartUploads();
					for (int i = 0; i < multipart.size(); i++) {
						System.out.println("{"+(i+1)+"} : ");
						System.out.println("Key : " +multipart.get(i).getKey());
						System.out.println("Upload Id : " +multipart.get(i).getUploadId());
						System.out.println("Initiated : "+multipart.get(i).getInitiated());
						System.out.println("Initiator : "+multipart.get(i).getInitiator());
						System.out.println("Storage Class : "+multipart.get(i).getStorageClass());
						System.out.println("Owner : "+multipart.get(i).getOwner());
					}	
					break;
				}
			}
		}
		System.out.println("Operation Successfull");

	}
}
package org.jcs.dss.examples;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jcs.dss.main.Bucket;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.DssObject;

public class DssExample {

	public static void main(String[] args) throws Exception {
		// To connect to the Dss Server.Takes access key, secret key and host of the resource
		DssConnection conn = new DssConnection
				("Y7PUMEKBZORIP07W2A1W",
						"OfKjGgFzcrRgi7JSyCaM3qZc8fqWXshFYMGyXQ7b",
						"http://192.168.56.111:7480");

		// Creates a bucket. Takes bucket name as input
		conn.createBucket("my-bucket");

		//List all the buckets. First create a List array and call listBuckets() and it will store list of buckets in that array
		List<Bucket> listbucket = new ArrayList<Bucket>();
		listbucket = conn.listBuckets();
		for (int i = 0; i < listbucket.size(); i++) {
			System.out.println(listbucket.get(i).getName());
		}

		// Uploads object to the bucket.Takes bucketname, filename to be stored in bucket, and path of the file
		conn.uploadObjectFromFileName("my-bucket", "file.mp4","/home/Desktop/video.mp4");


		//List all the objects in a bucket. First create a List array and call listObjects("bucketname") and it will store list of objects in that array 
		List<DssObject> dssobjects = new ArrayList<DssObject>();
		dssobjects = conn.listObjects("my-bucket");
		for (int i = 0; i < dssobjects.size(); i++) {
			System.out.println(dssobjects.get(i).getName());
		}

		//Download object from the bucket.Takes bucketname, filename which need to be download from bucket, and path of the file where file need to be saved
		conn.downloadObjectToFileName("my-bucket","file.mp4","/home/Desktop/filedownload.mp4");



		//Deletes the object from the bucket. Takes bucket name and object
		conn.deleteObject("my-bucket", "file.mp4");

		// Deletes the bucket. Takes bucket name as input
		conn.deleteBucket("my-bucket");


		System.out.println("Done!!!");

	}

}

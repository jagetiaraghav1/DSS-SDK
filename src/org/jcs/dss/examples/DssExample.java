package org.jcs.dss.examples;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jcs.dss.main.Bucket;
import org.jcs.dss.main.DssConnection;
import org.jcs.dss.main.DssObject;

public class DssExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		DssConnection conn = new DssConnection(
				"Y7PUMEKBZORIP07W2A1W",
				"OfKjGgFzcrRgi7JSyCaM3qZc8fqWXshFYMGyXQ7b",
				"http://192.168.56.111:7480");
		
		
		//conn.uploadObjectFromFileName("my-new-bucket","qw.mp4","/home/raghav/Desktop/w.mp4");

		//conn.downloadObjectToFileName("my-new-bucket","qw.mp4","/home/raghav/qww121.mp4");
		/*List<Bucket> hello = new ArrayList<Bucket>();

		hello = conn.listBuckets();
		for(int i=0;i<hello.size();i++){
			System.out.println(hello.get(i).getName());}
		List<DssObject> hello1 = new ArrayList<DssObject>();

		hello1 = conn.listObjects("my-new-bucket");
		for(int i=0;i<hello1.size();i++){
			System.out.println(hello1.get(i).getName());}
		
		System.out.println("=================");
		
		List<DssObject> hello2 = new ArrayList<DssObject>();

		hello2 = conn.listObjects("my-bucket");
		for(int i=0;i<hello2.size();i++){
			System.out.println(hello2.get(i).getName());}*/
		//conn.copyObject("my-bucket", "copyTest", "my-new-bucket/qwer");
			//	conn.headBucket("my-new-bucket");
//conn.headObject("my-new-bucket", "qwer");
		conn.getPresignedURLOp("my-new-bucket", "qwer", 1234512345);
		System.out.println("Done!!!");

		int expiryTime = (int) (System.currentTimeMillis()/1000);
System.out.println(expiryTime);


	}

}

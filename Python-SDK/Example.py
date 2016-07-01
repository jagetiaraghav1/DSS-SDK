from DssConn import *
from dss_auth import *
import math, os
from filechunkio import FileChunkIO
from genrate_xml import *
access_key = 'Y7PUMEKBZORIP07W2A1W'
secret_key = 'OfKjGgFzcrRgi7JSyCaM3qZc8fqWXshFYMGyXQ7b' 
host = 'http://192.168.56.111:7480'
conn = DssConnection(
access_key,
secret_key,
host
#debug=3
)
# PutObject = conn.upload_file_to_dss('hello-bucket','python','/home/raghav/Desktop/video.mp4')
# print PutObject.ETag
# print PutObject.UploadDate
def read_in_chunks(file_object, chunk_size=6024*1024):
    while True:
        data = file_object.read(chunk_size)
        if not data:
            break
        yield data

Parse = conn.list_buckets()
print Parse.Owner
for x in Parse.bucket:
    print x.Name
    print x.CreationDate


Parse = conn.list_objects('DSS-bucket')
print Parse.BucketName
for x in Parse.object:
    print x.Key
    print x.LastModified
    print x.ETag
    print x.Size
    print x.StorageClass
    print x.Owner


# request_url = conn.get_presigned_url('hello-bucket','newObject',6000)
#print request_url
#conn.create_bucket('DSS-bucket')
#conn.delete_bucket('DSS-bucket')


# object_detail = conn.download_object('hello-bucket','python','/home/raghav/Desktop/video4.mp4')
# print object_detail.ETag
# print object_detail.ContentLength
# print object_detail.ContentType
# print object_detail.LastModified
# print "======================="
# resp = conn.copy_object('DSS-bucket','copy1Python','hello-bucket/python')
# print resp.ETag
# print resp.LastModified
# print "=================="
#conn.delete_object('hello-bucket','python')
#conn.head_bucket('hello-bucket')


# source_path = '/home/raghav/Desktop/video1.mp4'
# source_size = os.stat(source_path).st_size
# print source_size
# initiate_multipart_upload=conn.initiate_multipart('DSS-bucket','multipartup2')
# print initiate_multipart_upload.Key
# print initiate_multipart_upload.UploadId
# print initiate_multipart_upload.BucketName
# f = open(source_path,'rb')
# i =0
# upload_part_list = []
# for piece in read_in_chunks(f):
# 	part_num = i+1
# 	upload_part = conn.upload_part('DSS-bucket','multipartup2',initiate_multipart_upload.UploadId,piece,part_num)
# 	upload_part_list.append(upload_part)	
# 	i = i+1
# for x in upload_part_list:
# 	print x.ETag
# 	print x.PartNumber

# list_part = conn.list_part('DSS-bucket','multipartup2',initiate_multipart_upload.UploadId)
# print list_part.BucketName
# print list_part.Key
# print list_part.UploadId
# print list_part.StorageClass
# print list_part.PartNumberMarker
# print list_part.NextPartNumberMarker
# print list_part.MaxParts
# print list_part.Owner
# for x in list_part.part_summary:
# 	print x.ETag
# 	print x.LastModified
# 	print x.ETag
# 	print x.Size

# XML = GenrateXML(upload_part_list)
# xml_string = XML.generate_xml()
# text_file = open("/home/raghav/Desktop/initiate.xml", "w")
# text_file.write(xml_string)
# text_file.close()
# complete_multipart_upload=conn.complete_multipart('DSS-bucket','multipartup2',initiate_multipart_upload.UploadId,'/home/raghav/Desktop/initiate.xml')
# print complete_multipart_upload.BucketName
# print complete_multipart_upload.Key
# print complete_multipart_upload.ETag

# multipartup_list=conn.list_multipart_upload('DSS-bucket')
# print multipartup_list.BucketName
# print multipartup_list.NextKeyMarker
# print multipartup_list.NextUploadIdMarker
# print multipartup_list.MaxUploads
# for x in multipartup_list.multipart_upload:
# 	print x.Owner
# 	print x.Initiator
# 	print x.StorageClass
# 	print x.Initiated
# 	print x.Key
# 	print x.UploadId
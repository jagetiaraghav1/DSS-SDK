
from dss_auth import *
from dss_bucket_ops import *
from dss_object_ops import *
import os
import sys
import time
import hmac
import json
import base64
import requests
import exceptions
from email.utils import formatdate
# import xml.sax
import json

class DssConnection():

    def __init__(self,access_key,secret_key,host):
        self.access_key=access_key
        self.secret_key=secret_key
        self.host=host


    def upload_file_to_dss(self,bucket_name,object_name,local_file_name):
    	self.bucket_name = bucket_name
    	self.object_name = object_name
    	self.local_file_name = local_file_name
        Op = PutObjectOp(self,self.bucket_name,self.object_name,self.local_file_name)
        resp = Op.execute()
        PutObject = Op.process_result(resp)
        return PutObject 

    def get_presigned_url(self,bucket_name,object_name,expiry):
        self.bucket_name=bucket_name
        self.object_name = object_name
        self.expiry = expiry
        Op = GetPresignedURLOp(self,self.bucket_name,self.object_name,self.expiry)
        request_url = Op.execute()
        return request_url

    def list_objects(self,bucket_name):
        self.bucket_name = bucket_name
        Op = ListObjectsOp(self,self.bucket_name)
        resp = Op.execute()
        Parse = Op.process_result(resp)
        return Parse        

    def list_buckets(self):
        Op = ListBucketsOp(self)
        resp =Op.execute()
        Parse= Op.process_result(resp)
        return Parse
        
    
    def create_bucket(self,bucket_name):
        self.bucket_name = bucket_name
        Op = CreateBucketOp(self,self.bucket_name)
        resp = Op.execute()
        Op.process_result(resp)

    def delete_bucket(self,bucket_name):
        self.bucket_name=bucket_name
        Op = DeleteBucketOp(self,self.bucket_name)
        resp = Op.execute()
        print resp

    def head_bucket(self,bucket_name):
        self.bucket_name = bucket_name
        Op = HeadBucketOp(self,self.bucket_name)
        resp = Op.execute()
        return resp

    def list_multipart_upload(self,bucket_name):
        self.bucket_name = bucket_name
        Op = ListMPUploadsOp(self,self.bucket_name)
        resp = Op.execute()
        multipart_upload_list = Op.process_result(resp)
        return multipart_upload_list

    def delete_object(self,bucket_name,object_name):
        self.bucket_name=bucket_name
        self.object_name=object_name
        Op = DeleteObjectOp(self,self.bucket_name,self.object_name)
        resp = Op.execute()

    def head_object(self,bucket_name,object_name):
        self.bucket_name = bucket_name
        self.object_name = object_name
        Op = HeadObjectOp(self,self.bucket_name,self.object_name)
        resp = Op.execute()
        Op.process_result(resp)

    def download_object (self,bucket_name,object_name,local_file_name):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.local_file_name = local_file_name
        Op = GetObjectOp(self,self.bucket_name,self.object_name,self.local_file_name)
        resp = Op.execute()
        object_detail = Op.process_result(resp)
        return object_detail

    def copy_object(self,bucket_name,object_name,copy_source):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.copy_source = copy_source
        Op = CopyObjectOp(self,self.bucket_name,self.object_name,self.copy_source)
        resp = Op.execute()
        copy_object = Op.process_result(resp)
        return copy_object


    def initiate_multipart(self,bucket_name,object_name):
        self.object_name = object_name
        self.bucket_name = bucket_name
        Op = InitMPUploadOp(self,self.bucket_name,self.object_name)
        resp = Op.execute()
        initiate_multipart_upload = Op.process_result(resp)
        return initiate_multipart_upload


    def cancel_multipart(self,bucket_name,object_name,upload_id):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        Op = CancelMPUploadOp(self,self.bucket_name,self.object_name,self.upload_id)
        resp = Op.execute()
        print resp 

    def list_part(self,bucket_name,object_name,upload_id):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        Op = ListPartsOp(self,self.bucket_name,self.object_name,self.upload_id)
        resp = Op.execute()
        list_part = Op.process_result(resp)
        return list_part

    def upload_part(self,bucket_name,object_name,upload_id,local_file_name,part_number):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        self.part_number = part_number
        self.local_file_name = local_file_name
        Op = UploadPartOp(self,self.bucket_name,self.object_name,self.upload_id,self.part_number,self.local_file_name)
        resp = Op.execute()
        upload_part_list = Op.process_result(resp)
        return upload_part_list
        
    def complete_multipart(self,bucket_name,object_name,upload_id,multipart_upload):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        self.multipart_upload = multipart_upload
        Op = CompleteMPUploadOp(self,self.bucket_name,self.object_name,self.upload_id,self.multipart_upload)
        resp = Op.execute()
        complete_multipart_upload = Op.process_result(resp)
        return complete_multipart_upload

            

    def get_access_key(self):
        return self.access_key

    def get_secret_key(self):
        return self.secret_key

    def get_host(self):
        return self.host

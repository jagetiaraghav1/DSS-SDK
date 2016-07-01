# Copyright (c) 2016 Jiocloud.com, Inc. or its affiliates.  All Rights Reserved
#
# Permission is hereby granted, free of charge, to any person obtaining a
# copy of this software and associated documentation files (the
# "Software"), to deal in the Software without restriction, including
# without limitation the rights to use, copy, modify, merge, publish, dis-
# tribute, sublicense, and/or sell copies of the Software, and to permit
# persons to whom the Software is furnished to do so, subject to the fol-
# lowing conditions:
#
# The above copyright notice and this permission notice shall be included
# in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
# OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABIL-
# ITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
# SHALL THE AUTHOR BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
# WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
# IN THE SOFTWARE.
#
from xml.sax import parseString
from upload_part_result import *
from list_part_result import *
from copy_object_result import *
from complete_mp_upload_result import *
from initiate_multipart_upload import *
from put_object_result import *
from object_detail_result import *
from dss_op import *
from dss_auth import *
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

class ObjectOp(DSSOp):

    def __init__(self,dssConnection,bucket_name,object_name):
        self.dssConnection = dssConnection
        DSSOp.__init__(self,self.dssConnection)
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.dss_op_path = '/' + self.bucket_name + '/' + self.object_name

    def execute(self):
        resp = self.make_request()
        return resp

    def process_result(self, result):
        # nonop currently
        return result



class DeleteObjectOp(ObjectOp):

    def __init__(self,dssConnection,bucket_name,object_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'DELETE'


class HeadObjectOp(ObjectOp):

    def __init__(self,dssConnection,bucket_name,object_name):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.dssConnection = dssConnection
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'HEAD'

    def process_result(self, result):
        # nonop currently, just dump a json in case of success
        if(result is not None and result.status_code == 200):
            response_json = ('{'
                      '"AcceptRanges": "'+ result.headers['accept-ranges'] + '",'
                      '"ContentType": "' + result.headers['content-type'] + '",'
                      '"LastModified": "' + result.headers['date'] + '",'
                      '"ContentLength": ' + result.headers['content-length'] + ','
                      '"ETag": "' + result.headers['etag'].replace('"', '\\"') + '",'
                      '"Metadata": {}'
                      '}')
            self.pretty_print_json_str(response_json)

        return result



class PutObjectOp(ObjectOp):
    
    def __init__(self,dssConnection,bucket_name,object_name,local_file_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'PUT'
        self.local_file_name = local_file_name 

    def execute(self):
        # get signature
        auth = DSSAuth(self.http_method, self.access_key, self.secret_key, self.dss_op_path, content_type = 'application/octet-stream')
        signature = auth.get_signature()
        self.http_headers['Authorization'] = signature
        self.http_headers['Date'] = formatdate(usegmt=True)
        statinfo = os.stat(self.local_file_name)
        self.http_headers['Content-Length'] = statinfo.st_size
        self.http_headers['Content-Type'] = 'application/octet-stream'

        # construct request
        request_url = self.dss_url + self.dss_op_path
        data = open(self.local_file_name, 'rb')
        # make request
        resp = requests.put(request_url, headers = self.http_headers, data=data, verify = False)
        return resp


    def process_result(self, result):
        # nonop currently, just dump a json in case of success
        if(result is not None and result.status_code == 200):
            PutObject = PutObjectResult(result.headers['etag'],result.headers['date'])
        return PutObject

class GetObjectOp(ObjectOp):
    
    
    def __init__(self,dssConnection,bucket_name,object_name,local_file_name):
        self.bucket_name=bucket_name
        self.dssConnection=dssConnection
        self.object_name=object_name
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.local_file_name = local_file_name
        self.http_method = 'GET'


    def execute(self):
        # get signature
        auth = DSSAuth(self.http_method, self.access_key, self.secret_key, self.dss_op_path)
        signature = auth.get_signature()
        self.http_headers['Authorization'] = signature
        self.http_headers['Date'] = formatdate(usegmt=True)

        # construct request
        request_url = self.dss_url + self.dss_op_path
        # make request
        resp = ''
        with open(self.local_file_name, 'wb') as handle:
            resp = requests.get(request_url, headers = self.http_headers, stream = True, verify = False)
            if resp.ok:
                for block in resp.iter_content(1024):
                    handle.write(block)
            else:
                resp.raise_for_status()

        return resp

    def process_result(self, result):
        # nonop currently, just dump a json in case of success
        if(result is not None and result.status_code == 200):

            object_detail = ObjectDetail(result.headers['content-length'],result.headers['content-type'],result.headers['etag'],result.headers['last-modified'])
        return object_detail


class GetPresignedURLOp(ObjectOp):
    
    
    def __init__(self,dssConnection,bucket_name,object_name,expiry):
        self.dssConnection = dssConnection
        self.bucket_name=bucket_name
        self.object_name=object_name
        self.validity = expiry
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'GET'

    def execute(self):
        # get signature
        expiry_time  = int(time.time()) + int(self.validity)
        auth = DSSAuth(self.http_method, self.access_key, self.secret_key, self.dss_op_path, use_time_in_seconds = True, expiry_time = expiry_time)
        signature = auth.get_signature()

        # construct url
        request_url = self.dss_url + self.dss_op_path
        request_url = request_url + '?JCSAccessKeyId=' + self.access_key + '&Expires=' + str(expiry_time) + '&Signature=' + signature
        return request_url

    def process_result(self, result):
        return result


class CopyObjectOp(ObjectOp):    
        
    def __init__(self,dssConnection,bucket_name,object_name,copy_source):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.copy_source = copy_source
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'PUT'

   
    def execute(self):
        self.http_headers['x-amz-metadata-directive'] = 'COPY'
        self.http_headers['x-amz-copy-source'] = self.copy_source
        print self.copy_source
        resp = self.make_request()
        return resp 

    def process_result(self, result):
        copy_object = CopyObjectResult()
        parseString(result.text,copy_object)
        return copy_object
        


class InitMPUploadOp(ObjectOp):
    
    
    def __init__(self,dssConnection,bucket_name,object_name):
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.dssConnection = dssConnection
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'POST'
        self.dss_query_str = 'uploads'
        self.dss_query_str_for_signature = 'uploads'

    def process_result(self,result):
        initiate_multipart_upload = IntiateUPResult()
        parseString(result.text,initiate_multipart_upload)
        return initiate_multipart_upload



class CancelMPUploadOp(ObjectOp):
    
    def __init__(self,dssConnection,bucket_name,object_name,upload_id):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'DELETE'
        self.dss_query_str = 'uploadId=' + self.upload_id
        self.dss_query_str_for_signature = 'uploadId=' + self.upload_id

class ListPartsOp(ObjectOp):
    
    def __init__(self,dssConnection,bucket_name,object_name,upload_id):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'GET'
        self.dss_query_str = 'uploadId=' + self.upload_id
        self.dss_query_str_for_signature = 'uploadId=' + self.upload_id

    def execute(self):
        resp = self.make_request()
        return resp

    def process_result(self,result):
        list_part = PartListResult()
        parseString(result.text,list_part)
        return list_part

class UploadPartOp(ObjectOp):
    
    def __init__(self,dssConnection,bucket_name,object_name,upload_id,part_number,local_file_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.upload_id = upload_id
        self.part_number = part_number
        self.local_file_name = local_file_name
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.dss_query_str = 'partNumber=' + str(self.part_number) + '&uploadId=' + self.upload_id
        self.dss_query_str_for_signature = 'partNumber=' +str(self.part_number) + '&uploadId=' + self.upload_id
        self.http_method = 'PUT'

    def execute(self):
        # get signature
        query_str = 'partNumber=' + str(self.part_number) + '&uploadId=' + self.upload_id
        auth = DSSAuth(self.http_method, self.access_key, self.secret_key, self.dss_op_path, query_str = self.dss_query_str_for_signature, content_type = 'application/octet-stream')
        signature = auth.get_signature()
        self.http_headers['Authorization'] = signature
        self.http_headers['Date'] = formatdate(usegmt=True)
        self.http_headers['Content-Length'] = len(self.local_file_name)
        self.http_headers['Content-Type'] = 'application/octet-stream'

        # construct request
        request_url = self.dss_url + self.dss_op_path 
        if(self.dss_query_str is not None):
            request_url += '?' + self.dss_query_str  
        data = self.local_file_name
        # make request
        resp = requests.put(request_url, headers = self.http_headers, data=data, verify = False)
        return resp

    def process_result(self, result):
        # nonop currently, just dump a json in case of success
        if(result is not None and result.status_code == 200):
            upload_part = UploadPartResult(result.headers['etag'],self.part_number)
        return upload_part

class CompleteMPUploadOp(ObjectOp):
    
    def __init__(self,dssConnection,bucket_name,object_name,upload_id,multipart_upload):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        self.object_name = object_name
        self.local_file_name = multipart_upload
        self.upload_id = upload_id
        ObjectOp.__init__(self,self.dssConnection,self.bucket_name,self.object_name)
        self.http_method = 'POST'
        self.dss_query_str = 'uploadId=' + self.upload_id
        self.dss_query_str_for_signature = 'uploadId=' + self.upload_id



    def execute(self):
        # get signature
        auth = DSSAuth(self.http_method, self.access_key, self.secret_key, self.dss_op_path, query_str = self.dss_query_str_for_signature, content_type = 'text/xml')
        signature = auth.get_signature()
        self.http_headers['Authorization'] = signature
        self.http_headers['Date'] = formatdate(usegmt=True)
        statinfo = os.stat(self.local_file_name)
        self.http_headers['Content-Length'] = statinfo.st_size
        self.http_headers['Content-Type'] = 'text/xml'

        # construct request
        request_url = self.dss_url + self.dss_op_path 
        if(self.dss_query_str is not None):
            request_url += '?' + self.dss_query_str  
        data = open(self.local_file_name, 'rb')
        # make request
        resp = requests.post(request_url, headers = self.http_headers, data=data, verify = False)
      
        # process response
        return resp

    def process_result(self,result):
        complete_multipart_upload = CompleteMPUploadResult()
        parseString(result.text,complete_multipart_upload)
        return complete_multipart_upload


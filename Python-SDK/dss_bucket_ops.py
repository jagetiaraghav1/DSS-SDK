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
from dss_op import *
from dss_auth import *
from list_bucket_result import *
from list_object_result import *
from mp_upload_list_result import *
import os
import sys
import time
import hmac
import json
import base64
import requests
import exceptions
from email.utils import formatdate
import xml.sax
from xml.sax import parseString
class BucketOp(DSSOp):

    def __init__(self,dssConnection,bucket_name):
        self.dssConnection = dssConnection
        DSSOp.__init__(self,self.dssConnection)
        self.bucket_name = bucket_name
        if self.bucket_name!=None:  
            self.dss_op_path = '/' + self.bucket_name

    def execute(self):
        resp = self.make_request()
        return resp

    def process_result(self, result):
        # nonop currently
        return result



class ListBucketsOp(BucketOp):

    def __init__(self,dssConnection):
        self.dssConnection = dssConnection
        BucketOp.__init__(self,self.dssConnection,None)
        self.dss_op_path = '/'
        self.http_method = 'GET'

    def process_result(self,result):
        Parse = BucketListResult()
        parseString(result.text,Parse)
        return Parse

class CreateBucketOp(BucketOp):

    def __init__(self,dssConnection,bucket_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        BucketOp.__init__(self,self.dssConnection,self.bucket_name)
        self.http_method = 'PUT'


    def process_result(self, result):
        if(result.status_code == 200):
            respone_json = '{ "Location": "' + self.dss_url + '/' + self.bucket_name + '" }'
            self.pretty_print_json_str(respone_json)
        return result


class DeleteBucketOp(BucketOp):

    def __init__(self,dssConnection,bucket_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        BucketOp.__init__(self,self.dssConnection,self.bucket_name)
        self.http_method = 'DELETE'


class HeadBucketOp(BucketOp):

    def __init__(self,dssConnection,bucket_name):
        self.dssConnection=dssConnection
        self.bucket_name=bucket_name
        BucketOp.__init__(self,self.dssConnection,self.bucket_name)
        self.http_method = 'HEAD'

class ListObjectsOp(BucketOp):

    def __init__(self,dssConnection,bucket_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        BucketOp.__init__(self,self.dssConnection,self.bucket_name)
        self.http_method = 'GET'

    def process_result(self,result):
        Parse = ObjectListResult()
        parseString(result.text,Parse)
        return Parse
    
class ListMPUploadsOp(BucketOp):
    
    def __init__(self,dssConnection,bucket_name):
        self.dssConnection = dssConnection
        self.bucket_name = bucket_name
        BucketOp.__init__(self,self.dssConnection,self.bucket_name)
        self.http_method = 'GET'
        self.dss_query_str_for_signature = 'uploads'
        self.dss_query_str = 'uploads'

    def process_result(self,result):
        mp_upload_list = MPUploadListResult()
        parseString(result.text,mp_upload_list)
        return mp_upload_list
    



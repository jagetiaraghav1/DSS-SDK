import xml.sax
from xml.sax.saxutils import escape, unescape,quoteattr

class MPUploadListResult(xml.sax.ContentHandler):
	
	def __init__(self):
		self.CurrentData = ""
		self.BucketName = ""
		self.NextKeyMarker = ""
		self.NextUploadIdMarker = ""
		self.MaxUploads = ""
		self.Key = ""
		self.UploadId = ""
		self.Initiator = ""
		self.StorageClass = ""
		self.Initiated =""	
		self.Owner = ""
		self.multipart_upload = []
	def startElement(self,tag,attributes):
		self.CurrentData = tag
		if tag == "Initiator":
			self.c = 2
		if tag == "Owner":
			self.c = 1	

	def characters(self,content):
		if self.CurrentData == "Bucket":
			self.BucketName = content
		elif self.CurrentData == "Key":
			self.Key = content
		elif self.CurrentData == "UploadId":
			self.UploadId = content
		elif self.CurrentData == "NextKeyMarker":
			self.NextKeyMarker = content
		elif self.CurrentData == "NextUploadIdMarker":
			self.NextUploadIdMarker = content
		elif self.CurrentData == "MaxUploads":
			self.MaxUploads = content
		elif self.CurrentData == "Initiated":
			self.Initiated = content
		elif self.CurrentData == "StorageClass":
			self.StorageClass = content
		elif self.CurrentData == "DisplayName" and self.c==1:
			self.Owner = content
		elif self.CurrentData == "DisplayName" and self.c ==2:
			self.Initiator = content



	def endElement(self, tag):
		if tag == "Upload":
			self.multipart_upload.append(MPUploadList(self.Key,self.UploadId,self.Initiator,self.Owner,self.StorageClass,self.Initiated))
		


class MPUploadList(object):
	def __init__(self,key,upload_id,initiator,owner,storage_class,initiated):
		self.Key = key
		self.UploadId = upload_id
		self.Initiator = initiator
		self.Owner = owner
		self.StorageClass = storage_class
		self.Initiated = initiated


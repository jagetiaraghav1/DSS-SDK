import xml.sax
from xml.sax.saxutils import escape, unescape,quoteattr
class ObjectListResult(xml.sax.ContentHandler):
	"""docstring for ClassName"""

	def __init__(self):
		self.CurrentData = ""
		self.BucketName = ""
		self.Key = ""
		self.LastModified = ""
		self.ETag = ""
		self.Size = ""
		self.StorageClass = ""
		self.Owner = ""
		self.object = []
	def startElement(self,tag,attributes):
		self.CurrentData = tag

	def characters(self,content):
		if self.CurrentData == "Name":
			self.BucketName = content
		elif self.CurrentData == "Key":
			self.Key = content
		elif self.CurrentData == "LastModified":
			self.LastModified = content
		elif self.CurrentData == "ETag":
			self.ETag = content
		elif self.CurrentData == "Size":
			self.Size = content
		elif self.CurrentData == "StorageClass":
			self.StorageClass = content
		elif self.CurrentData == "DisplayName":
			self.Owner = content


	def endElement(self, tag):
		if tag == "Contents":
			self.object.append(ObjectList(self.Key,self.LastModified,self.ETag,self.Size,self.StorageClass,self.Owner))
		


class ObjectList(object):
	def __init__(self,key,last_modified,etag,size,storage_class,owner):
		self.Key = key
		self.LastModified = last_modified
		self.ETag = etag
		self.Size = size
		self.StorageClass = storage_class
		self.Owner = owner


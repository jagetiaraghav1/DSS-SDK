import xml.sax
from xml.sax.saxutils import escape, unescape,quoteattr
class PartListResult(xml.sax.ContentHandler):
	"""docstring for ClassName"""

	def __init__(self):
		self.CurrentData = ""
		self.BucketName = ""
		self.PartNumberMarker = ""
		self.NextPartNumberMarker = ""
		self.MaxParts = ""
		self.Key = ""
		self.UploadId = ""
		self.LastModified = ""
		self.ETag = ""
		self.Size = ""
		self.StorageClass = ""
		self.PartNumber =""
		self.Owner = ""
		self.part_summary = []
	def startElement(self,tag,attributes):
		self.CurrentData = tag

	def characters(self,content):
		if self.CurrentData == "Bucket":
			self.BucketName = content
		elif self.CurrentData == "Key":
			self.Key = content
		elif self.CurrentData == "UploadId":
			self.UploadId = content
		elif self.CurrentData == "PartNumberMarker":
			self.PartNumberMarker = content
		elif self.CurrentData == "NextPartNumberMarker":
			self.NextPartNumberMarker = content
		elif self.CurrentData == "MaxParts":
			self.MaxParts = content
		elif self.CurrentData == "PartNumber":
			self.PartNumber = content
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
		if tag == "Part":
			self.part_summary.append(PartSummary(self.ETag,self.LastModified,self.PartNumber,self.Size))
		


class PartSummary(object):
	def __init__(self,etag,last_modified,part_number,size):
		self.PartNumber = part_number
		self.LastModified = last_modified
		self.ETag = etag
		self.Size = size
	


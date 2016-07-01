import xml.sax

class IntiateUPResult(xml.sax.ContentHandler):
	"""docstring for ClassName"""

	def __init__(self):
		self.CurrentData = ""
		self.BucketName = ""
		self.Key = ""
		self.UploadId = ""

	def startElement(self,tag,attributes):
		self.CurrentData = tag

	def characters(self,content):
		if self.CurrentData == "Bucket":
			self.Bucket = content
		elif self.CurrentData == "Key":
			self.Key = content
		elif self.CurrentData == "UploadId":
			self.UploadId = content


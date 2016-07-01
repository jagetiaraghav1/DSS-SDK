import xml.sax

class CompleteMPUploadResult(xml.sax.ContentHandler):
	"""docstring for ClassName"""

	def __init__(self):
		self.CurrentData = ""
		self.BucketName = ""
		self.Key = ""
		self.ETag = ""

	def startElement(self,tag,attributes):
		self.CurrentData = tag

	def characters(self,content):
		if self.CurrentData == "Bucket":
			self.BucketName = content
		elif self.CurrentData == "Key":
			self.Key = content
		elif self.CurrentData == "ETag":
			self.ETag = content


import xml.sax

class CopyObjectResult(xml.sax.ContentHandler):

	def __init__(self):
		self.CurrentData = ""
		self.ETag = ""
		self.LastModified = ""

	def startElement(self,tag,attributes):
		self.CurrentData = tag

	def characters(self,content):
		if self.CurrentData == "ETag":
			self.ETag = content
		elif self.CurrentData == "LastModified":
			self.LastModified = content
	


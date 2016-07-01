import xml.sax

class BucketListResult(xml.sax.ContentHandler):
	"""docstring for ClassName"""

	def __init__(self):
		self.CurrentData = ""
		self.Name = ""
		self.CreationDate = ""
		self.Owner = ""
		self.bucket = []


	def startElement(self,tag,attributes):
		self.CurrentData = tag

	def characters(self,content):
		if self.CurrentData == "Name":
			self.Name = content
		elif self.CurrentData == "CreationDate":
			self.CreationDate = content
		elif self.CurrentData == "DisplayName":
			self.Owner = content
		

	def endElement(self, tag):
		if tag == "Bucket":
			self.bucket.append(BucketList(self.Name,self.CreationDate))
		


class BucketList(object):
	def __init__(self,bucket_name,creation_date):
		self.Name = bucket_name
		self.CreationDate = creation_date


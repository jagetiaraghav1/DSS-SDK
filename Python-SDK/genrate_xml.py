from xml.etree.ElementTree import Element, SubElement, Comment, tostring
class GenrateXML:
	def __init__(self,multipart_upload):
		self.multipart_upload = multipart_upload

	def generate_xml(self):
		top = Element('CompleteMultipartUpload')
		for x in self.multipart_upload:
			child = SubElement(top, 'Part')
			child_partnumber = SubElement(child, 'PartNumber')
			child_partnumber.text = str(x.PartNumber)
			child_etag = SubElement(child, 'ETag')
			child_etag.text = x.ETag
		return tostring(top)




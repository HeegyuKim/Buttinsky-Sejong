package kr.unifox.friends.dicproc;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

public class FolderXmlsParser extends DataBuildingUtils
{
	public static interface Handler
	{
		public void handle(Document doc, XPath path) throws Exception;
	}
	
	public FolderXmlsParser()
	{
	}
	
	Handler handler;
	public void parse(String dirName, Handler handler)
	{
		this.handler = handler;
		
		handleXmlFiles(dirName);
	}

	@Override
	protected void handleXmlData(Document doc, XPath xpath) throws Exception
	{
		handler.handle(doc, xpath);
	}
}

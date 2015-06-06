package kr.unifox.friends.dicproc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class DataBuildingUtils
{
	File folder;
	File []listFiles;
	int limitCount = -1;
	
	protected void limitTest(int limitCount)
	{
		this.limitCount = limitCount;
	}
	
	protected void handleXmlFiles(String dir)
	{
		folder = new File(dir);
		listFiles = folder.listFiles();
		
		out.println("Processing directory: " + folder.getAbsolutePath());
		
		long begin = System.currentTimeMillis();
		int i = 0;
		
		for(File f : listFiles)
		{
			++i;
		
			if(limitCount != -1 && limitCount < i)
				break;
			
			if(!f.getName().toLowerCase().endsWith(".xml"))
				continue;

			if(i % 50 == 0)
				out.printf("%d/%d files proceed, %d ms elapsed\n",
						i, listFiles.length, System.currentTimeMillis() - begin);
				
			Document doc = null;
			XPath xpath = null;
			
			try {
				doc = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(f);
				xpath = XPathFactory.newInstance().newXPath();
			}
			catch(Exception e)
			{
				out.println("Could not parse XML: " + f.getName());
				e.printStackTrace();
			}
			
			try {
				// out.println("Processing File: " + f.getName());
				handleXmlData(doc, xpath);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}	
		
		out.println("Finish!");
	}

	protected abstract void handleXmlData(Document doc, XPath xpath)
		throws Exception;

		
		
	protected PrintStream out = System.out;
}

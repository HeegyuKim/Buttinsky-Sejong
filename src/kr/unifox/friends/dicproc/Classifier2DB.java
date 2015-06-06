package kr.unifox.friends.dicproc;

import java.io.PrintStream;

import javax.xml.xpath.XPath;

import kr.unifox.friends.spellchecker.hangeul.ChaeEon;

import org.w3c.dom.Document;

public class Classifier2DB extends DataBuildingUtils
{
	PrintStream db;
	
	public Classifier2DB() throws Exception
	{
		try(PrintStream db = new PrintStream("db/postposition.txt")) {
			this.db = db;
			handleXmlFiles("data/분류사_상세");
		}
	}

	@Override
	protected void handleXmlData(Document doc, XPath xpath) throws Exception
	{
	}
	
	public static void main(String[] args)
	{
		try
		{
			new Classifier2DB();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

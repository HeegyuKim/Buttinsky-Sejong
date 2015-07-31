package kr.unifox.friends.dicproc;

import java.io.PrintStream;

import javax.xml.xpath.XPath;

import kr.unifox.friends.spellchecker.hangeul.ChaeEon;
import kr.unifox.friends.spellchecker.hangeul.Josa;
import kr.unifox.sejong.ling.Hangeul;

import org.w3c.dom.Document;

public class Josa2DB extends DataBuildingUtils
{
	PrintStream db;
	
	public Josa2DB() throws Exception
	{
		try(PrintStream db = new PrintStream("db/postposition.txt")) {
			this.db = db;
			handleXmlFiles("data/조사_상세");
		}
	}

	@Override
	protected void handleXmlData(Document doc, XPath xpath) throws Exception
	{
		Josa c = new Josa();
		c.orth = doc.getElementsByTagName("orth").item(0).getTextContent();
		c.morphemes = Hangeul.spreadHangeulString(c.orth);

		db.printf("%s,%s\n", c.orth, c.morphemes);
	}

	public static void main(String[] args)
	{
		try
		{
			new Josa2DB();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


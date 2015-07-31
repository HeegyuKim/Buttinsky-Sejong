package kr.unifox.friends.dicproc;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.xpath.XPath;

import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.friends.spellchecker.hangeul.ChaeEon;

import org.w3c.dom.Document;

public class ChaeEon2DB extends DataBuildingUtils
{
	TreeMap<String, ChaeEon> map = new TreeMap<String, ChaeEon>();
	PrintStream db = null;
	String wordClass;
	
	private static final String dataInChaeEon = "data/체언_상세",
									dataInDepNoun = "data/의존명사_상세",
									dataInClassifier = "data/분류사_상세",// 분류사=단위명사
									dataInPronoun = "data/대명사_상세",
									dataOut = "db/substantives.txt";
	
	public ChaeEon2DB() throws Exception
	{
		try(PrintStream db = new PrintStream(dataOut)) {
			this.db = db;
			wordClass = "체언";
			handleXmlFiles(dataInChaeEon);
			wordClass = "의존명사";
			handleXmlFiles(dataInDepNoun);
			wordClass = "단위명사";
			handleXmlFiles(dataInClassifier);
			wordClass = "대명사";
			handleXmlFiles(dataInPronoun);
		}
		
	}

	@Override
	protected void handleXmlData(Document doc, XPath xpath) throws Exception
	{
		ChaeEon c = new ChaeEon();
		c.orth = doc.getElementsByTagName("orth").item(0).getTextContent();
		c.morphemes = Hangeul.spreadHangeulString(c.orth);
		c.wordClass = wordClass;
		
		db.printf("%s,%s,%s\n", c.orth, c.morphemes, c.wordClass);
	}

	
	public static void main(String[] args)
	{
		try{
			new ChaeEon2DB();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}

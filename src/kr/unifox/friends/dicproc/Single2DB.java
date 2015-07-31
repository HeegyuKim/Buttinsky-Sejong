package kr.unifox.friends.dicproc;

import java.io.PrintStream;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

import kr.unifox.friends.spellchecker.hangeul.Single;
import kr.unifox.sejong.ling.Hangeul;

public class Single2DB extends DataBuildingUtils 
{
	PrintStream db;
	
	private static final String dataInBusa = "data/부사_상세",
								dataInGwanHyungSa = "data/관형사_상세",
								dataInGamTanSa = "data/감탄사_상세",
								dataOut = "db/single.txt";
	
	String wordClass;
	
	public Single2DB() throws Exception
	{
		try(PrintStream db = new PrintStream(dataOut))
		{
			this.db = db;

			wordClass = "부사";
			handleXmlFiles(dataInBusa);
			wordClass = "관형사";
			handleXmlFiles(dataInGwanHyungSa);
			wordClass = "감탄사";
			handleXmlFiles(dataInGamTanSa);
		}
	}
	
	@Override
	protected void handleXmlData(Document doc, XPath xpath) throws Exception
	{
		Single s = new Single();
		s.orth = doc.getElementsByTagName("orth").item(0).getTextContent();
		s.morphemes = Hangeul.spreadHangeulString(s.orth);
		s.wordClass = wordClass;
		
		db.printf("%s,%s,%s\n", s.orth, s.morphemes, s.wordClass);
	}
	
	public static void main(String[] args) throws Exception
	{
		new Single2DB();
	}
}

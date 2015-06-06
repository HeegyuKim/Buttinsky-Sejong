package kr.unifox.friends.dicproc;

import java.io.File;
import java.io.PrintStream;

import javax.xml.xpath.XPath;

import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Hangeul;
import kr.unifox.friends.spellchecker.hangeul.HangeulException;
import kr.unifox.friends.spellchecker.hangeul.YongEon;

import org.w3c.dom.Document;

public class YongEon2DB extends DataBuildingUtils
{
	PrintStream db;
	
	private static final String dataInVA = "data/용언_상세/va",	// 형용사
								dataInVV = "data/용언_상세/vv", // 동사
								dataOut = "db/predicate.txt";
	
	String wordClass;
	
	public YongEon2DB() throws Exception
	{
		try(PrintStream db = new PrintStream(dataOut))
		{
			limitTest(5);
			
			this.db = db;
			buildDataByFuckingXmlFile_WhereIsDtDHellKorea(
					dataInVV, "동사");
			buildDataByFuckingXmlFile_WhereIsDtDHellKorea(
					dataInVA, "형용사");
			/*
			wordClass = "동사";
			handleXmlFiles(dataInVV);
			wordClass = "형용사";
			handleXmlFiles(dataInVA);
			*/
		}
	}
	
	protected void buildDataByFuckingXmlFile_WhereIsDtDHellKorea(String dirName, String wordClass)
	{
		File dir = new File(dirName);
		File []listFiles = dir.listFiles();
		
		for(File f : listFiles)
		{
			String filename = f.getName();
			if(!filename.toLowerCase().endsWith(".xml"))
				continue;

			try
			{
				YongEon y = new YongEon();
				y.orth = filename.substring(0, filename.length() - 4); 
				y.root_morphemes = Hangeul.spreadHangeulString(
							y.orth.substring(0, y.orth.length() - 1)
						);
				y.wordClass = wordClass;
				
				db.printf("%s,%s,%s\n", y.orth, y.root_morphemes, y.wordClass);
			}
			catch (HangeulException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void handleXmlData(Document doc, XPath xpath) throws Exception
	{
		YongEon y = new YongEon();
		y.orth = doc.getElementsByTagName("orth").item(0).getTextContent();
		y.wordClass = wordClass;
		
		StringBuilder orth = new StringBuilder(y.orth);

		if(orth.charAt(orth.length() - 1) == '다')
			orth.deleteCharAt(orth.length() - 1);
		
		y.root_morphemes = Hangeul.spreadHangeulString(orth.toString());
		
		db.printf("%s,%s,%s\n", y.orth, y.root_morphemes, y.wordClass);
	}
	
	public static void main(String[] args)
	{
		try{
			new YongEon2DB();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

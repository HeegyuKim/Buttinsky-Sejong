package kr.unifox.friends.dicproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;

import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Hangeul;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JeopSa2DB extends DataBuildingUtils
{
	TreeMap<String, JeopSa> map;
	
	public JeopSa2DB() throws Exception
	{
		map = new TreeMap<String, JeopSa>();
		handleXmlFiles("data/접사_상세");
		writeToDbFile("db/affix.txt");
	}

	@Override
	public void handleXmlData(Document doc, XPath xpath) throws Exception
	{
		JeopSa affix = new JeopSa();
		affix.orth = doc.getElementsByTagName("orth").item(0).getTextContent();
		affix.morphemes = Hangeul.spreadHangeulString(affix.orth);
		
		boolean ok = true;
		
		NodeList combs = doc.getElementsByTagName("result");
		for(int i = 0; i < combs.getLength(); ++i)
		{
			Node node = combs.item(i);
			String nodeText = node.getTextContent();
			int waveIndex = nodeText.indexOf('~');
			
			if(waveIndex == 0)
				affix.isPrefix = true;
			else if(waveIndex == nodeText.length() - 1)
				affix.isSuffix = true;
			else
			{
				out.printf("Exception(%s): %s\n", affix.orth, nodeText);
				// ok = false;
				// 예외적인 경우 접두사, 접미사 모두 되도록 함.
				affix.isPrefix = true;
				affix.isSuffix = true;
			}
		}
		
		if(ok)
			map.put(affix.orth, affix);
	}
	
	
	private void writeToDbFile(String filename) throws FileNotFoundException
	{
		try(PrintStream db = new PrintStream(filename))
		{
	
			for(Entry<String,JeopSa> e : map.entrySet())
			{
				JeopSa affix = e.getValue();
				
				db.printf("%s,%s,%s,%s\n", affix.orth, affix.morphemes, affix.isPrefix, affix.isSuffix);
			}
		}
		
	}
	
	
	
	public static void main(String[] args)
	{
		try
		{
			new JeopSa2DB();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

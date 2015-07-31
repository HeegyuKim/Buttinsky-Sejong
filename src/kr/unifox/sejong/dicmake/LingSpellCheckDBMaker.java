package kr.unifox.sejong.dicmake;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

import kr.unifox.sejong.dicmake.Parser.Handler;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.ling.HyungTaeSo;

public class LingSpellCheckDBMaker
	implements Parser.Handler<Exception>
{

	PrintWriter out;
	String pumsa, eon, type;
	XPath xpath;
	
	public LingSpellCheckDBMaker() 
	{
		//createTextDictionary();
		createHyungTaeSoDictionary();
	}
	
	public void createTextDictionary()
	{
		try(PrintWriter writer = new PrintWriter("db/dic.txt"))
		{
			out = writer;
			
			Handler<File> docHandler = (doc) -> handleDocuments(doc);
			Handler<File> yongEonHandler = (file) -> handleYongEonXmlFiles(file);
			Handler<String> txtHandler = (text) -> handleString(text);
			
			Parser parser = new Parser(this);
			xpath = parser.newXPath();
					
			/////////////////////////////////////////////////////////////
			eon = "체언";

			pumsa = "명사";
			parser.parseTextFile("data/체언_상세.txt", txtHandler);
			parser.parseTextFile("data/의존명사_상세.txt", txtHandler);
			parser.parseTextFile("data/특수어_상세.txt", txtHandler);
			parser.parseXmlDir("data/분류사_상세", docHandler);
			pumsa = "대명사";
			parser.parseTextFile("data/대명사_상세.txt", txtHandler);

			// 수사없냥
			
			////////////////////////////////////////////////////////
			eon = "용언";
			pumsa = "형용사";
			parser.parseXmlDir("data/용언_상세/va/", yongEonHandler);
			pumsa = "동사";
			parser.parseXmlDir("data/용언_상세/vv/", yongEonHandler);

			//////////////////////////////////////////////////////////
			eon = "관계언";
			pumsa = "조사";
			parser.parseTextFile("data/조사_상세.txt", txtHandler);
			
			//////////////////////////////////////////////////////////
			eon = "수식언";
			pumsa = "부사";
			parser.parseTextFile("data/부사_상세.txt", txtHandler);
			pumsa = "관형사";
			parser.parseTextFile("data/관형사_상세.txt", txtHandler);
			
			//////////////////////////////////////////////////////////
			eon = "독립언";
			pumsa = "감탄사";
			parser.parseTextFile("data/감탄사_상세.txt", txtHandler);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void createHyungTaeSoDictionary()
	{
		try(PrintWriter writer = new PrintWriter("db/hts.txt"))
		{
			out = writer;
			
			Handler<File> docHandler = (doc) -> handleDocuments(doc);
			Handler<String> txtHandler = (text) -> handleHyungTaeSo(text);
			
			Parser parser = new Parser(this);
					
			/////////////////////////////////////////////////////////////
			type = "어근";
			parser.parseTextFile("data/어근_상세.txt", txtHandler);
			type = "어미";
			parser.parseTextFile("data/어미_상세.txt", txtHandler);
			
			// parsing data/affix.txt
			List<String> lines = Files.readAllLines(Paths.get("db/affix.txt"));
			for(String line : lines)
			{
				try {
					StringTokenizer st = new StringTokenizer(line, ",");

					HyungTaeSo hts = new HyungTaeSo();
					hts.source = st.nextToken();
					hts.eumso = st.nextToken();
					
					Boolean isPreifx = Boolean.valueOf(st.nextToken()),
							isSuffix = Boolean.valueOf(st.nextToken());
					if(isPreifx && isSuffix)
						hts.type = HyungTaeSo.TYPE_AFFIX;
					else if(isPreifx)
						hts.type = HyungTaeSo.TYPE_PREFIX;
					else if(isSuffix)
						hts.type = HyungTaeSo.TYPE_SUFFIX;
					
					out.printf("%s,%s,%s\n", hts.source, hts.eumso, hts.type);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.printf("Exception occured while parsing line %s\n", line);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void handleHyungTaeSo(String word) throws HangeulException
	{
		if(word.isEmpty()) return;
		out.printf("%s,%s,%s\n", word, Hangeul.spreadHangeulString(word), type);
	}

	public void handleString(String word) throws HangeulException
	{
		if(word.isEmpty()) return;
		
		out.printf("%s,%s,%s,%s,%s\n", word, word, Hangeul.spreadHangeulString(word), pumsa, eon);
	}
	
	public void handleDocuments(File file) throws HangeulException
	{
		String source = file.getName();
		source = source.substring(0, source.lastIndexOf('.'));
		handleString(source);
	}
	public void handleYongEonXmlFiles(File file) throws HangeulException
	{
		String source = file.getName();
		source = source.substring(0, source.lastIndexOf('.') - 1);
		handleString(source);
	}

	@Override
	public void handle(Exception e) throws Exception
	{
		e.printStackTrace();
	}
	
	
	public static void main(String[] args)
	{
		new LingSpellCheckDBMaker();
	}
}

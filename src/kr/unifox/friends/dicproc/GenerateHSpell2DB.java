package kr.unifox.friends.dicproc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;



public class GenerateHSpell2DB
implements TextParser.Handler, FolderXmlsParser.Handler
{
	
	public GenerateHSpell2DB() throws Exception
	{
		try(PrintStream masterOut = new PrintStream("db2/master.txt");
			PrintStream affixOut = new PrintStream("db2/affix.txt"))
		{
			String []masters = {
				"data/감탄사_상세",
				"data/관형사_상세",
				"data/대명사_상세",
				"data/부사_상세",
				"data/분류사_상세",
				"data/체언_상세",
				"data/의존명사_상세",
				"data/용언_상세",
				"data/어근_상세.txt",
			};
			String []affices = {
				"data/조사_상세",
				"data/접사_상세",
				"data/어미_상세.txt",
			};
			
			parse(masters, masterOut);
			parse(affices, affixOut);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	PrintStream out;
	private void parse(String []paths, final PrintStream out) throws IOException
	{
		this.out = out;
		for(String path : paths)
		{
			File file = new File(path);
			if(file.isDirectory())
			{
				FolderXmlsParser parser = new FolderXmlsParser();
				parser.parse(path, this);
			}
			else
			{
				TextParser parser = new TextParser();
				parser.parse(path, this);
			}
		}
	}

	@Override
	public void handle(Document doc, XPath path) throws Exception
	{
		
	}

	@Override
	public void handle(String line) throws Exception
	{
		
	}
	

}

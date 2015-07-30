package kr.unifox.sejong.dicmake;

import org.w3c.dom.Document;

public class LingSpellCheckDBMaker
	implements Parser.Handler<Exception>
{

	public LingSpellCheckDBMaker() throws Exception
	{
		Parser parser = new Parser(this);

	}

	public void handleString(String data, String unknownArgs){
		
	}

	@Override
	public void handle(Exception e) throws Exception
	{
		e.printStackTrace();
	}
	
	
	public static void main(String[] args)
	{
		try {
			new LingSpellCheckDBMaker();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

package kr.unifox.friends.dicproc;

import java.io.IOException;
import java.io.PrintStream;

import kr.unifox.sejong.ling.Hangeul;

public class RootAndTail2DB
{

	public RootAndTail2DB()
	{
		TextParser root = new TextParser(),
				tail = new TextParser();
		try(PrintStream rootOut = new PrintStream("db/root.txt");
				PrintStream tailOut = new PrintStream("db/tail.txt");)
		{
			root.parse("data/어근_상세.txt", new TextParser.Handler()
			{
				@Override
				public void handle(String line) throws Exception
				{
					rootOut.printf("%s,%s\n", line, Hangeul.spreadHangeulString(line));
				}
			});
			
			tail.parse("data/어미_상세.txt",  new TextParser.Handler()
			{
				@Override
				public void handle(String line) throws Exception
				{
					tailOut.printf("%s,%s\n", line, Hangeul.spreadHangeulString(line));
				}
			});
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args)
	{
		new RootAndTail2DB();
	}
}

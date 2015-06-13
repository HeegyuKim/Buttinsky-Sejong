package kr.unifox.friends.dicproc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TextParser
{

	public static interface Handler
	{
		void handle(String line) throws Exception;
	}


	public void parse(String filename, Handler handler) throws IOException
	{
		List<String> lines = Files.readAllLines(
				Paths.get(filename), Charset.forName("MS949"));
		
		for(String line : lines)
		{
			if(line.isEmpty()) continue;
			
			try 
			{
				handler.handle(line);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

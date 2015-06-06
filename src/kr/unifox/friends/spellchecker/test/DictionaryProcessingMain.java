package kr.unifox.friends.spellchecker.test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.List;

public class DictionaryProcessingMain
{

	public static void main(String[] args)
	{
		try
		{
			List<String> lines = 
					Files.readAllLines(Paths.get("data/접사_상세.txt"), Charset.defaultCharset());
			
			for(int i = 0; i < Math.min(10, lines.size()); ++i)
			{
				System.out.println(lines.get(i));
				
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

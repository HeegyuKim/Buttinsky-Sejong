package kr.unifox.friends.spellchecker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LineTextDictionaryParser
{
	public static interface TokenListener
	{
		void handleTokens(List<String> tokens);
		void onResult(int success, int error, int total, long elapsedTime);
	}
	
	
	Path path;
	String filename;
	int limitCount = -1;
	
	public LineTextDictionaryParser(String filename)
		throws URISyntaxException
	{
		this.filename = filename;
		path = Paths.get(filename);
	}
	
	public void setLimit(int count)
	{
		this.limitCount = count;
	}
	

	public void parse(TokenListener lis) throws IOException
	{
		List<String> lines = Files.readAllLines(path, Charset.defaultCharset());
		List<String> tokens = new ArrayList<String>();
		
		System.out.printf("LineTextDictionary Parsing start... (%s)\n", filename);
		
		long startTime = System.currentTimeMillis();
		int i = 0;
		int errCount = 0;
		int interval = lines.size() / (int)Math.cbrt(lines.size());
		int total = lines.size();
		
		for(String line : lines)
		{
			if(limitCount != -1 && i >= limitCount)
				break;
			++i;
			
			if(i % interval == 0)
				System.out.printf("Progress %.0f%%(%d/%d), %dms elapsed.\n",
						(double)i / lines.size() * 100,
						i, lines.size(), 
						System.currentTimeMillis() - startTime);

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
		
			tokens.clear();
			while(tokenizer.hasMoreTokens())
				tokens.add(tokenizer.nextToken());
			
			try {
				lis.handleTokens(tokens);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				errCount ++;
			}
		}
		
		int success = i - errCount;
		long elapsed = System.currentTimeMillis() - startTime;
		
		lis.onResult(success, errCount, total, elapsed);
		System.out.printf("Parsing is terminated, %d success, %d err, %d total lines, %dms elapsed\n",
					i - errCount - 1, errCount, lines.size(), elapsed
				);
	}
}

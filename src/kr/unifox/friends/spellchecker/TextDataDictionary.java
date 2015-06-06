package kr.unifox.friends.spellchecker;

import java.io.EOFException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import kr.unifox.friends.spellchecker.LineTextDictionaryParser.TokenListener;
import kr.unifox.friends.spellchecker.hangeul.ChaeEon;
import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Josa;
import kr.unifox.friends.spellchecker.hangeul.Single;
import kr.unifox.friends.spellchecker.hangeul.YongEon;

public class TextDataDictionary 
implements Dictionary
{
	TreeMap<String, List<ChaeEon>> chaeEonMap = new TreeMap<String, List<ChaeEon>>();
	TreeMap<String, JeopSa> jeopSaMap = new TreeMap<String, JeopSa>();
	TreeMap<String, Josa> josaMap = new TreeMap<String, Josa>();
	TreeMap<String, List<Single>> singleMap = new TreeMap<String, List<Single>>();
	TreeMap<String, List<YongEon>> yongEonMap = new TreeMap<String, List<YongEon>>();

	int totalSuccess = 0, totalErr = 0, totalCount = 0;
	long totalTime = 0; 
	
	public TextDataDictionary(String rootDir)
			throws URISyntaxException, IOException 
	{
		LineTextDictionaryParser 
			affixDic = getParserFromRoot(rootDir, "affix.txt"),
			postPosDic = getParserFromRoot(rootDir, "postposition.txt"),
			predDic = getParserFromRoot(rootDir, "predicate.txt"),
			singleDic = getParserFromRoot(rootDir, "single.txt"),
			subDic = getParserFromRoot(rootDir, "substantives.txt");
		
		int limitCount = -1;
		affixDic.setLimit(limitCount);
		postPosDic.setLimit(limitCount);
		predDic.setLimit(limitCount);
		singleDic.setLimit(limitCount);
		subDic.setLimit(limitCount);
		
		
		affixDic.parse(new TokenListener() {
			@Override
			public void handleTokens(List<String> tokens)
			{
				JeopSa j = new JeopSa(
						tokens.get(0), 
						tokens.get(1),
						Boolean.valueOf(tokens.get(2)), 
						Boolean.valueOf(tokens.get(3))
						);
				jeopSaMap.put(j.morphemes, j);
			}
			@Override
			public void onResult(int success, int error, int total,
					long elapsedTime)
			{
				totalSuccess += success;
				totalErr += error;
				totalCount += total;
				totalTime += elapsedTime;
			}
		});
		postPosDic.parse(new TokenListener() {
			@Override
			public void handleTokens(List<String> tokens)
			{
				Josa josa = new Josa (
						tokens.get(0),
						tokens.get(1)
						);
				josaMap.put(josa.morphemes, josa);
			}
			@Override
			public void onResult(int success, int error, int total,
					long elapsedTime)
			{
				totalSuccess += success;
				totalErr += error;
				totalCount += total;
				totalTime += elapsedTime;
			}
		});
		predDic.parse(new TokenListener() {
			@Override
			public void handleTokens(List<String> tokens)
			{
				YongEon s = new YongEon(tokens.get(0), tokens.get(1), tokens.get(2));
				List<YongEon> list = yongEonMap.get(s.root_morphemes);
				if(list == null)
				{
					list = new ArrayList<YongEon>();
					yongEonMap.put(s.root_morphemes, list);
				}
				list.add(s);
			}
			@Override
			public void onResult(int success, int error, int total,
					long elapsedTime)
			{
				totalSuccess += success;
				totalErr += error;
				totalCount += total;
				totalTime += elapsedTime;
			}
		});
		singleDic.parse(new TokenListener() {
			@Override
			public void handleTokens(List<String> tokens)
			{
				Single s = new Single(tokens.get(0), tokens.get(1), tokens.get(2));
				List<Single> list = singleMap.get(s.morphemes);
				if(list == null)
				{
					list = new ArrayList<Single>();
					singleMap.put(s.morphemes, list);
				}
				list.add(s);
			}
			@Override
			public void onResult(int success, int error, int total,
					long elapsedTime)
			{
				totalSuccess += success;
				totalErr += error;
				totalCount += total;
				totalTime += elapsedTime;
			}
		});
		subDic.parse(new TokenListener() {
			@Override
			public void handleTokens(List<String> tokens)
			{
				ChaeEon s = new ChaeEon(tokens.get(0), tokens.get(1), tokens.get(2));
				List<ChaeEon> list = chaeEonMap.get(s.morphemes);
				if(list == null)
				{
					list = new ArrayList<ChaeEon>();
					chaeEonMap.put(s.morphemes, list);
				}
				list.add(s);
			}
			@Override
			public void onResult(int success, int error, int total,
					long elapsedTime)
			{
				totalSuccess += success;
				totalErr += error;
				totalCount += total;
				totalTime += elapsedTime;
			}
		});

		System.out.println();
		System.out.println("TextDataDictionary Loading succeeded.");
		System.out.printf("Total: %d, success: %d, error: %d, time: %dms\n", 
				totalCount, totalSuccess, totalErr, totalTime);
		
	}
	
	private LineTextDictionaryParser getParserFromRoot(String rootDir, String filename) 
			throws URISyntaxException
	{
		return new LineTextDictionaryParser(rootDir + "/" + filename);
	}


	@Override
	public List<ChaeEon> findChaeEon(String morphemes)
	{
		return chaeEonMap.get(morphemes);
	}

	@Override
	public JeopSa findJoepSa(String morphemes)
	{
		return jeopSaMap.get(morphemes);
	}

	@Override
	public Josa findJosa(String morphemes)
	{
		return josaMap.get(morphemes);
	}

	@Override
	public List<YongEon> findYongEon(String rootMorphemes)
	{
		return yongEonMap.get(rootMorphemes);
	}

	@Override
	public List<Single> findSingle(String morphemes)
	{
		return singleMap.get(morphemes);
	}
	
	
}

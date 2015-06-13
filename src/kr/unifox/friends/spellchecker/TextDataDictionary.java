package kr.unifox.friends.spellchecker;

import java.io.EOFException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import kr.unifox.friends.spellchecker.LineTextDictionaryParser.TokenListener;
import kr.unifox.friends.spellchecker.WordComponent.Type;
import kr.unifox.friends.spellchecker.hangeul.ChaeEon;
import kr.unifox.friends.spellchecker.hangeul.EoGeun;
import kr.unifox.friends.spellchecker.hangeul.EoMi;
import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Josa;
import kr.unifox.friends.spellchecker.hangeul.Single;
import kr.unifox.friends.spellchecker.hangeul.YongEon;

public class TextDataDictionary 
implements Dictionary
{
	abstract class TokenAdapter implements TokenListener 
	{
		@Override
		public void onResult(int success, int error, int total,
				long elapsedTime)
		{
			totalSuccess += success;
			totalErr += error;
			totalCount += total;
			totalTime += elapsedTime;
		}
	}
	
	TreeMap<String, List<ChaeEon>> chaeEonMap = new TreeMap<String, List<ChaeEon>>();
	TreeMap<String, JeopSa> jeopSaMap = new TreeMap<String, JeopSa>();
	TreeMap<String, Josa> josaMap = new TreeMap<String, Josa>();
	TreeMap<String, EoGeun> eogeunMap = new TreeMap<String, EoGeun>();
	TreeMap<String, EoMi> eomiMap = new TreeMap<String, EoMi>();
	TreeMap<String, List<Single>> singleMap = new TreeMap<String, List<Single>>();
	TreeMap<String, List<YongEon>> yongEonMap = new TreeMap<String, List<YongEon>>();
	TreeMap<String, WordComponent.Type> algebra = new TreeMap<String, WordComponent.Type>();
	TreeSet<String> rightArray = new TreeSet<String>();
	
	
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
			subDic = getParserFromRoot(rootDir, "substantives.txt"),
			rootDic = getParserFromRoot(rootDir, "root.txt"),
			tailDic = getParserFromRoot(rootDir, "tail.txt");
		
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
		rootDic.parse(new TokenAdapter()
		{
			@Override
			public void handleTokens(List<String> tokens)
			{
				EoGeun e = new EoGeun(tokens.get(0), tokens.get(1));
				eogeunMap.put(e.phenomes, e);
			}
		});
		tailDic.parse(new TokenAdapter()
		{
			@Override
			public void handleTokens(List<String> tokens)
			{
				EoMi e = new EoMi(tokens.get(0), tokens.get(1));
				eomiMap.put(e.phenomes, e);
			}
		});

		System.out.println();
		System.out.println("TextDataDictionary Loading succeeded.");
		System.out.printf("Total: %d, success: %d, error: %d, time: %dms\n", 
				totalCount, totalSuccess, totalErr, totalTime);

		try {
			readGrammar(rootDir);		
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void readGrammar(String rootDir) throws IOException
	{
		List<String> algebras = Files.readAllLines(Paths.get(rootDir+"/"+"algebra.txt"), Charset.defaultCharset()),
					seqs = Files.readAllLines(Paths.get(rootDir+"/"+"seq.txt"), Charset.defaultCharset());

		System.out.println();
		System.out.println("Grammartical Algebra(algebra.txt)");
		
		for(String alg : algebras)
		{
			if(alg.startsWith("#")) continue;
			
			int iSep = alg.indexOf('=');
			String left = alg.substring(0, iSep),
					right = alg.substring(iSep + 1, alg.length());
			
			StringTokenizer tokenizer = new StringTokenizer(right, " +");
			StringBuilder ids = new StringBuilder();
			
			while(tokenizer.hasMoreTokens())
			{
				String token = tokenizer.nextToken();
				WordComponent.Type t = WordComponent.Type.fromHangeul(token);
				if(t != null)
					ids.append(t.id);
			}
			
			String idseq = ids.toString();
			WordComponent.Type t = WordComponent.Type.fromHangeul(left);
			algebra.put(idseq, t);
			
			System.out.printf("%s(%c)=%s(%s)\n", left, t.id, right, idseq);
		}
		
		System.out.println();
		System.out.println("Right Array(seq.txt)");
		
		for(String s : seqs)
		{
			if(s.startsWith("#")) continue;

			StringTokenizer tokenizer = new StringTokenizer(s, " +");
			StringBuilder ids = new StringBuilder();
			
			while(tokenizer.hasMoreTokens())
			{
				String token = tokenizer.nextToken();
				WordComponent.Type t = WordComponent.Type.fromHangeul(token);
				if(t != null)
					ids.append(t.id);
			}
			
			String idstr = ids.toString();
			rightArray.add(idstr);
			System.out.printf("%s -> %s\n", s, idstr);
		}
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

	@Override
	public EoMi findEoMi(String phenomes)
	{
		return eomiMap.get(phenomes);
	}

	@Override
	public EoGeun findEoGeun(String phenomes)
	{
		return eogeunMap.get(phenomes);
	}

	private StringBuilder toIDsBuilder(List<WordComponent> comps)
	{
		StringBuilder builder = new StringBuilder();
		for(WordComponent c : comps)
		{
			builder.append(c.type.id);
		}
		return builder;
	}
	
	@Override
	public List<WordComponent> normalize(List<WordComponent> comps)
	{
		List<WordComponent> norm = new ArrayList<WordComponent>();
		norm.addAll(comps);
		StringBuilder ids = toIDsBuilder(comps);
		
		for(Entry<String, WordComponent.Type> e : algebra.entrySet())
		{
			String key = e.getKey();
			WordComponent.Type t = e.getValue();
			
			int len = key.length();
			
			while(true)
			{
				int i = ids.indexOf(key);
				if(i == -1) break;
				
				System.out.printf("find %s in %s at %d ", key, ids, i);
				ids.delete(i, i + len);
				ids.insert(i, t.id);
				System.out.printf(" and change to %s, len = %d\n", ids, len);
				
				WordComponent n = new WordComponent();
				StringBuilder phenomes = new StringBuilder(),
								origin = new StringBuilder();
				
				for(int j = 0; j < len; ++j)
				{
					WordComponent c = norm.remove(i);
					System.out.printf("Composing! i=%d, j=%d, %s(%s-%s)\n", i, j, c.origin, c.orthInWord, c.type);
					origin.append(c.origin);
					phenomes.append(c.orthInWord);
				}
				
				n.origin = origin.toString();
				n.orthInWord = phenomes.toString();
				n.type = t;
				System.out.printf("Composed! %s(%s - %s)\n", n.origin, n.orthInWord, n.type);
				norm.add(i,n);
			}
		}
		
		return norm;
	}

	@Override
	public boolean isRightWord(List<WordComponent> comps)
	{
		String seq = toIDSequenceString(comps);
		
		for(String right : rightArray)
		{
			if(seq.equals(right))
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isRightSequence(WordComponent head, WordComponent tail)
	{
		if(tail == null) return true;
		
		String seq = head.type.id + "" + tail.type.id;
		
		for(String right : rightArray)
		{
			if(seq.equals(right))
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isRightTypeArray(Type head, Type tail)
	{
		if(tail == null) return true;
		String seq = head.id + "" + tail.id;
		
		for(String right : rightArray)
		{
			//System.out.printf("%s ~ %s\n", seq, right);
			if(seq.equals(right))
				return true;
		}
		
		return false;
	}
	
	public String toIDSequenceString(List<WordComponent> comps)
	{
		StringBuilder seq = new StringBuilder();
		for(WordComponent comp : comps)
			seq.append(comp.type.id);
		return seq.toString();
	}
}

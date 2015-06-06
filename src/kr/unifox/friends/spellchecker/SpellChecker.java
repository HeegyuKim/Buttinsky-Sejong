package kr.unifox.friends.spellchecker;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kr.unifox.friends.spellchecker.WordComponent.Type;
import kr.unifox.friends.spellchecker.hangeul.ChaeEon;
import kr.unifox.friends.spellchecker.hangeul.Hangeul;
import kr.unifox.friends.spellchecker.hangeul.HangeulException;
import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Josa;
import kr.unifox.friends.spellchecker.hangeul.Single;
import kr.unifox.friends.spellchecker.hangeul.YongEon;

public class SpellChecker
{
	Dictionary dic;
	
	public SpellChecker(Dictionary dic)
	{
		this.dic = dic;
	}
	
	public List<List<Candidate>> checkSentenceWithService(String sentence)
			throws HangeulException
	{
		List<List<Candidate>> candidateWords = new ArrayList<List<Candidate>>();
		
		StringTokenizer tokenizer = new StringTokenizer(sentence, ".,() ");
		int i = 0;
		while(tokenizer.hasMoreTokens())
		{
			++i;
			ExecutorService service = Executors.newSingleThreadExecutor();
			List<Candidate> candies = Collections.synchronizedList(new ArrayList<Candidate>());
			
			String token = tokenizer.nextToken();
			CandiFinder finder = new CandiFinder(token, new Candidate(), service, this, candies);
			
			// find candies !
			Future<Void> f = service.submit(finder);
			try
			{
				f.get();
				for(Candidate candi : candies)
				{
					System.out.printf("%d --> %d\n", i, candi.comps.size());
					for(WordComponent wc : candi.comps)
						System.out.printf("%s(%s)\n", wc.origin, wc.type);
				}
				candidateWords.add(candies);
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}
		
		return candidateWords;
	}
	
	
	public CheckResult checkSentence(String sentence) throws HangeulException
	{
		CheckResult result = new CheckResult();
		result.originSentence = sentence;
		result.modifiedSentence = sentence;
		
		
		StringTokenizer tokenizer = new StringTokenizer(sentence, ".,() ");
		while(tokenizer.hasMoreTokens())
		{
			result.components.addAll(analyzeWord(tokenizer.nextToken()));
			result.components.add(new WordComponent("","",Type.WhiteSpace));
		}
		
		return result;
	}
	

	public List<WordComponent> analyzeWord(String word) throws HangeulException 
	{
		System.out.printf("Analyzing %s\n", word);
		ArrayList<WordComponent> compList  = null;
		String morphemes = "";
		List<Hangeul> chars = null;

		compList = new ArrayList<WordComponent>();
		
		try {
			chars = Hangeul.toHangeulList(word);
			morphemes = Hangeul.spreadHangeulString(word);
		}
		catch(HangeulException e)
		{
			compList.add(0, new WordComponent("", word, Type.Unknown));
			return compList;
		}
		
		//
		// 한 단어인가 확인한다
		// 단순 체언이거나 관형사, 부사, 감탄사일경우가 해당한다.
		WordComponent comp = new WordComponent();
		if(isSingle(morphemes, comp) || isChaeEon(morphemes, comp) || isYongEon(morphemes, comp))
		{
			System.out.printf("%s(%s)\n", comp.origin, comp.type);
			compList.add(0, comp);
			return compList;
		}
		// TODO: + 오타검사. 체언이나 Single을 잘못친거 아니냐?
		
		
		//
		// 조사나 어미를 찾는다.
		// 
		int mlen = morphemes.length();
		for(int i = mlen - 1; i >= 0; --i)
		{
			String partBack = morphemes.substring(i, mlen);
			comp = new WordComponent();
			
			// 조사찾기
			if(isJosa(partBack, comp) || isJoepSa(partBack, comp))
			{
				compList.add(0, comp);
				String remains = Hangeul.cutJamo(chars, i);
				
				System.out.printf("%s(%s, %s)\n", partBack, comp.origin, comp.type);

				compList.addAll(0, analyzeWord(remains));
				return compList;
			}
		}
		
		
		//
		// 나중에 가서 못찾으면 몰라 시발! ㅜㅜ
		compList.add(new WordComponent(word, word, Type.Unknown));

		return compList;
	}
	
	public boolean isChaeEon(String morphemes, WordComponent resComp)
	{
		List<ChaeEon> chList = dic.findChaeEon(morphemes);
		
		// 체언중에 있다!
		if(chList != null)
		{
			ChaeEon ch = chList.get(0);
			resComp.origin = ch.orth;
			resComp.orthInWord = morphemes;
			resComp.type = Type.ChaeEon;
			
			return true;
		}
		return false;
	}

	
	public boolean isYongEon(String morphemes, WordComponent resComp)
	{
		List<YongEon> chList = dic.findYongEon(morphemes);
		
		// 용언중에 있다!
		if(chList != null)
		{
			YongEon ch = chList.get(0);
			resComp.origin = ch.orth;
			resComp.orthInWord = morphemes;
			resComp.type = Type.YongEon;
			
			return true;
		}
		return false;
	}
	
	public boolean isSingle(String morphemes, WordComponent resComp)
	{
		List<Single> chList = dic.findSingle(morphemes);
		
		// 체언중에 있다!
		if(chList != null)
		{
			Single ch = chList.get(0);
			resComp.origin = ch.orth;
			resComp.orthInWord = morphemes;
			resComp.type = Type.Single;
			
			return true;
		}
		return false;
	}

	public boolean isJosa(String morphemes, WordComponent resComp)
	{
		Josa josa = dic.findJosa(morphemes);
		if(josa != null)
		{
			resComp.origin = josa.orth;
			resComp.orthInWord = morphemes;
			resComp.type = Type.Josa;
			return true;
		}
		return false;
	}
	public boolean isJoepSa(String morphemes, WordComponent resComp)
	{
		JeopSa joepsa = dic.findJoepSa(morphemes);
		if(joepsa != null)
		{
			resComp.origin = joepsa.orth;
			resComp.orthInWord = morphemes;
			resComp.type = Type.JoepSa;
			return true;
		}
		return false;
	}
	
}

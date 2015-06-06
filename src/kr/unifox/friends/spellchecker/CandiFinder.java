package kr.unifox.friends.spellchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.unifox.friends.spellchecker.WordComponent.Type;
import kr.unifox.friends.spellchecker.hangeul.Hangeul;
import kr.unifox.friends.spellchecker.hangeul.HangeulException;

public class CandiFinder implements Callable<Void>
{
	String word, spread;
	List<Hangeul> hlist;
	Candidate candi, tail;
	ExecutorService service;
	SpellChecker checker;
	List<Candidate> syncCandies;
	
	
	public CandiFinder(
			String word, 
			Candidate tail, 
			ExecutorService service, 
			SpellChecker checker,
			List<Candidate> syncCandies
			) throws HangeulException
	{
		this.word = word;
		this.hlist = Hangeul.toHangeulList(word);
		this.candi = new Candidate();
		this.tail = tail;
		this.service = service;
		this.checker = checker;
		this.syncCandies = syncCandies;
	}
	
	@Override
	public Void call() throws Exception
	{
		System.out.println(word + " call !");
		List<WordComponent> comps = candi.comps;
		spread = Hangeul.spreadHangeulString(word);
		boolean unknown = true;

		//
		// 한 단어인가 확인한다
		// 단순 체언이거나 관형사, 부사, 감탄사일경우가 해당한다.
		WordComponent comp = new WordComponent();
		if(checker.isSingle(spread, comp) ||
				checker.isChaeEon(spread, comp) || 
				checker.isYongEon(spread, comp))
		{
			comps.add(0, comp);
			syncCandies.add(Candidate.merge(candi, tail));
			return null;
		}

		//
		// 조사나 어미를 찾는다.
		// 
		int mlen = spread.length();
		for(int i = mlen - 1; i >= 0; --i)
		{
			String partBack = spread.substring(i, mlen);
			comp = new WordComponent();
			
			// 조사찾기
			if(checker.isJosa(partBack, comp) || 
					checker.isJoepSa(partBack, comp))
			{
				String remains = Hangeul.cutJamo(hlist, i);
				
			}
		}
		
			
		
		comps.add(0, new WordComponent(word, spread, Type.Unknown));
		
		return null;
	}
	
	void newCandi(int end)
	{
		
	}
	
	
	void checkIsSingle()
	{
		
	}
}

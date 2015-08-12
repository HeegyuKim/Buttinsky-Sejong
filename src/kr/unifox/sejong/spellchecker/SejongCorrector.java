package kr.unifox.sejong.spellchecker;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.spellchecker.mistakes.AhnAhnhMisktake;
import kr.unifox.sejong.spellchecker.mistakes.JosaJongSungMistake;
import kr.unifox.sejong.spellchecker.mistakes.Mistake;
import kr.unifox.sejong.spellchecker.mistakes.MistakeCorrector;
import kr.unifox.sejong.spellchecker.mistakes.Repaired;

public class SejongCorrector 
{
	public static class Tokenized
	{
		// start <= i < end
		public int start, end; 
		public String eojeol, replaced;
		public Mistake mistake;
		public boolean hasError = false;
		
		public Tokenized(int start, int end, String eojeol) {
			super();
			this.start = start;
			this.end = end;
			this.eojeol = eojeol;
		}
		public int length()
		{
			return end - start;
		}
	}
	
	public boolean enableDebugOutput = true;
	public PrintStream debug = System.out;
	
	Dictionary dic;
	List<MistakeCorrector> mistakes;
	Evaluator eval;
	
	public SejongCorrector(Dictionary dic) 
	{
		this.dic = dic;
		this.mistakes = new ArrayList<MistakeCorrector>();
		this.eval = new Evaluator(dic);
		
		mistakes.add(new AhnAhnhMisktake(dic));
		mistakes.add(new JosaJongSungMistake());
	}
	
	
	public String modify(String text, List<Tokenized> analyzedResult) throws HangeulException
	{
		long startTime = System.nanoTime();
		StringBuilder modifying = new StringBuilder(text);
		List<Tokenized> tokens = tokenizeByHangeul(text);
		
		if(enableDebugOutput)
		{
			debug.printf("Tokenize \"%s\"\nGet %d tokens.", text, tokens.size());
			int tokenI = 0;
			for(Tokenized token : tokens)
			{
				debug.printf("%s(%d~%d)", token.eojeol, token.start, token.end);
				if(tokenI != tokens.size() - 1)
					debug.print(", ");
				debug.println();
			}
		}
		int adjust = 0;
		
		for(Tokenized token : tokens)
		{
			List<CandidateArray> arrayList = searchCandidates(token.eojeol);

			if(enableDebugOutput)
			{
				debug.printf("%d CandidateArray is found on \"%s\" eojeol\n", 
						arrayList.size(), token.eojeol);
			}
			
			CandidateArray selected = null;
			boolean isCorrect = false, mistaken = false;
			int candiCountOfSelected = Integer.MAX_VALUE;
			int i = 0, selectedIndex = 0;
			
			for(CandidateArray array : arrayList)
			{
				++ i;
				EvaluationResult result = eval.evaluate(array);
				if(enableDebugOutput)
				{
					debug.printf("CandidateArray index: %d: ", i);
					if(result.evaluation == GrammaticalEvaluation.UNKNOWN)
						debug.println("Unknown");
					else
						debug.printf("%d Corrects, %d Stranges.\n", 
								result.corrects.size(),
								result.stranges.size());
				}
				
				if(selected == null)
					selected = result.corrects;
				else if(result.evaluation == GrammaticalEvaluation.CORRECT &&
						candiCountOfSelected > result.corrects.size() + result.stranges.size())
				{
					selected = result.corrects;
					selectedIndex = i;
					candiCountOfSelected = result.corrects.size() + result.stranges.size();
				}
				
				switch(result.evaluation)
				{
				case CORRECT:
				{
					// TODO: 실수를 찾는다.
					isCorrect = true;
					Iterator<Candidate> it = result.corrects.iterator();
					
					while(it.hasNext())
					{
						Candidate candi = it.next();
						Repaired r = findMistakes(candi);
						if(r.hasMistake)
						{
							mistaken = true;
							token.mistake = r.repaired.mistakes.get(0);
							candi.compList = r.repaired.compList;
							candi.mistakes = r.repaired.mistakes;
							
							StringBuilder sb = new StringBuilder();
							for(Component comp : candi.compList)
							{
								sb.append(comp.getSource());
							}
							candi.text = Hangeul.combineHangeulEumso(
									Hangeul.spreadHangeulString(sb.toString())
									);
							if(enableDebugOutput)
							{
								Mistake mis = r.repaired.mistakes.get(0);
								debug.printf("Find Mistake %s: %s\n%s -> %s\n",
										mis.type, mis.reason,
										candi.originalText, candi.text
										);
							}
										
								
						}
					}
					break;
				}
				case STRANGE:
				case UNKNOWN:
				{
					// TODO: 오류를 수정할 방안을 찾는다.
					break;
				}
				}
			}
			
			// 선택된 selected 로 token의 텍스트를 대체한다.
			if(selected == null) 
			{
				if(enableDebugOutput)
				{
					if(isCorrect)
						debug.printf("\"%s\" is correct eojeol.\n", token.eojeol);
					else
						debug.printf("There is no suitable replacement for %s",		
								token.eojeol
								);
				}
				
				token.hasError = true;
				continue;
			}
			else if(enableDebugOutput)
			{
				debug.printf("CandidateArray at %d is selected.\n", selectedIndex);
			}
			
			// Selected의 Corrects 중 첫번째를 고름
			Candidate selectedCandi = selected.get(0);
			
			// TODO: 이부분 개판 ㅂㄷㅂㄷ....
			// 실수가 있으면 우선적으로 됨, 수정해야 하니깐!
			for(Candidate candi : selected)
			{
				if(candi.mistakes != null)
				{
					selectedCandi = candi;
					if(enableDebugOutput)
					{
						debug.println("오류가 수정된 거 채택됨");
						break;
					}
				}
			}
			
			String correct = null;
			if(selectedCandi.text == null)
			{
				correct = selectedCandi.originalText;
				if(enableDebugOutput)
					debug.println("This eojeol is correct. There is no changes");
				continue;
			}
			else
			{
				token.replaced = correct = selectedCandi.text;
				token.hasError = true;
			}
			
			// 조정된 시작/끝값
			int start = adjust + token.start, end = adjust + token.end;
			
			modifying.delete(adjust + token.start, adjust + token.end);
			modifying.insert(adjust + token.start, correct);
			
			if(enableDebugOutput)
				debug.printf("Modifying %d~%d: \"%s\" to \"%s\"\n", 
						start, end,
						token.eojeol,
						correct
						);
			if(token.length() != correct.length())
			{
				int oldAdjust = adjust;
				adjust += correct.length() - token.length();
				
				if(enableDebugOutput)
					debug.printf("Adjustment Value is changed to %d from %d(because text modified to \"%s\" from \"%s\")\n",
							oldAdjust, adjust,
							correct, token.eojeol
							);
			}
		}
		
		long delta = System.nanoTime() - startTime;
		String modifiedText = modifying.toString(); 
		if(enableDebugOutput)
		{
			debug.printf("Totally %.4fms elapsed.\nBefore: %s\nAfter:%s\n",
					delta / 1000000.0f,
					text,
					modifiedText
					);
		}
		
		if(analyzedResult != null)
			analyzedResult.addAll(tokens);
		return modifiedText;
	}
	
	/**
	 * 한글 텍스트를 쪼갬
	 * 안녕1하셍 ㅇㅇㅎ -> 안녕,하셍,ㅇㅇㅎ
	 * @param text
	 * @return
	 */
	public List<Tokenized> tokenizeByHangeul(String text)
	{
		List<Tokenized> tokens = new ArrayList<Tokenized>();
		
		int start = 0, i = 0;
		boolean prevIsHangeul = false;
		
		for(i = 0; i < text.length(); ++i)
		{
			int cp = text.codePointAt(i);
			if(Hangeul.isHangeul(cp))
			{
				if(!prevIsHangeul)
					start = i;
				
				prevIsHangeul = true;
			}
			else
			{
				if(prevIsHangeul)
				{
					Tokenized token = new Tokenized(
							start, i, text.substring(start, i));
					tokens.add(token);
					start = -1;
				}

				prevIsHangeul = false;
			}
		}

		if(i - start > 1 && start != -1)
		{
			Tokenized token = new Tokenized(	
					start, i, text.substring(start, i));
			tokens.add(token);
		}
			
		
		return tokens;
	}
	
	public List<CandidateArray> searchCandidates(String eojeol) throws HangeulException
	{
		CandidateSearcher searcher = new CandidateSearcher(
				dic, eojeol, Hangeul.spreadHangeulString(eojeol));
		
		searcher.search();
		return searcher.getCandidateArrayList();
	}
	
	
	public Repaired findMistakes(Candidate candi)
	{
		Repaired rep = new Repaired();
		rep.source = rep.repaired = candi;
		boolean mistaken = false;
		
		for(MistakeCorrector mis : mistakes)
		{
			if(mis.checkMistake(rep.repaired, rep))
				mistaken = true;
			rep.hasMistake = false;
		}
		
		rep.hasMistake = mistaken;
		return rep;
	}

}

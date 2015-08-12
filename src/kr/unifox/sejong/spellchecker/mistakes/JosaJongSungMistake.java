package kr.unifox.sejong.spellchecker.mistakes;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.ling.Word;
import kr.unifox.sejong.spellchecker.Candidate;

/**
 * 
 * @author 김희규
 *
 * 을/를 틀리는거 바꿔줌 ㅎㅎ.
 */
public class JosaJongSungMistake implements MistakeCorrector 
{
	Set<String> jongsungExists,
				jongsungNone;
	
	public JosaJongSungMistake() 
	{
		jongsungExists = new TreeSet<String>();
		jongsungNone = new TreeSet<String>();

		jongsungExists.add("은");
		jongsungExists.add("을");
		jongsungExists.add("이");
		jongsungNone.add("는");
		jongsungNone.add("를");
		jongsungNone.add("가");
		
		jongsungExists = Collections.unmodifiableSet(jongsungExists);
		jongsungNone= Collections.unmodifiableSet(jongsungNone);
	}
	
	@Override
	public boolean checkMistake(Candidate candidate, Repaired repaired) 
	{
		repaired.source = candidate;
		
		
		for(int i = candidate.compList.size() - 1; i >= 1; --i)
		{
			Component comp = candidate.compList.get(i);
			String source = comp.getSource();
			
			if(comp.getTypeName().equals(Word.PUMSA_JOSA))
			{
				Component prev = candidate.compList.get(i - 1);
				
				// 마지막 글자가 종성이 있나 없나 확인해봄 ㅎ
				String prevSource = prev.getSource();
				char lastChar = prevSource.charAt(prevSource.length() - 1);
				
				try 
				{
					Hangeul hangeul = new Hangeul(lastChar);
					
					// 종성이 없어야 하는 조사인데 있다?
					if(hangeul.getJongsung() != ' ' 
							&& jongsungNone.contains(source))
					{
						repaired.hasMistake = true;
						repaired.repaired = new Candidate();
						repaired.repaired.compList.addAll(candidate.compList);
						
						String repairedSource = null;
						if(source.equals("를"))
							repairedSource = "을";
						else if(source.equals("는"))
							repairedSource = "은";
						else if(source.equals("가"))
							repairedSource = "이";

						Mistake mis = new Mistake( 
								String.format (
										"종성이 없는 %c 뒤에는 조사로 %s 대신 %s가 와야 합니다.",
										lastChar,
										source,
										repairedSource
										), 
								Mistake.MISTAKE_JOSA_JONGSUNG
								);
						repaired.repaired.addMistake(mis); 
						
						Word word = new Word(repairedSource, 
								Hangeul.spreadHangeulString(repairedSource), 
								repairedSource, 
								Word.PUMSA_JOSA, Word.EON_GWANGYEEON);
						repaired.repaired.compList.set(i, word);
					}
					// 종성이 있어야 하는 조사인데 없다?
					else if(hangeul.getJongsung() == ' '
							&& jongsungExists.contains(source))
					{
						// repaired 초기화
						repaired.hasMistake = true;
						repaired.repaired = new Candidate();
						repaired.repaired.compList.addAll(candidate.compList);
						
						
						String repairedSource = null;
						if(source.equals("을"))
							repairedSource = "를";
						else if(source.equals("은"))
							repairedSource = "는";
						else if(source.equals("이"))
							repairedSource = "가";

						Mistake mis = new Mistake( 
								String.format (
										"종성이 없는 %c 뒤에는 조사로 %s 대신 %s가 와야 합니다.",
										lastChar,
										source,
										repairedSource
										), 
								Mistake.MISTAKE_JOSA_JONGSUNG
								);
						repaired.repaired.addMistake(mis); 
						
						Word word = new Word(repairedSource, 
								Hangeul.spreadHangeulString(repairedSource), 
								repairedSource, 
								Word.PUMSA_JOSA, Word.EON_GWANGYEEON);
						repaired.repaired.compList.set(i, word);
					}
				} 
				catch (HangeulException e) {
					e.printStackTrace();
				}
				
				break;
			}
		}
				
		return repaired.hasMistake;
	}
}

package kr.unifox.sejong.spellchecker.mistakes;

import java.util.Iterator;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Linguistic;
import kr.unifox.sejong.ling.Word;
import kr.unifox.sejong.spellchecker.Candidate;

/**
 * 되/돼 실수 잡아주기 ^-^
 * @author 김희규
 *
 */
public class DoeDwaeMistake implements MistakeCorrector {
	@Override
	public boolean isCheckStranges() {
		return true;
	}

	@Override
	public boolean checkMistake(Candidate candidate, Repaired r) 
	{
		int componentListSize = candidate.compList.size();
		for(int i = 0; i < componentListSize; ++i)
		{
			boolean hasNext = (i + 1 < componentListSize);
			Component comp = candidate.compList.get(i);
			
			if(hasNext && Linguistic.equals(comp, "돼", Word.PUMSA_DONGSA))
			{
				Component next = candidate.compList.get(i + 1);
				if(Linguistic.equals(next, "어", HyungTaeSo.TYPE_TAIL))
				{
					String reason = "'돼'는 '되어'의 준말로 뒤에 어미 '어'가 다시 나오는 것은 " +
									"같은 어미가 중복되어 올바르지 않습니다.";
					Candidate repaired = new Candidate();
					repaired.originalText = "돼";
					repaired.compList.addAll(candidate.compList);
					repaired.compList.remove(i + 1);
					repaired.addMistake(new Mistake(reason, Mistake.MISTAKE_DOE_DWAE));
					r.repaired = repaired;
					r.hasMistake = true;
					break;
				}
				else
				{
					try {
						r.repaired = createRepairedForDoeDwaeHint(candidate, i, "되", "되");
					} catch (HangeulException e) {
						e.printStackTrace();
					}
					r.hasMistake = true;
					break;
				}
			}
			
			if(Linguistic.equals(comp, "되", Word.PUMSA_DONGSA))
			{
				if(hasNext)
				{
					Component next = candidate.compList.get(i + 1);
					if(Linguistic.equals(next, "어", HyungTaeSo.TYPE_TAIL) 
							|| Linguistic.equals(next, "ㅆ", HyungTaeSo.TYPE_TAIL))
					{
						try {
							r.repaired = createRepairedForDoeDwaeHint(candidate, i, "돼", "되");
						} catch (HangeulException e) {
							e.printStackTrace();
						}
					}
					else
					{
						try {
							r.repaired = createRepairedForDoeDwaeHint(candidate, i, "돼", "되");
						} catch (HangeulException e) {
							e.printStackTrace();
						}
						r.hasMistake = true;	
					}
					return true;
				}
				// 어절이 그냥 되로 끝날 경우
				// ex) 안되 -> 안돼
				// 그래도 되? -> 그래도 돼
				else
				{
					Candidate repaired = new Candidate();
					String reason = "되는 동사의 어간으로 어미 '어'와 결합해서 사용되어야 합니다. " +
									"따라서 '되어' 혹은 '되어'의 준말인 '돼'가 올바른 표현입니다.";
					repaired.originalText = "되";
					repaired.addMistake(
							new Mistake(
									reason, 
									Mistake.MISTAKE_DOE_DWAE
									)
							);
					repaired.compList.addAll(candidate.compList);
					repaired.compList.set(i, new HyungTaeSo("돼", "되", "ㄷㅙ"));
					r.repaired = repaired;
					r.hasMistake = true;	
				}
			}
			
		}
		return r.hasMistake;
	}

	private Candidate createRepairedForDoeDwaeHint(Candidate source, 
			int indexDoeDwae, String replaced, String root) throws HangeulException
	{
		Candidate repaired = new Candidate();
		repaired.compList.addAll(source.compList);
		repaired.compList.set(
				indexDoeDwae,
				new Word(
						replaced, 
						root, 
						Hangeul.spreadHangeulString(replaced),
						"동사",
						"용언"
						)
				);
		repaired.originalText = source.originalText;
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < source.compList.size(); ++i)
		{
			Component comp = source.compList.get(i);
			if(i != indexDoeDwae)
				sb.append(Hangeul.spreadHangeulString(comp.getSource()));
			else
				sb.append(Hangeul.spreadHangeulString(replaced));
		}
		
		String reason = "되는 동사 되다이고, 돼는 되어의 준말입니다. 되랑 되어를 구분하시려면 " +
				"돼를 되어로 바꾸어 보시면 됩니다. \n그래도 돼니(?) -> 그래도 되어니(X), 그래도 되니(O)";
		Mistake mistake = new Mistake(reason, Mistake.MISTAKE_DOE_DWAE);
		mistake.setConfuses(Hangeul.combineHangeulEumso(sb.toString()));
		
	
		repaired.addMistake(mistake);
		return repaired;
	}
}

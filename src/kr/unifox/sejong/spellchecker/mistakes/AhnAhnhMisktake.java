package kr.unifox.sejong.spellchecker.mistakes;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Linguistic;
import kr.unifox.sejong.ling.Word;
import kr.unifox.sejong.spellchecker.Candidate;
import kr.unifox.sejong.spellchecker.Dictionary;

/**
 * 안/않 틀리는거 바로잡아줌 
 * @author 김희규
 */
public class AhnAhnhMisktake implements MistakeCorrector {

	Dictionary dic;
	public AhnAhnhMisktake(Dictionary dic) 
	{
		this.dic = dic;
	}
	
	@Override
	public boolean isCheckStranges() {
		return false;
	}
	
	public boolean checkMistake(Candidate candidate, Repaired rep) 
	{
		if(candidate.compList.size() < 2)
			return false;
		
		Component first = candidate.compList.get(0),
				second = candidate.compList.get(1);
		// TODO: 일단 대부분의 경우인 않되만 잡도록 해놨음.
		// 
		if(Linguistic.equals(first, "않", Word.PUMSA_DONGSA) 
				&& Linguistic.equals(second, "되", HyungTaeSo.TYPE_TAIL))
		{
			// 않되고X, 안되고O
			// 않되O, 안되->안돼
			// 3개 이상일 경우 틀려부려...
			if(candidate.compList.size() >= 3)
			{
				// You're wrong!
				rep.repaired = new Candidate();
				rep.repaired.compList.addAll(candidate.compList);
				rep.repaired.compList.set(0, new Word("안", "ㅇㅏㄴ", "안", "형용사", "용언"));
				rep.repaired.addMistake(new Mistake(
						"'않'은 동사 아니하다의 준말로, 어미 '되' 앞에서는 아니나 안이 되어야 합니다.", 
						Mistake.MISTAKE_ANH_AN
						));
				rep.hasMistake = true;
			}
		}
		
		return rep.hasMistake;
	}
}

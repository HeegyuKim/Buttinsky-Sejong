package kr.unifox.friends.spellchecker;

import java.util.List;

import kr.unifox.friends.spellchecker.hangeul.ChaeEon;
import kr.unifox.friends.spellchecker.hangeul.EoGeun;
import kr.unifox.friends.spellchecker.hangeul.EoMi;
import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Josa;
import kr.unifox.friends.spellchecker.hangeul.Single;
import kr.unifox.friends.spellchecker.hangeul.YongEon;

public interface Dictionary
{
	JeopSa findJoepSa(String phenomes);
	Josa findJosa(String phenomes);
	List<ChaeEon> findChaeEon(String phenomes);
	List<YongEon> findYongEon(String rootPhenomes);
	List<Single> findSingle(String phenomes);
	EoMi findEoMi(String phenomes);
	EoGeun findEoGeun(String phenomes);
	
	List<WordComponent> normalize(List<WordComponent> comps);
	boolean isRightWord(List<WordComponent> comps);
	boolean isRightSequence(WordComponent head, WordComponent tail);
	boolean isRightTypeArray(WordComponent.Type head, WordComponent.Type tail);
}

package kr.unifox.friends.spellchecker;

import java.util.List;

import kr.unifox.friends.spellchecker.hangeul.ChaeEon;
import kr.unifox.friends.spellchecker.hangeul.JeopSa;
import kr.unifox.friends.spellchecker.hangeul.Josa;
import kr.unifox.friends.spellchecker.hangeul.Single;
import kr.unifox.friends.spellchecker.hangeul.YongEon;

public interface Dictionary
{
	JeopSa findJoepSa(String morphemes);
	Josa findJosa(String morphemes);
	List<ChaeEon> findChaeEon(String morphemes);
	List<YongEon> findYongEon(String rootMorphemes);
	List<Single> findSingle(String morphemes);
	
}

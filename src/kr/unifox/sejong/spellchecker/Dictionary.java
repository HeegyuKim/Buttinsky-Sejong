package kr.unifox.sejong.spellchecker;

import java.util.List;
import java.util.Set;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Word;

public interface Dictionary
{

	List<Component> find(String eumsos);
	
	/**
	 * 
	 * @param root 찾을 어간
	 * @return
	 */
	List<Word> findWord(String eumsos);

	/**
	 * 
	 * @param eumsos 찾을 형태소의 음소들
	 * @param allowedTypeSet 허용되는 형태소 종류 집합
	 * @return
	 */
	List<HyungTaeSo> findHyungTaeSo(String eumsos);
	
	/**
	 * 
	 * @param eumsos 찾을 형태소의 음소들
	 * @param allowedTypeSet 허용되는 형태소 종류 집합
	 * @return
	 */
	List<HyungTaeSo> findHyungTaeSoByType(String eumsos, Set<String> allowedTypeSet);
	
	/**
	 * 
	 * @param eumsos 찾을 어근의 음소
	 * @param allowedPumsaSet 허용되는 품사 집합
	 * @return
	 */
	List<Word> findWordByPumsa(String eumsos, Set<String> allowedPumsaSet);
	
	/**
	 * 
	 * @param eumsos 찾을 어근의 음소
	 * @param allowedEonSet 허용되는 언 집합
	 * @return
	 */
	List<Word> findWordByEon(String eumsos, Set<String> allowedEonSet);
}

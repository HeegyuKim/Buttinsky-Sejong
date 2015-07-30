package kr.unifox.sejong.spellchecker;

import java.util.EnumSet;
import java.util.List;

import kr.unifox.sejong.ling.Eon;
import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Pumsa;

public interface Dictionary
{
	/**
	 * 
	 * @param hyungTaeSo 찾을 형태소
	 * @return 
	 */
	List<HyungTaeSo> find(String hyungTaeSo);
	
	/**
	 * 
	 * @param root 찾을 어간
	 * @return
	 */
	List<HyungTaeSo> findByRoot(String root);
	
	/**
	 * 
	 * @param hyungTaeSo 찾을 형태소
	 * @param allowedTypeSet 허용되는 형태소 종류 집합
	 * @return
	 */
	List<HyungTaeSo> findByHyungTaeSo(String hyungTaeSo, EnumSet<HyungTaeSo.Type> allowedTypeSet);
	
	/**
	 * 
	 * @param hyungTaeSo 찾을 형태소
	 * @param allowedPumsaSet 허용되는 품사 집합
	 * @return
	 */
	List<HyungTaeSo> findByPumsa(String hyungTaeSo, EnumSet<Pumsa> allowedPumsaSet);
	
	/**
	 * 
	 * @param hyungTaeSo 찾을 형태소
	 * @param allowedEonSet 허용되는 언 집합
	 * @return
	 */
	List<HyungTaeSo> findByEon(String hyungTaeSo, EnumSet<Eon> allowedEonSet);
}

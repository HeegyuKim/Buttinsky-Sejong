package kr.unifox.sejong.ling;

import java.util.List;

import kr.unifox.friends.spellchecker.hangeul.Hangeul;

/**
 * 
 * @author KimHeekue
 * 형태소
 */
public class HyungTaeSo
{
	public enum Type {
		Root("어근"),	// 어근
		Stem("어간"),	// 어간
		Tail("어미"),	// 어미
		Suffix("접미사"),	// 접미사
		Prefix("접두사"),
		;
		
		
		String hangeulName;
		Type(String hangeulName)
		{
			this.hangeulName = hangeulName;
		}
		
	}
	
	
	/*
	 * 품사: 명사, 대명사, 수사, 동사, 
	 * 		형용사, 부사, 관형사, 조사, 감탄사
	 * 언
	 * - 체언: 명사, 대명사, 수사
	 * - 용언: 동사, 형용사
	 * - 수식언: 부사, 관형사
	 * - 관계언: 조사
	 * - 독립언: 감탄사
	 */
	
	public String eumso;	// 음소
	public List<Hangeul> hangeuls; // 한글 리스트
	public Pumsa pumsa;		// 품사
	public Eon eon;			// 언
	public Type type;		// 형태소의 종류
	
	public HyungTaeSo()
	{
	}

	public HyungTaeSo(String eumso, List<Hangeul> hangeuls, Pumsa pumsa, Eon eon, Type type)
	{
		this.eumso = eumso;
		this.hangeuls = hangeuls;
		this.pumsa = pumsa;
		this.eon = eon;
		this.type = type;
	}
	
}

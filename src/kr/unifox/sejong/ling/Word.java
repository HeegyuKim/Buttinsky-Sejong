package kr.unifox.sejong.ling;

import java.util.List;

/**
 * 
 * @author KimHeekue
 * 형태소
 */
public class Word implements Component
{
		
	
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

	public String source;		// 원형
	public String eumso;		// 음소
	public String root;			// 어근
	public String pumsa,		// 품사
				eon;			// 언
	
	public Word()
	{
	}

	public Word(String source, String eumso, String root, String pumsa, String eon) {
		this.source = source;
		this.eumso = eumso;
		this.root = root;
		this.pumsa = pumsa;
		this.eon = eon;
	}



	@Override
	public String getTypeName() {
		return pumsa;
	}

	@Override
	public String getSource() {
		return source;
	}
	
}

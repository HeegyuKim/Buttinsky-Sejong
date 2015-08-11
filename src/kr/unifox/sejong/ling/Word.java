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
	public static final String
		PUMSA_MYUNGSA = "명사",
		PUMSA_DAEMYUNGSA = "대명사",
		PUMSA_SUSA= "수사",
		PUMSA_DONGSA= "동사",
		PUMSA_HYUNGYONGSA = "형용사",
		PUMSA_BUSA = "부사",
		PUMSA_GWANHYUNGSA = "관형사",
		PUMSA_JOSA = "조사",
		PUMSA_GAMTANSA= "감탄사",
		EON_CHAEEON = "체언",
		EON_YONGEON = "용언",
		EON_SUSIKEON = "수식언",
		EON_GWANGYEEON = "관계언",
		EON_DOKRIPEON = "독립언",
		
		
		PUMSA_UNKNOWN = "알수없는품사",
		EON_UNKNOWN = "알수없는언"
		;

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
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Word)
		{
			Word that = (Word)obj;
			return source.equals(that.source)
					|| eumso.equals(that.eumso)
					|| root.equals(that.root)
					|| pumsa.equals(that.pumsa)
					|| eon.equals(that.eon)
					;
		}
		
		return false;
	}
}

package kr.unifox.friends.spellchecker;

public class WordComponent
{
	public enum Type
	{
		ChaeEon("체언", '0'),	// 체언
		YongEon("용언", '1'),	// 용언
		JeopSa("접사", '2'),		// 접사
		JeopDuSa("접두사", '3'),	// 접두사
		JeopMiSa("접미사", '4'),	// 접미사
		Josa("조사", '5'),		// 조사
		EoGan("어간", '6'),		// 어간
		EoGeun("어근", '7'),		// 어근
		EoMi("어미", '8'),		// 어미
		Single("단일", '9'),		// 단일: 관형사+부사+감탄사
		Strange("이상", 'a'),	// 이상한 구조로 되어 있는 것.
		WhiteSpace("공백", 'b'),	// 공백 ㅎ
		Unknown("모름", 'c'),		// 몰라 ㅜㅜ
		MyungSa("명사", 'd'),
		DaeMyungSa("대명사", 'e'),
		EuiJonMyungSa("의존명사", 'f'),
		DanWiMyungSa("단위명사", 'g'),
		DongSa("명사", 'h'),
		HyungYongSa("형용사", 'i'),
		BuSa("부사", 'j'),
		GwanHyungSa("관형사", 'k'),
		GamTanSa("감탄사", 'l'),
		;
		private static int typeIndex = 0;
		
		String type;
		char id;
		
		Type(String type, char id)
		{
			this.type = type;
			this.id = id;
		}
		
		public String toHangeul()
		{
			return type;
		}
		
		public static Type fromHangeul(String value)
		{
			for(Type t : Type.values())
			{
				if(t.type.equals(value))
					return t;
			}
			return null;
		}
	}
	
	public String origin, orthInWord;
	public Type type;
	
	public WordComponent()
	{
	}
	public WordComponent(String origin, String orthInWord, Type type)
	{
		this.origin = origin;
		this.orthInWord = orthInWord;
		this.type = type;
	}
}

package kr.unifox.friends.spellchecker;

public class WordComponent
{
	public enum Type
	{
		ChaeEon("체언"),	// 체언
		YongEon("용언"),	// 용언
		JoepSa("접사"),		// 접사
		JeopDuSa("접두사"),	// 접두사
		JeopMiSa("접미사"),	// 접미사
		Josa("조사"),		// 조사
		EoGan("어간"),		// 어간
		EoGun("어근"),		// 어근
		EoMi("어미"),		// 어미
		Single("단일"),		// 단일: 관형사+부사+감탄사
		Strange("이상"),	// 이상한 구조로 되어 있는 것.
		WhiteSpace("공백"),	// 공백 ㅎ
		Unknown("모름")		// 몰라 ㅜㅜ
		;
		
		String type;
		
		Type(String type)
		{
			this.type = type;
		}
		
		public String toHangeul()
		{
			return type;
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

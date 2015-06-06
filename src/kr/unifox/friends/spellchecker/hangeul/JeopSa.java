package kr.unifox.friends.spellchecker.hangeul;

public class JeopSa
{
	public String orth = "",		// 표기 형태
				morphemes = "";		// 형태소로 나누어 펼치기
	
	public boolean isPrefix = false, // 접두사로 쓰이나?
			isSuffix = false; // 접미사로 쓰이나?

	public JeopSa()
	{
	}
	
	public JeopSa(String orth, String morphemes, boolean isPrefix,
			boolean isSuffix)
	{
		this.orth = orth;
		this.morphemes = morphemes;
		this.isPrefix = isPrefix;
		this.isSuffix = isSuffix;
	}
	
	
}

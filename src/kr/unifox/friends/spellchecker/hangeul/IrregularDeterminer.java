package kr.unifox.friends.spellchecker.hangeul;


public class IrregularDeterminer
{
	
	public boolean isIrregular(String morphemes)
	{
		return morphemes.endsWith("ㅅ") ||
				morphemes.endsWith("ㅂ") ||
				morphemes.endsWith("ㅎ") ||
				morphemes.endsWith("ㄹㅡ") ||
				morphemes.endsWith("ㅇㅕ") ||
				morphemes.endsWith("ㄹㅓ") || 
				morphemes.endsWith("ㅇㅜ")
				;
	}
}

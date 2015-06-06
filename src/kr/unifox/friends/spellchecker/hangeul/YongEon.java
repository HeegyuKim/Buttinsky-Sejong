package kr.unifox.friends.spellchecker.hangeul;

public class YongEon
{
	public String orth,	// 표기형태
			root_morphemes,	// 어근 형태소
			wordClass		// 품사(동사?형용사?)
	;

	public YongEon(){}
	public YongEon(String orth, String root_morphemes, String wordClass)
	{
		this.orth = orth;
		this.root_morphemes = root_morphemes;
		this.wordClass = wordClass;
	}
}

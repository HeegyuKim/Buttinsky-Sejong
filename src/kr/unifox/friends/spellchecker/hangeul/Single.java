package kr.unifox.friends.spellchecker.hangeul;

/*
 * 홀로 어절이 되는 품사들을 의미합니다.
 * 관형사
 * 부사
 * 감탄사
 */
public class Single
{
	public String orth,
				morphemes,
				wordClass;

	public Single(){}
	public Single(String orth, String morphemes, String wordClass)
	{
		this.orth = orth;
		this.morphemes = morphemes;
		this.wordClass = wordClass;
	}

}

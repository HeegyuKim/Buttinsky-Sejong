package kr.unifox.friends.spellchecker.hangeul;

import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

public class ChaeEon 
{
	public String orth = ""; // 표기 형태
	public String morphemes = ""; // 형태소
	public String wordClass = ""; // 품사(명사,대명사,수사,단위명사,의존명사)
	
	public ChaeEon()
	{
	}
	
	public ChaeEon(String orth, String morphemes, String wordClass)
	{
		this.orth = orth;
		this.morphemes = morphemes;
		this.wordClass = wordClass;
	}
	
	
}
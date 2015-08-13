package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.List;

import kr.unifox.sejong.spellchecker.mistakes.Mistake;

public class Tokenized {
	// start <= i < end
	public int start, end; 
	public String eojeol, replaced;
	public Mistake mistake;
	public boolean hasError = false;
	public boolean isWrong = false;
	public List<String> confuses;	// 헷갈리는 것들
	
	public Tokenized(int start, int end, String eojeol) {
		super();
		this.start = start;
		this.end = end;
		this.eojeol = eojeol;
	}
	public int length()
	{
		return end - start;
	}
	public void addConfuses(Mistake mistake)
	{
		//System.out.printf("Token %s is WRONG!!!!!!!!!!!!!!\n\n", type);
		isWrong = true;
		if(confuses == null)
			confuses = new ArrayList<String>();
		confuses.addAll(mistake.confuses);
	}
}

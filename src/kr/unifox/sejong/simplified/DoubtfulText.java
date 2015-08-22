package kr.unifox.sejong.simplified;

import java.util.ArrayList;

public class DoubtfulText 
{
	public String originalText, replacedText;
	public ArrayList<Mistake> mistakeList = new ArrayList<>();

	public DoubtfulText() {
	}
	
	public DoubtfulText(String originalText, String replacedText,
			ArrayList<Mistake> mistakeList) {
		super();
		this.originalText = originalText;
		this.replacedText = replacedText;
		this.mistakeList = mistakeList;
	}

	
	public String getOriginalText() {
		return originalText;
	}

	public String getReplacedText() {
		return replacedText;
	}

	public ArrayList<Mistake> getMistakeList() {
		return mistakeList;
	}

		
	
}

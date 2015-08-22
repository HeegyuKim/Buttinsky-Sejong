package kr.unifox.sejong.simplified.regex;

import kr.unifox.sejong.simplified.Mistake;

public class RegexMistake implements Mistake 
{
	public String id, title, description, replaced;
	public int startIndex, endIndex;
	
	public RegexMistake() {
	}
	
	public RegexMistake(String id, String title, String description, String replaced, int startIndex, int endIndex) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.replaced = replaced;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}




	@Override
	public int getStartIndex() {
		return startIndex;
	}
	@Override
	public int getEndIndex() {
		return endIndex;
	}
	@Override
	public String getID() {
		return id;
	}
	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public String getReplaced() {
		return replaced;
	}
	@Override
	public boolean isError() {
		return replaced != null;
	}

}

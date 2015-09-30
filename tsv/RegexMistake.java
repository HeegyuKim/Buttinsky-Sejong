package kr.unifox.sejong.simplified.regex;

import kr.unifox.sejong.simplified.Mistake;

public class RegexMistake implements Mistake 
{
	public RegexTestItem testItem;
	public String title, description; 
	public int startIndex, endIndex;
	
	public RegexMistake() {
	}
	
	public RegexMistake(RegexTestItem testItem, String title, String description, int startIndex, int endIndex) {
		this.testItem = testItem;
		this.title = title;
		this.description = description;
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
		return testItem.id;
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
		return testItem.replaced;
	}
	@Override
	public boolean isError() {
		return testItem.replaced != null;
	}

}

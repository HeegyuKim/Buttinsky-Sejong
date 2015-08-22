package kr.unifox.sejong.simplified.regex;

import java.util.regex.Pattern;

public class RegexTestItem 
{
	public String id, replaced, regex;
	public Pattern pattern;
	public boolean spread;
	
	public RegexTestItem() {
	}

	public RegexTestItem(String id, String replaced, String regex, boolean spread) {
		super();
		this.id = id;
		this.replaced = replaced;
		this.regex = regex;
		this.spread = spread;
		pattern = Pattern.compile(regex);
	}
	
	public boolean isError()
	{
		return replaced != null;
	}
	
}

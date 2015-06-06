package kr.unifox.friends.spellchecker;

import java.util.ArrayList;
import java.util.List;

public class CheckResult
{
	public List<WordComponent> components = new ArrayList<WordComponent>();
	public String originSentence = "",
				modifiedSentence = "";
}

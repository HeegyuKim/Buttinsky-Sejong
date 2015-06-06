package kr.unifox.friends.spellchecker;

import java.util.ArrayList;
import java.util.List;

public class Candidate
{
	public final List<WordComponent> comps = new ArrayList<WordComponent>();
	
	public Candidate()
	{
	}
	
	
	
	public static Candidate merge(Candidate prev, Candidate last)
	{
		Candidate c = new Candidate();
		c.comps.addAll(prev.comps);
		c.comps.addAll(last.comps);
		return c;
	}
}

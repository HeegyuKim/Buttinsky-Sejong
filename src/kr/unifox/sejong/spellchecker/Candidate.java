package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.List;

import kr.unifox.sejong.ling.Component;

public class Candidate 
{
	public List<Component> compList;
	
	public Candidate() {
	}
	
	
	public String toString()
	{
		if(compList == null || compList.isEmpty())
			return "(empty)";

		StringBuilder sb = new StringBuilder();
		sb.append(compList.get(0).getSource());
		
		int i = 0;
		sb.append("(");
		for(Component c : compList)
		{
			sb.append(c.getTypeName());
			if(++i != compList.size())
				sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}
}

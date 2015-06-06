package kr.unifox.friends.spellchecker.hangeul;

import java.util.ArrayList;
import java.util.List;

public class HangeulString
{
	List<Object> obj = new ArrayList<Object>();
	
	public HangeulString(String str)
	{
		for(int i = 0; i < str.length(); ++i)
		{
			int cp = str.codePointAt(i);
			
			if(Hangeul.isHangeul(cp))
			{
				try
				{
					obj.add(new Hangeul((char)cp));
					continue;
				}
				catch (HangeulException e)
				{
					e.printStackTrace();
				}
			}			
			
			obj.add(str.charAt(i));
		}
	}
	
	
	@Override
	public String toString()
	{
		return null;
	}
}

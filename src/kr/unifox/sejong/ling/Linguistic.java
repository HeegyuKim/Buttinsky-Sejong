package kr.unifox.sejong.ling;

public class Linguistic 
{
	
	public static boolean equals(Component comp, String source, String type)
	{
		return comp.getSource().equals(source) &&
				comp.getTypeName().equals(type);
	}
}

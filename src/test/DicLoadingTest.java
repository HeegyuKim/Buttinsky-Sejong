package test;

import java.net.URISyntaxException;

import kr.unifox.friends.spellchecker.LineTextDictionaryParser;
import kr.unifox.friends.spellchecker.TextDataDictionary;
import kr.unifox.friends.spellchecker.WordComponent;
import kr.unifox.friends.spellchecker.WordComponent.Type;

public class DicLoadingTest
{
	TextDataDictionary dic;
	
	public DicLoadingTest() throws Exception
	{
		dic = new TextDataDictionary("db");
		
		System.out.println("\n\nTest Start\n");
		
		testTypeArray(Type.ChaeEon, Type.EoMi);
	}
	
	private void testTypeArray(WordComponent.Type a, WordComponent.Type b)
	{
		System.out.printf("%s & %s -> ", a.toHangeul(), b.toHangeul());
		if(dic.isRightTypeArray(a, b))
			System.out.println("ok");
		else
			System.out.println("no");
	}
	
	
	public static void main(String[] args) throws Exception
	{
		new DicLoadingTest();
	}

}

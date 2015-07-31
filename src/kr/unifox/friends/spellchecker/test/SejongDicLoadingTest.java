package kr.unifox.friends.spellchecker.test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Word;
import kr.unifox.sejong.spellchecker.TextFileDictionary;

public class SejongDicLoadingTest {

	public static void main(String[] args) {

		try 
		{
			TextFileDictionary dic = new TextFileDictionary("db");
			
			for(HyungTaeSo hts : dic.findHyungTaeSo("ㄴ"))
				print(hts);
			
			for(Word word: dic.findWord("ㄱㅏㅁ"))
				print(word);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void print(Object obj)
	{
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		
		for(Field f : fields)
		{
			if(Modifier.isStatic(f.getModifiers()))
				continue;
			
			try {
				System.out.printf("%s = %s\n", f.getName(), f.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		System.out.println("---------------------------------------\n");
	}
}

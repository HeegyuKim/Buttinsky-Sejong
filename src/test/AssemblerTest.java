package test;

import java.util.ArrayList;
import java.util.List;

import kr.unifox.sejong.assembler.Assembler;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.spellchecker.Tokenized;

import static java.lang.System.out;

public class AssemblerTest
{
	
	
	public AssemblerTest(String testString) throws HangeulException
	{
		out.printf("Test: %s\n", testString);
		
		Assembler assembler = new Assembler();
		String spreadText = spread(testString);
		
		out.printf("펼쳐진 텍스트: %s\n", spreadText);

		
		for(int i = 0; i < spreadText.length(); ++i)
		{
			char ch = spreadText.charAt(i);

			out.println(assembler.getAssemblingString() + " + " + ch);
			assembler.logForDebugging(out);
			
			if(ch == 'D')
				assembler.delete(1);
			else
				assembler.append(ch);
			
			out.println("결과: " + assembler.getAssemblingString());
			out.println("--------------------------------");
		}
	}
	

	public String spread(String text) throws HangeulException
	{
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < text.length(); ++i)
		{
			char ch = text.charAt(i);
			int cp = text.codePointAt(i);
			if(Hangeul.isHangeul(cp))
			{
				Hangeul hangeul = new Hangeul(text.charAt(i));
				char chars[] = hangeul.toChars();
				for(char phoneme : chars)
					if(phoneme != ' ')
						sb.append(phoneme);
			}
			else
			{
				sb.append(text.charAt(i));
			}
		}
			
		
		return sb.toString();
	}
	
	
	
	public static void main(String[] args)
	{
		try
		{
			new AssemblerTest("안녕하십니까. 저는DD 누구일까요?");
			new AssemblerTest("안노ㅏ ㅋㅋㅋㅋㅋㅋ 졸라 어으ㅣ업ㅅ네");
		}
		catch (HangeulException e)
		{
			e.printStackTrace();
		}
	}

	
}

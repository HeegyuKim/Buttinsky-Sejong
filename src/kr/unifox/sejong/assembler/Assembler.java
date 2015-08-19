package kr.unifox.sejong.assembler;

import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;

import static java.lang.System.out;

import java.io.PrintStream;

public class Assembler
{
	StringBuilder assemblingBuilder;
	boolean assemblePhonemeEnabled = true;
	
	
	public Assembler()
	{
		assemblingBuilder = new StringBuilder();
	}

	char cho = ' ', jung = ' ', jong = ' ', 
			jong1 = ' ', jong2 = ' ';	// 종1, 종2는 조합된 종성일 때(ㅆ,ㅀ 등) 합성된 두 음소임.
	
	char lastAppendedChar = 0;
	boolean lastAppendedCharIsJaEum = false,
			lastCharIsHangeul = false,
			isAssemblingHangeul = false;
	
	public void append(char ch) throws HangeulException 
	{
		int cp = Character.codePointAt(new char[]{ch}, 0);
		
		if(Hangeul.isHangeul(cp))
		{
			// 자음이네?
			if(Hangeul.isJaEum(ch))
			{
				// 한글 조합중이군?
				if(isAssemblingHangeul)
				{
					// 중성이 없으면 중성을 추가하자(초성만 조합됨)
					if(cho == ' ')
					{
						cho = ch;
						isAssemblingHangeul = true;
					}
					// 종성이 없으면 종성을 추가하자(초성만 조합됨)
					else if(jong == ' ')
					{
						if(jung != ' ')
							jong = ch;
						else
						{
							assemblingBuilder.append(ch);
							cho = ch;
						}
					}
					// 종성이 있고 이전에 추가가 된 글자가 자음이다?
					// 자음확인은 안필요할지도...
					else if(lastAppendedCharIsJaEum)
					{
						// 종성과 새로운 글자를 합쳐본다.
						char dp = PhonemeAssembler.assembleDoubleJongsung(
								jong, ch
								);
						
						// 안 합쳐져요!
						if(dp == 0)
						{
							// 그러면 새로운 글자
							char newChar = new Hangeul(cho, jung, jong).toChar();
							assemblingBuilder.append(newChar);
							cho = ch;
							jung = ' ';
							jong = ' ';
						}
						// 종성을 합친 글자로~
						else
						{
							jong1 = jong;
							jong2 = ch;
							jong = dp;
						}
					}
				}
				// 조합중이지 않군?
				else
				{
					isAssemblingHangeul = true;
					cho = ch;
				}
				
				lastAppendedCharIsJaEum = true;
			}
			// 모음이지?
			else
			{
				// 한글 조합중?
				if(isAssemblingHangeul)
				{
					// 마지막이 자음이여?
					if(lastAppendedCharIsJaEum)
					{
						// 종성이 있네?
						// 종성 + 모음으로 바꿔부려야지
						if(jong != ' ')
						{
							if(PhonemeAssembler.isDoubleJongsung(jong))
							{
								jong = jong1;
								assemblingBuilder.append(new Hangeul(cho, jung, jong).toChar());
								cho = jong2;
								jung = ch;
								jong = ' ';
							}
							else
							{
								assemblingBuilder.append(
										new Hangeul(cho, jung, ' ').toChar()
										);
								cho = jong;
								jung = ch;
								jong = ' ';
								jong1 = jong2 = ' ';
							}
						}
						else if(jung != ' ')
						{
							assemblingBuilder.append(new Hangeul(cho, jung, jong).toChar());
							cho = ' ';
							jung = ch;
						}
						else if(cho != ' ')
						{
							jung = ch;
						}
					}
				}
				// 아니여? 그러면 모음 솔로조합
				else
				{
					if(jung == ' ')
					{
						cho = ' ';
						jung = ch;
						isAssemblingHangeul = true;	
					}
					else
					{
						char newChar = PhonemeAssembler.assembleMoEum(jung, ch);
						if(newChar == 0)
						{
							
						}
						else
						{
							
						}
					}
				}
				lastAppendedCharIsJaEum = false;
			}
			
			lastCharIsHangeul = true;
		}
		else
		{
			if(isAssemblingHangeul)
			{
				assemblingBuilder.append(
						new Hangeul(cho, jung, jong).toChar()
						);
				isAssemblingHangeul = false;
				cho = jung = jong = ' ';				
			}
			assemblingBuilder.append(ch);
			lastAppendedCharIsJaEum = false;
			lastCharIsHangeul = false;
		}
		
		lastAppendedChar = ch;
	}
	
	public int delete(int count)  {
		int deletingCount = 0;
		
		if(isAssemblingHangeul)
		{
			// 종성을 제거한다!
			if(count > 0 && jong != ' ')
			{
				jong = ' ';
				-- count;
				++ deletingCount;
			}
			if(count > 0 && jung != ' ')
			{
				jung = ' ';
				-- count;
				++ deletingCount;
			}
			if(count > 0 && cho != ' ')
			{
				cho = ' ';
				-- count;
				++ deletingCount;
			}
		}
		
		if(count == 0)
			return deletingCount;

		int length = assemblingBuilder.length();
		int start = length - count;
		if(start < 0)
			start = 0;
		
		assemblingBuilder.delete(start, length);
		
		deletingCount = length - start;		

		if(cho == ' ')
			isAssemblingHangeul = false;
		
		lastAppendedChar = 0;
		lastCharIsHangeul = false;
		lastAppendedCharIsJaEum = false;
	
		return deletingCount;
	}
	
	
	public void logForDebugging(PrintStream out)
	{
		out.printf("%c%c%c(%c+%c)\n", cho, jung, jong, jong1, jong2);
		out.printf("lastAppendedChar: %c\n", lastAppendedChar);
		out.printf("lastCharIsHangeul: %s\n", lastCharIsHangeul);
		out.printf("lastAppendedCharIsJaEum: %s\n", lastAppendedCharIsJaEum);
		out.printf("isAssemblingHangeul: %s\n", isAssemblingHangeul);
	}
	
	public String getAssemblingString() throws HangeulException
	{
		if(isAssemblingHangeul)
		{
			return assemblingBuilder.toString() +
					new Hangeul(cho, jung, jong).toChar();
		}
		else
			return assemblingBuilder.toString();
	}
}

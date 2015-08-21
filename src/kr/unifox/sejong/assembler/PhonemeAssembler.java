package kr.unifox.sejong.assembler;

public class PhonemeAssembler
{
	
	/**
	 * 경음을 분해한다.
	 * @param ch
	 * @return
	 */
	public static MixedPhoneme disassembleFortis(char ch)
	{
		if(ch == 'ㄲ')
			return new MixedPhoneme('ㄱ', 'ㄱ', ch);
		if(ch == 'ㅉ')
			return new MixedPhoneme('ㅈ', 'ㅈ', ch);
		if(ch == 'ㄸ')
			return new MixedPhoneme('ㄷ', 'ㄷ', ch);
		if(ch == 'ㅃ')
			return new MixedPhoneme('ㅂ', 'ㅂ', ch);
		if(ch == 'ㅆ')
			return new MixedPhoneme('ㅅ', 'ㅅ', ch);
		
		return null;
	}
	public static MixedPhoneme disassembleDoubleJongsung(char ch)
	{
		if(ch == 'ㄳ')
			return new MixedPhoneme('ㄱ', 'ㅅ', ch);
		if(ch == 'ㄵ')
			return new MixedPhoneme('ㄴ', 'ㅈ', ch);
		if(ch == 'ㄶ')
			return new MixedPhoneme('ㄴ', 'ㅎ', ch);
		if(ch == 'ㄺ')
			return new MixedPhoneme('ㄹ', 'ㄱ', ch);
		if(ch == 'ㄻ')
			return new MixedPhoneme('ㄹ', 'ㅁ', ch);
		if(ch == 'ㄼ')
			return new MixedPhoneme('ㄹ', 'ㅂ', ch);
		if(ch == 'ㄽ')
			return new MixedPhoneme('ㄹ', 'ㅅ', ch);
		if(ch == 'ㄾ')
			return new MixedPhoneme('ㄹ', 'ㅌ', ch);
		if(ch == 'ㅀ')
			return new MixedPhoneme('ㄹ', 'ㅎ', ch);
		if(ch == 'ㅄ')
			return new MixedPhoneme('ㅂ', 'ㅅ', ch);
		
		return null;
	}

	public static MixedPhoneme disassembleDoubleMoEum(char ch)
	{
		if(ch == 'ㅘ')
			return new MixedPhoneme('ㅗ', 'ㅏ', ch);
		if(ch == 'ㅙ')
			return new MixedPhoneme('ㅗ', 'ㅐ', ch);
		if(ch == 'ㅚ')
			return new MixedPhoneme('ㅗ', 'ㅣ', ch);
		if(ch == 'ㅝ')
			return new MixedPhoneme('ㅜ', 'ㅓ', ch);
		if(ch == 'ㅞ')
			return new MixedPhoneme('ㅜ', 'ㅔ', ch);
		if(ch == 'ㅟ')
			return new MixedPhoneme('ㅜ', 'ㅣ', ch);
		if(ch == 'ㅢ')
			return new MixedPhoneme('ㅡ', 'ㅣ', ch);
		
		return null;
	}
	
	
	public static boolean isMixedPhoneme(char p)
	{
		switch(p) {
		case 'ㄲ':
		case 'ㅉ':
		case 'ㄸ':
		case 'ㅃ':
		case 'ㅆ':
			
		case 'ㄳ':
		case 'ㄵ':
		case 'ㄶ':
		case 'ㄺ':
		case 'ㄻ':
		case 'ㄼ':
		case 'ㄽ':
		case 'ㄾ':
		case 'ㅀ':
		case 'ㅄ':
			
		case 'ㅘ':
		case 'ㅙ':
		case 'ㅚ':
		case 'ㅝ':
		case 'ㅞ':
		case 'ㅟ':
		case 'ㅢ':
			return true;
		default:
			return false;
		}
	}

	public static boolean isDoubleJongsung(char ch)
	{
		switch(ch) {
		case 'ㄳ':
		case 'ㄵ':
		case 'ㄶ':
		case 'ㄺ':
		case 'ㄻ':
		case 'ㄼ':
		case 'ㄽ':
		case 'ㄾ':
		case 'ㅀ':
		case 'ㅄ':
			return true;
		default:
			return false;
		}
	}
	public static boolean isDoubleMoEum(char ch)
	{
		switch(ch) {
		case 'ㅘ':
		case 'ㅙ':
		case 'ㅚ':
		case 'ㅝ':
		case 'ㅞ':
		case 'ㅟ':
		case 'ㅢ':
			return true;
		default:
			return false;
		}
	}
	public static boolean isForst(char ch)
	{
		switch(ch) {
		case 'ㄲ':
		case 'ㅉ':
		case 'ㄸ':
		case 'ㅃ':
		case 'ㅆ':
			return true;
		default:
			return false;
		}
	}
	
	/**
	 * 자음 두개를 합침
	 * ㄲㄸㅃㅆㅉ
	 * ㄳㄵㄶㄺㄻㄼㄽㄾㅀㅄ
	 * 
	 * @param p1
	 * @param p2
	 * @return 0 if no result.
	 */
	public static char assembleMixed(char p1, char p2)
	{
		if(p1 == 'ㄱ' && p2 == 'ㄱ')
			return 'ㄲ';
		if(p1 == 'ㄷ' && p2 == 'ㄷ')
			return 'ㄸ';
		if(p1 == 'ㅂ' && p2 == 'ㅂ')
			return 'ㅃ';
		if(p1 == 'ㅅ' && p2 == 'ㅅ')
			return 'ㅆ';
		if(p1 == 'ㅈ' && p2 == 'ㅈ')
			return 'ㅉ';
		
		if(p1 == 'ㄱ' && p2 == 'ㅅ')
			return 'ㄳ';
		if(p1 == 'ㄴ' && p2 == 'ㅈ')
			return 'ㄵ';
		if(p1 == 'ㄴ' && p2 == 'ㅎ')
			return 'ㄶ';
		if(p1 == 'ㄹ' && p2 == 'ㄱ')
			return 'ㄺ';
		if(p1 == 'ㄹ' && p2 == 'ㅁ')
			return 'ㄻ';
		if(p1 == 'ㄹ' && p2 == 'ㅂ')
			return 'ㄼ';
		if(p1 == 'ㄹ' && p2 == 'ㅅ')
			return 'ㄽ';
		if(p1 == 'ㄹ' && p2 == 'ㅌ')
			return 'ㄾ';
		if(p1 == 'ㄹ' && p2 == 'ㅎ')
			return 'ㅀ';
		if(p1 == 'ㅂ' && p2 == 'ㅅ')
			return 'ㅄ';
		return 0;
	}

	public static char assembleFortis(char p1, char p2)
	{
		if(p1 == 'ㄱ' && p2 == 'ㄱ')
			return 'ㄲ';
		if(p1 == 'ㄷ' && p2 == 'ㄷ')
			return 'ㄸ';
		if(p1 == 'ㅂ' && p2 == 'ㅂ')
			return 'ㅃ';
		if(p1 == 'ㅅ' && p2 == 'ㅅ')
			return 'ㅆ';
		if(p1 == 'ㅈ' && p2 == 'ㅈ')
			return 'ㅉ';
		return 0;
	}
	
	public static char assembleDoubleJongsung(char p1, char p2)
	{
		if(p1 == 'ㄱ' && p2 == 'ㅅ')
			return 'ㄳ';
		if(p1 == 'ㄴ' && p2 == 'ㅈ')
			return 'ㄵ';
		if(p1 == 'ㄴ' && p2 == 'ㅎ')
			return 'ㄶ';
		if(p1 == 'ㄹ' && p2 == 'ㄱ')
			return 'ㄺ';
		if(p1 == 'ㄹ' && p2 == 'ㅁ')
			return 'ㄻ';
		if(p1 == 'ㄹ' && p2 == 'ㅂ')
			return 'ㄼ';
		if(p1 == 'ㄹ' && p2 == 'ㅅ')
			return 'ㄽ';
		if(p1 == 'ㄹ' && p2 == 'ㅌ')
			return 'ㄾ';
		if(p1 == 'ㄹ' && p2 == 'ㅎ')
			return 'ㅀ';
		if(p1 == 'ㅂ' && p2 == 'ㅅ')
			return 'ㅄ';
		return 0;
	}

	/**
	 * 모음 두개를 합침
	 * ㅘㅙㅚㅝㅞㅟㅢ
	 * 
	 * @param p1
	 * @param p2
	 * @return 0 if no result.
	 */
	public static char assembleMoEum(char p1, char p2)
	{
		if(p1 == 'ㅗ' && p2 == 'ㅏ')
			return 'ㅘ';
		if(p1 == 'ㅗ' && p2 == 'ㅐ')
			return 'ㅙ';
		if(p1 == 'ㅗ' && p2 == 'ㅣ')
			return 'ㅚ';
		if(p1 == 'ㅜ' && p2 == 'ㅓ')
			return 'ㅝ';
		if(p1 == 'ㅜ' && p2 == 'ㅔ')
			return 'ㅞ';
		if(p1 == 'ㅜ' && p2 == 'ㅣ')
			return 'ㅟ';
		if(p1 == 'ㅡ' && p2 == 'ㅣ')
			return 'ㅢ';
		return 0;
	}
}

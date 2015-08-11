package kr.unifox.sejong.ling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import kr.unifox.friends.spellchecker.hangeul.NotHangeulException;

public class Hangeul
{
	
	public static final class Jaum 
	{
		public static final char
			KIYUK = 'ㄱ',
			NIEUN = 'ㄴ',
			DIGUT = 'ㄷ',
			RIEUL = 'ㄹ',
			MIEUM = 'ㅁ',
			BIEUB = 'ㅂ',
			SIEOT = 'ㅅ',
			EIEUNG = 'ㅇ',
			JIEUT = 'ㅈ',
			CHIEUT = 'ㅊ',
			KIEUT = 'ㅋ',
			TIEUT = 'ㅌ',
			PIEUT = 'ㅍ',
			HIEUT = 'ㅎ',
		
			SSANG_KIYUK = 'ㄲ',
			SSANG_DIGUT = 'ㄸ',
			SSANG_BIEUB = 'ㅃ',
			SSANG_SIOT = 'ㅆ',
			SSANG_JIUT = 'ㅉ',

			KIYUK_SIOT = 'ㄳ',
			NIEUN_JIEUT = 'ㄵ',
			NIEUN_HIEUT = 'ㄶ',
			NIEUN_KIYUK = 'ㄺ',
			NIEUN_MIEUM = 'ㄻ',
			RIEUL_BIEUB = 'ㄼ',
			RIEUL_SIOT = 'ㄽ',
			RIEUL_TIEUT = 'ㄾ',
			RIEUL_HIEUT = 'ㅀ',
			BIEUB_SIOT = 'ㅄ';
	}
	
	public static final class Moum
	{
		public static final char
			A = 'ㅏ',
			AE = 'ㅐ',
			YA = 'ㅑ',
			YAE = 'ㅒ',
			EO = 'ㅓ',
			E = 'ㅔ',
			YEO = 'ㅕ',
			YE = 'ㅖ',
			O = 'ㅗ',
			WA = 'ㅘ',
			WAE = 'ㅙ',
			OE = 'ㅚ',
			YO = 'ㅛ',
			U = 'ㅜ',
			WO= 'ㅝ',
			WE = 'ㅞ',
			WI = 'ㅟ',
			YU = 'ㅠ',
			EU = 'ㅡ',
			UI = 'ㅢ',
			I = 'ㅣ';
	}

	public static final String 
		YongEon = ("용언"),
		ChaeEon = ("체언"),
		SooSikEon =("수식언"),
		GwanGyeEon =("관계언"),
		DokRipEon =("독립언")
	
	;	
	public static final String
		MyungSa = "명사",
		DaeMyungSa = "대명사",
		SuSa = "수사",
		DongSa = "동사",
		HyungYongSa = "형용사",
		GwanHyungSa = "관형사",
		BuSa = "부사",
		GamTanSa = "감탄사",
		JoSa = "조사"	
	;

	/*
	 * 참고 링크
	 * http://warmz.tistory.com/717
	 * 
	 */

    // 유니코드 한글 시작 : 44032, 끝 : 55199
	public static final int BASE_CODE = 0xAC00; // = 44032
	public static final int HANGEUL_END = 0xD79F; // = 55199
	
    public static final int CHOSUNG_CODE = 588;
    public static final int JUNGSUNG_CODE = 28;
    public static final int COMP_JAEUM_BEGIN = 0x3131,
    						COMP_JAEUM_END = 0x314E,
    						COMP_MOEUM_BEGIN = 0X314F,
    						COMP_MOEUM_END = 0X3163;
    // 초성 리스트. 00 ~ 18
    public static final char[] CHOSUNG_LIST = {
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 
        'ㅅ', 'ㅆ', 'ㅇ' , 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
     
    // 중성 리스트. 00 ~ 20
    public static final char[] JUNGSUNG_LIST = {
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 
        'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 
        'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 
        'ㅡ', 'ㅢ', 'ㅣ'
    };
     
    // 종성 리스트. 00 ~ 27 + 1(1개 없음)
    public static final char[] JONGSUNG_LIST = {
        ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ',
        'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 
        'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 
        'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    
    
    
    public static int getJongsungIndex(char jongsung)
    {
    	for(int i = 0; i < JONGSUNG_LIST.length; ++i)
    		if(jongsung == JONGSUNG_LIST[i])
    			return i;
    	return -1;
    }
    public static int getChosungIndex(char chosung)
    {
    	for(int i = 0; i < CHOSUNG_LIST.length; ++i)
    		if(chosung == CHOSUNG_LIST[i])
    			return i;
    	return -1;
    }
    public static int getJungsungIndex(char jungsung)
    {
    	for(int i = 0; i < JUNGSUNG_LIST.length; ++i)
    		if(jungsung == JUNGSUNG_LIST[i])
    			return i;
    	return -1;
    }
    
    public static boolean isJaEum(char ch)
    {
    	return ch >= COMP_JAEUM_BEGIN && ch <= COMP_JAEUM_END;
    }
    public static boolean isMoEum(char ch)
    {
    	return ch >= COMP_MOEUM_BEGIN && ch <= COMP_MOEUM_END;
    }
    public static boolean isJamo(char ch)
    {
    	return isJaEum(ch) || isMoEum(ch);
    }
    
    
    
    
    
    
    
    
    
    
    public char getChosung()
	{
		return chosung;
	}
	public char getJungsung()
	{
		return jungsung;
	}
	public char getJongsung()
	{
		return jongsung;
	}











	private char chosung = ' ', jungsung = ' ', jongsung = ' ';
    

    public Hangeul(char hangeulCharacter) throws HangeulException
    {
    	int code = (int)hangeulCharacter;


    	if((code >= COMP_JAEUM_BEGIN && code <= COMP_JAEUM_END))
    	{
    		this.chosung = hangeulCharacter;
    		return;
    	}
    	if((code >= COMP_MOEUM_BEGIN && code <= COMP_MOEUM_END))
    	{
    		this.jungsung = hangeulCharacter;
    		return;
    	}
    	
    	
    	if(!isHangeul(hangeulCharacter))
    		throw new NotHangeulException(hangeulCharacter);
    	
    	int base = hangeulCharacter - BASE_CODE;
    	int c1 = (char)(base / CHOSUNG_CODE);
    	int c2 = (char)((base - (CHOSUNG_CODE * c1)) / JUNGSUNG_CODE);
    	int c3 = (char)((base - (CHOSUNG_CODE * c1) - (JUNGSUNG_CODE * c2)));
    	
    	this.chosung = CHOSUNG_LIST[c1];
    	this.jungsung = JUNGSUNG_LIST[c2];
    	this.jongsung = JONGSUNG_LIST[c3];
    }
    
    
    
    public Hangeul(char chosung, char jungsung, char jongsung) throws HangeulException
    {
    	
    	if(dbg((chosung != ' ' && !isJaEum(chosung)), "초성이상") || 
    			dbg((jungsung != ' ' && !isMoEum(jungsung)), "중성이상") || 
    			dbg((jongsung != ' ' && !isJaEum(jongsung)), "종성이상")
    			)
    		throwHE(chosung, jungsung, jongsung);
		
    	this.chosung = chosung;
    	this.jungsung = jungsung;
    	this.jongsung = jongsung;
    }
    private boolean dbg(boolean b, String t)
    {
    	if(b)
    		System.out.println(t);
    	return b;
    }
    
    private void throwHE(char c1, char c2, char c3) throws NotHangeulException
    {
		throw new NotHangeulException(
				String.format("Could not compose the Hangeul with '%c' '%c' '%c'",
						c1, c2, c3
						)
				);
    }
    
    public boolean isStrange()
    {
    	return isHangeul(chosung) || 
    			isHangeul(jungsung) || 
    			isHangeul(jongsung);
    }
    
    
    public char toChar()
    {
    	int c1 = getChosungIndex(chosung),
    			c2 = getJungsungIndex(jungsung),
    			c3 = getJongsungIndex(jongsung);
    	
    	return (char)(BASE_CODE + c3 + c2 * JUNGSUNG_CODE + c1 * CHOSUNG_CODE);
    }
    
    public int getJamoCount()
    {
    	int count = 0;
    	if(chosung != ' ') ++ count;
    	if(jungsung != ' ') ++ count;
    	if(jongsung != ' ') ++ count;
    	
    	return count;
    }
    
    public char[] toChars()
    {
    	return new char[]{
    		chosung,
    		jungsung,
    		jongsung
    	};
    }
    
    
    public String spread()
    {
    	StringBuilder builder = new StringBuilder();
    	builder.append(chosung);
    	if(jungsung != ' ')
    	builder.append(jungsung);
    	if(jongsung != ' ')
    		builder.append(jongsung);
    	
    	return builder.toString();
    }
    
    
    public static boolean isHangeul(int codePoint)
    {
    	return codePoint >= BASE_CODE && codePoint <= HANGEUL_END;
    }
    
    /**
     * 
     * @param hangeulText
     * This parameter string must be composed by only Hangeul!
     * Other 
     * @return
     */
    public static List<Hangeul> toHangeulList(String hangeulText)
    		throws HangeulException
    {
    	ArrayList<Hangeul> hangeulList = new ArrayList<Hangeul>(hangeulText.length());
    	
    	for(int i = 0; i < hangeulText.length(); ++i)
    	{
    		char ch = hangeulText.charAt(i);
    		hangeulList.add(new Hangeul(ch));
    	}
    	
    	return hangeulList;
    }
    
    public static String spreadHangeulString(String hangeulText) throws HangeulException
    {
    	StringBuilder builder = new StringBuilder();
    	for(int i = 0; i < hangeulText.length(); ++i)	
    	{
    		char ch = hangeulText.charAt(i);
    		if(isHangeul(ch))
    			builder.append(new Hangeul(ch).spread());
    		else
    			builder.append(ch);
    	}
    	
    	return builder.toString();
    }
    
    public static String combineHangeulString(String spreadText) throws HangeulException
    {
    	// TODO: 구현안됨.
    	return spreadText;
    }
    
    public static String HangeulListToString(List<Hangeul> hangeulList)
    {
    	StringBuilder builder = new StringBuilder(hangeulList.size());
    	
    	return builder.toString();
    }

	
	public static String cutJamo(List<Hangeul> hlist, int end) throws HangeulException
	{
		int jamoCount = 0;
		for(int i = 0; i < hlist.size(); ++i)
		{
			Hangeul h = hlist.get(i);
			jamoCount += h.getJamoCount();
			
			if(jamoCount >= end)
			{
				StringBuilder sb = new StringBuilder();
				for(int j = 0; j < i; ++j)
				{
					sb.append(hlist.get(j).toChar());
				}
				
				int over = jamoCount - end;
				if(over == 0)
					sb.append(h.toChar());
				if(over == 1)
					sb.append(new Hangeul(h.chosung, h.jungsung, ' ').toChar());
				if(over == 2)
					sb.append(h.chosung);
				
				return sb.toString();
			}
		}
		return HangeulListToString(hlist);
	}
}

package kr.unifox.friends.spellchecker.test;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;

import kr.unifox.friends.spellchecker.hangeul.NotHangeulException;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;

import static java.lang.System.out;

public class HangeulTestMain extends TestFrame
{
	JLabel label1, label2;


	public HangeulTestMain()
	{
		super("한글 테스트", 500, 300);

	}

	public static void isJaEum(char ch)
	{
		System.out.printf("%c는 자음입니까? %s\n", ch, Hangeul.isJaEum(ch));
	}
	public static void isHangeul(char ch)
	{
		System.out.printf("%c는 한글입니까? %s\n", ch, Hangeul.isHangeul(ch));
	}
	public static void combine(String eumsos) throws HangeulException
	{
		System.out.printf("%s 를 결합! 두구두구 -->  %s\n", eumsos, Hangeul.combineHangeulEumso(eumsos));
	}
	public static void showHangeul(char cho, char jung, char jong) throws HangeulException
	{
		System.out.printf("%c + %c + %c = %c\n", cho, jung, jong, new Hangeul(cho, jung, jong).toChar());
	}
	public static void main(String[] args)
	{
		try
		{
			Hangeul a = new Hangeul('아');
			Hangeul b = new Hangeul('앜');
			Hangeul c = new Hangeul('ㅇ');
			Hangeul d = new Hangeul('아');
			
			out.println(a.spread());
			out.println(a.toChar());
			out.println(Hangeul.spreadHangeulString("룰루랄라"));
			out.println(Hangeul.spreadHangeulString("됬다"));
			out.println(Hangeul.spreadHangeulString("됐다"));
			out.printf("%s: %d\n", b.spread(), b.getJamoCount());
			out.printf("%s: %d\n", c.spread(), c.getJamoCount());
			out.printf("%s: %d\n", d.spread(), d.getJamoCount());
			
			String hstr = "안녕";
			List<Hangeul> hlist = Hangeul.toHangeulList(hstr);
			out.printf("%s -3= %s\n", hstr, Hangeul.cutJamo(hlist, 5));

			isHangeul('ㅆ');
			isHangeul('ㅁ');

			showHangeul('ㄱ', ' ', ' ');
			showHangeul('ㄱ', 'ㅡ', ' ');
			showHangeul('ㄱ', 'ㅏ', 'ㅅ');
			showHangeul('ㄱ', 'ㅏ', 'ㅀ');
			combine("ㄱㅡㄹㅓㅎㄱㅔ");
			combine("ㅋㅋㅣㅏㅣㅓㄴㄹ");
			combine("ㅁㄴㅇㄼㅁㄴ");
			combine("ㄱㅏㄴㅏㅇㅛ");
			combine("ㄱㅡㄹㅓㄴ");
		}
		catch (HangeulException e)
		{
			e.printStackTrace();
		}
	}

}

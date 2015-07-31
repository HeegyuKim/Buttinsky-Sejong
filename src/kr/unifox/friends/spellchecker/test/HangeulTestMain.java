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
			out.printf("%s: %d\n", b.spread(), b.getJamoCount());
			out.printf("%s: %d\n", c.spread(), c.getJamoCount());
			out.printf("%s: %d\n", d.spread(), d.getJamoCount());
			
			String hstr = "안녕";
			List<Hangeul> hlist = Hangeul.toHangeulList(hstr);
			out.printf("%s -3= %s\n", hstr, Hangeul.cutJamo(hlist, 5));
		}
		catch (HangeulException e)
		{
			e.printStackTrace();
		}
	}

}

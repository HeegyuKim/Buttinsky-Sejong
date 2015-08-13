package kr.unifox.sejong.spellchecker.mistakes;

import java.util.ArrayList;
import java.util.List;

public class Mistake 
{
	public static final String
	MISTAKE_JOSA_JONGSUNG = "mis_eun_neun",
	MISTAKE_ANH_AN = "mis_an_anh",
	MISTAKE_DOE_DWAE= "mis_doe_dwae",
	WRONG_CONTINUED = "err_wrong_contd",
	WRONG_NO_ROOT = "err_no_root"
	;
	
	public String reason, type;
	public boolean isWrong;
	public List<String> confuses;	// 헷갈리는 것들
	
	public Mistake(String reason, String type) {
		this.reason = reason;
		this.type = type;
		isWrong = true;
	}
	
	public void setConfuses(String ...cases)
	{
		System.out.printf("Mistake %s is WRONG!!!!!!!!!!!!!!\n\n", type);
		isWrong = false;
		confuses = new ArrayList<String>();
		for(String c : cases)
			confuses.add(c);
	}
}

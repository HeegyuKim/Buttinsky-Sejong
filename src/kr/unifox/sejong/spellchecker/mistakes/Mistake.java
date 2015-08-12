package kr.unifox.sejong.spellchecker.mistakes;

public class Mistake 
{
	public static final String
	MISTAKE_JOSA_JONGSUNG = "mis_eun_neun",
	MISTAKE_ANH_AN = "mis_an_anh",
	WRONG_CONTINUED = "err_wrong_contd",
	WRONG_NO_ROOT = "err_no_root"
	;
	
	public String reason, type;

	public Mistake(String reason, String type) {
		this.reason = reason;
		this.type = type;
	}
	
	
}

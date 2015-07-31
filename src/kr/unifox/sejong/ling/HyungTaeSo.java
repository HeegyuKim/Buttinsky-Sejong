package kr.unifox.sejong.ling;

public class HyungTaeSo implements Component
{
	public static final String
		TYPE_ROOT = "어근",
		TYPE_STEM = "어간",
		TYPE_TAIL = "어미",
		TYPE_AFFIX = "접사",
		TYPE_SUFFIX = "접미사",
		TYPE_PREFIX = "접두사",
		TYPE_JOSA= "조사"
		;
	
	public String
		source,		// 원형
		eumso,		// 음소들
		type		// 종류
		;
	
	public HyungTaeSo() {
	}

	public HyungTaeSo(String source, String eumso, String type) {
		this.source = source;
		this.eumso = eumso;
		this.type = type;
	}

	@Override
	public String getTypeName() {
		return type;
	}

	@Override
	public String getSource() {
		return source;
	}
	
	
}

package kr.unifox.sejong.ling;

public enum Eon
{
	YongEon("용언"),
	ChaeEon("체언"),
	SooSikEon("수식언"),
	GwanGyeEon("관계언"),
	DokRipEon("독립언")
	
	;

	String hangeulName;
	Eon(String hangeulName)
	{
		this.hangeulName = hangeulName;
	}
}

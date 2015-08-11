package kr.unifox.sejong.ling;

/*
 * Component는 어절의 구성요소를 의미합니다.
 * 형태소일 수도 있고, 어절일 수도 있습니다.
 * 
 * 2015-08-11 김희규
 * 이게 왜 이렇게 해놨냐면 형태소는 분석된 거고 어절은 분석되지 않은거?
 * 그렇게 구분하려고 해놓은 것 같은데 아몰랑 ㅁㄴㅇㄹ
 */
public interface Component
{
	public String getTypeName();
	public String getSource();
}

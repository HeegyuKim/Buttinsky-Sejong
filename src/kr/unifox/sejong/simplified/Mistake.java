package kr.unifox.sejong.simplified;

public interface Mistake {

	String getID();
	String getTitle();
	String getDescription();
	String getReplaced();
	int getStartIndex();
	int getEndIndex();
	boolean isError();
}

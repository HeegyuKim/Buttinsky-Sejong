package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.List;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.spellchecker.mistakes.Mistake;

/**
 * 
 * @author 김희규
 * Candidate 클래스는 한 형태소 (예를 들면 '의')가 될 수 있는 후보집합입니다.
 * '의사의'라는 어절을 '의사'와 '의'로 나누었다면
 * '의'는 여러가지 의미를 가지고 있기 때문에
 * Candidate 객체는 그 후보리스트를 갖게 됩니다.
 * Index 0: 의(조사)
 * Index 1: 의(어미)
 * 
 * 나중에 Evaluator에서는 
 * '의사', '의' 처럼 나뉘어서 쓰입니다.
 * 
 * 젠장 이렇게 개판으로 구현해놓으면 안되는뎋..
 * 
 */
public class Candidate 
{
	public List<Component> compList;
	public String originalText, text;
	public List<Mistake> mistakes;
	
	public Candidate() {
		compList = new ArrayList<>();
	}
	
	public void addMistake(Mistake mistake)
	{
		if(mistakes == null)
			mistakes = new ArrayList<Mistake>();
		
		mistakes.add(mistake);
	}
	
	// 예시 출력할 때 보기에 편하게 하려고...
	// 어차피 형태소는 똑같으니까
	// 형태소(타입, 타입, 타입) 이런식으로 출력됨
	// 예시: 어(감탄사,어근,어미) 
	public String toString()
	{
		if(compList == null || compList.isEmpty())
			return "(empty)";

		StringBuilder sb = new StringBuilder();
		sb.append(compList.get(0).getSource());
		
		int i = 0;
		sb.append("(");
		for(Component c : compList)
		{
			sb.append(c.getTypeName());
			if(++i != compList.size())
				sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}
}

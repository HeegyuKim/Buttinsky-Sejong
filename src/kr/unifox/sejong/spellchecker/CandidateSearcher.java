package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.ling.Word;

/**
 * 
 * @author 김희규
 * CandidateSearcher는 어절을 모든 경우의 수로 쪼개어서
 * 후보 집합을 만들어 주는 역할을 합니다.
 * 
 * 예를 들어 '안녕'이라는 어절이 있다면
 * 'ㅇㅏㄴㄴㅕㅇ' 으로 펼친 뒤(Hangeul.spreadHangeulString())
 * 
 * 막~ 쪼개보는 겁니다.
 * 'ㅇㅏ' + 'ㄴㄴㅕ' + ㅇ
 * 
 * 물론 뒤에서부터요.
 * 그래서 음소의 수가 n개라면
 * n! 만큼 반복하게 됩니다.
 * 
 */
public class CandidateSearcher 
{
	Dictionary dic;
	List<CandidateArray> candiList = new ArrayList<>();
	
	String eumso;
	
	public CandidateSearcher(Dictionary dic, String eumso) 
	{
		this.dic = dic;
		this.eumso = eumso;
	}
	
	public void search()
	{
		// eumso = "ㅇㅏㄴㄴㅕㅇ" 이라고 가정
		// 해당 음소의 단어를 먼저 찾아봅니다.
		// "ㅇㅏㄴㄴㅕㅇ" 을 찾아봄.
		List<Component> one = dic.find(eumso);
		if(one != null)
		{
			Candidate candi = new Candidate();
			candi.compList = one;
			CandidateArray candies = new CandidateArray();
			candies.add(candi);
			candiList.add(candies);
		}
		
		// 음소의 뒤에서부터 짤라가며 Head와 Tail로 나눕니다.
		//
		// if i = 3, 
		// head = ㅇㅏㄴ
		// tail = ㄴㅕㅇ
		//
		// tail에 해당하는 후보 집합을 얻어온 뒤
		// head 부분의 모든 후보 집합을 얻어내서
		// tail 앞에 추가하고 최종적으로 searchSubset() 메서드가
		// candiList 에 후보들을 추가하게 됩니다.
		for(int i = eumso.length() - 2; i >= 0; --i)
		{
			String head = eumso.substring(0, i),
					tail = eumso.substring(i);
			
			List<Component> tails = dic.find(tail);
			
			if(tails == null)
				continue;

			List<Candidate> candies = new ArrayList<>();
			Candidate candi = new Candidate();
			candi.compList = tails;
			candies.add(candi);
			
			searchSubset(head, candies);
		}
	}
	
	private void searchSubset(String eumso, List<Candidate> candies)
	{
		// 
		// search() 함수에서 했던 걸 바로 이어서 하게 됨, 참고바람.
		// 
		for(int i = eumso.length() - 2; i >= 0; --i)
		{
			String head = eumso.substring(0, i),
					tail = eumso.substring(i);

			List<Component> tails = dic.find(tail);
			// 최적화를 위한 필터링...
			if(tails != null && candies.size() > 0)
				tails = filterComponentThatCanBefore(tails, candies.get(0).compList);
			
			if(tails == null)
			{
				// 알 수 없어... 그냥 넣어버림
				if(i == 0)
				{
					String combined;
					try 
					{
						System.out.println(eumso + " 아몰랑~~ ");
						combined = Hangeul.combineHangeulString(eumso);
						Word unknownWord = new Word(
								combined,
								eumso,
								combined,
								Word.PUMSA_UNKNOWN,
								Word.EON_UNKNOWN
								);	
						
						CandidateArray newCandies = new CandidateArray();
						newCandies.addAll(candies);
						Candidate candi = new Candidate();
						candi.compList.add(unknownWord);
						newCandies.add(0, candi);
					} 
					catch (HangeulException e) {
						e.printStackTrace();
					}
					break;
				}
				continue;
			}

			// 찾은 후보 목록을 이전 후보들(candies=tail)의 앞에 추가하고
			// 남은 앞의 부분의 후보집합을 찾아야 한닷
			CandidateArray newCandies = new CandidateArray();
			newCandies.addAll(candies);
			
			Candidate candi = new Candidate();
			candi.compList = tails;
			newCandies.add(0, candi);
			
			// i = 0 이면 tail 은 string 전체이므로
			// 더 이상 찾을 후보집합이 존재하지 않음.
			// 그러므로 추가하고 아니라면 더 찾아야 함.
			if(i > 0)
				searchSubset(head, newCandies);
			else
				candiList.add(newCandies);
		}
	}
		
	private List<Component> filterComponentThatCanBefore(List<Component> heads, List<Component> tails)
	{
		List<Component> filtered = new ArrayList<Component>();
		
		
		for(Component h : heads)
		{
			for(Component t : tails)
			{
				if(dic.isWellContinued(h.getTypeName(), t.getTypeName()))
				{
					filtered.add(h);
					break;
				}
			}	
		}
		if(filtered.size() == 0) return null;
		return filtered;
	}
	public List<CandidateArray> getCandidateArrayList()
	{
		return candiList;
	}
}

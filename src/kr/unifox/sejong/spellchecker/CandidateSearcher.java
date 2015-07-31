package kr.unifox.sejong.spellchecker;

import java.util.ArrayList;
import java.util.List;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.Hangeul;

public class CandidateSearcher 
{
	Dictionary dic;
	List<List<Candidate>> candiList = new ArrayList<>();
	
	String eumso;
	
	public CandidateSearcher(Dictionary dic, String eumso) 
	{
		this.dic = dic;
		this.eumso = eumso;
	}
	
	public void search()
	{
		List<Component> one = dic.find(eumso);
		if(one != null)
		{
			Candidate candi = new Candidate();
			candi.compList = one;
			List<Candidate> candies = new ArrayList<>();
			candies.add(candi);
			candiList.add(candies);
		}
		
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
		for(int i = eumso.length() - 2; i >= 0; --i)
		{
			String head = eumso.substring(0, i),
					tail = eumso.substring(i);

			List<Component> tails = dic.find(tail);
			
			if(tails == null)
				continue;

			List<Candidate> newCandies = new ArrayList<>();
			newCandies.addAll(candies);
			
			Candidate candi = new Candidate();
			candi.compList = tails;
			newCandies.add(0, candi);
			
			if(i > 0)
				searchSubset(head, newCandies);
			else
				candiList.add(newCandies);
		}
	}
	
	public List<List<Candidate>> getCandidateListArray()
	{
		return candiList;
	}
}

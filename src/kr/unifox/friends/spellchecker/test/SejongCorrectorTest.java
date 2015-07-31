package kr.unifox.friends.spellchecker.test;

import java.io.IOException;
import java.util.List;

import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.spellchecker.Candidate;
import kr.unifox.sejong.spellchecker.CandidateSearcher;
import kr.unifox.sejong.spellchecker.Dictionary;
import kr.unifox.sejong.spellchecker.TextFileDictionary;

public class SejongCorrectorTest {

	Dictionary dic;
	
	private void testCandidateSearcher(String eojeol) throws HangeulException
	{
		long start = System.currentTimeMillis();
		
		CandidateSearcher cs = new CandidateSearcher(dic, Hangeul.spreadHangeulString(eojeol));
		System.out.println("Start CandidateSearcher Test: " + eojeol);
		
		cs.search();
		
		int i = 1;
		for(List<Candidate> candies : cs.getCandidateListArray())
		{
			System.out.printf("Candidate %d. \n", i++);
			
			for(Candidate candi : candies)
			{
				System.out.printf("%s ", candi.toString());
			}
			System.out.println();
		}
		
		long elapsed = System.currentTimeMillis() - start;
		System.out.printf("%dms elapsed\n", elapsed);
		System.out.println("-------------------------------");
	}
	
	public SejongCorrectorTest() {
		try {
			dic = new TextFileDictionary("db");
			testCandidateSearcher("그녀의");
			testCandidateSearcher("어머나");
			testCandidateSearcher("그러하다");
			testCandidateSearcher("어디에");
			testCandidateSearcher("맛있어");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {

		new SejongCorrectorTest();
	}

}

package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONException;
import org.json.JSONObject;

import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.simplified.DoubtfulText;
import kr.unifox.sejong.simplified.Mistake;
import kr.unifox.sejong.simplified.SimpleChecker;
import kr.unifox.sejong.simplified.TestResult;
import kr.unifox.sejong.simplified.regex.RegexMistake;
import kr.unifox.sejong.simplified.regex.RegexTestItem;
import kr.unifox.sejong.simplified.regex.RegexTester;

public class SimpleTestMain {

	public static void main(String[] args) throws FileNotFoundException, IOException, JSONException {
		
		// assets에 넣어서 InputStream 받아와서 로딩할 것
		SimpleChecker checker = new SimpleChecker(
				new FileInputStream("tsv/data.json")
				);
		
		String text = "담배를 피다. 담배를 피우다. 않되! 구렛나루 뇌졸증 덤뵤~ 나도 암~";
		TestResult result = checker.test(text);
		/*
		 * DoubtfulText 안에 Mistakes가 있음
		 * Test가 여러 종류(Regex, 형태소분석)일 경우를 대비했지만
		 * 실제로는 정규표현식검사만 하기 때문에 DoubtfulText는 최대 1개밖에 없다.
		 * 
		 * 즉 DoubtfulTextList 안에 A, B가 들어있다면
		 * A는 첫번째 검사를 통해 오류가 수정된 내역들
		 * B는 두번째 검사를 통해 오류가 수정된 내역들을 의미
		 * 
		 * DoubtfulText에서 original은 원본, replaced는 모든 실수가 수정된 텍스트를 의미함.
		 * 한 문장에 여러가지 실수(Mistake)가 있을 수 있음.
		 * 
		 */
		// 
		
		// 실수 존재함
		if(result.doubtfulTextList.size() > 0)
		{
			for(DoubtfulText dft : result.doubtfulTextList)
			{
				String original = dft.getOriginalText(),
						replaced = dft.replacedText;
				System.out.printf("원본: %s\n수정됨: %s\n", original, replaced);
				System.out.printf("실수: %d개.\n", dft.mistakeList.size());
				
				int i = 0;
				for(Mistake mistake : dft.getMistakeList())
				{
					++i;
					System.out.println("#" + i);
					if(mistake.isError())
						System.out.println("종류: 오류");
					else
						System.out.println("종류: 혼돈");
					System.out.println("ID: " + mistake.getID());
					System.out.println("시작 인덱스: " + mistake.getStartIndex());
					System.out.println("끝 인덱스: " + mistake.getEndIndex());
					System.out.println("제목: " + mistake.getTitle());
					System.out.println("설명: " + mistake.getDescription().replaceAll("\\n", "\n"));
					if(mistake.isError())
					{
						System.out.println("대체(수정) 내용: " + mistake.getReplaced());
						
						// 원본 텍스트에서 이 실수만 수정한 텍스트를 얻는 방법임.
						if(mistake instanceof RegexMistake)
						{
							RegexMistake regexMistake = (RegexMistake)mistake;
							RegexTestItem testItem = regexMistake.testItem;
							
							String fixed = null;
							if(testItem.spread)
							{
								try {
									String spread = Hangeul.spreadHangeulString(original);
									fixed = spread.replaceAll(testItem.regex, testItem.replaced);
									System.out.println(fixed);
									fixed = Hangeul.combineHangeulEumso(fixed);
								} 
								catch (HangeulException e) {
									e.printStackTrace();
								}
							}
							else
								fixed = original.replaceAll(testItem.regex, testItem.replaced);
							System.out.println("이 실수만 수정하면: " + fixed);
						}
					}
					
					System.out.println();
				}
			}
			
		}
		else
			System.out.println("실수 없음~");
	}

}

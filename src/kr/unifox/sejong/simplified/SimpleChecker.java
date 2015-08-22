package kr.unifox.sejong.simplified;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONException;

import kr.unifox.sejong.simplified.regex.RegexTester;

public class SimpleChecker {

	ArrayList<Tester> testerList = new ArrayList<Tester>();
	
	public SimpleChecker(InputStream regexFile) throws IOException, JSONException 
	{
		testerList.add(RegexTester.create(regexFile));
	}
	
	public TestResult test(String text)
	{
		TestResult result = new TestResult();
		String modifyingText = text;
		
		for(Tester tester : testerList)
		{
			DoubtfulText doubtful = tester.test(modifyingText);
			
			if(doubtful != null)
			{
				modifyingText = doubtful.replacedText;
				result.doubtfulTextList.add(doubtful);
				
				for(Mistake mistake : doubtful.mistakeList)
				{
					if(mistake.isError())
						++ result.errorCount;
					else
						++ result.confusableCount;
				}
			}
		}
		
		return result;
	}
}

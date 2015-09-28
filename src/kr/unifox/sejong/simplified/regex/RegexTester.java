package kr.unifox.sejong.simplified.regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.simplified.DoubtfulText;
import kr.unifox.sejong.simplified.Tester;

public class RegexTester implements Tester 
{
	public static RegexTester create(InputStream input) throws IOException, JSONException
	{
		BufferedReader reader = new  BufferedReader(
				new InputStreamReader(input)
				);
		
		StringBuilder builder = new StringBuilder();
		String line = null;
		
		while((line = reader.readLine()) != null)
			builder.append(line);
		
		String source = builder.toString();
		//source = source.replaceAll("(^)?[^\\S\\n]*/(?:\\*(.*?)\\*/[^\\S\\n]*|/[^\\n]*)($)?", "");
		
		//System.out.println("주석이 제거된 JSON: \n" + source);
		
		return new RegexTester(new JSONObject(source));
	}
	
	
	
	ArrayList<RegexTestItem> testItemList = new ArrayList<RegexTestItem>();
	JSONObject descriptions, titles;
	
	public RegexTester(JSONObject json) throws JSONException 
	{
		JSONArray confusables = json.getJSONArray("confusable"),
				errors = json.getJSONArray("errors");
		
		loadTestItemsFromArray(confusables);
		loadTestItemsFromArray(errors);
		
		descriptions = json.getJSONObject("descriptions");
		titles = json.getJSONObject("titles");
	}
	
	
	
	private void loadTestItemsFromArray(JSONArray array) throws JSONException
	{

		for(int i = 0; i < array.length(); ++i)
		{
			JSONObject itemJson = array.getJSONObject(i);
			RegexTestItem item = new RegexTestItem(
					itemJson.getString("id"),
					itemJson.optString("replaced", null),
					itemJson.getString("regex"),
					itemJson.optBoolean("spread", false)
					);
			testItemList.add(item);
		}		
	}
	
	@Override
	public DoubtfulText test(String text)
	{
		String modifyingText = text;
		
		DoubtfulText doubtful = new DoubtfulText();

		
		for(RegexTestItem testItem : testItemList)
		{
			try {

				if(testItem.spread)
				{
					modifyingText = Hangeul.spreadHangeulString(modifyingText);
					System.out.printf("%s 검사 중 펼쳤다!: %s\n", testItem.id, modifyingText);
					
				}
				System.out.printf("Regex: %s, 대체: %s\n", testItem.regex, testItem.replaced);
					
				
				Matcher matcher = testItem.pattern.matcher(modifyingText);

				while(matcher.find())
				{
					System.out.println("엥? 발견!");
					RegexMistake mistake = new RegexMistake();
					
					mistake.id = testItem.id;
					mistake.startIndex = matcher.start();
					mistake.endIndex = matcher.end();
					mistake.description = descriptions.optString(mistake.id, "내용이 없습니다.");
					mistake.replaced = testItem.replaced;
					
					doubtful.mistakeList.add(mistake);
					
					if(testItem.isError())
					{
						String replaced = testItem.replaced;
						char lastChar = replaced.charAt(replaced.length() - 1);
						Hangeul hangeul = new Hangeul(lastChar);

						if(testItem.spread)
						{
							replaced = Hangeul.combineHangeulEumso(replaced);
							mistake.replaced = replaced;
						}
						
						if(hangeul.getJamoCount() == 3)
							mistake.title = String.format("\"%s\"으로 바꾸기", replaced);
						else
							mistake.title = String.format("\"%s\"로 바꾸기", replaced);
						
						modifyingText = String.format("%s%s%s", 
								modifyingText.substring(0, mistake.startIndex),
								testItem.replaced,
								modifyingText.substring(mistake.endIndex, modifyingText.length())
								);
					}
					else
						mistake.title = titles.optString(mistake.id, mistake.id);
					
				}
				if(testItem.spread)
				{
					modifyingText = Hangeul.combineHangeulEumso(modifyingText);
					System.out.printf("다시 결합했다!: %s\n\n", modifyingText);
				}
			}
			catch(HangeulException e)
			{
				e.printStackTrace();
			}
		}
		
		doubtful.originalText = text;
		doubtful.replacedText = modifyingText;
		
		return doubtful;
	}
}

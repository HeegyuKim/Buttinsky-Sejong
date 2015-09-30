package kr.unifox.sejong.simplified.regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
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
		JSONArray confusables = json.getJSONArray("confusables"),
				errors = json.getJSONArray("errors");
		
		loadTestItemsFromArray(errors);
		loadTestItemsFromArray(confusables);
		
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
				}
					
				
				Matcher matcher = testItem.pattern.matcher(modifyingText);
				StringBuffer sb = new StringBuffer();
				
				while(matcher.find())
				{
					RegexMistake mistake = new RegexMistake();
					
					mistake.testItem = testItem;
					mistake.startIndex = matcher.start();
					mistake.endIndex = matcher.end();
					mistake.description = descriptions.getString(testItem.id);
					mistake.title = titles.getString(testItem.id);
					
					if(testItem.replaced != null)
						matcher.appendReplacement(
								sb, 
								testItem.replaced
								);
					else
						matcher.appendReplacement(sb, "");
					
					
					doubtful.mistakeList.add(mistake);
				}
				matcher.appendTail(sb);
				
				if(testItem.spread)
					modifyingText = Hangeul.combineHangeulEumso(sb.toString());
				else
					modifyingText = sb.toString();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		doubtful.originalText = text;
		doubtful.replacedText = modifyingText;
		
		return doubtful;
	}
}

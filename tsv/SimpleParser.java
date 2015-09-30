package kr.unifox.sejong.dicmake;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.unifox.sejong.ling.Hangeul;
import kr.unifox.sejong.ling.HangeulException;

public class SimpleParser {

	JSONObject data = new JSONObject(),
			titles = new JSONObject(),
			descriptions = new JSONObject();
	JSONArray errors = new JSONArray(),
			confusables = new JSONArray();
	
	int id = 0;

	public SimpleParser() throws Exception {
		
		id = 0;

		readTSV("tsv/맞춤법검사 - 오류.tsv").forEach(args -> {
			JSONObject error = new JSONObject();
			try {
				++id;
				String strId = String.format("error_%06d", id);
				error.put("id", strId);
				error.put("regex", args[0]);
				error.put("replaced", args[1]);
				
				String title = String.format("%s -> %s", args[0], args[1]);;
				String description;
				// description 있음
				if(args.length > 2)
					description = args[2].replaceAll("\\n", "\n");
				else
					description = createDescription(args[0], args[1]);
				
				errors.put(error);
				titles.put(strId, title);
				descriptions.put(strId, description);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		readTSV("tsv/맞춤법검사 - 혼돈.tsv").forEach(args -> {
			JSONObject confusable = new JSONObject();
			try {
				++id;
				String strId = String.format("conf_%06d", id);
				confusable.put("id", strId);
				confusable.put("regex", args[0]);
				
				String title = args[1];
				String description;
				// description 있음
				if(args.length > 2)
					description = args[2].replaceAll("\\n", "\n");
				else
					description = createDescription(args[0], args[1]);
				
				confusables.put(confusable);
				titles.put(strId, title);
				descriptions.put(strId, description);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		readTSV("tsv/맞춤법검사 - 특수형.tsv").forEach(args -> {
			try {
				++id;
				String type = args[0];
				if(type.equals("오류"))
				{
					JSONObject error = new JSONObject();
					String strId = String.format("error_%06d", id);
					error.put("id", strId);
					error.put("regex", args[1]);
					error.put("replaced", args[2]);
					error.put("spread", true);
					
					String title = String.format("%s -> %s", args[1], args[2]);
					String description = args[3].replaceAll("\\n", "\n");
					
					errors.put(error);
					titles.put(strId, title);
					descriptions.put(strId, description);
					
				}
				else
				{
					JSONObject confusable = new JSONObject();
					String strId = String.format("conf_%06d", id);
					confusable.put("id", strId);
					confusable.put("regex", args[1]);
					confusable.put("spread", Boolean.valueOf(args[4]));
					
					String title = args[2];
					String description = args[3].replaceAll("\\n", "\n");

					confusables.put(confusable);
					titles.put(strId, title);
					descriptions.put(strId, description);					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		data.put("errors", errors);
		data.put("confusables", confusables);
		data.put("descriptions", descriptions);
		data.put("titles", titles);
		
		try(PrintWriter pw = new PrintWriter("tsv/data.json"))
		{
			pw.print(data.toString(4));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String createDescription(String before, String after) throws HangeulException
	{
		String josa1 = lastCharHasJongsung(before)? "을":"를",
				josa2 = lastCharHasJongsung(after)? "으로":"로";
		return String.format("%s%s %s%s 바꿔야 합니다.", before, josa1, after, josa2);
	}

	private boolean lastCharHasJongsung(String word) throws HangeulException
	{
		int lastChar = word.codePointAt(word.length() - 1);
		if(!Hangeul.isHangeul(lastChar))
			return false;
		
		Hangeul h = new Hangeul(word.charAt(word.length() - 1));
		return h.getJongsung() != ' ';
	}
	
	private List<String[]> readTSV(String filename) throws IOException {
		List<String[]> dataList = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(filename));
		
		for(String line : lines)
			dataList.add(line.split("\t"));
		
		return dataList;
	}
	
	public static void main(String[] args) {
		try {
			new SimpleParser();   
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

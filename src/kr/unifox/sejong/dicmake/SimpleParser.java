package kr.unifox.sejong.dicmake;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class SimpleParser {

	public SimpleParser() throws Exception {
		
		readTSV("tsv/맞춤법검사 - 오류.tsv").forEach(args -> {
			
		});
		
	}
	
	
	
	private List<String[]> readTSV(String filename) throws IOException {
		List<String[]> dataList = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(filename), Charset.forName("MS949"));
		
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

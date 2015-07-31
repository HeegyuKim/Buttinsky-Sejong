package kr.unifox.sejong.spellchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import kr.unifox.sejong.ling.Component;
import kr.unifox.sejong.ling.HyungTaeSo;
import kr.unifox.sejong.ling.Word;

public class TextFileDictionary implements Dictionary {


	Map<String, List<Word>> wordTable = new TreeMap<>();
	Map<String, List<HyungTaeSo>> htsTable = new TreeMap<>();
	
	
	public TextFileDictionary(String dir)
		throws IOException
	{
		List<String> lines = Files.readAllLines(Paths.get(dir, "dic.txt"));
		
		for(String line : lines)
		{
			List<String> tokens = separateLine(line);
			Word word = createWord(tokens);
			
			List<Word> words = wordTable.get(word.eumso);
			if(words == null)
			{
				words = new ArrayList<>();
				words.add(word);
				wordTable.put(word.eumso, words);
			}
			else
				words.add(word);
		}
		

		lines = Files.readAllLines(Paths.get(dir, "hts.txt"));
		
		for(String line : lines)
		{
			List<String> tokens = separateLine(line);
			HyungTaeSo hts = createHTS(tokens);
			
			List<HyungTaeSo> htss = htsTable.get(hts.eumso);
			if(htss == null)
			{
				htss = new ArrayList<>();
				htss.add(hts);
				htsTable.put(hts.eumso, htss);
			}
			else
				htss.add(hts);
		}
		
		
	}
	
	private List<String> separateLine(String line)
	{
		List<String> list = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		
		while(tokenizer.hasMoreTokens())
			list.add(tokenizer.nextToken());
		
		return list;
	}

	private Word createWord(List<String> items)
	{
		Word word = new Word();
		word.source = items.get(0);
		word.root = items.get(1);
		word.eumso = items.get(2);
		word.pumsa = items.get(3);
		word.eon = items.get(4);
		return word;
	}
	
	private HyungTaeSo createHTS(List<String> items)
	{
		HyungTaeSo hts = new HyungTaeSo();
		hts.source = items.get(0);
		hts.eumso = items.get(1);
		hts.type = items.get(2);
		return hts;
	}

	public List<Component> find(String eumsos) {
		List<Component> comps = new ArrayList<>();
		
		List<Word> words = findWord(eumsos);
		if(words != null)
			comps.addAll(words);
		
		List<HyungTaeSo> htss = findHyungTaeSo(eumsos);
		if(htss != null)
			comps.addAll(htss);
		
		if(comps.isEmpty())
			return null;
		else
			return comps;
	}
	
	/**
	 * 음소로 단어찾기!!ㅇ ㅇ맘니남;ㄹ
	 */
	@Override
	public List<Word> findWord(String eumsos) {
		return wordTable.get(eumsos);
	}

	//
	//
	// 품사로 단어 필터링해서 찾기~
	@Override
	public List<Word> findWordByPumsa(String eumsos, Set<String> allowedPumsaSet) {
		List<Word> words = wordTable.get(eumsos);
		if(words == null) return null;
		
		Iterator<Word> it = words.iterator();
		while(it.hasNext())
		{
			Word word = it.next();
			if(!allowedPumsaSet.contains(word.pumsa))
				it.remove();
		}
		
		return words;
	}

	/*
	 * (non-Javadoc)
	 * @see kr.unifox.sejong.spellchecker.Dictionary#findWordByEon(java.lang.String, java.util.Set)
	 * 시발코딩하기싫다
	 * 
	 */
	@Override
	public List<Word> findWordByEon(String eumsos, Set<String> allowedEonSet) 
	{
		List<Word> words = wordTable.get(eumsos);
		if(words == null) return null;
		
		Iterator<Word> it = words.iterator();
		while(it.hasNext())
		{
			Word word = it.next();
			if(!allowedEonSet.contains(word.eon))
				it.remove();
		}
		
		return words;
	}

	@Override
	public List<HyungTaeSo> findHyungTaeSo(String eumsos) {
		return htsTable.get(eumsos);
	}

	@Override
	public List<HyungTaeSo> findHyungTaeSoByType(String eumsos, Set<String> allowedTypeSet) 
	{
		if(allowedTypeSet.contains(HyungTaeSo.TYPE_AFFIX))
		{
			allowedTypeSet.add(HyungTaeSo.TYPE_PREFIX);
			allowedTypeSet.add(HyungTaeSo.TYPE_SUFFIX);
		}
		
		List<HyungTaeSo> htsList =  htsTable.get(eumsos);
		Iterator<HyungTaeSo> it = htsList.iterator();
		while(it.hasNext())
		{
			HyungTaeSo hts = it.next();
			if(!allowedTypeSet.contains(hts.type))
				it.remove();
		}
		
		return htsList;
	}

}
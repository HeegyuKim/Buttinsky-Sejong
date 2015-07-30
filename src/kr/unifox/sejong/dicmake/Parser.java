package kr.unifox.sejong.dicmake;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class Parser
{
	/**
	 * 
	 * @author KimHeekue
	 *
	 * @param <T> 처리할 데이터의 종류
	 */
	public interface Handler<T>
	{
		void handle(T data) throws Exception;	
	}
	
	Handler<Exception> exceptionHandler;
	XPathFactory xpathFactory;
	

	public Parser(Handler<Exception> exceptionHandler)
	{
		this.exceptionHandler = exceptionHandler;
		xpathFactory = XPathFactory.newInstance();
	}
	
	public XPath newXPath()
	{
		return xpathFactory.newXPath();
	}

	
	/**
	 * .txt 파일을 파싱합니다.
	 * @param filename 파싱할 파일
	 * @param handler 파싱한 데이터를 처리할 핸들러
	 */
	public void parseTextFile(String filename, Handler<String> handler)
		throws Exception
	{
		List<String> lines = Files.readAllLines(
				Paths.get(filename), 
				Charset.forName("MS949")
				);
		
		for(String line : lines)
		{
			if(line.trim().isEmpty()) 
				continue;
			try {
				handler.handle(line);
			}
			catch(Exception e) {
				if(exceptionHandler != null)
					exceptionHandler.handle(e);
			}
		}
	}
	
	/**
	 * 특정 디렉토리 안의 모든 xml파일을 파싱합니다
	 * @param dirName xml파일들이 저장된 디렉토리
	 * @param handler 파싱한 데이터를 처리할 핸들러
	 * @throws ParserConfigurationException 
	 */
	public void parseXmlDir(String dirName, Handler<Document> handler) 
			throws Exception
	{
		// 디렉토리에서 xml파일만 걸러낸다
		File dir = new File(dirName);
		File[] xmlFiles = dir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(".xml");
			}
		});
		
		
		//
		// 파싱할 빌더 생성
		DocumentBuilder builder = 
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		//
		// for loop
		for(File xmlFile : xmlFiles)
		{
			// parse xml file
			try {
				Document doc = builder.parse(xmlFile);
				handler.handle(doc);
			}
			catch(Exception e)
			{
				if(exceptionHandler != null)
					exceptionHandler.handle(e);
			}
		}
	}
}

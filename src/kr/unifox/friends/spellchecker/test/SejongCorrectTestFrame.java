package kr.unifox.friends.spellchecker.test;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import kr.unifox.sejong.ling.HangeulException;
import kr.unifox.sejong.spellchecker.Dictionary;
import kr.unifox.sejong.spellchecker.SejongCorrector;
import kr.unifox.sejong.spellchecker.TextFileDictionary;
import kr.unifox.sejong.spellchecker.SejongCorrector.Tokenized;


public class SejongCorrectTestFrame extends JFrame {

	SejongCorrector corrector;
	Dictionary dic;
	
	JTextField input;
	JTextArea out;
	

	public SejongCorrectTestFrame() throws URISyntaxException, IOException
	{
		super("Sejong SpellChecker Test");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));
	
		dic = new TextFileDictionary("db");
		corrector = new SejongCorrector(dic);
		
		initComponents();
		
		pack();
		setVisible(true);
	}
	
	private void initComponents()
	{
		input = new JTextField();
		out = new JTextArea();
		
		out.setEditable(false);
		
		setLayout(new GridLayout(2, 1));
		add(input);
		add(out);
		
		
		input.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					String text = input.getText();

					computeUsedMemory();
					
					ArrayList<Tokenized> tokens = new ArrayList<>();
					String modified = corrector.modify(text, tokens);
					computeUsedMemory();
					System.out.println("\n---------------------------------------------");
					
					StringBuilder result = new StringBuilder();
					result.append(modified).append('\n');
					
					boolean hasError = false;
					
					for(Tokenized token : tokens)
					{
						if(!token.hasError)
							continue;
						
						hasError = true;
						result.append(String.format(
								"오류: %s(%d~%d)",
								text.substring(token.start, token.end),
								token.start, token.end
								));
						if(token.mistake != null)
							result.append(String.format(
									"다음으로 수정됨: %s. %s",
									token.replaced,
									token.mistake.reason));
						result.append('\n');
					}
					
					if(!hasError)
						result.insert(0, "오류가 없네요! 짝짝짞~\n");
					out.setText(result.toString());
				}
				catch (HangeulException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
  	public static void computeUsedMemory()
  	{
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    //runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
  	}
	private static final long MEGABYTE = 1024L * 1024L;
	
	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}
  	
	
	public static void main(String[] args)
	{
		try
		{
			new SejongCorrectTestFrame();
		}
		catch (URISyntaxException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

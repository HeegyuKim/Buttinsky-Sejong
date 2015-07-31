package kr.unifox.friends.spellchecker.test;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kr.unifox.friends.spellchecker.Candidate;
import kr.unifox.friends.spellchecker.CheckResult;
import kr.unifox.friends.spellchecker.Dictionary;
import kr.unifox.friends.spellchecker.SpellChecker;
import kr.unifox.friends.spellchecker.TextDataDictionary;
import kr.unifox.friends.spellchecker.WordComponent;
import kr.unifox.sejong.ling.HangeulException;

public class SpellCheckerTestFrameMain
extends JFrame
{
	Dictionary dic;
	SpellChecker checker;
	JTextField input;
	JTextArea out;
	

	public SpellCheckerTestFrameMain() throws URISyntaxException, IOException
	{
		super("SpellChecker Test");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(600, 400));
	
		dic = new TextDataDictionary("db");
		checker = new SpellChecker(dic);
		
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
				CheckResult result;
				try
				{
					String text = input.getText();
					result = checker.checkSentence(text);
					StringBuilder builder = new StringBuilder();
					
					int i = 0;
					for(WordComponent comp : result.components)
					{
						++i;
						if(comp.type == kr.unifox.friends.spellchecker.WordComponent.Type.WhiteSpace)
							builder.append("  ");
						else
						{
							builder.append(String.format("%s(%s)", comp.origin, comp.type.toHangeul()));
							if(i != result.components.size() - 1)
								builder.append("/");
						}
						
					}

					out.setText(
							result.originSentence + "\n" + 
							builder.toString() + "\n" +
							result.modifiedSentence
							);
					input.setText("");
					
					List<WordComponent> norm = dic.normalize(result.components);
					for(WordComponent comp : norm)
					{
						++i;
						if(comp.type == kr.unifox.friends.spellchecker.WordComponent.Type.WhiteSpace)
							System.out.print("  ");
						else
						{
							System.out.print(String.format("%s(%s)", comp.origin, comp.type.toHangeul()));
							if(i != result.components.size() - 1)
								System.out.print("/");
						}
						
					}
					
					
					/*
					System.out.println("\n\nSpell checking with service...\n");
					List<List<Candidate>> words = checker.checkSentenceWithService(text);
					i = 0;
					for(List<Candidate> candies : words)
					{
						++i;
						System.out.printf("Candidate Word at %d \n", i);
						for(Candidate candi : candies)
						{
							int j = 1;
							System.out.printf("%d-%d) ", i, j);
							for(WordComponent comp : candi.comps)
							{
								if(comp.type == kr.unifox.friends.spellchecker.WordComponent.Type.WhiteSpace)
									System.out.print(" => ");
								else
								{
									System.out.printf("%s(%s)", comp.origin, comp.type.toHangeul());
									if(j != result.components.size() - 1)
										System.out.print("/");
								}

								++j;
							}
							System.out.println();
						}
					}
					*/

				}
				catch (HangeulException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	

	
	public static void main(String[] args)
	{
		try
		{
			new SpellCheckerTestFrameMain();
		}
		catch (URISyntaxException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package kr.unifox.friends.spellchecker.test;

import java.awt.Dimension;

import javax.swing.JFrame;

public class TestFrame extends JFrame
{
	public TestFrame(String title, int width, int height)
	{
		super(title);
		
		
		setLocationByPlatform(true);
		setPreferredSize(new Dimension(width, height));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
}

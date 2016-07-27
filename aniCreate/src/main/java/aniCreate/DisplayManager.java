package aniCreate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DisplayManager {
	DisplayManager()
	{
		
	}
	
	boolean init()
	{
		frame = new JFrame("anicreate");
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		frame.setUndecorated(false);
		frame.setIgnoreRepaint(true);
		frame.setResizable(true);
		frame.setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int)screenSize.getWidth(), (int)((29.0 * screenSize.getHeight())/30.0));
		frame.createBufferStrategy(2);
		return true;
	}
	
	void setBackgroundColor(float r, float g, float b, float a)
	{
		frame.setBackground(new Color(r,g,b,a));
	}
	
	void update()
	{
		BufferStrategy buffStrat = frame.getBufferStrategy();
		if (!buffStrat.contentsLost())
		{
			buffStrat.show();
		}
		graphics = (Graphics2D)buffStrat.getDrawGraphics();
	}
	
	public Graphics2D getGraphics()
	{
		return graphics;
	}
	private Graphics2D graphics;
	private JFrame frame;
}
package aniCreate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DisplayManager {
	public static final double DISPLAY_DEFAULT_W = 1920d;
	public static final double DISPLAY_DEFAULT_H = 1080d;
	protected static final int SCREEN_Y_OFF_DEFAULT = 0;
	protected static final int SCREEN_X_OFF_DEFAULT = 0;
	
	public DisplayManager()
	{
		
	}
	
	boolean init(InputManager im)
	{
		frame = new JFrame("anicreate");
		dca = new DisplayComponentAdapter(this);
		frame.addComponentListener(dca);
		frame.addMouseListener(im);
		frame.addMouseMotionListener(im);
		frame.addKeyListener(im);
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
		updateScreenSize();
		return true;
	}
	
	void setBackgroundColor(float r, float g, float b, float a)
	{
		frame.setBackground(new Color(r, g, b, a));
	}
	
	void update()
	{
		if (buffStrat == null || !buffStrat.contentsLost())
		{
			if (buffStrat != null)
			{
				buffStrat.show();
			}
			buffStrat = frame.getBufferStrategy();
			graphics = (Graphics2D)buffStrat.getDrawGraphics();
			graphics.clearRect(screenXOff, screenYOff, (int)(DISPLAY_DEFAULT_W * screenWScale), (int)(DISPLAY_DEFAULT_H * screenHScale));
		}
	}
	
	
	void updateScreenSize()
	{
		Dimension actualSize = frame.getContentPane().getSize();
		screenW = actualSize.getWidth();
		screenH = actualSize.getHeight();
		screenWScale = screenW/DISPLAY_DEFAULT_W;
		screenHScale = screenH/DISPLAY_DEFAULT_H;
		screenYOff = frame.getInsets().top;
		screenXOff = frame.getInsets().left;
	}
	
	public double screenW = DISPLAY_DEFAULT_W;
	public double screenH = DISPLAY_DEFAULT_H;
	public double screenWScale = 1d;
	public double screenHScale = 1d;
	public int screenYOff = SCREEN_Y_OFF_DEFAULT;
	public int screenXOff = SCREEN_X_OFF_DEFAULT;
	public Graphics2D graphics;
	public JFrame frame;
	private DisplayComponentAdapter dca;
	private BufferStrategy buffStrat;
}
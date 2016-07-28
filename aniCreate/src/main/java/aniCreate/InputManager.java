package aniCreate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class InputManager implements MouseListener, KeyListener, MouseMotionListener{
	
	InputManager(DisplayManager dm)
	{
		this.dm = dm;
		typedKeys = new ArrayList<Character>();
	}
	
	public void update()
	{
		typedKeys.clear();
		mouseClicked = false;
		mouseClickX = -1000;
		mouseClickY = -1000;
	}
	
	public boolean keyPressed(char c)
	{
		if (typedKeys.contains(Character.toLowerCase(c)))
		{
			return true;
		}
		return typedKeys.contains(Character.toUpperCase(c));
	}
	
	public boolean charPressed(char c)
	{
		return typedKeys.contains(c);
	}
	
	public void keyPressed(KeyEvent event) {
		
		
	}

	public void keyReleased(KeyEvent event) {
		
	}

	public void keyTyped(KeyEvent event) {
		typedKeys.add(event.getKeyChar());
	}

	public void mouseClicked(MouseEvent event) {
		mouseClicked = true;
		mouseClickX = scaleMouseX(event.getX());
		mouseClickY = scaleMouseY(event.getY());
	}

	public void mouseEntered(MouseEvent event) {
		
	}

	public void mouseExited(MouseEvent event) {
		
		
	}

	public void mousePressed(MouseEvent event) {
		mouseDown = true;
	}
	
	public void mouseReleased(MouseEvent event) {
		mouseDown = false;
	}
	private ArrayList<Character> typedKeys;
	public boolean mouseDown = false;
	public boolean mouseClicked = false;
	public double mouseClickX = 0;
	public double mouseClickY = 0;
	public double mouseX = 0;
	public double mouseY = 0;
	public void mouseDragged(MouseEvent event) {
		mouseX = scaleMouseX(event.getX());
		mouseY = scaleMouseY(event.getY());
	}

	public void mouseMoved(MouseEvent event) {
		mouseX = scaleMouseX(event.getX());
		mouseY = scaleMouseY(event.getY());
	}
	
	private double scaleMouseX(int mouseX)
	{
		return ((double)mouseX)/dm.screenWScale - dm.screenXOff;
	}
	
	private double scaleMouseY(int mouseY)
	{
		return ((double)mouseY)/dm.screenHScale - dm.screenYOff;
	}
	
	private DisplayManager dm;
}

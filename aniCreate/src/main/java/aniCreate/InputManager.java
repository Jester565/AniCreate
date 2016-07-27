package aniCreate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class InputManager implements MouseListener, KeyListener, MouseMotionListener{
	
	InputManager()
	{
		typedKeys = new ArrayList<Character>();
	}
	
	public void update()
	{
		typedKeys.clear();
		mouseClicked = false;	
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
		
	}

	public void mouseEntered(MouseEvent event) {
		
		
	}

	public void mouseExited(MouseEvent event) {
		
		
	}

	public void mousePressed(MouseEvent event) {
		mouseDown = true;
		mouseClickX = event.getX();
		mouseClickY = event.getY();
	}
	
	public void mouseReleased(MouseEvent event) {
		mouseDown = false;
	}
	private ArrayList<Character> typedKeys;
	public boolean mouseDown = false;
	public boolean mouseClicked = false;
	public int mouseClickX = 0;
	public int mouseClickY = 0;
	public int mouseX = 0;
	public int mouseY = 0;
	public void mouseDragged(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
	}

	public void mouseMoved(MouseEvent event) {
		mouseX = event.getX();
		mouseY = event.getY();
	}
}

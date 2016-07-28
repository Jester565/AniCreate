package aniCreate;

import java.awt.Color;

public class ShapeRenderer {
	public ShapeRenderer(DisplayManager dm)
	{
		this.dm = dm;
	}
	
	public void drawRect(double x, double y, double w, double h, float r, float g, float b, float a)
	{
		dm.graphics.setColor(new Color(r,g,b,a));
		dm.graphics.fillRect((int)(x * dm.screenWScale + dm.screenXOff), (int)(y * dm.screenHScale + dm.screenYOff), (int)(w * dm.screenWScale), (int)(h * dm.screenHScale));
	}
	
	private DisplayManager dm;
}

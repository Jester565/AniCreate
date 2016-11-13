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
	

	public void drawRect(double x, double y, double w, double h, Color color)
	{
		dm.graphics.setColor(color);
		dm.graphics.fillRect((int)(x * dm.screenWScale + dm.screenXOff), (int)(y * dm.screenHScale + dm.screenYOff), (int)(w * dm.screenWScale), (int)(h * dm.screenHScale));
	}
	
	public void drawCircle(int x, int y, int radius, float r, float g, float b, float a)
	{
		dm.graphics.setColor(new Color(r,g,b,a));
		dm.graphics.fillOval((int)((x-radius) * dm.screenWScale + dm.screenXOff), (int)((y+radius) * dm.screenHScale + dm.screenYOff), (int)((radius*2) * dm.screenWScale), (int)((radius*2) * dm.screenHScale));
	}
	
	public void drawCircleOutline(int x, int y, int radius, float r, float g, float b, float a)
	{
		dm.graphics.setColor(new Color(r,g,b,a));
		dm.graphics.drawOval((int)((x-radius) * dm.screenWScale + dm.screenXOff), (int)((y+radius) * dm.screenHScale + dm.screenYOff), (int)((radius*2) * dm.screenWScale), (int)((radius*2) * dm.screenHScale));
	}
	
	public void drawCircleFillOutlined(int x, int y, int radius, float fillR, float fillG, float fillB, float fillA, float outR, float outG, float outB, float outA)
	{
		drawCircle(x, y, radius, fillR, fillG, fillB, fillA);
		drawCircleOutline(x, y, radius, outR, outG,outB, outA);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, float r, float g, float b, float a)
	{
		dm.graphics.setColor(new Color(r,g,b,a));
		dm.graphics.drawLine((int)(x1 * dm.screenWScale + dm.screenXOff), (int)(y1 * dm.screenHScale + dm.screenYOff), (int)(x2 * dm.screenWScale + dm.screenXOff), (int)(y1 * dm.screenHScale + dm.screenYOff));
	}
	
	private DisplayManager dm;
}

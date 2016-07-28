package aniCreate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class TextRenderer {
	public TextRenderer(DisplayManager dm)
	{
		this.dm = dm;
		fonts = new HashMap<String, Font>();
	}
	
	private int getFontWidth(String s)
	{
		FontMetrics fm = dm.graphics.getFontMetrics();
		return fm.stringWidth(s);
	}

	
	public boolean addFont(String fontPath)
	{
		InputStream is = getClass().getClassLoader().getResourceAsStream(fontPath);
		if (is == null)
		{
			System.err.println("Could not find font " + fontPath);
			return false;
		}
		BufferedInputStream bis = new BufferedInputStream(is);
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, bis);
			int lastSlashI = fontPath.lastIndexOf('/');
			int lastPeriodI = fontPath.lastIndexOf('.');
			if (lastSlashI == -1)
			{
				lastSlashI = 0;
			}
			if (lastPeriodI == -1)
			{
				lastPeriodI = fontPath.length();
			}
			String fontName = fontPath.substring(lastSlashI, lastPeriodI + 1);
			fonts.put(fontName, f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void drawText(String msg, double x, double y, float fontSize, float r, float g, float b, float a)
	{
		if (fonts.size() == 0)
		{
			System.err.println("No fonts can be used for drawText");
			return;
		}
		fontSize *= (float)(dm.screenHScale * dm.screenWScale);
		Font newFont = fonts.values().iterator().next();
		Font currentFont = dm.graphics.getFont();
		if (newFont != currentFont || newFont.getSize() != (int)fontSize)
		{
			if (newFont.getSize() != (int)fontSize)
			{
				newFont = newFont.deriveFont(fontSize);
			}
			dm.graphics.setFont(newFont);
		}
		dm.graphics.setColor(new Color(r, g, b, a));
		dm.graphics.drawString(msg, (int)(x * dm.screenWScale + dm.screenXOff), (int)(y * dm.screenHScale + dm.screenYOff));
	}
	
	public void drawText(String msg, double x, double y, String fontName, float fontSize, float r, float g, float b, float a)
	{
		fontSize *= (float)(dm.screenHScale * dm.screenWScale);
		Font newFont = fonts.get(fontName);
		if (newFont == null)
		{
			System.err.println("Font: " + fontName + " could not be found when calling drawText");
			return;
		}
		Font currentFont = dm.graphics.getFont();
		if (newFont != currentFont || newFont.getSize() != (int)fontSize)
		{
			if (newFont.getSize() != (int)fontSize)
			{
				newFont = newFont.deriveFont(fontSize);
			}
			dm.graphics.setFont(newFont);
		}
		dm.graphics.setColor(new Color(r, g, b, a));
		dm.graphics.drawString(msg, (int)(x * dm.screenWScale) + dm.screenXOff, (int)(y * dm.screenHScale + dm.screenYOff));
	}
	

	public void drawCenteredText(String msg, double x, double y, float fontSize, float r, float g, float b, float a)
	{
		if (fonts.size() == 0)
		{
			System.err.println("No fonts can be used for drawText");
			return;
		}
		fontSize *= (float)(dm.screenHScale * dm.screenWScale);
		Font newFont = fonts.values().iterator().next();
		Font currentFont = dm.graphics.getFont();
		if (newFont != currentFont || newFont.getSize() != (int)fontSize)
		{
			if (newFont.getSize() != (int)fontSize)
			{
				newFont = newFont.deriveFont(fontSize);
			}
			dm.graphics.setFont(newFont);
		}
		dm.graphics.setColor(new Color(r, g, b, a));
		dm.graphics.drawString(msg, (int)((x - getFontWidth(msg)/2d) * dm.screenWScale + dm.screenXOff), (int)(y * dm.screenHScale + dm.screenYOff));
	}
	
	public void drawCenteredText(String msg, double x, double y, String fontName, float fontSize, float r, float g, float b, float a)
	{
		fontSize *= (float)(dm.screenHScale * dm.screenWScale);
		Font newFont = fonts.get(fontName);
		if (newFont == null)
		{
			System.err.println("Font: " + fontName + " could not be found when calling drawText");
			return;
		}
		Font currentFont = dm.graphics.getFont();
		if (newFont != currentFont || newFont.getSize() != (int)fontSize)
		{
			if (newFont.getSize() != (int)fontSize)
			{
				newFont = newFont.deriveFont(fontSize);
			}
			dm.graphics.setFont(newFont);
		}
		dm.graphics.setColor(new Color(r, g, b, a));
		dm.graphics.drawString(msg, (int)((x - getFontWidth(msg)/2d) * dm.screenWScale + dm.screenXOff), (int)(y * dm.screenHScale + dm.screenYOff));
	}
	
	private DisplayManager dm;
	private HashMap<String, Font> fonts;
}

package impl;

import java.awt.Color;
import java.util.ArrayList;

import aniCreate.Image;

public class ScanPoint {
	
	static final int SCAN_RADIUS = 200;
	static final int COLOR_DIF_AVG = 30;
	static final int X_DIF_SMOOTH = 5;

	public ScanPoint(Image startImg, int vidX, int vidY)
	{
		cords = new ArrayList<Cord>();
		cords.add(new Cord(vidX, vidY));
		scanColor = new Color(startImg.img.getRGB(vidX, vidY), true);
		lastX = vidX;
		lastY = vidY;
	}
	
	public void rewind(int frames)
	{
		for (int i = 0; i < frames; i++)
		{
			cords.remove(cords.size() - 1);
		}
		lastX = cords.get(cords.size() - 1).x;
		lastY = cords.get(cords.size() - 1).y;
	}
	
	public void correct (Image frameImg, int x, int y)
	{
		lastX = x;
		lastY = y;
		cords.get(cords.size() - 1).x = x;
		cords.get(cords.size() - 1).y = y;
		scanColor = new Color(frameImg.img.getRGB(x, y),true);
	}
	
	public void smooth()
	{
		int startSmooth = 0;
		for (int i = 0; i < cords.size(); i++)
		{
			double dis = Math.sqrt(Math.pow(cords.get(i).x - cords.get(startSmooth).x, 2) + Math.pow(cords.get(i).y - cords.get(startSmooth).y, 2));
			if (dis > X_DIF_SMOOTH)
			{
				if (i - startSmooth > 1)
				{
					double avgX = 0;
					double avgY = 0;
					for (int j = startSmooth; j < i; j++)
					{
						avgX += cords.get(j).x;
						avgY += cords.get(j).y;
					}
					avgX /= (i - startSmooth);
					avgY /= (i - startSmooth);
					for (int j = startSmooth; j < i; j++)
					{
						cords.get(j).x = (int)avgX;
						cords.get(j).y = (int)avgY;
					}
				}
				startSmooth = i;
			}
		}
	}
	
	public void update(Image frameImg)
	{
		double matchX = -SCAN_RADIUS - 1;
		double matchY = -SCAN_RADIUS - 1;
		double minMatchVal = Double.MAX_VALUE;
		for (double sX = -SCAN_RADIUS; sX < SCAN_RADIUS; sX++)
		{
			for (double sY = -SCAN_RADIUS; sY < SCAN_RADIUS; sY++)
			{
				if ((int)(sX + lastX) >= 0 && (int)(sX + lastX) < 1920 && (int)(sY + lastY) >= 0 && (int)(sY + lastY) < 1080)
				{
					double dis = Math.sqrt(sX * sX + sY * sY); 
					Color pointColor = new Color(frameImg.img.getRGB((int)sX + lastX, (int)sY + lastY), true);
					double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2)) * 1.5d;
					double matchVal = dis + colCompare;
					if (matchVal < minMatchVal)
					{
						minMatchVal = matchVal;
						matchX = sX + lastX;
						matchY = sY + lastY;
					}
				}
			}
		}
		int tX = 0;
		int tY = 0;
		int totalCounted = 0;
		for (int i = - 15; i < 15; i++)
		{
			for (int j = -15; j < 15; j++)
			{
				int pX = i + (int)matchX;
				int pY = j + (int)matchY;
				if (pX >= 0 && pX < 1920 && pY >= 0 && pY < 1080)
				{
					Color pointColor = new Color(frameImg.img.getRGB(pX, pY), true);
					double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2));
					if (colCompare <= COLOR_DIF_AVG)
					{
						tX += i;
						tY += j;
						totalCounted++;
					}
				}
			}
		}
		if (totalCounted > 0)
		{
			tX /= totalCounted;
			tY /= totalCounted;
		}
		cords.add(new Cord((int)matchX + tX, (int)matchY + tY));
		lastX = (int)matchX;
		lastY = (int)matchY;
	}
	
	public ArrayList<Cord> cords;
	public Color scanColor;
	private int lastX = 0;
	private int lastY = 0; 
}

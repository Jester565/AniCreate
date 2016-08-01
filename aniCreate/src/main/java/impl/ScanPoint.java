package impl;

import java.awt.Color;
import java.util.ArrayList;

import aniCreate.Image;

public class ScanPoint {
	
	static final int SCAN_RADIUS = 200;
	static final int COLOR_DIF_AVG = 50;

	public ScanPoint(Image startImg, int vidX, int vidY)
	{
		cords = new ArrayList<Cord>();
		cords.add(new Cord(vidX, vidY));
		scanColor = new Color(startImg.img.getRGB(vidX, vidY), true);
		lastX = vidX;
		lastY = vidY;
	}
	
	public void update(Image frameImg)
	{
		double matchX = 0;
		double matchY = 0;
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
		int hX = 0;
		int lX = 0;
		int hY = 0;
		int lY = 0;
		for (int i = 0; i < 50; i++)
		{
			if ((int)matchX + i >= 1919)
			{
				hX = i;
				break;
			}
			Color pointColor = new Color(frameImg.img.getRGB((int)matchX + i, (int)matchY), true);
			double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2));
			if (colCompare > COLOR_DIF_AVG)
			{
				hX = i;
				break;
			}
		}
		for (int i = 0; i < 50; i++)
		{
			if ((int)matchX - i <= 0)
			{
				hX = i;
				break;
			}
			Color pointColor = new Color(frameImg.img.getRGB((int)matchX - i, (int)matchY), true);
			double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2));
			if (colCompare > COLOR_DIF_AVG)
			{
				lX = i;
			}
		}
		for (int i = 0; i < 50; i++)
		{
			if ((int)matchY + i >= 1079)
			{
				hX = i;
				break;
			}
			Color pointColor = new Color(frameImg.img.getRGB((int)matchX, (int)matchY + i), true);
			double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2));
			if (colCompare > COLOR_DIF_AVG)
			{
				hY = i;
			}
		}
		for (int i = 0; i < 50; i++)
		{
			if ((int)matchY - i <= 0)
			{
				hX = i;
				break;
			}
			Color pointColor = new Color(frameImg.img.getRGB((int)matchX, (int)matchY - i), true);
			double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2));
			if (colCompare > COLOR_DIF_AVG)
			{
				lY = i;
			}
		}
		matchX += (hX - lX);
		matchY += (hY - lY);
		cords.add(new Cord((int)matchX, (int)matchY));
		lastX = (int)matchX;
		lastY = (int)matchY;
	}
	
	public ArrayList<Cord> cords;
	public Color scanColor;
	private int lastX = 0;
	private int lastY = 0; 
}

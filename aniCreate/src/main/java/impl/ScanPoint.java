package impl;

import java.awt.Color;
import java.util.ArrayList;

import aniCreate.Image;

public class ScanPoint {
	
	static final int SCAN_RADIUS = 200;

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
				double dis = Math.sqrt(sX * sX + sY * sY);
				Color pointColor = new Color(frameImg.img.getRGB((int)sX + lastX, (int)sY + lastY), true);
				double colCompare = Math.sqrt(Math.pow(pointColor.getRed() - scanColor.getRed(), 2) + Math.pow(pointColor.getGreen() - scanColor.getGreen(), 2) + Math.pow(pointColor.getBlue() - scanColor.getBlue(), 2));
				double matchVal = dis + colCompare;
				if (matchVal < minMatchVal)
				{
					minMatchVal = matchVal;
					matchX = sX + lastX;
					matchY = sY + lastY;
				}
			}
		}
		cords.add(new Cord((int)matchX, (int)matchY));
		lastX = (int)matchX;
		lastY = (int)matchY;
	}
	
	public ArrayList<Cord> cords;
	public Color scanColor;
	private int lastX = 0;
	private int lastY = 0; 
}

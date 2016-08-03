package impl;

import java.awt.Point;
import java.util.ArrayList;

import aniCreate.Core;
import aniCreate.Image;

public class AniPart {
	public AniPart(Core core)
	{
		this.core = core;
		oriCords = new ArrayList<Cord>();
		angles = new ArrayList<Double>();
	}
	
	public boolean init(Part part, double totalTime)
	{
		ScanPoint oriSP = part.rOrigin;
		ScanPoint farSP = part.rFar;
		double localAngle = Math.atan2(part.iOrigin.y - part.iFar.y, part.iOrigin.x - part.iFar.x);
		if (localAngle < 0)
		{
			localAngle = Math.PI * 2 + localAngle;
		}
		this.imgScale = part.length/part.img.img.getHeight();
		oriCords = oriSP.cords;
		for (int i = 0; i < farSP.cords.size(); i++)
		{
			Cord oriCord = oriSP.cords.get(i);
			Cord farCord = farSP.cords.get(i);
			double angle = Math.atan2(oriCord.y - farCord.y, oriCord.x - farCord.x);
			if (angle < 0)
			{
				angle = Math.PI * 2 + angle;
			}
			angles.add(angle - localAngle);
		}
		this.img = part.img;
		img.setRotateOriginPixels(part.iOrigin.x, part.iOrigin.y);
		this.imgName = part.imgName;
		double filmFPS = oriSP.cords.size()/totalTime;
		this.fpsRate = filmFPS/core.logicalFrameRate;
		return true;
	}
	
	public void reset()
	{
		aniTime = 0;
	}
	
	public void draw(double xScale, double yScale)
	{
		aniTime += core.rate * fpsRate;
		if ((int)aniTime + 1 < oriCords.size())
		{
			Cord firstCord = oriCords.get((int)aniTime);
			Cord secondCord = oriCords.get((int)aniTime + 1);
			double firstAngle = angles.get((int)aniTime);
			double secondAngle = angles.get((int)aniTime + 1);
			double betweenVal = aniTime - (int)aniTime;
			double x = firstCord.x + (secondCord.x - firstCord.x) * betweenVal;
			double y = firstCord.y + (secondCord.y - firstCord.y) * betweenVal;
			double angle = firstAngle;
			img.setRads(angle);
			img.drawScale(x * xScale, y * yScale, imgScale * xScale, imgScale * yScale);
		}
	}
	
	private Core core;
	private double imgScale = 1;
	private double aniTime = 0;
	private ArrayList<Cord> oriCords;
	private ArrayList<Double> angles;
	private Image img;
	private String imgName;
	private double fpsRate;
}

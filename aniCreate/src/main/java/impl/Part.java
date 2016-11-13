package impl;

import java.io.File;

import aniCreate.Core;
import aniCreate.Image;

public class Part {

	Part(Core core, File imgFile)
	{
		this.core = core;
		img = new Image(core.getDisplayManager());
		img.init(imgFile);
		imgName = imgFile.getName();
	}
	
	public void drawPointSelect()
	{
		String msg = "Select ";
		if (rOrigin == null)
		{
			msg += "origin";
		}
		else
		{
			msg += "far";
		}
		core.getTextRenderer().drawText(msg, img.img.getWidth() + 10, 20, 30, 1, 1, 1, 1);
		Cord cord = selectPoint();
		if (cord != null)
		{
			if (iOrigin == null)
			{
				iOrigin = cord;
			}
			else
			{
				iFar = cord;
			}
			pointSelected = true;
		}
	}
	
	public void setScanPoint(ScanPoint sp)
	{
		if (rOrigin == null)
		{
			rOrigin = sp;
			pointSelected = false;
		}
		else
		{
			rFar = sp;
			Cord oriCord = rOrigin.cords.get(rOrigin.cords.size() - 1);
			Cord farCord = rFar.cords.get(rFar.cords.size() - 1);
			length = Math.sqrt(Math.pow(oriCord.x - farCord.x,2) + Math.pow(oriCord.y - farCord.y, 2));
			double rate = (double)img.img.getHeight()/(double)(iFar.y - iOrigin.y);
			length *= rate;
			pointSelected = false;
		}
	}
	
	public boolean pointsSet()
	{
		return (rOrigin != null && rFar != null);
	}
	
	Cord selectPoint()
	{
		if(core.getButtonManager().buttonClicked(0, 0, img.img.getWidth(), img.img.getHeight(), 1, 1, 1, .7f))
		{
			sX = (int) core.getInputManager().getScaleMouseClickX();
			sY = (int) core.getInputManager().getScaleMouseClickY();
		}
		img.draw(0, 0);
		if (sX >= 0)
		{
			core.getShapeRenderer().drawRect(sX - 2, sY - 2, 4, 4, 0,1,0,1);
			core.getTextRenderer().drawText("Confirm Point?", 0, img.img.getHeight() + 20, 20, 1, 1, 1, 1);
			if (core.getButtonManager().buttonClicked(0, img.img.getHeight() + 50, 100, 50, 0, 1, 0, 1))
			{
				Cord cord = new Cord(sX, sY);
				sX = -1;
				sY = -1;
				return cord;
			}
		}
		return null;
	}
	
	public void project()
	{
		img.setRotateOriginPixels(iOrigin.x, iOrigin.y);
		Cord oriCord = rOrigin.cords.get(rOrigin.cords.size() - 1);
		Cord farCord = rFar.cords.get(rFar.cords.size() - 1);
		double localAngle = Math.atan2(iOrigin.y - iFar.y, iOrigin.x - iFar.x);
		if (localAngle < 0)
		{
			localAngle = Math.PI * 2 + localAngle;
		}
		double angle = Math.atan2(oriCord.y - farCord.y, oriCord.x - farCord.x);
		if (angle < 0)
		{
			angle = Math.PI * 2 + angle;
		}
		angle -= localAngle;
		if (core.getInputManager().isKeyPressed('j'))
		{
			System.out.println("rad: " + angle);
			double degs = angle * (180.0/Math.PI);
			System.out.println("deg: " + degs);
			System.out.println(oriCord.y - farCord.y);
			System.out.println(farCord.x - oriCord.x);
		}
		img.setRads(angle); 
		double imgScale = length/(double)img.img.getHeight();
		img.draw(oriCord.x - iOrigin.x * imgScale, oriCord.y - iOrigin.y * imgScale, imgScale * img.img.getWidth(), length);
	}
	
	double length = 0;
	int sX = -1;
	int sY = -1;
	Cord iOrigin;
	Cord iFar;
	public String imgName;
	public boolean pointSelected = false;
	public ScanPoint rOrigin;
	public ScanPoint rFar;
	public Image img;
	private Core core;
}

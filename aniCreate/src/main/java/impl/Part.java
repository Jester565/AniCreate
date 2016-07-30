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
	
	void setScanPoint(ScanPoint sp)
	{
		if (rOrigin == null)
		{
			rOrigin = sp;
			pointSelected = false;
		}
		else
		{
			rFar = sp;
			pointSelected = false;
		}
	}
	
	Cord selectPoint()
	{
		if(core.getButtonManager().buttonClicked(0, 0, img.img.getWidth(), img.img.getHeight(), 1, 1, 1, .7f))
		{
			sX = (int) core.getInputManager().mouseX;
			sY = (int) core.getInputManager().mouseY;
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
	
	int sX = -1;
	int sY = -1;
	Cord iOrigin;
	Cord iFar;
	public boolean pointSelected = false;
	public ScanPoint rOrigin;
	public ScanPoint rFar;
	private Image img;
	private Core core;
}

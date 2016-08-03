package impl;

import aniCreate.Core;
import aniCreate.Image;

public class AniCore extends Core{

	public AniCore() {
		super();
	}
	
	@Override
	protected boolean init()
	{
		boolean fail = false;
		if (!super.init())
		{
			return false;
		}
		fail |= !tr.addFont("Calibri.ttf");
		vidCreator = new VideoCreator(this);
		fail |= !vidCreator.init("C:/Users/ajcra/Downloads/test6.mp4");
		vidCreator.getImageAtTime(0);
		image = new Image(dm);
		image.init("forArmReal.png");
		return !fail;
	}

	@Override
	protected void draw() {
		if (vidScanner == null)
		{
			vidScanner = vidCreator.draw();
		}
		else if (!vidScanner.finishCorrect)
		{
			vidScanner.draw();
		}
		else
		{
			if (animation == null)
			{
				animation = new Animation(this);
				animation.init(vidScanner.parts, vidCreator.endSeconds - vidCreator.startSeconds);
			}
			animation.draw();
			if (im.keyPressed('r'))
			{
				animation.reset();
			}
		}
		/*
		image.drawScale(500, 500, .5d, .5d);
		if (im.keyPressed('d'))
		{
			image.addRads(.02d);
		}
		if (im.keyPressed('a'))
		{
			image.addRads(-.02d);
		}
		*/
	}
	private VideoCreator vidCreator;
	private VideoScanner vidScanner = null;
	private Animation animation = null;
	private Image image = null;
}

package impl;

import aniCreate.Core;

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
		fail |= !vidCreator.init("C:/Users/ajcra/Downloads/test3.mp4");
		vidCreator.getImageAtTime(0);
		return !fail;
	}

	@Override
	protected void draw() {
		if (vidScanner == null)
		{
			vidScanner = vidCreator.draw();
		}
		else
		{
			vidScanner.draw();
		}
	}
	private VideoCreator vidCreator;
	private VideoScanner vidScanner = null;
}

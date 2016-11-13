package impl;

import aniCreate.Core;
import aniCreate.Image;
import aniCreate.Music;
import aniCreate.Sound;
import aniCreate.TextField;

public class AniCore extends Core{

	public AniCore() {
		super();
	}
	
	@Override
	protected boolean init(String windowName)
	{
		boolean fail = false;
		if (!super.init("AniCreate"))
		{
			return false;
		}
		fail |= !tr.loadFont("Calibri.ttf");
		fail |= !tr.setFont("Calibri");
		field = new TextField(this, 500, 50);
		sound = new Sound("./grass1.wav", 1);
		music = new Music("./rangerTheme.wav", 1);
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
			if (im.isKeyPressed('r'))
			{
				animation.reset();
			}
		}
		field.draw(500, 500);
		if (getInputManager().isCharPressed('l'))
		{
			sound.play();
		}
		if (getInputManager().isCharPressed('k'))
		{
			sound.stopAndReset();
		}
		if (getInputManager().isCharPressed('p'))
		{
			music.play();
		}
		if (getInputManager().isCharPressed('o'))
		{
			music.stopAndReset();
		}
	}
	private Music music;
	private Sound sound;
	private TextField field;
	private VideoCreator vidCreator;
	private VideoScanner vidScanner = null;
	private Animation animation = null;
	private Image image = null;
}

package impl;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

import aniCreate.Core;
import aniCreate.DisplayManager;
import aniCreate.Image;

public class VideoCreator {
	static final double SECOND_SCALE = 1000000;
	public VideoCreator(Core core)
	{
		this.core = core;
		timeFrame = new Image(core.getDisplayManager());
	}
	
	public boolean init(String filePath)
	{
		grabber = new FFmpegFrameGrabber(filePath);
		String format = filePath.substring(filePath.lastIndexOf('.') + 1);
		grabber.setFormat(format);
		try {
			grabber.start();
			grabber.grabImage();
			firstFrameNum = grabber.getFrameNumber();
			vidLength = (double)grabber.getLengthInTime()/SECOND_SCALE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void getImageAtTime(double secs)
	{
		try {
			int frameNum = (int)(secs * grabber.getFrameRate()) + firstFrameNum;
			if (frameNum > grabber.getLengthInFrames())
			{
				System.err.println("Frame exceeded the amount of frames in the video");
				return;
			}
			grabber.setFrameNumber(frameNum);
			Java2DFrameConverter jImgConverter = new Java2DFrameConverter();
			Frame frame = grabber.grabImage();
			timeFrame.init(jImgConverter.convert(frame));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw()
	{
		if (core.getInputManager().keyPressed('d') && vidSeconds < vidLength)
		{
			vidSeconds+=(1d/SECOND_SCALE);
			getImageAtTime(vidSeconds);
		}
		if (core.getInputManager().keyPressed('a') && vidSeconds > 0)
		{
			vidSeconds-=(1d/SECOND_SCALE);
			getImageAtTime(vidSeconds);
		}
		timeFrame.draw(0, 0, 1920, 1080);
		if (core.getButtonManager().buttonClicked(100, 900, DisplayManager.DISPLAY_DEFAULT_W - 200, 100, 1, 0, 0, .6f))
		{
			vidSeconds = ((core.getInputManager().mouseClickX - 100) / (DisplayManager.DISPLAY_DEFAULT_H - 200)) * vidLength;
			getImageAtTime(vidSeconds);
		}
		core.getShapeRenderer().drawRect(100 + (vidSeconds/vidLength) * (DisplayManager.DISPLAY_DEFAULT_W - 200)- 5, 870, 10, 160, .7f, .7f, .7f, .7f);
	}
	
	private double vidLength = 0;
	private double vidSeconds = 0;
	private int firstFrameNum = 0;
	private FFmpegFrameGrabber grabber;
	private Image timeFrame;
	private Core core;
}

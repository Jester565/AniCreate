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
		jImgConverter = new Java2DFrameConverter();
		grabber = new FFmpegFrameGrabber(filePath);
		String format = filePath.substring(filePath.lastIndexOf('.') + 1);
		grabber.setFormat(format);
		try {
			grabber.start();
			grabber.grabImage();
			firstFrameNum = grabber.getFrameNumber();
			vidLength = (double)grabber.getLengthInTime()/SECOND_SCALE;
			endSeconds = vidLength;
			vidFPS = grabber.getFrameRate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void getImageAtTime(double secs)
	{
		try {
			int frameNum = (int)(secs * vidFPS) + firstFrameNum;
			if (frameNum > grabber.getLengthInFrames())
			{
				System.err.println("Frame exceeded the amount of frames in the video");
				return;
			}
			grabber.setFrameNumber(frameNum);
			Frame frame = grabber.grabImage();
			timeFrame.init(jImgConverter.convert(frame));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public VideoScanner draw()
	{
		if (core.getInputManager().keyPressed('d') && vidSeconds < vidLength)
		{
			vidSeconds+=(.1d);
			getImageAtTime(vidSeconds);
		}
		if (core.getInputManager().keyPressed('a') && vidSeconds > 0)
		{
			vidSeconds-=(.1d);
			getImageAtTime(vidSeconds);
		}
		timeFrame.draw(0, 0, 1920, 1080);
		drawVidInfo();
		drawStartInfo(400, 0);
		drawEndInfo(800, 0);
		if (core.getButtonManager().buttonClicked(100, 900, DisplayManager.DISPLAY_DEFAULT_W - 200, 100, 1, 0, 0, .6f))
		{
			vidSeconds = ((core.getInputManager().mouseX - 100) / (DisplayManager.DISPLAY_DEFAULT_W - 200)) * vidLength;
			getImageAtTime(vidSeconds);
		}
		core.getShapeRenderer().drawRect(100 + (vidSeconds/vidLength) * (DisplayManager.DISPLAY_DEFAULT_W - 200) - 5, 870, 10, 160, .7f, .7f, .7f, .7f);
		core.getShapeRenderer().drawRect(100 + (startSeconds/vidLength) * (DisplayManager.DISPLAY_DEFAULT_W - 200) - 5, 870, 10, 160, 0f, 0f, 1f, .7f);
		core.getShapeRenderer().drawRect(100 + (endSeconds/vidLength) * (DisplayManager.DISPLAY_DEFAULT_W - 200) - 5, 870, 10, 160, 1f, 0f, 0f, .7f);
		if (core.getButtonManager().buttonClicked(1200, 0, 200, 100, 0, 1f, 0, .5f))
		{
			return new VideoScanner(core, grabber, jImgConverter, (int)(startSeconds * vidFPS) + firstFrameNum, (int)(endSeconds * vidFPS) + firstFrameNum);
		}
		core.getTextRenderer().drawCenteredText("Finished", 1300, 30, 40, 0, 0, 0, 1);
		return null;
	}
	
	private void drawVidInfo()
	{
		core.getShapeRenderer().drawRect(0, 0, 200, 100, .7f, .7f, .7f, .5f);
		core.getTextRenderer().drawText("FPS: " + Double.toString(roundThousands(vidFPS)), 5, 20, 20, 0, 0, 0, 1);
		core.getTextRenderer().drawText("Length: " + Double.toString(roundThousands(vidLength)), 5, 50, 20, 0, 0, 0, 1);
		core.getTextRenderer().drawText("VidTime: " + Double.toString(roundThousands(vidSeconds)), 5, 80, 20, 0, 0, 0, 1);
	}
	
	private void drawStartInfo(double x, double y)
	{
		core.getShapeRenderer().drawRect(x, y, 200, 100, .5f, .5f, 1, .5f);
		core.getTextRenderer().drawCenteredText("Start Point", x + 100, y + 20, 20, 0, 0, 0, 1);
		core.getTextRenderer().drawText("Time: " + Double.toString(roundThousands(startSeconds)), x + 5, y + 50, 20, 0, 0, 0, 1);
		if (core.getButtonManager().buttonClicked(x + 20, y + 80, 160, 17, 0, 0, 1f, .9f))
		{
			startSeconds = vidSeconds;
		}
	}
	
	private void drawEndInfo(double x, double y)
	{
		core.getShapeRenderer().drawRect(x, y, 200, 100, 1, 0, 0, .5f);
		core.getTextRenderer().drawCenteredText("End Point", x + 100, y + 20, 20, 0, 0, 0, 1);
		core.getTextRenderer().drawText("Time: " + Double.toString(roundThousands(endSeconds)), x + 5, y + 50, 20, 0, 0, 0, 1);
		if (core.getButtonManager().buttonClicked(x + 20, y + 80, 160, 17, 1f, 0, 0, .9f))
		{
			endSeconds = vidSeconds;
		}
	}
	
	private double roundThousands(double num)
	{
		return Math.round(num * 1000.0)/1000.0;
	}
	
	public double startSeconds = 0;
	public double endSeconds = 0;
	private double vidFPS;
	private double vidLength = 0;
	private double vidSeconds = 0;
	private int firstFrameNum = 0;
	private FFmpegFrameGrabber grabber;
	private Java2DFrameConverter jImgConverter;
	private Image timeFrame;
	private Core core;
}

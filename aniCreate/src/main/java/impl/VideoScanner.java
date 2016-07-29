package impl;

import java.awt.Color;
import java.util.ArrayList;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;

import aniCreate.Core;
import aniCreate.DisplayManager;
import aniCreate.Image;
import impl.ScanPoint.Cord;

public class VideoScanner {
	public VideoScanner(Core core, FFmpegFrameGrabber grabber, Java2DFrameConverter jImgConverter, int startFrame, int endFrame)
	{
		this.core = core;
		this.startFrame = startFrame;
		this.endFrame = endFrame;
		this.grabber = grabber;
		this.jImgConverter = jImgConverter;
		frameImg = new Image(core.getDisplayManager());
		try {
			grabber.setFrameNumber(startFrame);
			Frame frame = grabber.grabImage();
			frameImg.init(jImgConverter.convert(frame));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanPoints = new ArrayList<ScanPoint>();
	}
	
	public void draw()
	{
		frameImg.draw(0, 0, DisplayManager.DISPLAY_DEFAULT_W, DisplayManager.DISPLAY_DEFAULT_H);
		if (core.getInputManager().mouseClicked)
		{
			if (core.getInputManager().mouseX > 200)
			{
				scanPoints.add(new ScanPoint(frameImg, (int)core.getInputManager().mouseX, (int)core.getInputManager().mouseY));
			}
		}
		int y = 0;
		for (int i = 0; i < scanPoints.size(); i++)
		{
			Cord cord = scanPoints.get(i).cords.get(scanPoints.get(i).cords.size() - 1);
			int overBox = 0;
			if (core.getButtonManager().overRect(0, y, 200, 100))
			{
				overBox = 1;
			}
			Color scanCol = scanPoints.get(i).scanColor;
			core.getShapeRenderer().drawRect(0, y, 200, 100, scanCol.getRed()/255f, scanCol.getGreen()/255f, scanCol.getBlue()/255f, .5f + .5f * overBox);
			core.getShapeRenderer().drawRect(cord.x - 2 - overBox * 2, cord.y - 2 - overBox * 2, 5 + overBox * 5, 5 + overBox * 5, 1, overBox, overBox, 1);
			if (overBox == 1 && core.getInputManager().mouseClicked)
			{
				scanPoints.remove(i);
				i--;
			}
			y += 110;
		}
		if (core.getInputManager().keyPressed('d'))
		{
			nextFrame();
		}
	}
	
	private boolean nextFrame()
	{
		if (grabber.getFrameNumber() > endFrame)
		{
			System.out.println("reached endframe");
			return false;
		}
		try {
			Frame frame = grabber.grabImage();
			frameImg.init(jImgConverter.convert(frame));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		for (int i = 0; i < scanPoints.size(); i++)
		{
			scanPoints.get(i).update(frameImg);
		}
		return true;
	}
	
	private ArrayList<ScanPoint> scanPoints;
	private Image frameImg;
	private Core core;
	private FFmpegFrameGrabber grabber;
	private Java2DFrameConverter jImgConverter;
	private int startFrame;
	private int endFrame;
}

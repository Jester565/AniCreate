package impl;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.FrameGrabber.Exception;

import aniCreate.Core;
import aniCreate.DisplayManager;
import aniCreate.Image;

public class VideoScanner {
	public VideoScanner(Core core, FFmpegFrameGrabber grabber, Java2DFrameConverter jImgConverter, int startFrame, int endFrame)
	{
		this.core = core;
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
		parts = new ArrayList<Part>();
		fileChooser = new JFileChooser();
	}
	
	public void draw()
	{
		if (!finishScanSelect)
		{
			int i = selectScanPointIndex();
			if (i >= 0)
			{
				scanPoints.remove(i);
			}
			if (core.getButtonManager().buttonClicked(0, DisplayManager.DISPLAY_DEFAULT_H - 100, 100, 100, 0, 1, 0, 1))
			{
				finishScanSelect = true;
			}
		}
		else
		{
			partSelect();
		}
	}
	
	private int selectScanPointIndex()
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
				return i;
			}
			y += 110;
		}
		return -1;
	}
	
	private void partSelect()
	{
		if (!partForming)
		{
			if (core.getButtonManager().buttonClicked(860, 50, 200, 100, 1, 1, 1, 1))
			{
				fileSearching = true;
			}
			core.getTextRenderer().drawCenteredText("Select File", 960, 90, 20, 0, 0, 0, 1);
			if (fileSearching)
			{
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile = fileChooser.getSelectedFile();
					parts.add(new Part(core, selectedFile));
					fileSearching = false;
					partForming = true;
				}
				if (result == JFileChooser.CANCEL_OPTION)
				{
					fileSearching = false;
				}
			}
		}
		else
		{
			Part part = parts.get(parts.size() - 1);
			if (!part.pointsSet())
			{
				if (!part.pointSelected)
				{
					part.drawPointSelect();
				}
				else
				{
					int i = selectScanPointIndex();
					if (i >= 0)
					{
						part.setScanPoint(scanPoints.get(i));
					}
				}
			}
			else
			{
				frameImg.draw(0, 0);
				part.project(); 
			}
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
	
	private boolean finishScanSelect = false;
	private ArrayList<Part> parts;
	private ArrayList<ScanPoint> scanPoints;
	private Image frameImg;
	private Core core;
	boolean fileSearching = false;
	boolean partForming = false;
	private JFileChooser fileChooser;
	private FFmpegFrameGrabber grabber;
	private Java2DFrameConverter jImgConverter;
	private int endFrame;
}

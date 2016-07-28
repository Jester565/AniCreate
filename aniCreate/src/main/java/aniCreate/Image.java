package aniCreate;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Image {
	public Image(DisplayManager dm)
	{
		this.dm = dm;
		trans = new AffineTransform();
	}
	
	public boolean init(BufferedImage img)
	{
		this.img = img;
		return true;
	}
	
	public boolean init(String filePath)
	{
		InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
		try {
			img = ImageIO.read(is);
			return true;
		} catch (IOException e) {
			System.err.println("Could not find the image " + filePath + " when creating an image");
		}
		return false;
	}
	
	public void setDegs(double degs)
	{
		rads = (Math.PI/180d) * degs;
		trans.rotate(rads, origX, origY);
	}

	public void addDegs(double deltaDegs)
	{
		rads += (Math.PI/180d) * deltaDegs;
		trans.rotate(rads, origX, origY);
	}
	
	public double getDegs()
	{
		return rads * (180d/Math.PI);
	}
	
	public void setRads(double rads)
	{
		this.rads = rads;
		trans.rotate(rads, origX, origY);
	}
	
	public void addRads(double deltaRads)
	{
		rads += deltaRads;
	}
	
	public double getRads()
	{
		return rads;
	}
	
	public void setRotateOriginPixels(int imgX, int imgY)
	{
		origX = imgX;
		origY = imgY;
	}
	
	public void setRotateOriginScale(double imgScaleX, double imgScaleY)
	{
		origX = imgScaleX * img.getWidth();
		origY = imgScaleY * img.getHeight();
	}
	
	public void draw(double x, double y, double w, double h)
	{
		trans = new AffineTransform();
		trans.translate(x * dm.screenWScale + dm.screenXOff, y * dm.screenHScale + dm.screenYOff);
		trans.rotate(rads, origX * (w/img.getWidth()) * dm.screenWScale, origY * (h/img.getHeight()) * dm.screenHScale);
		trans.scale((w / img.getWidth()) * dm.screenWScale, (h / img.getHeight()) * dm.screenHScale);
		dm.graphics.drawImage(img, trans, null);
	}
	
	protected double rads = 0;
	protected double origX = 0;
	protected double origY = 0;
	protected BufferedImage img;
	protected DisplayManager dm;
	protected AffineTransform trans;
}

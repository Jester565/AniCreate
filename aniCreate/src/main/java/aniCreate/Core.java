package aniCreate;

public class Core {
	public final double DEFAULT_FRAME_RATE = 60;
	
	Core()
	{
		
	}
	
	public void run()
	{
		if (init())
		{
			draw();
		}
		else
		{
			System.err.println("Init of the Core failed");
		}
	}
	
	public void setFrameRateCap(int frameRate)
	{
		this.frameRate = frameRate;
	}
	
	private boolean init()
	{
		dm = new DisplayManager();
        if (!dm.init())
        {
        	return false;
        }
        dm.setBackgroundColor(0, 0, 0, 1);
		return true;
	}
	
	private void draw()
	{
		while (true)
		{
			long startTime = System.currentTimeMillis();
        	dm.update();
        	long timeLeft = (long)(1.0/frameRate - (System.currentTimeMillis() - startTime));
        	if (timeLeft > 0)
        	{
        		try {
					Thread.sleep(timeLeft);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	public DisplayManager dm;
	public double frameRate = DEFAULT_FRAME_RATE;
}

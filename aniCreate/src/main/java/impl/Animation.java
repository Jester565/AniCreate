package impl;

import java.util.ArrayList;

import aniCreate.Core;

public class Animation {
	public Animation(Core core)
	{
		this.core = core;
		aniParts = new ArrayList<AniPart>();
	}
	
	public boolean init(ArrayList<Part> parts, double filmTime)
	{
		for (int i = 0; i < parts.size(); i++)
		{
			AniPart aniPart = new AniPart(core);
			aniPart.init(parts.get(i), filmTime);
			aniParts.add(aniPart);
		}
		return true;
	}
	
	public void draw()
	{
		for (int i = 0; i < aniParts.size(); i++)
		{
			aniParts.get(i).draw(1,1);
		}
	}
	
	public void reset()
	{
		for (int i = 0; i < aniParts.size(); i++)
		{
			aniParts.get(i).reset();
		}
	}
	
	private ArrayList<AniPart> aniParts;
	private Core core;
}

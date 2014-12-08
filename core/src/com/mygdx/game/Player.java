package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Player extends SpaceObject {
	
	private long rateOfFire;
	public long lastFired;
	private int health;

	public Player(float x, float y, int w, int h, int frameW, int frameH,
			int delay, Texture image) {
		super(x, y, w, h, frameW, frameH, delay, true, image);
		this.rateOfFire = 200;
		this.lastFired = System.currentTimeMillis();
	}
	
	public boolean canFire()
	{			
		if ( System.currentTimeMillis() - this.lastFired > this.rateOfFire) {
			return true;
		}
		return false;		
	}
	
	

}

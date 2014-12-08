package com.mygdx.game;

public class SpaceObjectCollection {

	public SpaceObject m[];
	public SpaceObject sample;

	public SpaceObjectCollection(int count, SpaceObject sample) {
		this.sample = sample;
		this.m = new SpaceObject[count];
	}
	
	public void update(int offsetX,int offsetY)
    {
		for (int i = 0; i < m.length; i++) {
			if (m[i]!=null) {
				m[i].updatePosition(offsetX, offsetY);
			}
		}
    }

	public boolean spawn(float x, float y) {
        
		int i=getFreeObjectIndex();
		
		if (i == -1)
			return false;
        
		m[i]=new SpaceObject(x, y, sample.w, sample.h, sample.frameW, sample.frameH, sample.delay, sample.isLooping, sample.image);
		
		return true;
	}

	private int getFreeObjectIndex() {

		for (int i = 0; i < m.length; i++) {
			if (m[i] == null) {
				return i;
			}
		}

		return -1;
	}
	
	public void reset()
	{
		for (int i = 0; i < m.length; i++) {
			this.m[i] = null;
		}		
	}

}

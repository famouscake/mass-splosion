package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpaceObject {
	public boolean isLooping;
	public boolean animationEnded;
	
	public float x, y;

	public int w, h;

	public int frameW, frameH;

	Sprite States[];

	public int delay;

	public int counter;
	public int currentSprite;
	
	public Texture image;

	public SpaceObject(float x, float y, int w, int h, int frameW, int frameH,
			int delay,boolean isLooping, Texture image) {

		this.counter = 0;
		this.currentSprite = 0;
		this.isLooping = isLooping;
		this.animationEnded = false;

		this.x = x;
		this.y = y;

		this.w = w;
		this.h = h;

		this.frameW = frameW;
		this.frameH = frameH;

		this.delay = delay;
		
		this.image = image;

		this.States = new Sprite[image.getWidth() / frameW];

		for (int i = 0; i < image.getWidth(); i += frameW) {
			System.out.print(i + " ");
			this.States[i / frameW] = new Sprite(image, i, 0, frameW, frameH);
		}
	}

	public Sprite getCurrentSprite() {

		Sprite current = this.States[this.currentSprite];

		this.counter = (this.counter + 1) % this.delay;

		if (this.counter == 0) {				
			
			if ( this.isLooping || this.currentSprite < this.States.length - 1 ) {
				this.currentSprite = (this.currentSprite + 1) % this.States.length;
			}
			else {
				this.animationEnded = true;
			}
		}

		return current;

	}
	
	public boolean isLastSprite()
	{
		if ( this.currentSprite == this.States.length -1 ) {
			return true;
		}
		return false;
	}

	public void updatePosition(float offsetX, float offsetY) {
		this.x += offsetX;
		this.y += offsetY;
	}
}

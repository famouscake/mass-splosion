package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpaceObject {
	public float x, y;
	public int w, h;

	public int frameW, frameH;

	Sprite States[];

	public int delay;

	public int counter;
	public int currentSprite;

	public SpaceObject(float x, float y, int w, int h, int frameW,
			int frameH, int delay, Texture image) {

		this.counter = 0;
		this.currentSprite = 0;

		this.x = x;
		this.y = y;

		this.w = w;
		this.h = h;

		this.frameW = frameW;
		this.frameH = frameH;

		this.delay = delay;

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
			this.currentSprite = (this.currentSprite + 1) % this.States.length;
		}

		return current;

	}
	
	public void update() {
	}
	}

}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture player;
	Player garry;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		player = new Texture("player.png");
		garry = new Player(0, 0, 80, 80, 100, 100, 5, player);

	}

	@Override
	public void render() {

		this.update();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(garry.getCurrentSprite(), garry.x, garry.y);
		batch.end();
	}

	public void update() {

		this.handleInput();

	}

	public void handleInput() {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			garry.y = garry.y + 1;
		}
		

		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			garry.y = garry.y - 1;
		}
		

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			garry.x = garry.x - 1;
		}
		

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			garry.x = garry.x + 1;
		}
		
		float accelX = Gdx.input.getAccelerometerX();
	    float accelY = Gdx.input.getAccelerometerY();
	    float accelZ = Gdx.input.getAccelerometerZ();
	    
	    garry.x += accelY;
	    garry.y += -accelX;
	}

}

package com.mygdx.game;

import java.util.Random;

import sun.font.TrueTypeFont;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class MyGdxGame extends ApplicationAdapter {

	private boolean gameOver;

	private int score;
	private String yourScoreName;
	BitmapFont yourBitmapFontName;

	public double dist(float a, float b, float c, float d) {
		return Math.sqrt((a - c) * (a - c) + (b - d) * (b - d));
	}
	
	public long totalFrames = 0;

	Texture backgroundTexture;
	Texture playerTexture;
	Texture asteroidTexture;
	Texture splosionTexture;
	Texture shipsplosionTexture;
	Texture projectileTexture;

	SpaceObject background;
	SpaceObject shipsplosion;
	SpaceObjectCollection asteroids;
	SpaceObjectCollection lasers;
	SpaceObjectCollection splosions;

	SpriteBatch batch;
	Player garry;

	float height, width;

	@Override
	public void create() {
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean touchDown(int x, int y, int pointer, int button) {
				Input.blq = true;
				return true;
			}

			public boolean touchUp(int x, int y, int pointer, int button) {
				Input.blq = false;
				return true; // return true to indicate the event was handled
			}
		});

		this.height = Gdx.graphics.getHeight();
		this.width = Gdx.graphics.getWidth();
		

		batch = new SpriteBatch();

		backgroundTexture = new Texture("bg.jpg");
		playerTexture = new Texture("player.png");
		asteroidTexture = new Texture("ast.png");
		splosionTexture = new Texture("splosion.png");
		shipsplosionTexture = new Texture("shipsplosion.png");
		projectileTexture = new Texture("projectile.png");

		garry = new Player(0, 0, 100, 100, 100, 100, 5, playerTexture);

		asteroids = new SpaceObjectCollection(5, new SpaceObject(0, 0, 100,
				100, 64, 64, 9, true, asteroidTexture));

		lasers = new SpaceObjectCollection(10, new SpaceObject(0, 0, 75, 30,
				55, 20, 9, true, projectileTexture));

		splosions = new SpaceObjectCollection(5, new SpaceObject(0, 0, 100,
				100, 64, 64, 9, false, splosionTexture));
		score = 0;
		yourScoreName = "score: ";
		yourBitmapFontName = new BitmapFont();

	}

	
	@Override
	public void render() {

		this.update();
		
		this.totalFrames++;

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		int w = (int) (totalFrames % (long)1024);
	    batch.draw(new TextureRegion(this.backgroundTexture, w, 0, 1024, 800), 0, 0, width, height);


		batch.draw(garry.getCurrentSprite(), garry.x, garry.y, garry.w, garry.h);

		for (SpaceObject x : this.asteroids.m) {
			if (x != null)
				batch.draw(x.getCurrentSprite(), x.x, x.y, x.w, x.h);
		}

		for (SpaceObject x : this.lasers.m) {
			if (x != null)
				batch.draw(x.getCurrentSprite(), x.x, x.y, x.w, x.h);
		}
		
		for (SpaceObject x : this.splosions.m) {
			if (x != null)
				batch.draw(x.getCurrentSprite(), x.x, x.y, x.w, x.h);
		}

		if (this.shipsplosion != null) {
			batch.draw(shipsplosion.getCurrentSprite(), shipsplosion.x,
					shipsplosion.y, shipsplosion.w, shipsplosion.h);
		}

		yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		yourBitmapFontName.draw(batch, yourScoreName + this.score, 25, 100);

		if (gameOver) {
			yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			yourBitmapFontName.draw(batch, "Press enter to start again!", 25,
					300);

		}

		batch.end();
	}

	public void resetGame() {
		this.lasers.reset();
		this.asteroids.reset();
		this.splosions.reset();

		this.garry.x = 0;
		this.garry.y = this.height / 2;

		this.score = 0;

		this.gameOver = false;
	}

	public void update() {

		if (this.gameOver && ( Gdx.input.isKeyJustPressed(Keys.ENTER) || Input.blq)) {
			this.resetGame();
		}

		garry.updatePosition(Input.getPlayerOffsetX(garry) * 5,
				Input.getPlayerOffsetY(garry) * 5);

		if ((Input.isPlayerFiring() || Input.blq) && garry.canFire()) {

			this.lasers.spawn(garry.x + 30, garry.y);
			this.lasers.spawn(garry.x + 30, garry.y + 70);

			garry.lastFired = System.currentTimeMillis();
		}

		this.asteroids.update(-5, 0);
		this.lasers.update(10, 0);
		this.splosions.update(-5, 0);

		if ((new Random().nextInt(100) <= 10)) {
			this.asteroids.spawn(this.width + 100,
					(float) (new Random().nextInt((int) this.height)));
		}

		checkLasers();
		checkAsteroids();
		checkSplosions();
		
		if(this.shipsplosion != null && this.shipsplosion.animationEnded)
			this.shipsplosion = null;

		detect_laser_rock_collision();
		detect_player_rock_collision();

	}

	public void checkLasers() {
		for (int i = 0; i < this.lasers.m.length; i++) {
			if (lasers.m[i] != null && lasers.m[i].x >= this.width)
				lasers.m[i] = null;
		}
	}

	public void checkAsteroids() {
		for (int i = 0; i < this.asteroids.m.length; i++) {
			if (asteroids.m[i] != null && asteroids.m[i].x < -50)
				asteroids.m[i] = null;
		}
	}

	public void checkSplosions() {
		for (int i = 0; i < this.splosions.m.length; i++) {
			if (splosions.m[i] != null && splosions.m[i].animationEnded)
				splosions.m[i] = null;
		}
	}

	void detect_laser_rock_collision() {
		for (int i = 0; i < this.lasers.m.length; i++) {
			if (lasers.m[i] != null) {
				for (int j = 0; j < this.asteroids.m.length; j++)

					if (asteroids.m[j] != null) {
						double d = dist(lasers.m[i].x + lasers.m[i].w,

						lasers.m[i].y + lasers.m[i].h / 2,

						asteroids.m[j].x + asteroids.m[j].w / 2,

						asteroids.m[j].y + asteroids.m[j].h / 2);

						if (d < asteroids.sample.h / 2 + 5) {
							splosions.spawn(asteroids.m[j].x, asteroids.m[j].y);

							// lasers.m[i]=null;
							lasers.m[i].y = -1000;

							asteroids.m[j] = null;
							this.score++;

							// player.current_ammo++;
							// score+=1;

							break;
						}
					}
			}
		}
	}

	void detect_player_rock_collision() {
		for (int i = 0; i < asteroids.m.length; i++) {
			if (asteroids.m[i] != null) {
				double d = dist(garry.x + garry.w / 2,

				garry.y + garry.h / 2,

				asteroids.m[i].x + asteroids.m[i].w / 2,

				asteroids.m[i].y + asteroids.m[i].h / 2);

				if (d < 80) {
					splosions.spawn(asteroids.m[i].x, asteroids.m[i].y);

					asteroids.m[i] = null;

					// game_over=true;
					shipsplosion = new SpaceObject(garry.x, garry.y, 100, 100, 100,
							100, 5, false, shipsplosionTexture);

					gameOver = true;

					garry.x = -100000;
					garry.y = -100000;

					return;
				}
			}
		}
	}
}

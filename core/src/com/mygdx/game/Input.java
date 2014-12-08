package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Input {
	
	static public boolean blq = false;
	

//	float accelX = Gdx.input.getAccelerometerX();
//    float accelY = Gdx.input.getAccelerometerY();
//    
//    garry.x += accelY;
//    garry.y += -accelX;

	static public float getPlayerOffsetY(Player garry)
	{
		if (Gdx.input.isKeyPressed(Keys.UP) && garry.y + 1 < Gdx.graphics.getHeight()) {
			return 1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.DOWN) && garry.y - 1 > 0) {
			return -1;
		}
		
		float accelX = - Gdx.input.getAccelerometerX();
		
		if ( accelX != 0 && garry.y + accelX >0 && garry.y + accelX < Gdx.graphics.getHeight()) {
			return accelX / (float)(5.0);
		}
				
		return 0;
	}
	
	static public float getPlayerOffsetX(Player garry)
	{
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && garry.x + 1 < Gdx.graphics.getWidth()) {
			return 1;
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT) && garry.x - 1 > 0) {
			return -1;
		}
		
		float accelY = Gdx.input.getAccelerometerY();
		
		if ( accelY != 0 && garry.x + accelY >0 && garry.x + accelY < Gdx.graphics.getWidth()) {
			return accelY / (float)(5.0);
		}		
				
		return 0;
	}
	
	
	static public boolean isPlayerFiring()
	{
		if (Gdx.input.isKeyJustPressed(Keys.SPACE))
			return true;
		return false;
	}

}
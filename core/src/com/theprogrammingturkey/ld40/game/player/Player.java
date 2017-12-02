package com.theprogrammingturkey.ld40.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.theprogrammingturkey.ld40.game.GameBase;
import com.theprogrammingturkey.ld40.graphics.Draw2D;

public class Player
{
	protected GameBase game;

	protected int shooterX;
	protected int shooterY;
	protected boolean leftSide;

	private int shootDelay = 10;
	private boolean isShootPressed = false;

	public Player(GameBase game, boolean leftSide)
	{
		this.game = game;
		this.leftSide = leftSide;
		if (leftSide)
		{
			shooterX = 0;
		}
		else
		{
			shooterX = Gdx.graphics.getWidth();
		}
		shooterY = (Gdx.graphics.getHeight() / 2);
	}

	public void update()
	{
		shootDelay--;
		if (isShootPressed && shootDelay <= 0 && game.getGameData().sideShoot(leftSide))
		{
			float x = shooterX - Gdx.input.getX();
			float y = Gdx.input.getY() - shooterY;
			double angle = (Math.atan2(x, y) + (Math.PI / 2)) * (leftSide ? -1 : 1);
			float xUnit = (float) Math.cos(angle) * (leftSide ? -1 : 1);
			float yUnit = (float) Math.sin(angle);
			game.shootBall(shooterX, shooterY, xUnit, yUnit);
			shootDelay = 10;
		}
	}

	public void render(boolean run)
	{
		float x = shooterX - Gdx.input.getX();
		float y = Gdx.input.getY() - shooterY;
		float angle = (float) Math.toDegrees(Math.atan2(x, -y)) + 90;

		if (leftSide)
		{
			angle += 180;
		}
		Draw2D.drawCircle(shooterX, shooterY, 40, Color.RED, true);
		Draw2D.drawRect(shooterX - 50, shooterY - 10, 50, 20, angle, Color.RED, true);
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		if (button == Buttons.RIGHT)
		{
			isShootPressed = true;
			return true;
		}
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		if (button == Buttons.RIGHT)
		{
			isShootPressed = false;
			return true;
		}
		return false;
	}

	public int getShooterX()
	{
		return this.shooterX;
	}

	public int getShooterY()
	{
		return this.shooterY;
	}
}

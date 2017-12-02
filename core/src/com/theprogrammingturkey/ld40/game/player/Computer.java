package com.theprogrammingturkey.ld40.game.player;

import com.badlogic.gdx.graphics.Color;
import com.theprogrammingturkey.ld40.game.GameBase;
import com.theprogrammingturkey.ld40.game.entity.Entity;
import com.theprogrammingturkey.ld40.graphics.Draw2D;

public class Computer extends Player
{
	private int shootDelay = 60;

	public Computer(GameBase game, boolean leftSide)
	{
		super(game, leftSide);
	}

	public void update()
	{
		shootDelay--;
		if (shootDelay <= 0 && game.getGameData().sideShoot(false))
		{
			Entity closestEnt = game.getClosestPuck(this);
			float x = closestEnt.getX() - shooterX;
			float y = closestEnt.getY() - shooterY;
			double angle = Math.atan2(x, y) + (Math.PI / 2);
			float xUnit = (float) Math.cos(angle);
			float yUnit = (float) Math.sin(angle);
			game.shootBall(shooterX, shooterY, xUnit, yUnit);
			shootDelay = 60;
		}
	}

	public void render(boolean run)
	{
		float angle = 0;
		Entity closestEnt = game.getClosestPuck(this);
		if (run && closestEnt != null)
		{
			float x = shooterX - closestEnt.getX();
			float y = closestEnt.getY() - shooterY;
			angle = (float) Math.toDegrees(Math.atan2(x, y)) - 90;
		}
		Draw2D.drawCircle(shooterX, shooterY, 40, Color.RED, true);
		Draw2D.drawRect(shooterX - 50, shooterY - 5, 50, 20, angle, Color.RED, true);
	}
}

package com.theprogrammingturkey.ld40.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.theprogrammingturkey.ld40.graphics.Draw2D;

public class EntityBall extends Entity
{
	private int radius;

	public EntityBall(Body body, int radius)
	{
		super(body);
		this.radius = radius;
	}

	public void render()
	{
		Draw2D.drawCircle(getX(), getY(), this.radius, Color.WHITE, true);
	}
}

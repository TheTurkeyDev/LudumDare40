package com.theprogrammingturkey.ld40.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.theprogrammingturkey.ld40.graphics.Draw2D;

public class EntityPuck extends Entity
{
	private int size;
	private boolean circle;

	public EntityPuck(Body body, int size, boolean circle)
	{
		super(body);
		this.size = size;
		this.circle = circle;
	}

	public void render()
	{
		if (circle)
		{
			Draw2D.drawCircle(getX(), getY(), this.size, Color.WHITE, true);
		}
		else
		{
			Draw2D.drawTriangle(getX(), getY(), size, size, super.body.getAngle(), Color.WHITE, true);
		}
	}
}
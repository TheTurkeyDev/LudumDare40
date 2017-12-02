package com.theprogrammingturkey.ld40.game.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.theprogrammingturkey.ld40.graphics.Draw2D;

public class Entity
{
	protected Body body;
	protected int width = 0;
	protected int height = 0;

	public Entity(Body body)
	{
		this.body = body;
	}

	public Entity(Body body, int width, int height)
	{
		this.body = body;
		this.width = width;
		this.height = height;
	}

	public void render()
	{
		Draw2D.drawRect(getX() / (width / 2), getY() - 5, width, height, Color.RED, true);
	}

	public void update()
	{

	}

	public float getX()
	{
		return this.body.getPosition().x;
	}

	public float getY()
	{
		return this.body.getPosition().y;
	}
}

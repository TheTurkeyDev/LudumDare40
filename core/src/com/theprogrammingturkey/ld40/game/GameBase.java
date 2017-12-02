package com.theprogrammingturkey.ld40.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.theprogrammingturkey.ld40.game.entity.Entity;
import com.theprogrammingturkey.ld40.game.entity.EntityBall;
import com.theprogrammingturkey.ld40.game.entity.EntityPuck;
import com.theprogrammingturkey.ld40.game.player.Computer;
import com.theprogrammingturkey.ld40.game.player.Player;
import com.theprogrammingturkey.ld40.graphics.Draw2D;
import com.theprogrammingturkey.ld40.screens.GameScreen;

public class GameBase
{
	private List<Entity> entities = new ArrayList<Entity>();

	private GameScreen screen;
	private GameData data;
	private World world;

	private Computer comp;
	private Player player;

	public GameBase(GameScreen screen)
	{
		this.screen = screen;
		data = new GameData();
		world = new World(new Vector2(0, 0), true);

		comp = new Computer(this, false);
		player = new Player(this, true);

		initRound();
	}

	public void update()
	{
		world.step(1 / 60f, 6, 2);

		for (int i = entities.size() - 1; i >= 0; i--)
		{
			Entity ent = entities.get(i);
			ent.update();
			boolean offRight = ent.getX() > Gdx.graphics.getWidth();
			boolean offLeft = ent.getX() < 0;
			if (offLeft || offRight)
			{
				entities.remove(i);
				if (ent instanceof EntityPuck)
				{
					if (offRight)
					{
						data.rightSideRecivePuck();
					}
					else
					{
						data.leftSideRecivePuck();
					}

					if (data.getLeftSideRoundScore() + data.getRightSideRoundScore() == 3)
					{
						this.endRound();
						break;
					}
				}
				else if (ent instanceof EntityBall)
				{
					data.giveSideBall(offLeft);
				}
			}
		}

		comp.update();
		player.update();
	}

	public void render(boolean run)
	{
		for (Entity ent : entities)
		{
			ent.render();
		}

		comp.render(run);
		player.render(run);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		Draw2D.drawRect(0, height - 10, (width / 2) * ((float) data.getLeftSideBallsSaved() / data.getTotalBallsAvailable()), 10, Color.GREEN, true);
		float barWidth = width - ((width / 2) * ((float) data.getRightSideBallsSaved() / data.getTotalBallsAvailable()));
		Draw2D.drawRect(barWidth, height - 10, barWidth, 10, Color.GREEN, true);

		Draw2D.drawString(15, height / 2 + 20, "" + data.getLeftSideRoundScore(), 1f, Color.GREEN, true);
		Draw2D.drawString(15, height / 2 - 15, "" + data.getLeftSideGameScore(), 1, Color.WHITE, true);
		Draw2D.drawString(width - 15, height / 2 + 20, "" + data.getRightSideRoundScore(), 1, Color.GREEN, true);
		Draw2D.drawString(width - 15, height / 2 - 15, "" + data.getRightSideGameScore(), 1, Color.WHITE, true);
	}

	public void initRound()
	{
		data.initRound();

		this.entities.clear();

		Entity puck = new EntityPuck(this.createBallBody(600, 400, 0, 0, 5, 25), 25, true);
		this.addEntity(puck);
		Entity puck2 = new EntityPuck(this.createBallBody(600, 450, 0, 0, 3, 15), 15, true);
		this.addEntity(puck2);
		Entity puck3 = new EntityPuck(this.createBallTriangleBody(600, 350, 0, 0, 3, 15), 15, false);
		this.addEntity(puck3);
		Entity wallTop = new Entity(this.createWallBody(true), Gdx.graphics.getWidth(), 10);
		this.addEntity(wallTop);
		Entity wallBottom = new Entity(this.createWallBody(false), Gdx.graphics.getWidth(), 10);
		this.addEntity(wallBottom);
	}

	public void endRound()
	{
		data.endRound();
		screen.roundEnd();
	}

	public void addEntity(Entity ent)
	{
		this.entities.add(ent);
	}

	public Body createBallBody(float xPos, float yPos, float velX, float velY, float density, float radius)
	{
		float xAdjusted = xPos;
		float yAdjusted = yPos;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(xAdjusted, yAdjusted);
		Body body = world.createBody(bodyDef);
		body.setLinearVelocity(velX, velY);
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		// kg/ms2
		fixtureDef.density = density;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = .3f;
		body.createFixture(fixtureDef);
		circle.dispose();

		return body;
	}

	public Body createBallTriangleBody(float xPos, float yPos, float velX, float velY, float density, float radius)
	{
		float xAdjusted = xPos;
		float yAdjusted = yPos;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(xAdjusted, yAdjusted);
		Body body = world.createBody(bodyDef);
		body.setLinearVelocity(velX, velY);
		PolygonShape triangleShape = new PolygonShape();
		triangleShape.set(new float[] { -16, -16, 16, -16, 0, 16 });
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = triangleShape;
		// kg/ms2
		fixtureDef.density = density;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = .3f;
		body.createFixture(fixtureDef);
		triangleShape.dispose();

		return body;
	}

	public Body createWallBody(boolean top)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set((Gdx.graphics.getWidth() / 2f), (top ? Gdx.graphics.getHeight() - 5f : 5f));
		Body body = world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(Gdx.graphics.getWidth() / 2, 5.0f);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();

		return body;
	}

	public void shootBall(int x, int y, float unitX, float UnitY)
	{
		this.addEntity(new EntityBall(this.createBallBody(x - (int) (50f * unitX), y + (int) (50f * UnitY), -(int) (100f * unitX), (int) (100f * UnitY), 3f, 4), 4));
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		player.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		player.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	public Entity getClosestPuck(Player player)
	{
		double closestDist = Integer.MAX_VALUE;
		Entity closestEnt = null;
		for (Entity ent : entities)
		{
			if (ent instanceof EntityPuck)
			{
				double dist = Math.sqrt(Math.pow(ent.getX() - player.getShooterX(), 2) + Math.pow(ent.getY() - player.getShooterY(), 2));
				if (dist < closestDist || closestEnt == null)
				{
					closestEnt = ent;
					closestDist = dist;
				}
			}
		}
		return closestEnt;
	}

	public GameData getGameData()
	{
		return this.data;
	}

	public void dispose()
	{
		world.dispose();
	}
}

package com.theprogrammingturkey.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.theprogrammingturkey.ld40.game.GameBase;
import com.theprogrammingturkey.ld40.graphics.Draw2D;

public class GameScreen implements InputProcessor
{
	private GameBase game;
	private Stage stage;
	private Button startButton;
	private Button nextRoundButton;
	private Button newGameButton;

	private boolean run = false;

	public GameScreen()
	{
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		game = new GameBase(this);
		stage = new Stage();

		startButton = this.createButton("Start", "textures/button.png", (width / 2) - 100, (height / 2) + 100);
		startButton.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				roundStart();
			}
		});

		nextRoundButton = this.createButton("Continue", "textures/button.png", (width / 2) - 100, (height / 2) + 100);
		nextRoundButton.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				roundStart();
			}
		});

		newGameButton = this.createButton("New Game", "textures/button.png", (width / 2) - 100, (height / 2));
		newGameButton.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor)
			{
				gameEnd();
			}
		});

		startButton.setVisible(true);
		nextRoundButton.setVisible(false);
		newGameButton.setVisible(false);
	}

	public void update()
	{
		if (run)
		{
			game.update();
		}
	}

	public void render()
	{
		game.render(run);
		if (!run)
		{
			int width = Gdx.graphics.getWidth() / 2;
			int height = Gdx.graphics.getHeight() / 2;
			Draw2D.drawString(width, height + 250, "Original Game Name", 2, Color.WHITE, true);
			Draw2D.drawRect(width - 300, height - 200, 600, 400, Color.RED, true);
		}
		stage.draw();
	}

	public Button createButton(String text, String texture, int x, int y)
	{
		TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(texture))));
		TextButtonStyle style = new TextButtonStyle();
		style.up = myTexRegionDrawable;
		style.font = Draw2D.getFont();
		Button button = new TextButton(text, style);
		button.setPosition(x, y);
		stage.addActor(button);
		return button;
	}

	public void roundStart()
	{
		game.initRound();
		run = true;
		startButton.setVisible(false);
		nextRoundButton.setVisible(false);
		newGameButton.setVisible(false);
	}

	public void roundEnd()
	{
		this.run = false;
		nextRoundButton.setVisible(true);
		newGameButton.setVisible(true);
	}

	public void gameEnd()
	{
		this.run = false;
		startButton.setVisible(true);
	}

	public void dispose()
	{
		game.dispose();
	}

	@Override
	public boolean keyDown(int keycode)
	{
		stage.keyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		stage.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		stage.keyTyped(character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		stage.touchDown(screenX, screenY, pointer, button);
		game.touchDown(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		stage.touchUp(screenX, screenY, pointer, button);
		game.touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		stage.touchDragged(screenX, screenY, pointer);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		stage.mouseMoved(screenX, screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		stage.scrolled(amount);
		return false;
	}

}

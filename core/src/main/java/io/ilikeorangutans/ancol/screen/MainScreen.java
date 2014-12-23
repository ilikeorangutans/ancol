package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 */
public class MainScreen implements Screen {

	private final Stage stage;

	public MainScreen(final Game game, final Skin skin) {

		stage = new Stage(new ScreenViewport());

		Gdx.input.setInputProcessor(stage);

		TextButton quit = new TextButton("Quit", skin, "default");
		quit.setPosition(100, 100);
		quit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		stage.addActor(quit);

		TextButton startGame = new TextButton("Start Game", skin, "default");
		startGame.setPosition(100, 200);
		startGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game, skin));
			}
		});
		stage.addActor(startGame);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}

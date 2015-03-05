package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *
 */
public class LoadingScreen implements Screen {

	private final Stage stage;
	private final Game game;
	private final Skin skin;
	private final AssetManager assetManager;
	private final Runnable runOnComplete;
	private Label label;

	public LoadingScreen(Game game, Skin skin, AssetManager assetManager, Runnable runOnComplete) {
		this.game = game;
		this.skin = skin;
		this.assetManager = assetManager;
		this.runOnComplete = runOnComplete;
		stage = new Stage();
	}

	@Override
	public void show() {
		label = new Label("Loading... ", skin);
		stage.addActor(label);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (assetManager.update()) {
			runOnComplete.run();
		}
		label.setText(String.format("Loading %d%%...", (int) (assetManager.getProgress() * 100)));
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}

package io.ilikeorangutans.ancol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.screen.LoadingScreen;
import io.ilikeorangutans.ancol.screen.MainScreen;

public class AnCol extends Game {

	private final InputProcessorFactory inputProcessorFactory;

	private Skin skin;

	private AssetManager assetManager;

	public AnCol(InputProcessorFactory inputProcessorFactory) {
		this.inputProcessorFactory = inputProcessorFactory;
	}

	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		assetManager = new AssetManager();
		assetManager.load("packed/ancol.atlas", TextureAtlas.class);

		setScreen(new LoadingScreen(this, skin, assetManager, new Runnable() {
			@Override
			public void run() {
				TextureAtlas atlas = assetManager.get("packed/ancol.atlas", TextureAtlas.class);
				setScreen(new MainScreen(AnCol.this, skin, inputProcessorFactory, atlas));
			}
		}));

	}

	@Override
	public void dispose() {
		super.dispose();

		skin.dispose();
		assetManager.dispose();
	}
}

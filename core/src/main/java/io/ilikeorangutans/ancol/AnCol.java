package io.ilikeorangutans.ancol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ilikeorangutans.ancol.input.InputProcessorFactory;
import io.ilikeorangutans.ancol.screen.MainScreen;

public class AnCol extends Game {

	private final InputProcessorFactory inputProcessorFactory;
	private Stage stage;

	private Skin skin;

	public AnCol(InputProcessorFactory inputProcessorFactory) {

		this.inputProcessorFactory = inputProcessorFactory;
	}

	@Override
	public void create() {

		Gdx.input.setCatchBackKey(true);

		stage = new Stage();
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		setScreen(new MainScreen(this, skin, inputProcessorFactory));
	}

	@Override
	public void dispose() {
		super.dispose();

		skin.dispose();
	}
}

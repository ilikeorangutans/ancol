package io.ilikeorangutans.ancol;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ilikeorangutans.ancol.screen.MainScreen;

public class AnCol extends Game {

    private Stage stage;

    private Skin skin;

    @Override
    public void create() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        setScreen(new MainScreen(this, skin));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        super.dispose();

        skin.dispose();
    }
}

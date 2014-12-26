package io.ilikeorangutans.ancol.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ilikeorangutans.ancol.game.colony.ColonyComponent;
import io.ilikeorangutans.ecs.Entity;

/**
 * Created by jakob on 26/12/14.
 */
public class ColonyUI {
	private final Stage stage;
	private final Skin skin;
	private final Entity entity;

	public ColonyUI(Stage stage, Skin skin, Entity colony) {
		this.stage = stage;
		this.skin = skin;
		this.entity = colony;
	}

	public void setupAndShowUI() {
		final ColonyComponent colony = entity.getComponent(ColonyComponent.class);

		final Window window = new Window(colony.getName(), skin);
		window.setResizable(true);
		window.pad(22, 7, 7, 7);
		window.setSize(768, Gdx.graphics.getHeight());
		window.setPosition((Gdx.graphics.getWidth() / 2) - 384, Gdx.graphics.getHeight());
//		window.setDebug(true);


		Table buttons = new Table(skin);
//		buttons.debug();
		window.add(buttons).expandX().expandY().bottom().right();
		TextButton renameButton = new TextButton("Rename", skin);
		renameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent e, float x, float y) {
				Dialog d = new Dialog("Rename Colony", skin);
				final TextField textField = d.getContentTable().add(new TextField(colony.getName(), skin)).getActor();

				d.button(new TextButton("Rename", skin)).addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent e, float x, float y) {
						colony.setName(textField.getText());
						window.setTitle(colony.getName());
						entity.updated();
					}
				});
				d.button(new TextButton("Cancel", skin));
				d.show(stage);
			}
		});
		buttons.add(renameButton).padBottom(11).fillX().width(100);
		buttons.row();
		TextButton closeButton = new TextButton("Close", skin);
		closeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				window.setVisible(false);
			}
		});
		buttons.add(closeButton).fillX();

		// We need a... uhm... window manager here. Right now we can open the same window multiple times...
		stage.addActor(window);

	}
}

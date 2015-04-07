package io.ilikeorangutans.ancol.ui.colony;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.ilikeorangutans.ancol.game.colonist.Job;
import io.ilikeorangutans.ancol.ui.JobSelect;

import java.util.Collection;

/**
 * Shows a job selector in a Dialog.
 */
class JobSelectUI extends ChangeListener implements JobSelect {
	private final Skin skin;
	private final Stage stage;
	private JobSelectListener listener;
	private Dialog dialog;

	public JobSelectUI(Skin skin, Stage stage) {
		this.skin = skin;
		this.stage = stage;
	}

	@Override
	public void select(Collection<Job> jobs) {
		dialog = new Dialog("Select Job", skin);
		dialog.button("Cancel");
		dialog.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				event.stop();
				listener.jobSelected(null);
			}
		});

		Table table = dialog.getContentTable();
		table.add("Select job for colonist:");
		table.row();

		for (Job job : jobs) {
			TextButton button = new TextButton(job.getName(), skin);
			button.setUserObject(job);
			button.addListener(this);
			table.add(button).fillX();
			table.row();
		}

		dialog.show(stage);
	}

	@Override
	public void addJobSelectListener(JobSelectListener listener) {
		this.listener = listener;
	}

	@Override
	public void changed(ChangeEvent event, Actor actor) {
		event.stop();
		Job job = (Job) event.getTarget().getUserObject();
		listener.jobSelected(job);
		dialog.hide();
		dialog.remove();
	}
}

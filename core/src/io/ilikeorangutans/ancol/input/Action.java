package io.ilikeorangutans.ancol.input;

import java.util.Observable;

/**
 *
 */
public abstract class Action extends Observable {

	private boolean enabled;

	private String name;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void perform() {
		if (isEnabled())
			doPerform();
	}

	public abstract void doPerform();
}

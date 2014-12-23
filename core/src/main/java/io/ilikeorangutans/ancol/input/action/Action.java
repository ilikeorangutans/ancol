package io.ilikeorangutans.ancol.input.action;

import java.util.Observable;

/**
 * Describes a user action for the UI. Actions can be enabled, which will allow them to trigger the actual game state
 * change, or disabled, where changes will be ignored.
 * <p/>
 * Actions are observable so UI elements can easily be updated.
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

package io.ilikeorangutans.ancol.game.capability;

/**
 *
 */
public class BuildColonyCapability extends ImmutableCapability {
	public static final String NAME = "found-colony";
	public static final Capability BUILD_COLONY = new BuildColonyCapability();

	private BuildColonyCapability() {
		super(NAME);
	}
}

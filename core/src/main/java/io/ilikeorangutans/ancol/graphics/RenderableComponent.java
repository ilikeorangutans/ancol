package io.ilikeorangutans.ancol.graphics;

import io.ilikeorangutans.ecs.Component;
import io.ilikeorangutans.ecs.ComponentType;

/**
 *
 */
public class RenderableComponent implements Component {

	private static final ComponentType COMPONENT_TYPE = ComponentType.fromClass(RenderableComponent.class);

	private int spriteId;

	private boolean visible = true;

	public RenderableComponent(int spriteId) {
		this.spriteId = spriteId;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getSpriteId() {
		return spriteId;
	}

	@Override
	public ComponentType getType() {
		return COMPONENT_TYPE;
	}

}

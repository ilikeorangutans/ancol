package io.ilikeorangutans.ancol.game.colony.building;

import io.ilikeorangutans.ancol.game.production.Modifier;

/**
 *
 */
public interface Building extends Modifier {

	String getName();

	Blueprint getBlueprint();

}

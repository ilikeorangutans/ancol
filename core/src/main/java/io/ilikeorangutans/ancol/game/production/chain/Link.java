package io.ilikeorangutans.ancol.game.production.chain;

import io.ilikeorangutans.ancol.game.ware.Ware;
import io.ilikeorangutans.ancol.game.ware.Wares;

/**
 *
 */
public interface Link {
	void produce(Wares wares);

	boolean requiresInput();

	Ware getInput();

	Ware getOutput();
}

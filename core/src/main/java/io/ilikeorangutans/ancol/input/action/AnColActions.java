package io.ilikeorangutans.ancol.input.action;

import io.ilikeorangutans.ancol.path.PathFinder;
import io.ilikeorangutans.bus.EventBus;

/**
 *
 */
public class AnColActions {

	private final EventBus bus;
	private Action improveTileAction;
	private Action endTurnAction;
	private Action buildRoadAction;
	private Action buildColonyAction;
	private MoveAction moveAction;

	public AnColActions(EventBus bus, PathFinder pathFinder) {
		this.bus = bus;

		createActions(pathFinder);
	}

	private void createActions(PathFinder pathFinder) {
		improveTileAction = new ImproveTileAction(bus);
		bus.subscribe(improveTileAction);
		endTurnAction = new EndTurnAction(bus);
		buildRoadAction = new BuildRoadAction(bus);
		bus.subscribe(buildRoadAction);
		buildColonyAction = new BuildColonyAction(bus);
		bus.subscribe(buildColonyAction);

		moveAction = new MoveAction(bus, pathFinder);
		bus.subscribe(moveAction);
	}

	public Action getImproveTileAction() {
		return improveTileAction;
	}

	public Action getEndTurnAction() {
		return endTurnAction;
	}

	public Action getBuildRoadAction() {
		return buildRoadAction;
	}

	public Action getBuildColonyAction() {
		return buildColonyAction;
	}

	public MoveAction getMoveAction() {
		return moveAction;
	}
}

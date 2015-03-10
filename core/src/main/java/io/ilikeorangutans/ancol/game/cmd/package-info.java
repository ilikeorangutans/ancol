/**
 *
 * Command Subsystem
 *
 * This subsystem encapsulates commands and related logic. Commands are used to describe specific actions that a
 * player would like to perform. Commands are announced via instances of {@link io.ilikeorangutans.ancol.game.cmd.event.CommandEvent}
 * which carries the command to execute or add to an entity. In order for an entity to support this, it must contain
 * the {@link io.ilikeorangutans.ancol.game.cmd.ControllableComponent} component which holds and maintains the pending
 * commands for the entity. Once a CommandEvent has been issued it will be processed by the {@link io.ilikeorangutans.ancol.game.cmd.CommandEventHandler},
 * which will add the command to the given entity.
 *
 */
package io.ilikeorangutans.ancol.game.cmd;
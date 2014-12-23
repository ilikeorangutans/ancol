package io.ilikeorangutans.bus;

/**
 *
 */
public interface EventHandler {

    <T extends Event> void handle(T event);

}

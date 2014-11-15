package io.ilikeorangutans.bus;

/**
 *
 */
public interface Bus {

    void subscribe(Class<? extends Event> type, EventHandler handler);

    void subscribe(Class<? extends Event> type, EventQueue queue);

}

package io.ilikeorangutans.bus;

/**
 *
 */
public interface Bus {

    void subscribe(Object handler);

    void subscribe(Class<? extends Event> type, EventQueue queue);

}

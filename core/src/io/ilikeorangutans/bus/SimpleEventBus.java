package io.ilikeorangutans.bus;

/**
 *
 */
public class SimpleEventBus implements EventBus {

    @Override
    public void fire(Event event) {

    }

    @Override
    public void queue(Event event) {

    }

    @Override
    public void subscribe(Class<? extends Event> type, EventHandler handler) {

    }

    @Override
    public void subscribe(Class<? extends Event> type, EventQueue queue) {

    }
}

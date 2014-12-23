package io.ilikeorangutans.bus;

/**
 *
 */
public interface Emitter {

    /**
     * Publishes an event synchronously. Registered event handlers will be called immediately by the bus.
     *
     * @param event
     */
    void fire(Event event);

    /**
     * Queues an event for asynchronous processing. Registered event handlers will not be called by the bus, but will
     * have to poll for any pending events themselves.
     *
     * @param event
     */
    void queue(Event event);

}

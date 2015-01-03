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
	 * Queues an event for asynchronous processing. Asynchronous events will be handled at the discretion of the bus.
	 *
	 * @param event
	 */
	void queue(Event event);

}

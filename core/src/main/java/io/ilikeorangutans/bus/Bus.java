package io.ilikeorangutans.bus;

/**
 *
 */
public interface Bus {

	void subscribe(Object handler);

	/**
	 * Causes the bus to deliver all the queued events.
	 */
	void processQueue();

}

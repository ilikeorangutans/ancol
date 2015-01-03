package io.ilikeorangutans.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 */
public class SimpleEventBus implements EventBus {

	private final Map<String, List<HandlerInfo>> handlers = new HashMap<String, List<HandlerInfo>>();

	private final List<Event> queue = new ArrayList<Event>();

	@Override
	public void fire(Event event) {
		if (event == null)
			return;

		final String key = event.getClass().getName();
		if (!handlers.containsKey(key))
			return;

		for (HandlerInfo hi : handlers.get(key)) {
			try {
				hi.method.invoke(hi.handler, event);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Could not call event handler", e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Exception while calling event handler for event " + event, e);
			}
		}

	}

	@Override
	public void queue(Event event) {
		queue.add(event);
	}

	@Override
	public void subscribe(Object handler) {

		if (handler == null)
			throw new NullPointerException("Cannot subscribe null object!");

		List<Class<? extends Event>> events = new ArrayList<Class<? extends Event>>();
		for (Method m : handler.getClass().getMethods()) {
			if (m.getParameterTypes().length != 1)
				continue;

			Subscribe subscribe = m.getAnnotation(Subscribe.class);
			if (subscribe == null)
				continue;

			Class<?> subscribedType = m.getParameterTypes()[0];

			subscribe(subscribedType, m, handler);
		}
	}

	@Override
	public void processQueue() {
		for (ListIterator<Event> li = queue.listIterator(); li.hasNext(); ) {
			Event event = li.next();
			li.remove();
			fire(event);
		}
	}

	private void subscribe(Class<?> type, Method m, Object handler) {
		final String key = type.getName();
		if (!handlers.containsKey(key))
			handlers.put(key, new ArrayList<HandlerInfo>());

		HandlerInfo handlerInfo = new HandlerInfo();
		handlerInfo.method = m;
		handlerInfo.handler = handler;
		handlers.get(key).add(handlerInfo);
	}

	private final class HandlerInfo {
		Method method;
		Object handler;
	}
}

package org.streampipes.wrapper;

import java.io.Serializable;
import java.util.List;

import org.streampipes.model.impl.EventGrounding;
import org.streampipes.model.impl.EventSchema;
import org.streampipes.model.impl.EventStream;

public class InputStreamParameters implements Serializable {

	private static final long serialVersionUID = -240772928651344246L;
	
	private EventGrounding eventGrounding;
	private EventSchema eventSchema;
	private String inName;
	
	private static String topicPrefix = "topic://";
	
	public InputStreamParameters(EventStream inputStream) {
		super();
		this.eventGrounding = inputStream.getEventGrounding();
		this.inName = topicPrefix + eventGrounding.getTransportProtocol().getTopicName();
		this.eventSchema = inputStream.getEventSchema();
	}
	
	public EventGrounding getEventGrounding() {
		return eventGrounding;
	}
	
	public void setEventGrounding(EventGrounding eventGrounding) {
		this.eventGrounding = eventGrounding;
	}
	
	public String getInName() {
		return inName;
	}
	
	public void setInName(String eventName) {
		this.inName = eventName;
	}
	
	public List<String> getAllProperties() {
		return eventSchema.toPropertyList();
	}
	
}
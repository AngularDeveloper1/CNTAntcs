package com.es.wfx.analytics.bean;
public class GaRawEventData {
	private String title;
	private String eventType;
	private String userId;
	private String platform;
	private String course;
	private String chapter;
	private String correlationID;
	private WidgetData widgetData;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	
	/**
	 * @return the correlationID
	 */
	public String getCorrelationID() {
		return correlationID;
	}

	/**
	 * @param correlationID the correlationID to set
	 */
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	public WidgetData getWidgetData() {
		return widgetData;
	}

	public void setWidgetData(WidgetData widgetData) {
		this.widgetData = widgetData;
	}

}

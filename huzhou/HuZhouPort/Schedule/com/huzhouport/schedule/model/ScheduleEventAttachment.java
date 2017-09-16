package com.huzhouport.schedule.model;

public class ScheduleEventAttachment implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private int eventId;
	private int attachmentId;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

}

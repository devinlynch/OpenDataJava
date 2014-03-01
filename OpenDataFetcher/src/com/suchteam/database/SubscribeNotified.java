package com.suchteam.database;

import java.util.Date;

public class SubscribeNotified {
	
	private String subscribeNotifiedId;
	private Subscribe subscribe;
	private DatasetRecord datasetRecord;
	private Date notificationDate;
	private String content;
	private String subject;
	private boolean sent;
	
	public String getSubscribeNotifiedId() {
		return subscribeNotifiedId;
	}
	public void setSubscribeNotifiedId(String subscribeNotifiedId) {
		this.subscribeNotifiedId = subscribeNotifiedId;
	}
	public Subscribe getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Subscribe subscribe) {
		this.subscribe = subscribe;
	}
	public DatasetRecord getDatasetRecord() {
		return datasetRecord;
	}
	public void setDatasetRecord(DatasetRecord datasetRecord) {
		this.datasetRecord = datasetRecord;
	}
	public Date getNotificationDate() {
		return notificationDate;
	}
	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}

	
}

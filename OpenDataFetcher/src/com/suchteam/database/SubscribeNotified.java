package com.suchteam.database;

import java.util.Date;

public class SubscribeNotified {
	
	private String subscribeNotifiedId;
	private Subscribe subscribe;
	private DatasetRecord datasetRecord;
	private Date notificationDate;
	
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
}

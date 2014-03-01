package com.suchteam.database;

import java.util.Date;

public class DatasetValue {

	private String datasetValueId;
	private DatasetRecord datasetRecord;
	private DatasetInput datasetInput;
	private String value;
	private Date lastUpdated;
	
	
	public String getDatasetValueId() {
		return datasetValueId;
	}
	public void setDatasetValueId(String datasetValueId) {
		this.datasetValueId = datasetValueId;
	}
	public DatasetInput getDatasetInput() {
		return datasetInput;
	}
	public void setDatasetInput(DatasetInput datasetInput) {
		this.datasetInput = datasetInput;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public DatasetRecord getDatasetRecord() {
		return datasetRecord;
	}
	public void setDatasetRecord(DatasetRecord datasetRecord) {
		this.datasetRecord = datasetRecord;
	}
	
}

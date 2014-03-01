package com.suchteam.opendata;

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
	public DatasetRecord getDatasetRecordId() {
		return datasetRecord;
	}
	public void setDatasetRecordId(DatasetRecord datasetRecord) {
		this.datasetRecord = datasetRecord;
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
	public Date getTimestamp() {
		return lastUpdated;
	}
	public void setTimestamp(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	
	
}

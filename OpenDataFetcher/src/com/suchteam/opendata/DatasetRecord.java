package com.suchteam.opendata;

import java.util.Date;
import java.util.Set;

public class DatasetRecord {
	
	private String datasetRecordId;
	private Dataset dataset;
	private Set<DatasetValue> values;
	private Date creationDate;
	
	public String getDatasetRecordId() {
		return datasetRecordId;
	}
	public void setDatasetRecordId(String datasetRecordId) {
		this.datasetRecordId = datasetRecordId;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDatasetId(Dataset dataset) {
		this.dataset = dataset;
	}
	public Set<DatasetValue> getValues() {
		return values;
	}
	public void setValues(Set<DatasetValue> values) {
		this.values = values;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	

}

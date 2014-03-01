package com.suchteam.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DatasetRecord {
	
	private String datasetRecordId;
	private Dataset dataset;
	private Set<DatasetValue> values;
	private Date creationDate;
	private Set<SubscribeNotified> notifies;
	private String externalId;
	
	public DatasetRecord() {
		this.values = new HashSet<DatasetValue>();
		this.notifies = new HashSet<SubscribeNotified>();
	}
	
	public String getDatasetRecordId() {
		return datasetRecordId;
	}
	public void setDatasetRecordId(String datasetRecordId) {
		this.datasetRecordId = datasetRecordId;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
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
	public Set<SubscribeNotified> getNotifies() {
		return notifies;
	}
	public void setNotifies(Set<SubscribeNotified> notifies) {
		this.notifies = notifies;
	}
	
	public void addValue(DatasetValue value) {
		getValues().add(value);
	}
	
	public void addNotify(SubscribeNotified notify) {
		getNotifies().add(notify);
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	

	public DatasetValue getValueForInput(DatasetInput input) {
		Iterator<DatasetValue> it = getValues().iterator();
		while(it.hasNext()) {
			DatasetValue v = it.next();
			if(v.getDatasetInput().getDatasetInputId().equals(input.getDatasetInputId()))
				return v;
		}
		return null;
	}
}

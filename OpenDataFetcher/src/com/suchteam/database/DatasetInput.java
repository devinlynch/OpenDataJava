package com.suchteam.database;

public class DatasetInput {

	private String datasetInputId;
	private String name;
	private DataType dataType;
	private Dataset dataset;
	private String visualName;
	
	public String getDatasetInputId() {
		return datasetInputId;
	}
	public void setDatasetInputId(String datasetInputId) {
		this.datasetInputId = datasetInputId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	public String getVisualName() {
		return visualName;
	}
	public void setVisualName(String visualName) {
		this.visualName = visualName;
	}
	
	
	
}

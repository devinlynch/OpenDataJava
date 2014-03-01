package com.suchteam.opendata;

import com.suchteam.database.Dataset;

public abstract class AbstractDataFetcher {
	private Dataset dataset;
	
	public abstract void createDatasetRecords();

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
}

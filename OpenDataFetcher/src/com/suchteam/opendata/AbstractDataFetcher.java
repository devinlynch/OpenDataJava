package com.suchteam.opendata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.suchteam.database.DataAccess;
import com.suchteam.database.Dataset;
import com.suchteam.database.DatasetInput;

public abstract class AbstractDataFetcher {
	private Dataset dataset;
	private DataAccess access;

	public abstract void createDatasetRecords() throws IOException;

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	
	public DataAccess getAccess() {
		return access;
	}

	public void setAccess(DataAccess access) {
		this.access = access;
	}
	
	public Map<String, DatasetInput> getInputMapForName(Dataset dataset) {
		Map<String, DatasetInput> map = new HashMap<String, DatasetInput>();
		for (DatasetInput i : dataset.getInputs()) {
			map.put(i.getName(), i);
		}
		return map;
	}
}

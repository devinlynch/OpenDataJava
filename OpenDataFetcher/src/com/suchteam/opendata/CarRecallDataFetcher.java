package com.suchteam.opendata;

import com.suchteam.database.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.suchteam.database.DatasetRecord;

public class CarRecallDataFetcher extends AbstractDataFetcher {

	private DataAccess access;

	public static void main(String[] args) {
		CarRecallDataFetcher fetcher = new CarRecallDataFetcher();
		try {
			fetcher.createDatasetRecords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createDatasetRecords() throws IOException {
		CSVReader reader = null;

		setAccess(new DataAccess());

		getAccess().beginTransaction();
		Dataset dataset = (Dataset) getAccess().get(Dataset.class, "1");
		getAccess().commit();
		DatasetRecord tempRecord = null;
		DatasetValue tempValue = null;

		try {
			reader = new CSVReader(new FileReader("vrdb_full_monthly.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] prevLine = null;
		String[] nextLine = null;

		while ((nextLine = reader.readNext()) != null) {
			prevLine = nextLine;
		}

		getAccess().beginTransaction();
		String latestRecordId = (String) getAccess()
				.getLastestRecordForDatasetId("1");
		getAccess().commit();

		try {
			reader = new CSVReader(new FileReader("vrdb_full_monthly.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> header = new ArrayList<String>();

		if (latestRecordId.equalsIgnoreCase(prevLine[0])) {
			System.out.println("No new records");
			return;
		}

		nextLine = reader.readNext();

		for (int i = 0; i < nextLine.length; i++) {
			header.add(nextLine[i]);
		}

		while ((nextLine = reader.readNext()) != null) {
			try{
				if((Integer.parseInt(nextLine[0]) <= Integer
									.parseInt(latestRecordId))){
					continue;
				}
			} catch(Exception e){
				continue;
			}
			tempRecord = new DatasetRecord();
			tempRecord.setDataset(dataset);
			tempRecord.setCreationDate(new Date());
			tempRecord.setExternalId(nextLine[0]);
			for (int i = 0; i < nextLine.length; i++) {
				tempValue = new DatasetValue();
				tempValue.setDatasetInput(getInputForName(dataset,
						header.get(i)));
				if (tempValue.getDatasetInput() != null) {
					tempValue.setDatasetRecord(tempRecord);
					tempValue.setLastUpdated(new Date());
					tempValue.setValue(nextLine[i]);
					tempRecord.getValues().add(tempValue);
				}
			}
			getAccess().beginTransaction();
			getAccess().save(tempRecord);
			getAccess().commit();
		}

	}

	public DatasetInput getInputForName(Dataset dataset, String name) {
		for (DatasetInput i : dataset.getInputs()) {
			if (i.getName().equalsIgnoreCase(name)) {
				return i;
			}
		}
		return null;
	}

	public DataAccess getAccess() {
		return access;
	}

	public void setAccess(DataAccess access) {
		this.access = access;
	}

}

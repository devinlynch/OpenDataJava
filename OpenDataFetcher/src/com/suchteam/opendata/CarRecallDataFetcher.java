package com.suchteam.opendata;

import com.suchteam.database.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suchteam.database.DatasetRecord;

public class CarRecallDataFetcher extends AbstractDataFetcher {


	public static void main(String[] args) {
		CarRecallDataFetcher fetcher = new CarRecallDataFetcher();
		try {
			fetcher.setAccess(new DataAccess());
			if(args.length>0 && args[0].equals("fetch")) {
				fetcher.fetchCsv();
			}
			
			fetcher.createDatasetRecords();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fetcher.getAccess().getSession().getSessionFactory().close();
	}
	
	public void fetchCsv() {
		String urlAddress = "http://data.tc.gc.ca/extracts/vrdb_full_monthly.csv";
		try{
			URL website = new URL(urlAddress);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(System.getProperty("csvDumpLocationAndName", "car_recall.csv"));
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createDatasetRecords() throws IOException {
		CSVReader reader = null;

		getAccess().beginTransaction();
		Dataset dataset = (Dataset) getAccess().get(Dataset.class, "1");
		getAccess().commit();
		DatasetRecord tempRecord = null;
		DatasetValue tempValue = null;

		try {
			reader = new CSVReader(new FileReader(System.getProperty("csvFilePath", "test.csv")));
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
			reader = new CSVReader(new FileReader("test.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> header = new ArrayList<String>();

		if(latestRecordId == null) {
			latestRecordId= "0";
		}
		
		if (latestRecordId.equalsIgnoreCase(prevLine[0])) {
			System.out.println("No new records");
			return;
		}

		nextLine = reader.readNext();

		for (int i = 0; i < nextLine.length; i++) {
			header.add(nextLine[i]);
			System.out.println(nextLine[i]);
		}

		boolean didProcess = false;
		Map<String, DatasetInput> inputNameMap = getInputMapForName(dataset);
		while ((nextLine = reader.readNext()) != null) {
			try{
				if((Integer.parseInt(nextLine[0]) <= Integer.parseInt(latestRecordId))){
					continue;
				}
			
				tempRecord = new DatasetRecord();
				tempRecord.setDataset(dataset);
				tempRecord.setCreationDate(new Date());
				tempRecord.setExternalId(nextLine[0]);
				for (int i = 0; i < nextLine.length; i++) {
					DatasetInput input = inputNameMap.get(header.get(i));
					if(input == null)
						continue;
					
					tempValue = new DatasetValue();
					tempValue.setDatasetInput(input);
					tempValue.setDatasetRecord(tempRecord);
					tempValue.setLastUpdated(new Date());
					tempValue.setValue(nextLine[i]);
					tempRecord.getValues().add(tempValue);
				}
				
				if(tempRecord.getValues().size() == 0) {
					continue;
				}
				
				didProcess = true;
				
				getAccess().beginTransaction();
				getAccess().save(tempRecord);
				getAccess().commit();
			
			} catch(Exception e){
				try{
					if(getAccess().isTransactionActive())
						getAccess().rollback();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		if(didProcess) {
			getAccess().beginTransaction();
			dataset = getAccess().get(Dataset.class, "1");
			dataset.setLastProcessDate(new Date());
			getAccess().save(dataset);
			getAccess().commit();
		}
	}


}

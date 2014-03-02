package com.suchteam.opendata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.suchteam.database.DataAccess;
import com.suchteam.database.Dataset;
import com.suchteam.database.DatasetInput;
import com.suchteam.database.DatasetRecord;
import com.suchteam.database.DatasetValue;

public class BorderWaitTimeDataFetcher extends AbstractDataFetcher {
	public static void main(String[] args) {
		BorderWaitTimeDataFetcher fetcher = new BorderWaitTimeDataFetcher();
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
		String urlAddress = "http://www.cbsa-asfc.gc.ca/bwt-taf/bwt-eng.csv";
		try{
			URL website = new URL(urlAddress);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(System.getProperty("csvDumpLocationAndName", "bwt.csv"));
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void createDatasetRecords() throws IOException {
		BufferedReader reader = null;

		getAccess().beginTransaction();
		Dataset dataset = (Dataset) getAccess().get(Dataset.class, "2");
		getAccess().commit();

		try {
			reader = new BufferedReader(new FileReader(System.getProperty("csvFilePath", "bwt-eng.csv")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		List<String> header = new ArrayList<String>();

		String line = reader.readLine();
		String[] columns = line.split(";;");
		for (int i = 0; i < columns.length; i++) {
			header.add(columns[i].trim());
			System.out.println(columns[i].trim());
		}

		boolean didProcess = false;
		Map<String, DatasetInput> inputNameMap = getInputMapForName(dataset);
		while ((line = reader.readLine()) != null) {
			try{
				columns = line.split(";;");
				
				getAccess().beginTransaction();
				String borderId = columns[0].trim();
				DatasetRecord existingRecord  = getAccess().getRecordByExternalIdForDataset("2", borderId);
				
				boolean isNewRecord = false;
				if(existingRecord == null) {
					existingRecord = new DatasetRecord();
					existingRecord.setDataset(dataset);
					existingRecord.setExternalId(borderId);
					isNewRecord = true;
				}
				
				existingRecord.setCreationDate(new Date());
				
				for (int i = 0; i < columns.length-1; i++) {
					DatasetInput input = inputNameMap.get(header.get(i));
					if(input == null)
						continue;
					
					String value = columns[i].trim();
					if(i > 2) { 
						int minutes= 0;
						if(value.matches(".*\\d.*")){
							minutes = new Scanner(value).useDelimiter("\\D+").nextInt();
						}
						
						if(value.contains("hour")) {
							minutes = i*60;
						}
						value = Integer.toString(minutes);
					}
					
					DatasetValue tempValue = null;
					
					if(!isNewRecord){
						tempValue = existingRecord.getValueForInput(input);
						
						if(tempValue != null) {
							if(tempValue.getValue() != null && tempValue.getValue().equalsIgnoreCase(value))
								continue;
						}
					}
					
					if(tempValue == null) {
						tempValue = new DatasetValue();
						tempValue.setDatasetInput(input);
						tempValue.setDatasetRecord(existingRecord);
						existingRecord.getValues().add(tempValue);
					}
					
					tempValue.setLastUpdated(new Date());
					tempValue.setValue(value);
				}
				
				if(existingRecord.getValues().size() == 0) {
					getAccess().rollback();
					continue;
				}
				
				didProcess = true;
				
				getAccess().save(existingRecord);
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
			dataset = getAccess().get(Dataset.class, "2");
			dataset.setLastProcessDate(new Date());
			getAccess().save(dataset);
			getAccess().commit();
		}
	}

}

package com.suchteam.opendata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.views.AbstractView;

public class CarRecallDataFetcher extends AbstractDataFetcher {
	
	
	 public static void main(String[] args){
		 CarRecallDataFetcher fetcher = new CarRecallDataFetcher();
	    fetcher.createDatasetRecords();
	        
		
		
	}

	@Override
	public void createDatasetRecords() {
		CSVReader reader = null;
	    
	    DatasetRecord tempRecord = null;
	    
	    
		try {
			reader = new CSVReader(new FileReader("vrdb_full_monthly.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    String [] nextLine;
	    List<String> header = new ArrayList<String>();
	  
	    try {
			if ((nextLine = reader.readNext()) != null) {
				for(int i = 0; i < nextLine.length; i++){
					header.add(nextLine[i]);
					System.out.println("Found header: " + nextLine[i]);
				}
			}
			
			while((nextLine = reader.readNext()) != null){
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

package com.suchteam.opendata.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.suchteam.database.DataAccess;
import com.suchteam.database.Dataset;
import com.suchteam.database.Subscribe;
import com.suchteam.database.SubscribeAssertion;
import com.suchteam.database.SubscribeNotified;

public class NotificationProcessor extends Thread {
	private DataAccess access;
	private boolean stopped;
	
	
	public static void main(String[] args) {
		NotificationProcessor p = new NotificationProcessor();
		p.setAccess(new DataAccess());
		p.processDataSets();
	}
	
	@Override
	public void run() {
		
		while(!stopped) {
			processDataSets();
			
			try{
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				break;
			}
		}
	}
	
	protected void processDataSets() {
		List<String> datasetIds = getDatasetIds();
		for(String datasetId : datasetIds) {
			try{
				handleDataset(datasetId);
			} catch(Exception e) {
				// Stop the current process iteration if something goes wrong
				e.printStackTrace();
				break;
			}
		}
	}
	
	private void handleDataset(String datasetId) {
		getAccess().beginTransaction();
		Dataset dataset = getAccess().get(Dataset.class, datasetId);
		
		List<String> subscribeIds = getAccess().getDatasetSubscriberIds(dataset.getDatasetId());
		
		for(String subscribeId : subscribeIds) {
			handleSubscribe(subscribeId);
		}
		
		getAccess().commit();
	}
	
	private void handleSubscribe(String subscribeId) {
		Subscribe subscribe = getAccess().get(Subscribe.class, subscribeId);
		Date lastProcessDate = subscribe.getLastProcessedDate();
		if(lastProcessDate != null){
			// Non initial process
			if(subscribe.getLastProcessedDate().after(subscribe.getDataset().getLastProcessDate())) {
				// The data set hasn't been updated since the last time we processed this subscribe
				return;
			}
		} else {
			// Initial Process
			lastProcessDate = subscribe.getCreatedDate();
		}
		
		
		
		List<SubscribeAssertion> assertions = new ArrayList<SubscribeAssertion>(subscribe.getAssertions());
		List<String> alreadyNotifiedRecalls = new ArrayList<String>();
		for(SubscribeNotified notified : subscribe.getNotifies()) {
			alreadyNotifiedRecalls.add(notified.getDatasetRecord().getDatasetRecordId());
		}
		
		Query query = getAccess().getSession().createSQLQuery(""
				+ "select dataset_record_id from" + 
				"	(SELECT dr.dataset_record_id, count(*) as c FROM " + 
				"				dataset_record dr " + 
				"				left join dataset_value dv on dv.dataset_record_id=dr.dataset_record_id " + 
				"				where dr.creation_date > :lastProcessDate " + 
				"				and dr.dataset_id = :datasetId" + 
				"				and " + 
				"					("+generateAssertionAndClauses(assertions)+
				"					)" + 
				"				and dr.dataset_record_id not in (:alreadyNotifiedRecords) ) drinner" + 
				"	where drinner.c >= :numAssertions")
				.setParameter("lastProcessDate", lastProcessDate)
				.setParameter("datasetId", subscribe.getDataset().getDatasetId())
				.setParameter("numAssertions", assertions.size())
				.setParameter("alreadyNotifiedRecords", alreadyNotifiedRecalls);
		
		for(int i=0; i < assertions.size(); i++) {
			SubscribeAssertion assertion = assertions.get(i);
			query
				.setParameter("assertion"+(i+1)+"value", assertion.getValue())
				.setParameter("assertion"+(i+1)+"inputid", assertion.getInput().getDatasetInputId());
		}
		
		List<String> datasetRecords = query.list();
		System.out.println(datasetRecords);
	}
	
	private String generateAssertionAndClauses(List<SubscribeAssertion> assertions) {
		String s ="";
		
		int i = 1;
		for(SubscribeAssertion a : assertions) {
			s = s + "(dv.value = :assertion"+i+"value and dv.dataset_input_id = :assertion"+i+"inputid) ";
			if(i != assertions.size()) {
				s = s + " OR ";
			}
			i++;
		}
		
		return s;
	}
	
	protected List<String> getDatasetIds() {
		getAccess().beginTransaction();
		List<String> ids = getAccess().getDataSetIds();
		getAccess().commit();
		return ids;
	}
	
	public DataAccess getAccess() {
		return access;
	}
	public void setAccess(DataAccess access) {
		this.access = access;
	}
	
	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
}

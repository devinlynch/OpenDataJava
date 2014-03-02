package com.suchteam.opendata.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.suchteam.database.DataAccess;
import com.suchteam.database.DatasetInput;
import com.suchteam.database.DatasetRecord;
import com.suchteam.database.DatasetValue;
import com.suchteam.database.Subscribe;
import com.suchteam.database.SubscribeAssertion;
import com.suchteam.database.SubscribeNotified;

public class NotificationProcessor {
	private DataAccess access;
	private List<String> specificDataSets;
	
	public static void main(String[] args) {
		NotificationProcessor p = new NotificationProcessor();
		
		if(args != null && args.length > 0) {
			List<String> l = new ArrayList<String>();
			for(String arg : args) {
				l.add(arg);
			}
			p.setSpecificDataSets(l);
		}
		
		p.setAccess(new DataAccess());
		p.processDataSets();
		
		
		p.getAccess().getSession().getSessionFactory().close();
	}
	
	protected void processDataSets() {
		List<String> datasetIds = getDatasetIds();
		for(String datasetId : datasetIds) {
			try{
				handleDataset(datasetId);
			} catch(Exception e) {
				try{
					if(getAccess().isTransactionActive())
						getAccess().rollback();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				
				// Stop the current process iteration if something goes wrong
				e.printStackTrace();
				break;
			}
		}
	}
	
	private void handleDataset(String datasetId) {
		getAccess().beginTransaction();
		List<String> subscribeIds = getAccess().getDatasetSubscriberIds(datasetId);
		getAccess().commit();
		
		for(String subscribeId : subscribeIds) {
			try{
				getAccess().beginTransaction();
				handleSubscribe(subscribeId);
				getAccess().commit();
			} catch(Exception e) {
				try{
					getAccess().rollback();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
	
	private void handleSubscribe(String subscribeId) {
		Subscribe subscribe = getAccess().get(Subscribe.class, subscribeId);
		
		List<String> recordsToNotifyAbout = getRecordIdsForSubscribeNotifications(subscribe);
		
		subscribe.setLastProcessedDate(new Date());
		getAccess().save(subscribe);
		
		if(recordsToNotifyAbout == null || recordsToNotifyAbout.isEmpty()) {
			// There was no records to notify about
			return;
		}
		
		for(String id : recordsToNotifyAbout) {
			generateNotification(subscribe, id);
		}
	}

	public List<String> getRecordIdsForSubscribeNotifications(Subscribe subscribe) {
		Date lastProcessDate = subscribe.getLastProcessedDate();
		if(lastProcessDate != null){
			// Non initial process
			if(subscribe.getLastProcessedDate().after(subscribe.getDataset().getLastProcessDate())) {
				// The data set hasn't been updated since the last time we processed this subscribe
				return null;
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
				+ "select cast(dataset_record_id as CHAR(50)) from" + 
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
		
		@SuppressWarnings("unchecked")
		List<String> datasetRecords = query.list();
		return datasetRecords;
	}
	
	private String generateAssertionAndClauses(List<SubscribeAssertion> assertions) {
		String s ="";
		
		int i = 1;
		for(@SuppressWarnings("unused") SubscribeAssertion a : assertions) {
			s = s + "(dv.value = :assertion"+i+"value and dv.dataset_input_id = :assertion"+i+"inputid) ";
			if(i != assertions.size()) {
				s = s + " OR ";
			}
			i++;
		}
		
		return s;
	}
	
	protected List<String> getDatasetIds() {
		if(specificDataSets != null)
			return specificDataSets;
		
		getAccess().beginTransaction();
		List<String> ids = getAccess().getDataSetIds();
		getAccess().commit();
		return ids;
	}
	
	
	public void generateNotification(Subscribe subscribe, String dataSetRecordId) {
		DatasetRecord record = getAccess().get(DatasetRecord.class, dataSetRecordId);
		
		SubscribeNotified notify = new SubscribeNotified();
		notify.setDatasetRecord(record);
		notify.setNotificationDate(new Date());
		notify.setSubscribe(subscribe);
		
		String content = replaceVariables(record.getDataset().getEmailContent(), record, subscribe);
		String subject = record.getDataset().getSubjectContent();
		if(subject == null)
			subject = "";
		
		notify.setContent(content);
		notify.setSubject(subject);
		
		getAccess().save(notify);
	}
	
	
	public String replaceVariables(String content, DatasetRecord record, Subscribe subscribe) {
		if(content == null)
			return "";
		
		for(DatasetInput input : record.getDataset().getInputs()) {
			DatasetValue val = record.getValueForInput(input);
			String value="";
			if(val != null) {
				value = val.getValue();
			}
			content = replaceVariable(content, input.getName(), value);
		}
		
		return content;
	}
	
	protected String replaceVariable(String content, String name, String value) {
		return content = content.replace("@@"+name+"@@", value);
	}
	
	
	public DataAccess getAccess() {
		return access;
	}
	public void setAccess(DataAccess access) {
		this.access = access;
	}
	
	public List<String> getSpecificDataSets() {
		return specificDataSets;
	}

	public void setSpecificDataSets(List<String> specificDataSets) {
		this.specificDataSets = specificDataSets;
	}
}

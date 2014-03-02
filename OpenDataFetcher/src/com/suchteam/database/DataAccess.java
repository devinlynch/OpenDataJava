package com.suchteam.database;

import java.util.List;

import com.suchteam.opendata.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;

public class DataAccess {
	private static SessionFactory sessionFactory;
	private static Configuration config;
	
	@SuppressWarnings("deprecation")
	public static void configure() {
		if(sessionFactory== null) {
			if(config == null) {
				config = new Configuration().configure(); 
				addClassMappings();
			}
			
			sessionFactory = config.buildSessionFactory();
		}
	}
	
	public Session getSession() {
		configure();
		return sessionFactory.getCurrentSession();
	}
	
	public static void addClassMappings() {
		config.addClass(Dataset.class);
		config.addClass(DatasetInput.class);
		config.addClass(DatasetRecord.class);
		config.addClass(DatasetValue.class);
		config.addClass(DataType.class);
		config.addClass(Subscribe.class);
		config.addClass(SubscribeAssertion.class);
		config.addClass(SubscribeNotified.class);
	}
	
	public void beginTransaction() {
		getSession().beginTransaction();
	}
	
	public void commit() {
		getSession().getTransaction().commit();
	}
	
	public boolean isTransactionActive() {
		return getSession().getTransaction() != null && getSession().getTransaction().isActive();
	}
	
	public void rollback() {
		getSession().getTransaction().rollback();
	}
	
	public void save(Object o) {
		getSession().save(o);
	}
	
	public static void main(String[] args) {
		DataAccess access = new DataAccess();
		access.beginTransaction();
		
		access.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {
	    if (entity == null) {
	        throw new 
	           NullPointerException("Entity passed for initialization is null");
	    }

	    Hibernate.initialize(entity);
	    if (entity instanceof HibernateProxy) {
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
	                .getImplementation();
	    }
	    return entity;
	}
	
	@SuppressWarnings("unchecked")
	public <T>T get(Class<T> type, String id) {
		return (T) getSession().get(type, id);
	}
	
	public Object getLastestRecordForDatasetId(String datasetId) {
		return (String)  getSession().createSQLQuery("select external_id from dataset_record where dataset_id = :datasetId" +
				" order by external_id desc limit 1").setParameter("datasetId", datasetId).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDataSetIds() {
		return (List<String>) getSession()
				.createSQLQuery("select cast(dataset_id as CHAR(50)) from dataset")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getDatasetSubscriberIds(String datasetId) {
		return (List<String>) getSession()
				.createSQLQuery("select cast(subscribe_id as CHAR(50)) from subscribe where dataset_id = :dsid and unsubscribed=0")
				.setParameter("dsid", datasetId)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getEmailsThatNeedSending() {
		return (List<String>) getSession().createSQLQuery("select cast(subscribe_notified_id as CHAR(50)) from subscribe_notified where sent = 0").list();
	}
	
	public DatasetRecord getRecordByExternalIdForDataset(String datasetId, String externalId) {
		@SuppressWarnings("unchecked")
		List<DatasetRecord> list = getSession().createQuery("from DatasetRecord where dataset.datasetId = :dsid and externalId like :exId")
				.setParameter("dsid", datasetId)
				.setParameter("exId", externalId)
				.list();
		
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}

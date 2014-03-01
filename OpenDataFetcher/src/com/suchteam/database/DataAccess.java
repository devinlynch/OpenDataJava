package com.suchteam.database;

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
}

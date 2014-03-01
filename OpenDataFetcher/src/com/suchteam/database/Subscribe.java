package com.suchteam.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Subscribe {

	private String subscribeId;
	private String emailAddress;
	private Date sentDate;
	private Dataset dataset;
	private Set<SubscribeAssertion> assertions;
	private boolean unsubscribed;
	private Set<SubscribeNotified> notifies;
	
	public Subscribe() {
		this.assertions = new HashSet<SubscribeAssertion>();
		this.notifies = new HashSet<SubscribeNotified>();
	}
	
	public String getSubscribeId() {
		return subscribeId;
	}
	public void setSubscribeId(String subscribeId) {
		this.subscribeId = subscribeId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	public Set<SubscribeAssertion> getAssertions() {
		return assertions;
	}
	public void setAssertions(Set<SubscribeAssertion> assertions) {
		this.assertions = assertions;
	}
	public boolean isUnsubscribed() {
		return unsubscribed;
	}
	public void setUnsubscribed(boolean unsubscribed) {
		this.unsubscribed = unsubscribed;
	}
	public Set<SubscribeNotified> getNotifies() {
		return notifies;
	}
	public void setNotifies(Set<SubscribeNotified> notifies) {
		this.notifies = notifies;
	}
	
	public void addAssertion(SubscribeAssertion assertion) {
		getAssertions().add(assertion);
	}
	
	public void addNotify(SubscribeNotified notify) {
		getNotifies().add(notify);
	}
}

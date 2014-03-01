package com.suchteam.opendata;

import java.util.Date;
import java.util.Set;

public class Subscribe {

	private String subscribeId;
	private String emailAddress;
	private Date sentDate;
	private Dataset dataset;
	private Set<SubscribeAssertion> assertions;
	
	
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
	
	
	
}

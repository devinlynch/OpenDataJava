package com.suchteam.database;

public class SubscribeAssertion {

	private String subscribeAssertionId;
	private String value;
	private String assertion;
	private Subscribe subscribe;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAssertion() {
		return assertion;
	}
	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}
	public Subscribe getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(Subscribe subscribe) {
		this.subscribe = subscribe;
	}
	public String getSubscribeAssertionId() {
		return subscribeAssertionId;
	}
	public void setSubscribeAssertionId(String subscribeAssertionId) {
		this.subscribeAssertionId = subscribeAssertionId;
	}
	
	
	
}

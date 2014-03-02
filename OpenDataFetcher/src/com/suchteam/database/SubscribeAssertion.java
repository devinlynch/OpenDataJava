package com.suchteam.database;

public class SubscribeAssertion {
	public enum AssertionTypes {
		EQUALS,
		LESS_THAN,
		GREATER_THAN,
		GREATER_EQUAL_TO,
		LESS_EQUAL_TO,
		CONTAINS
	}
	
	
	private String subscribeAssertionId;
	private String value;
	private String assertion;
	private Subscribe subscribe;
	private DatasetInput input;
	
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
	public DatasetInput getInput() {
		return input;
	}
	public void setInput(DatasetInput input) {
		this.input = input;
	}
	
	
	public AssertionTypes getAssertionType() {
		if(assertion.equals("0")) {
			return AssertionTypes.EQUALS;
		} else if(assertion.equals("1")) {
			return AssertionTypes.LESS_THAN;
		} else if(assertion.equals("2")) {
			return AssertionTypes.GREATER_THAN;
		} else if(assertion.equals("3")) {
			return AssertionTypes.GREATER_EQUAL_TO;
		} else if(assertion.equals("4")) {
			return AssertionTypes.LESS_EQUAL_TO;
		} else if(assertion.equals("5")) {
			return AssertionTypes.CONTAINS;
		} else {
			return AssertionTypes.EQUALS;
		} 
	}
	
	
}

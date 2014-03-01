package com.suchteam.database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Dataset  {

	private String datasetId;
	private String name;
	private String processFrequency;
	private Date lastMessageTime;
	private Set<DatasetInput> inputs;
	private Set<DatasetRecord> records;
	private String classname;
	private String emailContent;
	private Date lastProcessDate;
	private String subjectContent;
	
	public Dataset() {
		this.inputs = new HashSet<DatasetInput>();
		this.records = new HashSet<DatasetRecord>();
	}
	
	public String getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessFrequency() {
		return processFrequency;
	}
	public void setProcessFrequency(String processFrequency) {
		this.processFrequency = processFrequency;
	}
	public Date getLastMessageTime() {
		return lastMessageTime;
	}
	public void setLastMessageTime(Date lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}
	public Set<DatasetInput> getInputs() {
		return inputs;
	}
	public void setInputs(Set<DatasetInput> inputs) {
		this.inputs = inputs;
	}
	
	public Set<DatasetRecord> getRecords() {
		return records;
	}
	public void setRecords(Set<DatasetRecord> records) {
		this.records = records;
	}
	
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public void addInput(DatasetInput input) {
		getInputs().add(input);
	}
	public void addRecord(DatasetRecord record) {
		getRecords().add(record);
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public Date getLastProcessDate() {
		return lastProcessDate;
	}

	public void setLastProcessDate(Date lastProcessDate) {
		this.lastProcessDate = lastProcessDate;
	}

	public String getSubjectContent() {
		return subjectContent;
	}

	public void setSubjectContent(String subjectContent) {
		this.subjectContent = subjectContent;
	}
	
}

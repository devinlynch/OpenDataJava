package com.suchteam.database;

public class DataType {
	
	public static DataType INTEGER_DATA_TYPE = new DataType("2", "Integer");
	public static DataType STRING_DATA_TYPE = new DataType("1", "String");
	
	private String dataTypeId;
	private String name;
	
	public DataType() {
		
	}
	
	public DataType(String id, String name) {
		this.dataTypeId = id;
		this.name = name;
	}
	
	public String getDataTypeId() {
		return dataTypeId;
	}
	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}

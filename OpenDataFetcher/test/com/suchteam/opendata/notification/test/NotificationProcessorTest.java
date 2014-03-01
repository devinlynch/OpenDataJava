package com.suchteam.opendata.notification.test;
import static org.junit.Assert.*;

import org.junit.Test;

import com.suchteam.database.DataType;
import com.suchteam.database.Dataset;
import com.suchteam.database.DatasetInput;
import com.suchteam.database.DatasetRecord;
import com.suchteam.database.DatasetValue;
import com.suchteam.opendata.notification.NotificationProcessor;
public class NotificationProcessorTest {

	@Test
	public void testReplace() {
		Dataset ds = new Dataset();
		ds.setClassname("Test");
		ds.setName("test");
		
		DatasetInput i1 = new DatasetInput();
		i1.setDataset(ds);
		i1.setDataType(DataType.STRING_DATA_TYPE);
		i1.setName("c1");
		ds.addInput(i1);
		i1.setDatasetInputId("1");
		
		DatasetInput i2 = new DatasetInput();
		i2.setDataset(ds);
		i2.setDataType(DataType.STRING_DATA_TYPE);
		i2.setName("c2");
		i2.setDatasetInputId("2");
		ds.addInput(i2);
		
		DatasetRecord r1 = new DatasetRecord();
		DatasetValue v1 = new DatasetValue();
		v1.setDatasetInput(i1);
		v1.setValue("foo");
		v1.setDatasetRecord(r1);
		r1.addValue(v1);
		r1.setDataset(ds);
		
		DatasetValue v2 = new DatasetValue();
		v2.setDatasetInput(i2);
		v2.setValue("bar");
		v2.setDatasetRecord(r1);
		r1.addValue(v2);
		
		NotificationProcessor p = new NotificationProcessor();
		
		String content = "@@c1@@ - @@c2@@";
		content = p.replaceVariables(content, r1, null);
		assertEquals("foo - bar", content);
	}
}

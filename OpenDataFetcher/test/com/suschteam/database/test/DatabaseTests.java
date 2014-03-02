package com.suschteam.database.test;
import org.junit.Test;

import com.suchteam.database.DataAccess;
import com.suchteam.database.DataType;
import com.suchteam.database.Dataset;
import com.suchteam.database.DatasetInput;
import com.suchteam.database.DatasetRecord;
import com.suchteam.database.DatasetValue;
import com.suchteam.database.Subscribe;
import com.suchteam.database.SubscribeAssertion;
import com.suchteam.database.SubscribeNotified;

import static org.junit.Assert.*;

public class DatabaseTests {

	@Test
	public void t1() {
		DataAccess access = new DataAccess();
		
		access.beginTransaction();
		
		Dataset d = access.get(Dataset.class, "1");
		DatasetInput input = access.get(DatasetInput.class, "1");
		DatasetRecord record = access.get(DatasetRecord.class, "1");
		DatasetValue value = access.get(DatasetValue.class, "1");
		Subscribe subscribe = access.get(Subscribe.class, "1");
		DataType dtype = access.get(DataType.class, "1");
		SubscribeAssertion assertion = access.get(SubscribeAssertion.class, "1");
		SubscribeNotified notified = access.get(SubscribeNotified.class, "1");
		
		access.getSession().createQuery("from DatasetRecord").setParameter("datasetId", "1").list();
		
		/*Dataset ds = new Dataset();
		ds.setClassname("Test");
		ds.setName("test");
		access.save(ds);
		
		DatasetInput i1 = new DatasetInput();
		i1.setDataset(ds);
		i1.setDataType(DataType.STRING_DATA_TYPE);
		i1.setName("c1");
		ds.addInput(i1);
		
		DatasetInput i2 = new DatasetInput();
		i2.setDataset(ds);
		i2.setDataType(DataType.STRING_DATA_TYPE);
		i2.setName("c2");
		ds.addInput(i2);
		
		DatasetRecord r1 = new DatasetRecord();
		DatasetValue v1 = new DatasetValue();
		v1.setDatasetInput(i1);
		v1.setValue("foo");
		v1.setDatasetRecord(r1);
		r1.addValue(v1);
		
		DatasetValue v2 = new DatasetValue();
		v2.setDatasetInput(i2);
		v2.setValue("bar");
		v2.setDatasetRecord(r1);
		r1.addValue(v2);
		
		ds.addRecord(r1);
		
		access.save(ds);*/
		
		access.commit();
	}
}

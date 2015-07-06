package application.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import application.model.BMIModel;
import application.model.BMIRecord;
import application.model.DietModel;
import application.model.TrainingModel;

public class TestModel {

	String path = "testdata\\bmi_records.csv";
	String path_faulty = "testdata\\bmi_records_faulty.csv";;

	// Cannot instantiate abstract class, need to use an implementation
	BMIModel model;
	String[][] testData = {
			{ "2013-10-10", "84.5", "1.81" },
			{ "2013-10-14", "84.1", "1.81" },
			{ "2013-10-21", "83.4", "1.82" },
			{ "2013-10-26", "82.9", "1.82" },
			{ "2013-11-02", "81.0", "1.82" },
			{ "2013-11-08", "79.2", "1.82" }
	};
	// Ensure this number is correct before running test unit
	int numRecordsInFile = 6;

	@Before
	public void setUp() throws Exception {
		model = new BMIModel(new File(path));
		for (int i = 0; i < testData.length; i++) {
			model.add(testData[i]);
		}
	}

	@Test
	public void testModel() {
		assertNotNull(model);
	}

	@Test
	public void testGetSize() {
		assertTrue(testData.length == model.getSize());
	}

	@Test
	public void testGetRecords() {
		assertNotNull(model.getRecords());
	}

	@Test
	public void testGetRecord() {
		int index = 1;
		BMIRecord br = new BMIRecord();
		model.setRecord(index, br);
		assertSame(br, model.getRecord(index));
	}

	@Test
	public void testGetLastRecord() {
		assertSame(model.getRecord(model.getSize() - 1), model.getLastRecord());
	}

	@Test
	public void testAddT() {
		BMIRecord br = new BMIRecord();
		model.add(br);
		assertTrue(model.getLastRecord().equals(br));
	}

	@Test
	public void testAddStringArray_BMIModel() {
		String[] s = { "2013-11-25", "64.0", "1.82" };
		model.add(s);
		assertTrue(model.getSize() == testData.length + 1);
	}

	@Test
	public void testAddStringArray_TrainingModel() {
		// File object is irrelevant to this test
		TrainingModel tr = new TrainingModel(new File(path));
		String[] s = { "2013-11-26", "Cycling", "Leisurely ride through Milton Keynes", "01:45:15", "5.2", "Milton Keynes' redway" };
		tr.add(s);
		assertTrue(tr.getSize() == 1);
	}

	@Test
	public void testAddStringArray_DietModel() {
		// File object is irrelevant to this test
		DietModel dr = new DietModel(new File(path));
		String[] s = { "2013-11-26", "Carrots", "Vitamin C", "0.3" };
		dr.add(s);
		assertTrue(dr.getSize() == 1);
	}

	@Test
	public void testSetRecord() {
		int index = 4;
		BMIRecord br = new BMIRecord();
		model.setRecord(index, br);
		assertTrue(model.getRecord(index).equals(br));
	}

	@Test
	public void testDelete() {
		int index = 2;
		model.delete(index);
		assertTrue(model.getSize() == testData.length - 1);
	}

	@Test
	public void testReadRecords() {
		// Replace with empty model
		model = new BMIModel(new File(path));
		model.readRecords();
		assertTrue(model.getSize() == numRecordsInFile);
	}

	@Test
	public void testSaveRecords() {
		model.saveRecords();
		model.readRecords();
		assertTrue(model.getSize() == numRecordsInFile);
	}

	@Test
	public void testAnyReadErrors_False() {
		model.readRecords();
		assertFalse(model.anyReadErrors());
	}

	@Test
	public void testAnyReadErrors_True() {
		model = new BMIModel(new File(path_faulty));
		model.readRecords();
		assertTrue(model.anyReadErrors());
	}

}

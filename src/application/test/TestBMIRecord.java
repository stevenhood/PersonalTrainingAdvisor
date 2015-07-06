package application.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.Utils;
import application.model.BMIRecord;

public class TestBMIRecord {

	BMIRecord record;
	String date = "2013-11-13";
	double weight = 60.7;
	double height = 1.75;
	double bmi = weight / Math.pow(height, 2);

	@Before
	public void setUp() throws Exception {
		record = new BMIRecord(date, weight, height);
	}

	@Test
	public void testToString() {
		assertTrue(record.toString().equals(date + "," + weight + "," + height));
	}

	@Test
	public void testBMIRecord() {
		assertNotNull(new BMIRecord());
	}

	@Test
	public void testBMIRecordStringDoubleDouble() {
		assertNotNull(record);
	}

	@Test
	public void testGetDate() {
		assertTrue(record.getDate().equals(date));
	}

	@Test
	public void testSetDate() {
		String newDate = "1999-10-22";
		record.setDate(newDate);
		assertTrue(record.getDate().equals(newDate));
	}

	@Test
	public void testGetWeight() {
		assertEquals(weight, record.getWeight(), 0.0);
	}

	@Test
	public void testSetWeight() {
		double newWeight = 57.7;
		record.setWeight(newWeight);
		assertEquals(newWeight, record.getWeight(), 0.0);
	}

	@Test
	public void testGetHeight() {
		assertEquals(height, record.getHeight(), 0.0);
	}

	@Test
	public void testSetHeight() {
		double newHeight = 1.98;
		record.setHeight(newHeight);
		assertEquals(newHeight, record.getHeight(), 0.0);
	}

	@Test
	public void testGetRawBMI() {
		assertEquals(bmi, record.getRawBMI(), 0.0);
	}

	@Test
	public void testGetBMI() {
		assertEquals(Utils.roundDouble(bmi, 2), record.getBMI(), 0.0);
	}

}

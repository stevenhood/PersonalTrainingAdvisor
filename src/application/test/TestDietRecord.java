package application.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.model.DietRecord;

public class TestDietRecord {

	DietRecord record;
	String date = "2013-11-25";
	String name = "Steak";
	String nutrition = "Protein";
	double kjContent = 0.8;

	@Before
	public void setUp() throws Exception {
		record = new DietRecord(date, name, nutrition, kjContent);
	}

	@Test
	public void testToString() {
		assertTrue(record.toString().equals(date + "," + name + "," + nutrition + "," + kjContent));
	}

	@Test
	public void testDietRecord() {
		assertNotNull(new DietRecord());
	}

	@Test
	public void testDietRecordStringStringStringDouble() {
		assertNotNull(record);
	}

	@Test
	public void testGetName() {
		assertTrue(record.getName().equals(name));
	}

	@Test
	public void testSetName() {
		String newName = "Pineapple";
		record.setName(newName);
		assertTrue(record.getName().equals(newName));
	}

	@Test
	public void testGetNutrition() {
		assertTrue(record.getNutrition().equals(nutrition));
	}

	@Test
	public void testSetNutrition() {
		String newNutrition = "Vitamin C";
		record.setNutrition(newNutrition);
		assertTrue(record.getNutrition().equals(newNutrition));
	}

	@Test
	public void testGetKjContent() {
		assertEquals(kjContent, record.getKjContent(), 0.0);
	}

	@Test
	public void testSetKjContent() {
		double newKjContent = 0.3;
		record.setKjContent(newKjContent);
		assertEquals(newKjContent, record.getKjContent(), 0.0);
	}

}

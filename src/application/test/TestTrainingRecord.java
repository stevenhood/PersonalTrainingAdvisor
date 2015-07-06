package application.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.model.TrainingRecord;

public class TestTrainingRecord {

	TrainingRecord record;
	String date = "19-11-2013";
	String category = "Jogging";
	String description = "This is a description.";
	String time = "01:20:15";
	double distance = 10.1;
	String route = "Wollaton park";

	@Before
	public void setUp() throws Exception {
		record = new TrainingRecord(date, category, description, time, distance, route);
	}

	@Test
	public void testToString() {
		assertTrue(record.toString().equals(date + "," + category + "," + description + "," + time + "," + distance + "," + route));
	}

	@Test
	public void testTrainingRecord() {
		assertNotNull(new TrainingRecord());
	}

	@Test
	public void testTrainingRecordStringStringStringString() {
		record = new TrainingRecord(date, category, description, time);
		assertNotNull(record);
	}

	@Test
	public void testTrainingRecordStringStringStringStringDoubleString() {
		assertNotNull(record);
	}

	@Test
	public void testGetCategory() {
		assertTrue(record.getCategory().equals(category));
	}

	@Test
	public void testSetCategory() {
		String newCategory = "Swimming";
		record.setCategory(newCategory);
		assertTrue(record.getCategory().equals(newCategory));
	}

	@Test
	public void testGetDescription() {
		assertTrue(record.getDescription().equals(description));
	}

	@Test
	public void testSetDescription() {
		String newDescription = "Went swimming.";
		record.setDescription(newDescription);
		assertTrue(record.getDescription().equals(newDescription));
	}

	@Test
	public void testGetTime() {
		assertTrue(record.getTime().equals(time));
	}

	@Test
	public void testSetTime() {
		String newTime = "00:35:42";
		record.setTime(newTime);
		assertTrue(record.getTime().equals(newTime));
	}

	@Test
	public void testGetDistance() {
		assertEquals(distance, record.getDistance(), 0.0);
	}

	@Test
	public void testSetDistance() {
		double newDistance = 0.4;
		record.setDistance(newDistance);
		assertEquals(newDistance, record.getDistance(), 0.0);
	}

	@Test
	public void testGetRoute() {
		assertTrue(record.getRoute().equals(route));
	}

	@Test
	public void testSetRoute() {
		String newRoute = "Pool";
		record.setRoute(newRoute);
		assertTrue(record.getRoute().equals(newRoute));
	}

}

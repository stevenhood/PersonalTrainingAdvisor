package application.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Record subclass for storing training data.
 */
public class TrainingRecord extends Record {

	private SimpleStringProperty category;
	private SimpleStringProperty description;
	private SimpleStringProperty time;
	private SimpleDoubleProperty distance;
	private SimpleStringProperty route;

	/**
	 * Creates a TrainingRecord with default data. Used when an exception occurs
	 * in Model.readRecords as a result of a faulty data file.
	 */
	public TrainingRecord() {
		super();
		category = new SimpleStringProperty("--");
		description = new SimpleStringProperty("--");
		time = new SimpleStringProperty("");
		distance = new SimpleDoubleProperty(-1);
		route = new SimpleStringProperty("--");
	}

	/**
	 * Creates a TrainingRecord without the optional fields distance and route,
	 * which are set default values.
	 * 
	 * @param date
	 *            in the format YYYY-MM-DD
	 * @param category
	 * @param description
	 * @param time
	 *            in the format HH:MM:SS
	 */
	public TrainingRecord(String date, String category, String description, String time) {
		super(date);
		this.category = new SimpleStringProperty(category);
		this.description = new SimpleStringProperty(description);
		this.time = new SimpleStringProperty(time);

		distance = new SimpleDoubleProperty(0.0);
		route = new SimpleStringProperty("n/a");
	}

	public TrainingRecord(String date, String category, String description, String time, double distance, String route) {
		this(date, category, description, time);
		this.distance = new SimpleDoubleProperty(distance);
		this.route = new SimpleStringProperty(route);
	}

	public String getCategory() {
		return category.get();
	}

	public void setCategory(String c) {
		category.set(c);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String d) {
		description.set(d);
	}

	public String getTime() {
		return time.get();
	}

	public void setTime(String t) {
		time.set(t);
	}

	public double getDistance() {
		return distance.get();
	}

	public void setDistance(double d) {
		distance.set(d);
	}

	public String getRoute() {
		return route.get();
	}

	public void setRoute(String r) {
		route.set(r);
	}

	@Override
	public String toString() {
		return getDate() + "," + getCategory() + "," + getDescription() + "," + getTime() + "," + getDistance() + "," + getRoute();
	}

}

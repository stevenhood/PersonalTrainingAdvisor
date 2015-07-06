package application.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Record subclass for storing diet data.
 */
public class DietRecord extends Record {

	private SimpleStringProperty name;
	private SimpleStringProperty nutrition;
	private SimpleDoubleProperty kjContent;

	/**
	 * Creates a DietRecord with default data. Used when an exception occurs in
	 * Model.readRecords as a result of a faulty data file.
	 */
	public DietRecord() {
		super();
		name = new SimpleStringProperty("--");
		nutrition = new SimpleStringProperty("--");
		kjContent = new SimpleDoubleProperty(-1);
	}

	/**
	 * Creates a TrainingRecord with specified data.
	 * 
	 * @param date
	 *            in the format YYYY-MM-DD
	 * @param name
	 * @param nutrition
	 * @param kjContent
	 */
	public DietRecord(String date, String name, String nutrition, double kjContent) {
		super(date);
		this.name = new SimpleStringProperty(name);
		this.nutrition = new SimpleStringProperty(nutrition);
		this.kjContent = new SimpleDoubleProperty(kjContent);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String n) {
		name.set(n);
	}

	public String getNutrition() {
		return nutrition.get();
	}

	public void setNutrition(String n) {
		nutrition.set(n);
	}

	public double getKjContent() {
		return kjContent.get();
	}

	public void setKjContent(double t) {
		kjContent.set(t);
	}

	@Override
	public String toString() {
		return getDate() + "," + getName() + "," + getNutrition() + "," + getKjContent();
	}

}

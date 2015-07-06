package application.model;

import application.Utils;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Record subclass for storing BMI data.
 */
public class BMIRecord extends Record {

	private SimpleDoubleProperty weight;
	private SimpleDoubleProperty height;

	/**
	 * Creates a BMIRecord with default data. Used when an exception occurs in
	 * Model.readRecords as a result of a faulty data file.
	 */
	public BMIRecord() {
		super();
		weight = new SimpleDoubleProperty(-1);
		height = new SimpleDoubleProperty(-1);
	}

	/**
	 * Creates a BMIRecord with specified data.
	 * 
	 * @param date
	 *            in the format YYYY-MM-DD
	 * @param weight
	 * @param height
	 */
	public BMIRecord(String date, double weight, double height) {
		super(date);
		this.weight = new SimpleDoubleProperty(weight);
		this.height = new SimpleDoubleProperty(height);
	}

	public double getWeight() {
		return weight.get();
	}

	public void setWeight(double w) {
		weight.set(w);
	}

	public double getHeight() {
		return height.get();
	}

	public void setHeight(double h) {
		height.set(h);
	}

	/**
	 * Returns the calculated BMI unrounded.
	 */
	public double getRawBMI() {
		return getWeight() / Math.pow(getHeight(), 2);
	}

	/**
	 * Returns the calculated BMI rounded to 2 decimal places.
	 */
	public double getBMI() {
		return Utils.roundDouble(getRawBMI(), 2);
	}

	@Override
	public String toString() {
		return getDate() + "," + getWeight() + "," + getHeight();
	}

}

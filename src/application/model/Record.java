package application.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * The superclass for all records.
 * 
 * Stores: <br>
 * Date - in the format YYYY-MM-DD.
 */
public abstract class Record {

	private SimpleStringProperty date;

	/**
	 * Creates a Record with the default date value "1970-01-01". Used when an
	 * exception occurs in Model.readRecords as a result of a faulty data file.
	 */
	public Record() {
		date = new SimpleStringProperty("1970-01-01");
	}

	/**
	 * Creates a Record with the specified date.
	 */
	public Record(String date) {
		this.date = new SimpleStringProperty(date);
	}

	/**
	 * Gets the date value.
	 */
	public String getDate() {
		return date.get();
	}

	/**
	 * Sets the date value.
	 */
	public void setDate(String d) {
		date.set(d);
	}

	/**
	 * Returns a string of concatenated record fields delimited by commas.
	 */
	@Override
	public abstract String toString();

}

package application.model;

import java.io.File;

import application.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model that stores records of type DietRecord.
 */
public class DietModel extends Model<DietRecord> {

	public DietModel(File file) {
		super(file);
	}

	/**
	 * @param record
	 *            a .csv record split into a String[], which must have been in
	 *            the format "date,name,nutritionalinfo,kjcontent".
	 */
	@Override
	public void add(String[] record) {
		DietRecord dr;

		try {
			dr = new DietRecord(record[0], // Date
					record[1], // Name
					record[2], // Nutritional info
					Double.parseDouble(record[3])); // kJ content

		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			dr = new DietRecord();
			setReadErrors();
		}

		records.add(dr);
	}

	/**
	 * Returns an ObservableList of DietRecords whose date property is within
	 * the specified range.
	 * 
	 * @param range
	 *            of record date to include in milliseconds
	 */
	public ObservableList<DietRecord> filterDate(long range) {
		ObservableList<DietRecord> obs = FXCollections.observableArrayList();
		range = System.currentTimeMillis() - range;

		for (DietRecord r : records) {
			if (Utils.dateToMillis(r.getDate()) > range) {
				obs.add(r);
			}
		}

		return obs;
	}
}

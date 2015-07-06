package application.model;

import java.io.File;

/**
 * Model that stores records of type TrainingRecord.
 */
public class TrainingModel extends Model<TrainingRecord> {

	public TrainingModel(File file) {
		super(file);
	}

	/**
	 * @param record
	 *            a .csv record split into a String[], which must have been in
	 *            the format
	 *            "date,category,description,time,distancetravelled,route".
	 */
	@Override
	public void add(String[] record) {
		TrainingRecord tr;

		try {
			tr = new TrainingRecord(record[0], // date
					record[1], // category
					record[2], // description
					record[3], // time
					Double.parseDouble(record[4]), // distance travelled
					record[5]); // route

		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			tr = new TrainingRecord();
			setReadErrors();
		}

		records.add(tr);
	}

}

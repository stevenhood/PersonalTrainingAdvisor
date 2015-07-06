package application.model;

import java.io.File;

/**
 * Model that stores records of type BMIRecord.
 */
public class BMIModel extends Model<BMIRecord> {

	public BMIModel(File file) {
		super(file);
	}

	/**
	 * @param record
	 *            a .csv record split into a String[], which must have been in
	 *            the format "date,weight,height".
	 */
	@Override
	public void add(String[] record) {
		BMIRecord br;

		try {
			br = new BMIRecord(record[0], // Date
					Double.parseDouble(record[1]), // Weight
					Double.parseDouble(record[2])); // Height

		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			br = new BMIRecord();
			setReadErrors();
		}

		records.add(br);
	}

}

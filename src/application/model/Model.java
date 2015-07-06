package application.model;

import java.io.File;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The superclass for all data models. Stores an ArrayList of an arbitrary
 * number of records.
 * 
 * @param <T>
 *            The record type that the Model stores.
 */
public abstract class Model<T> {

	private File file;
	protected ArrayList<T> records;
	private boolean readErrors;

	/**
	 * Creates an empty Model with a file object, ready to be read from.
	 * 
	 * @param file
	 *            .csv file that stores records int the format of type T.
	 */
	public Model(File file) {
		this.file = file;
		records = new ArrayList<T>();
		readErrors = false;
	}

	/**
	 * Get the number of records stored in the ArrayList.
	 */
	public int getSize() {
		return records.size();
	}

	/**
	 * Returns an ObservableList built directly from the ArrayList that can be
	 * used to populate a TableView.
	 * 
	 * @see ObservableList
	 */
	public ObservableList<T> getRecords() {
		return FXCollections.observableArrayList(records);
	}

	/**
	 * Gets a record from the ArrayList at the specified index.
	 * 
	 * @return the record at position index.
	 */
	public T getRecord(int index) {
		if (getSize() < 1) {
			return null;
		} else {
			return records.get(index);
		}
	}

	/**
	 * Get the record at the last position in the ArrayList.
	 * 
	 * @return the record at size - 1.
	 */
	public T getLastRecord() {
		if (getSize() < 1) {
			return null;
		} else {
			return records.get(getSize() - 1);
		}
	}

	/**
	 * Add a record to the ArrayList.
	 */
	public void add(T r) {
		records.add(r);
	}

	/**
	 * Adds a record to the ArrayList by instantiating one from an array of
	 * strings. The implementation of this method influences the way the records
	 * are stored in the .csv file.
	 * 
	 * @param record
	 *            the record as an array of strings, with each index
	 *            corresponding to a field.
	 */
	public abstract void add(String[] record);

	/**
	 * Sets the record at the specified index in the ArrayList.
	 */
	public void setRecord(int index, T record) {
		records.set(index, record);
	}

	/**
	 * Deletes a record at a specified index in the ArrayList.
	 */
	public void delete(int index) {
		records.remove(index);
	}

	/**
	 * Reads records from the file. Each record should be delimited by a new
	 * line. Record fields should be delimited by commas.
	 */
	public void readRecords() {
		if (records.size() > 0) {
			records.clear();
		}

		ArrayList<String[]> str_records = FileIO.readFile(file);

		for (String[] s : str_records) {
			add(s);
		}
	}

	/**
	 * Writes all records stored in the ArrayList to file.
	 */
	public void saveRecords() {
		FileIO.writeFile(file, records);
	}

	/**
	 * Prints all records in the ArrayList (with fields delimited by commas) to
	 * the standard output for debugging purposes.
	 */
	public void printRecords() {
		int size = records.size();
		System.out.println("No. records : " + size);

		for (int i = 0; i < size; i++) {
			System.out.println(i + ". " + records.get(i).toString());
		}
	}

	/**
	 * Sets the field readErrors to true if the add(String[]) method catches one
	 * or more exceptions due to a faulty data file.
	 */
	protected void setReadErrors() {
		readErrors = true;
	}

	/**
	 * Returns true when any problems reading records from the file were
	 * encountered. This may be caused by an IndexOutOfBoundException or a
	 * NumberFormatException.
	 */
	public boolean anyReadErrors() {
		return readErrors;
	}

}

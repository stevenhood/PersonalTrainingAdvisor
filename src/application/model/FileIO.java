package application.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Methods for reading and writing .csv files.
 */
public class FileIO {

	private static BufferedReader br;
	private static BufferedWriter bw;

	/**
	 * Reads data from a .csv file and splits each line into an array of
	 * strings.
	 * 
	 * @param file
	 *            .csv file to read.
	 * @return an ArrayList of records as arrays of strings.
	 */
	public static ArrayList<String[]> readFile(File file) {

		ArrayList<String[]> records = null;

		try {
			br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
			records = new ArrayList<String[]>();
			String[] record;

			String line = br.readLine();

			while (line != null) {
				record = line.split(",");
				records.add(record);
				line = br.readLine();
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
			records = null;
		}

		return records;
	}

	/**
	 * Writes an array of records (each as a string with fields concatenated
	 * together, delimited by commas) to a .csv file. Uses the toString method
	 * (which should be overridden by all Record subclasses) of each record to
	 * convert it into a comma delimited string of fields.
	 * 
	 * @param file
	 *            .csv file to write.
	 * @param records
	 *            an ArrayList of records.
	 */
	public static void writeFile(File file, ArrayList<?> records) {

		try {
			bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

			for (Object obj : records) {
				bw.write(obj.toString());
				bw.newLine();
			}

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package application;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.model.BMIModel;
import application.model.BMIRecord;
import application.model.DietModel;
import application.model.DietRecord;
import application.model.TrainingModel;
import application.model.TrainingRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Arbiter between Model objects and JavaFX view.
 */
public class Controller implements Initializable {

	private static final String ERROR_FAULTYDATAFILE = "Formatting errors/missing fields were encountered when reading .csv file";
	private static final String ERROR_NOOPENFILE = "No file open";

	private static final String ERROR_BMI_REQUIREDFIELDS = "Weight and height are required";
	private static final String ERROR_BMI_NOTDOUBLE = "Weight and height must be decimal values";
	private static final String ERROR_BMI_LESSTHANONE = "Weight and height must be greater than zero";

	private static final String ERROR_TRAINING_REQUIREDFIELDS = "Date, category and time are required";
	private static final String ERROR_TRAINING_NOTDOUBLE = "Distance must be a decimal value";

	private static final String ERROR_DIET_REQUIREDFIELDS = "Date, name and kJ content are required";
	private static final String ERROR_DIET_NOTDOUBLE = "kJ content must be a decimal value";

	private static final String ERROR_NORECORDSELECTED = "No record selected";
	private static final String ERROR_INCORRECTDATEFORMAT = "Date must be in the format YYYY-MM-DD";
	private static final String ERROR_INCORRECTTIMEFORMAT = "Time must be in the format HH:MM:SS";

	private static final String WARNING_UNSAVEDTITLE = "Unsaved changes";
	private static final String WARNING_UNSAVEDMSG = "Do you want to save the changes you made to the open record?";

	private static final String WARNING_DELETETITLE = "Delete Record";
	private static final String WARNING_DELETEMSG = "Are you sure you want to permanently delete this record?";

	private BMIModel bmiModel;
	private BMIRecord openBMIRecord;
	private boolean isBMIEdit;
	private int editedBMIRecordIndex;
	private TableViewSelectionModel<BMIRecord> tblBMISelectionModel;

	private TrainingModel trainingModel;
	private boolean isTrainingEdit;
	private int editedTrainingRecordIndex;
	private TableViewSelectionModel<TrainingRecord> tblTrainingSelectionModel;

	private DietModel dietModel;
	private boolean isDietEdit;
	private boolean isPeriodSet;
	private int editedDietRecordIndex;
	private TableViewSelectionModel<DietRecord> tblDietSelectionModel;

	@FXML
	private Tab tabBMI, tabTraining, tabDiet;

	// BMI tab form controls
	@FXML
	private TextField txtWeight, txtHeight;
	@FXML
	private Label lblCalculatedBMI, lblCalculatedBMIDifference, lblPreviousBMI, lblPreviousWeight;
	@FXML
	private Rectangle rctBMI;
	@FXML
	private Tooltip ttpCalculatedBMI;
	@FXML
	private TableView<BMIRecord> tblBMI;
	@FXML
	private TableColumn<BMIRecord, String> tbcBMIDate, tbcWeight, tbcHeight, tbcBMI;

	// Training tab form controls
	@FXML
	private TextField txtTrainingDate, txtTime, txtDistance, txtRoute;
	@FXML
	private TextArea txtDescription;
	@FXML
	private ComboBox<String> cboCategory;
	@FXML
	private TableView<TrainingRecord> tblTraining;
	@FXML
	private TableColumn<TrainingRecord, String> tbcTrainingDate, tbcCategory, tbcDescription, tbcTime, tbcDistance, tbcRoute;

	// Diet tab form controls
	@FXML
	private TextField txtDietDate, txtName, txtKjContent;
	@FXML
	private Label lblKjTarget;
	@FXML
	private TextArea txtNutritionalInfo;
	@FXML
	private ComboBox<String> cboPeriod;
	@FXML
	private TableView<DietRecord> tblDiet;
	@FXML
	private TableColumn<DietRecord, String> tbcDietDate, tbcName, tbcKjContent, tbcNutritionalInfo;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		isBMIEdit = false;
		isTrainingEdit = false;
		isDietEdit = false;
		isPeriodSet = false;

		tblBMISelectionModel = tblBMI.getSelectionModel();
		tbcBMIDate.setCellValueFactory(new PropertyValueFactory<BMIRecord, String>("date"));
		tbcWeight.setCellValueFactory(new PropertyValueFactory<BMIRecord, String>("weight"));
		tbcHeight.setCellValueFactory(new PropertyValueFactory<BMIRecord, String>("height"));
		tbcBMI.setCellValueFactory(new PropertyValueFactory<BMIRecord, String>("BMI"));

		tblTrainingSelectionModel = tblTraining.getSelectionModel();
		tbcTrainingDate.setCellValueFactory(new PropertyValueFactory<TrainingRecord, String>("date"));
		tbcCategory.setCellValueFactory(new PropertyValueFactory<TrainingRecord, String>("category"));
		tbcDescription.setCellValueFactory(new PropertyValueFactory<TrainingRecord, String>("description"));
		tbcTime.setCellValueFactory(new PropertyValueFactory<TrainingRecord, String>("time"));
		tbcDistance.setCellValueFactory(new PropertyValueFactory<TrainingRecord, String>("distance"));
		tbcRoute.setCellValueFactory(new PropertyValueFactory<TrainingRecord, String>("route"));

		tblDietSelectionModel = tblDiet.getSelectionModel();
		tbcDietDate.setCellValueFactory(new PropertyValueFactory<DietRecord, String>("date"));
		tbcName.setCellValueFactory(new PropertyValueFactory<DietRecord, String>("name"));
		tbcKjContent.setCellValueFactory(new PropertyValueFactory<DietRecord, String>("kjContent"));
		tbcNutritionalInfo.setCellValueFactory(new PropertyValueFactory<DietRecord, String>("nutrition"));
	}

	private void loadBMITable() {
		if (bmiModel != null) {
			tblBMI.setItems(bmiModel.getRecords());
		}
	}

	private void loadTrainingTable() {
		if (trainingModel != null) {
			tblTraining.setItems(trainingModel.getRecords());
		}
	}

	private void loadDietTable() {
		if (dietModel != null) {
			tblDiet.setItems(dietModel.getRecords());
		}
	}

	/**
	 * Loads a BMIRecord into the BMI tab.
	 * 
	 * @param br
	 *            record to load
	 * @param ready
	 *            if true, the weight is not loaded.
	 */
	private void loadBMIRecord(BMIRecord br, boolean ready) {
		// No existing records when attempting to load previous
		if (br == null) {
			return;
		}

		// Boolean specifies whether to load the whole record for editing
		// or just the weight and height as a template for a new record
		if (ready) {
			txtWeight.clear();
		} else {
			txtWeight.setText(String.valueOf(br.getWeight()));
		}

		// Populate lblWeight and txtHeight with weight and height values from
		// last record
		txtHeight.setText(String.valueOf(br.getHeight()));
		lblPreviousWeight.setText(String.valueOf(br.getWeight()));
		lblPreviousBMI.setText(String.valueOf(br.getBMI()));
	}

	private void loadTrainingRecord(TrainingRecord tr) {
		txtTrainingDate.setText(tr.getDate());
		cboCategory.setValue(tr.getCategory());
		txtDescription.setText(tr.getDescription());
		txtTime.setText(String.valueOf(tr.getTime()));
		txtDistance.setText(String.valueOf(tr.getDistance()));
		txtRoute.setText(tr.getRoute());
	}

	private void loadDietRecord(DietRecord dr) {
		txtDietDate.setText(dr.getDate());
		txtName.setText(dr.getName());
		txtNutritionalInfo.setText(dr.getNutrition());
		txtKjContent.setText(String.valueOf(dr.getKjContent()));
	}

	/**
	 * Opens a FileChooser for the selection of a comma separated values (.csv)
	 * file.
	 * 
	 * @param title
	 *            to be displayed on the title bar of the dialog.
	 * @return the selected .csv file
	 */
	private File openFileDialog(String title) {
		FileChooser fc = new FileChooser();
		fc.setTitle(title);
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Comma separated values", "*.csv"));
		return fc.showOpenDialog(new Stage());
	}

	/**
	 * Opens a FileChooser and attempts to call readRecords on that file, and
	 * load the BMI tab and table. The .csv file must be in the format specified
	 * by the add(String[]) method in BMIModel.
	 */
	@FXML
	public void openBMIFile(ActionEvent ae) {
		File f = openFileDialog("Open BMI file");
		if (f == null) {
			return;
		}
		bmiModel = new BMIModel(f);
		bmiModel.readRecords();
		loadBMITable();
		loadBMIRecord(bmiModel.getLastRecord(), true);

		if (bmiModel.anyReadErrors()) {
			messageDialog(ERROR_FAULTYDATAFILE);
			bmiModel = null;
		}
	}

	/**
	 * Opens a FileChooser and attempts to call readRecords on that file, and
	 * load the Training tab and table. The .csv file must be in the format
	 * specified by the add(String[]) method in TrainingModel.
	 */
	@FXML
	public void openTrainingFile(ActionEvent ae) {
		File f = openFileDialog("Open Training file");
		if (f == null) {
			return;
		}
		trainingModel = new TrainingModel(f);
		trainingModel.readRecords();
		loadTrainingTable();

		if (trainingModel.anyReadErrors()) {
			messageDialog(ERROR_FAULTYDATAFILE);
			trainingModel = null;
		}
	}

	/**
	 * Opens a FileChooser and attempts to call readRecords on that file, and
	 * load the Diet tab and table. The .csv file must be in the format
	 * specified by the add(String[]) method in DietModel.
	 */
	@FXML
	public void openDietFile(ActionEvent ae) {
		File f = openFileDialog("Open Diet file");
		if (f == null) {
			return;
		}
		dietModel = new DietModel(f);
		dietModel.readRecords();
		loadDietTable();

		if (dietModel.anyReadErrors()) {
			messageDialog(ERROR_FAULTYDATAFILE);
			dietModel = null;
		}
	}

	/**
	 * Returns true if all required data in the BMI tab is present and all data
	 * is validated and verified as correct. Displays an error message and
	 * returns false otherwise.
	 */
	private boolean validateBMITab() {
		String weight = txtWeight.getText().trim();
		String height = txtHeight.getText().trim();

		if (weight.length() < 1 || height.length() < 1) {
			messageDialog(ERROR_BMI_REQUIREDFIELDS);
			return false;
		} else if (!Utils.isDouble(weight) || !Utils.isDouble(height)) {
			// Values must be doubles
			messageDialog(ERROR_BMI_NOTDOUBLE);
			return false;
		} else if (Double.parseDouble(weight) <= 0 || Double.parseDouble(height) <= 0) {
			// Values must be greater than zero
			messageDialog(ERROR_BMI_LESSTHANONE);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns true if all required data in the Training tab is present and all
	 * data is validated and verified as correct. Displays an error message and
	 * returns false otherwise.
	 */
	private boolean validateTrainingTab() {
		String date = txtTrainingDate.getText().trim();
		String category = cboCategory.getValue().trim();
		String time = txtTime.getText().trim();
		String distance = txtDistance.getText().trim();

		if (date.length() < 1 || category == null || category.length() < 1 || time.length() < 1) {
			messageDialog(ERROR_TRAINING_REQUIREDFIELDS);
			return false;
		} else if (!Utils.isDate(date)) {
			// Date is not in the format YYYY-MM-DD
			messageDialog(ERROR_INCORRECTDATEFORMAT);
			return false;
		} else if (!Utils.isTime(time)) {
			// Time is not in the format HH:MM:SS
			messageDialog(ERROR_INCORRECTTIMEFORMAT);
			return false;
		} else if (distance.length() > 0 && !Utils.isDouble(distance)) {
			// Distance must be a double
			messageDialog(ERROR_TRAINING_NOTDOUBLE);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns true if all required data in the Diet tab is present and all data
	 * is validated and verified as correct. Displays an error message and
	 * returns false otherwise.
	 */
	private boolean validateDietTab() {
		String date = txtDietDate.getText().trim();
		String name = txtName.getText().trim();
		String kJContent = txtKjContent.getText().trim();

		if (date.length() < 1 || name.length() < 1 || kJContent.length() < 1) {
			messageDialog(ERROR_DIET_REQUIREDFIELDS);
			return false;
		} else if (!Utils.isDate(date)) {
			// Date is not in the format YYYY-MM-DD
			messageDialog(ERROR_INCORRECTDATEFORMAT);
			return false;
		} else if (!Utils.isDouble(kJContent)) {
			// kJ content must be a double
			messageDialog(ERROR_DIET_NOTDOUBLE);
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Calculates the BMI and difference with previous using data from the BMI
	 * tab. Sets the rectangle and tooltip to the appropriate colour/text
	 * depending on the BMI's classification.
	 */
	@FXML
	public void calculateBMI(ActionEvent ae) {

		if (!validateBMITab()) {
			return;
		}

		double previousBMI;
		if (bmiModel == null || bmiModel.getSize() < 1) {
			previousBMI = 0.0;
		} else {
			previousBMI = bmiModel.getLastRecord().getRawBMI();
		}

		double weight = Double.parseDouble(txtWeight.getText().trim());
		double height = Double.parseDouble(txtHeight.getText().trim());
		// Calculate BMI and difference with BMI of last record, if exists
		double currentBMI = new BMIRecord("", weight, height).getRawBMI();
		double difference = currentBMI - previousBMI;

		// Change colour of rectangle to reflect on health range of BMI
		// Set tooltip to give appropriate information about BMI classification
		if (currentBMI <= 16) {
			// Severely under weight
			rctBMI.setFill(Color.RED);
			ttpCalculatedBMI.setText("Severely underweight (less than 16)");

		} else if (currentBMI > 16 && currentBMI <= 18.5) {
			// Under weight
			rctBMI.setFill(Color.YELLOW);
			ttpCalculatedBMI.setText("Underweight (between 16 and 18.5)");

		} else if (currentBMI > 18.5 && currentBMI <= 25) {
			// Normal weight
			rctBMI.setFill(Color.LIME);
			ttpCalculatedBMI.setText("Healthy (between 18.5 and 25)");

		} else if (currentBMI > 25 && currentBMI <= 30) {
			// Overweight
			rctBMI.setFill(Color.YELLOW);
			ttpCalculatedBMI.setText("Overweight (between 25 and 30)");

		} else {
			// currentBMI > 30
			// Severely overweight
			rctBMI.setFill(Color.RED);
			ttpCalculatedBMI.setText("Severely overweight (greater than 30)");
		}

		String changeSign = "";
		if (currentBMI > previousBMI) {
			changeSign = "+";
		}

		// Round to 2 d.p.
		currentBMI = Utils.roundDouble(currentBMI, 2);
		difference = Utils.roundDouble(difference, 2);
		lblCalculatedBMI.setText(String.valueOf(currentBMI));
		lblCalculatedBMIDifference.setText(changeSign + String.valueOf(difference));
	}

	/**
	 * Populates the Diet table with records within a date range specified in
	 * cboPeriod.
	 */
	@FXML
	public void setPeriod(ActionEvent ae) {
		if (dietModel == null) {
			return;
		}

		String period = cboPeriod.getValue();
		long range;

		switch (period) {
		case "All":
			loadDietTable();
			return;
		case "Year":
			range = Utils.YEAR;
			break;
		case "Month":
			range = Utils.MONTH;
			break;
		case "Week":
			range = Utils.WEEK;
			break;
		case "Day":
			range = Utils.DAY;
			break;
		default:
			range = System.currentTimeMillis();
			break;
		}

		isPeriodSet = true;
		tblDiet.setItems(dietModel.filterDate(range));
	}

	/**
	 * Saves the data on the currently selected tab.
	 */
	@FXML
	public void saveSelectedTab() {
		if (tabBMI.isSelected()) {
			saveBMITab();

		} else if (tabTraining.isSelected()) {
			saveTrainingTab();

		} else if (tabDiet.isSelected()) {
			saveDietTab();
		}
	}

	private void saveBMITab() {
		if (bmiModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		if (!validateBMITab()) {
			return;
		}

		BMIRecord br;
		if (isBMIEdit) {
			// Currently editing an existing record
			br = new BMIRecord(openBMIRecord.getDate(), Double.parseDouble(txtWeight.getText().trim()), Double.parseDouble(txtHeight.getText().trim()));
			bmiModel.setRecord(editedBMIRecordIndex, br);

		} else {
			// A new record
			br = new BMIRecord(new Date(System.currentTimeMillis()).toString(), Double.parseDouble(txtWeight.getText().trim()), Double.parseDouble(txtHeight.getText().trim()));
			bmiModel.add(br);
		}

		bmiModel.saveRecords();
		isBMIEdit = false;
		// Load part of the last record to have the previous height, weight and
		// BMI ready for a new record
		loadBMIRecord(bmiModel.getLastRecord(), true);
		loadBMITable();
	}

	private void saveTrainingTab() {
		if (trainingModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		if (!validateTrainingTab()) {
			return;
		}

		TrainingRecord tr = new TrainingRecord(txtTrainingDate.getText().trim(), cboCategory.getValue().trim(), txtDescription.getText().trim(), txtTime.getText().trim());
		String distance = txtDistance.getText().trim();
		String route = txtRoute.getText().trim();
		if (distance.length() > 0) {
			tr.setDistance(Double.parseDouble(distance));
		}
		if (route.length() > 0) {
			tr.setRoute(route);
		}

		if (isTrainingEdit) {
			trainingModel.setRecord(editedTrainingRecordIndex, tr);
		} else {
			trainingModel.add(tr);
		}

		trainingModel.saveRecords();
		clearTrainingTab();
		loadTrainingTable();
	}

	private void saveDietTab() {
		if (dietModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		if (!validateDietTab()) {
			return;
		}

		DietRecord dr = new DietRecord(txtDietDate.getText().trim(), txtName.getText().trim(), txtNutritionalInfo.getText().trim(), Double.parseDouble(txtKjContent.getText().trim()));

		if (isDietEdit) {
			dietModel.setRecord(editedDietRecordIndex, dr);
		} else {
			dietModel.add(dr);
		}

		dietModel.saveRecords();
		clearDietTab();
		loadDietTable();
	}

	/**
	 * Loads the selected record in the tab's table for editing. Shows a
	 * confirmation dialog if the currently selected tab has an unsaved record
	 * in edit to save, discard changes or cancel.
	 */
	@FXML
	public void editSelectedRecord(ActionEvent ae) {
		if ((tabBMI.isSelected() && isBMIEdit) || (tabTraining.isSelected() && isTrainingEdit) || (tabDiet.isSelected() && isDietEdit)) {
			int choice = confirmDialog(WARNING_UNSAVEDMSG, WARNING_UNSAVEDTITLE);

			switch (choice) {
			case JOptionPane.YES_OPTION:
				saveSelectedTab();
				break;

			case JOptionPane.NO_OPTION:
				// Discard unsaved changes
				break;

			default:
				// Do nothing to form
				return;
			}
		}

		if (tabBMI.isSelected()) {
			editSelectedBMIRecord();

		} else if (tabTraining.isSelected()) {
			editSelectedTrainingRecord();

		} else if (tabDiet.isSelected()) {
			editSelectedDietRecord();
		}
	}

	private void editSelectedBMIRecord() {
		if (bmiModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		int index = tblBMISelectionModel.getSelectedIndex();

		if (index < 0) {
			messageDialog(ERROR_NORECORDSELECTED);
			return;
		} else {
			isBMIEdit = true;
			openBMIRecord = bmiModel.getRecord(index);
			editedBMIRecordIndex = index;
			loadBMIRecord(openBMIRecord, false);
		}
	}

	private void editSelectedTrainingRecord() {
		if (trainingModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		int index = tblTrainingSelectionModel.getSelectedIndex();

		if (index < 0) {
			messageDialog(ERROR_NORECORDSELECTED);
			return;
		} else {
			isTrainingEdit = true;
			editedTrainingRecordIndex = index;
			loadTrainingRecord(trainingModel.getRecord(index));
		}
	}

	private void editSelectedDietRecord() {
		if (dietModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		if (isPeriodSet) {
			loadDietTable();
			isPeriodSet = false;
		}

		int index = tblDietSelectionModel.getSelectedIndex();

		if (index < 0) {
			messageDialog(ERROR_NORECORDSELECTED);
			return;
		} else {
			isDietEdit = true;
			editedDietRecordIndex = index;
			loadDietRecord(dietModel.getRecord(index));
		}
	}

	/**
	 * Deletes a record selected in the tab's table from the respective model.
	 * Shows a confirmation dialog if the currently selected tab has an unsaved
	 * record in edit to save, discard changes or cancel.
	 */
	@FXML
	public void deleteSelectedRecord(ActionEvent ae) {
		if ((tabBMI.isSelected() && isBMIEdit) || (tabTraining.isSelected() && isTrainingEdit) || (tabDiet.isSelected() && isDietEdit)) {
			int choice = confirmDialog(WARNING_UNSAVEDMSG, WARNING_UNSAVEDTITLE);

			switch (choice) {
			case JOptionPane.YES_OPTION:
				saveSelectedTab();
				break;

			case JOptionPane.NO_OPTION:
				if (tabBMI.isSelected()) {
					clearBMITab();

				} else if (tabTraining.isSelected()) {
					clearTrainingTab();

				} else if (tabDiet.isSelected()) {
					clearDietTab();
				}
				break;

			default:
				return;
			}
		}

		int choice = confirmDialog(WARNING_DELETEMSG, WARNING_DELETETITLE);

		switch (choice) {
		case JOptionPane.YES_OPTION:
			break;

		default:
			return;
		}

		if (tabBMI.isSelected()) {
			deleteSelectedBMIRecord();

		} else if (tabTraining.isSelected()) {
			deleteSelectedTrainingRecord();

		} else if (tabDiet.isSelected()) {
			deleteSelectedDietRecord();
		}
	}

	private void deleteSelectedBMIRecord() {
		if (bmiModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		int index = tblBMISelectionModel.getSelectedIndex();

		if (index < 0) {
			messageDialog(ERROR_NORECORDSELECTED);
			return;
		} else {
			bmiModel.delete(index);
			bmiModel.saveRecords();
			loadBMITable();
		}
	}

	private void deleteSelectedTrainingRecord() {
		if (trainingModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		int index = tblTrainingSelectionModel.getSelectedIndex();

		if (index < 0) {
			messageDialog(ERROR_NORECORDSELECTED);
			return;
		} else {
			trainingModel.delete(index);
			trainingModel.saveRecords();
			loadTrainingTable();
		}
	}

	private void deleteSelectedDietRecord() {
		if (dietModel == null) {
			messageDialog(ERROR_NOOPENFILE);
			return;
		}

		if (isPeriodSet) {
			loadDietTable();
			isPeriodSet = false;
		}

		int index = tblDietSelectionModel.getSelectedIndex();

		if (index < 0) {
			messageDialog(ERROR_NORECORDSELECTED);
			return;
		} else {
			dietModel.delete(index);
			dietModel.saveRecords();
			loadDietTable();
		}
	}

	/**
	 * Clears the selected tab of data. Shows a confirmation dialog if the
	 * currently selected tab has an unsaved record in edit to save, discard
	 * changes or cancel.
	 */
	@FXML
	public void clearSelectedTab() {
		if ((tabBMI.isSelected() && isBMIEdit) || (tabTraining.isSelected() && isTrainingEdit) || (tabDiet.isSelected() && isDietEdit)) {
			int choice = confirmDialog(WARNING_UNSAVEDMSG, WARNING_UNSAVEDTITLE);

			switch (choice) {
			case JOptionPane.YES_OPTION:
				saveSelectedTab();
				break;

			case JOptionPane.NO_OPTION:
				// Discard unsaved changes
				break;

			default:
				// Do nothing to form
				return;
			}
		}

		if (tabBMI.isSelected()) {
			clearBMITab();

		} else if (tabTraining.isSelected()) {
			clearTrainingTab();

		} else if (tabDiet.isSelected()) {
			clearDietTab();
		}
	}

	private void clearBMITab() {
		txtWeight.clear();
		txtHeight.clear();
		lblPreviousWeight.setText("--");
		lblPreviousBMI.setText("--");
		lblCalculatedBMI.setText("--");
		lblCalculatedBMIDifference.setText("");
		rctBMI.setFill(Color.WHITE);
		ttpCalculatedBMI.setText("");

		// Load the previous record, if one exists, for the convenient creation
		// of new records
		if (bmiModel != null) {
			loadBMIRecord(bmiModel.getLastRecord(), true);
		}

		isBMIEdit = false;
		openBMIRecord = null;
	}

	private void clearTrainingTab() {
		isTrainingEdit = false;
		txtTrainingDate.clear();
		cboCategory.setValue("");
		txtDescription.clear();
		txtTime.clear();
		txtDistance.clear();
		txtRoute.clear();
	}

	private void clearDietTab() {
		isDietEdit = false;
		cboPeriod.setValue("");
		txtDietDate.clear();
		cboCategory.setValue("");
		txtName.clear();
		txtNutritionalInfo.clear();
		txtKjContent.clear();
		loadDietTable();
	}

	/**
	 * Displays a message dialog containing program information. This consists
	 * of name, version, author, etc.
	 */
	@FXML
	public void openAboutDialog(ActionEvent ae) {
		JOptionPane.showMessageDialog(null, "Personal Training Advisor v1.0\nby Steven Hood", "About", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Displays an error message dialog with an OK button
	 * 
	 * @param message
	 *            to be displayed in body
	 */
	public static void messageDialog(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Displays a confirm dialog with Yes, No and Cancel buttons. Returns an
	 * integer to indicate which button was pressed.
	 * 
	 * @param message
	 *            to be displayed in body
	 * @param title
	 *            to be displayed in the title bar
	 */
	public static int confirmDialog(String message, String title) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
	}

}

# Personal Training Advisor

## Setting up data files

The application uses comma separated value files (.csv).
Setting up the application for a new user is as simple as
creating a blank text file named with the .csv extension.

If you wish to import data, it must be in the format as follows:
-Each record is delimited by a newline character \n
-Each field is delimited by a comma (Type:description):

BMI:		"Text:date, Decimal:weight, Decimal:height"
Training:	"Text:date, Text:category, Text:description, Text:time, Decimal:distancetravelled , Text:route"
Diet:		"Text:date, Text:name, Text:nutritionalinfo, Decimal:kjcontent"

If the .csv file is not in the appropriate format for the section you open it for,
you will be prompted with an error message.

## Known bugs

* Viewing records by their age in the diet tab using the period
drop-down menu does not accurately filter records by date.
* When records in the diet table have been filtered by date,
clicking on a record then selecting edit or delete will return
an error "No record selected". This is because the table must
be refilled with all data before any can be selected, so therefore
the process of selecting a record and clicking edit/delete must be
repeated twice.
* The above error bug also occurs in any tab:
clicking to edit/delete another record whilst a record is currently
being edited will display a prompt to save or discard the changes.
If the user chooses the option to save, the selected record will
become unselected and the user will have to select it and click
the command they chose before again.

## Testing

JUnit4 unit tests have been devised for each record subclass and the data model superclass in the tests directory.

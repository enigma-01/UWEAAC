package com.example.a324606177.waterlooengineeringadmissioncalculator;

import android.app.Activity;
import android.os.Bundle;

// Import our widget classes
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

// Import Event handling classes for Button and EditText widgets
import android.view.View.OnClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

// Import SharedPreferences and its Editor class
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

// Import the Toast class for pop-up messages that will be displayed to the user
import android.widget.Toast;

/*
    Name: Kevin Pierce
    Date: May 29, 2019
    Description: This class represents the main activity for my application, the University of
                 Waterloo Engineering Admissions Chance Calculator (UWEACC for short). Relying
                 upon ACTUAL data provided by the UWaterloo, my application serves to approximate
                 one's chances of their admission into Waterloo's prestigious Engineering program
                 (as to alleviate some of the stress associated with waiting for acceptances). The
                 user may enter their marks, their perceived AIF/Interview scores, whether or not they
                 are in IB, and also their chosen program and the high school they currently attend.

                 Although not totally accurate, it relies upon the REAL-WORLD data for average
                 adjustment factors based on high schools (Based on 2018**), the AIF and Interview
                 scores, as well as an International Baccalaureate adjustment factor (This last one
                 was my own inclusion). This class extends the Activity class.

*/
public class WaterlooAdmissionCalculatorActivity extends Activity {

    // Declare private instance variables for our EditText widgets
    private EditText englishMarkEditText;
    private EditText functionsMarkEditText;
    private EditText calculusMarkEditText;
    private EditText physicsMarkEditText;
    private EditText chemistryMarkEditText;
    private EditText otherCourseMarkEditText;
    private EditText aifScoreEditText;
    private EditText interviewScoreEditText;

    // Declare private instance variables for our Button, TextView, Spinner, and Switch widgets
    private Button clearButton;
    private Button calculateButton;
    private TextView chanceTextView;
    private Spinner programSpinner;
    private Spinner schoolSpinner;
    private Switch ibStatusSwitch;

    // Declare array representing all EditText objects
    private EditText[] editTextArray;

    // Declare reference for a SharedPreferences object
    private SharedPreferences savedPrefs;

    /*
    This method is integral to the Activity Lifestyle, and is called upon the initial launch of the
    application (When the user taps on the icon for the first time). It is responsible for much
    of the initialization of the program (Where we instantiate much of the widgets as well as their
    event handlers). This method accepts the most recent instance state of the application as a
    parameter, and returns nothing.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterloo_admission_calculator);

        // Initialize the EditText Widget objects
        englishMarkEditText = findViewById(R.id.englishMarkEditText);
        functionsMarkEditText = findViewById(R.id.functionsMarkEditText);
        calculusMarkEditText = findViewById(R.id.calculusMarkEditText);
        physicsMarkEditText = findViewById(R.id.physicsMarkEditText);
        chemistryMarkEditText = findViewById(R.id.chemistryMarkEditText);
        otherCourseMarkEditText = findViewById(R.id.otherCourseMarkEditText);
        aifScoreEditText = findViewById(R.id.aifScoreEditText);
        interviewScoreEditText = findViewById(R.id.interviewScoreEditText);

        // Initialize our Button, TextView, Spinner, and Switch widgets
        clearButton = findViewById(R.id.clearButton);
        calculateButton = findViewById(R.id.calculateButton);
        chanceTextView = findViewById(R.id.chanceTextView);
        programSpinner = findViewById(R.id.programSpinner);
        schoolSpinner = findViewById(R.id.schoolSpinner);
        ibStatusSwitch = findViewById(R.id.ibStatusSwitch);

        OnEditorActionListener textEventHandler = new TextFieldListener();

        // Set the event listener for our EditText widgets
        englishMarkEditText.setOnEditorActionListener(textEventHandler);
        functionsMarkEditText.setOnEditorActionListener(textEventHandler);
        calculusMarkEditText.setOnEditorActionListener(textEventHandler);
        physicsMarkEditText.setOnEditorActionListener(textEventHandler);
        chemistryMarkEditText.setOnEditorActionListener(textEventHandler);
        otherCourseMarkEditText.setOnEditorActionListener(textEventHandler);
        aifScoreEditText.setOnEditorActionListener(textEventHandler);
        interviewScoreEditText.setOnEditorActionListener(textEventHandler);

        // Set the event listeners for our button widgets
        OnClickListener buttonEventListener = new ButtonListener();
        clearButton.setOnClickListener(buttonEventListener);
        calculateButton.setOnClickListener(buttonEventListener);

        // Create an ArrayAdapter for the spinner objects (programSpinner then schoolSpinner)
        ArrayAdapter<CharSequence> programSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.programs_array, android.R.layout.simple_spinner_item);
        programSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        programSpinner.setAdapter(programSpinnerAdapter);

        ArrayAdapter<CharSequence> schoolSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.schools_array, android.R.layout.simple_spinner_item);
        schoolSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolSpinner.setAdapter(schoolSpinnerAdapter);

        // Instantiate an array containing all EditText objects
        editTextArray = new EditText[]{englishMarkEditText, functionsMarkEditText, calculusMarkEditText,
                                    physicsMarkEditText, chemistryMarkEditText, otherCourseMarkEditText,
                                    aifScoreEditText, interviewScoreEditText};

        // Get the SharedPreferences object
        savedPrefs = getSharedPreferences( "AdmissionCalculatorPrefs", MODE_PRIVATE );
    }

    /*
    This method is integral to the Activity Lifestyle, as it saves the activity's instance variables
    (Namely the marks, AIF / Interview score, IB status, admission chance, school and program). This
    method retains the values inputted by the user despite actions such as closing the app. It
    accepts no parameters and returns nothing.
    */
    @Override
    protected void onPause() {

        // Save instance variables pertaining to marks, AIF/Interview scores, and admission chance
        Editor prefsEditor = savedPrefs.edit();
        prefsEditor.putString("englishMark", englishMarkEditText.getText().toString());
        prefsEditor.putString("functionsMark", functionsMarkEditText.getText().toString());
        prefsEditor.putString("calculusMark", calculusMarkEditText.getText().toString());
        prefsEditor.putString("physicsMark", physicsMarkEditText.getText().toString());
        prefsEditor.putString("chemistryMark", chemistryMarkEditText.getText().toString());
        prefsEditor.putString("otherCourseMark", otherCourseMarkEditText.getText().toString());
        prefsEditor.putString("aifScore", aifScoreEditText.getText().toString());
        prefsEditor.putString("interviewScore", interviewScoreEditText.getText().toString());
        prefsEditor.putString("admissionChance", chanceTextView.getText().toString());

        // Save instance variables pertaining to the IB status and chosen schools/programs
        prefsEditor.putBoolean("ibStatus", ibStatusSwitch.isChecked());
        prefsEditor.putInt("selectedProgram", programSpinner.getSelectedItemPosition());
        prefsEditor.putInt("selectedSchool", schoolSpinner.getSelectedItemPosition());

        prefsEditor.commit();

        // Calling the parent onPause() must be done LAST
        super.onPause();
    }

    /*
    This method is integral to our applicant's Activity Lifestyle. It is responsible for restoring
    all instance variables (Marks, AIF/Interview score, IB status, admission chance, school and program)
    to the values entered by the user once the activity regains focus. It accepts no parameters and returns nothing.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Load and set the instance variables for the applicant's marks back (The default
        // strings are simply empty)
        englishMarkEditText.setText(savedPrefs.getString("englishMark", ""));
        functionsMarkEditText.setText(savedPrefs.getString("functionsMark", ""));
        calculusMarkEditText.setText(savedPrefs.getString("calculusMark", ""));
        physicsMarkEditText.setText(savedPrefs.getString("physicsMark", ""));
        chemistryMarkEditText.setText(savedPrefs.getString("chemistryMark", ""));
        otherCourseMarkEditText.setText(savedPrefs.getString("otherCourseMark", ""));
        aifScoreEditText.setText(savedPrefs.getString("aifScore", ""));
        interviewScoreEditText.setText(savedPrefs.getString("interviewScore", ""));
        chanceTextView.setText(savedPrefs.getString("admissionChance", "0%"));

        // Load and set the instance variables for the applicant's chosen program, school, and
        // whether or not they are in the IB program
        ibStatusSwitch.setChecked(savedPrefs.getBoolean("ibStatus", false));
        programSpinner.setSelection(savedPrefs.getInt("selectedProgram",0));
        schoolSpinner.setSelection(savedPrefs.getInt("selectedSchool", 0));
    }

    /*
    This class is responsible for handling all the events which pertain to our EditText widgets. It
    implements the android.widget.TextView.OnEditorActionListener class, and contains the method
    onEditorAction, which allows for events to be handled when interaction with EditText widgets
    occurs.
     */
    class TextFieldListener implements OnEditorActionListener {
        @Override

        /*
        This method is called whenever an action on an EditText widget is performed. It accepts the
        EditText widget that was interacted with (as an object), an integer acting as an identifier
        of the specific action performed, and the event itself (as an object) as parameters. It returns
        a boolean value that represents whether or not the soft keyboard should remain open; false
        if no, true if yes.
         */
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            // ACTION_DONE occurs if the user CLOSES the keyboard
            // ACTION_NEXT occurs if the user hits the "enter" key and moves onto the next text field
            // Also ensure that the user did NOT just simply hit the enter key (Prevents crashing)
            if ((actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) &&
                    !v.getText().toString().isEmpty()) {

                // Check if the EditText widget is the AIF Score text field
                if (v.getId() == R.id.aifScoreEditText) {

                    // If the user attempts to enter a score ABOVE 5 for their AIF Score
                    // Set 1 as the default value (It is impossible to score higher than 5)
                    if (Integer.parseInt(aifScoreEditText.getText().toString()) > 5) {
                        aifScoreEditText.setText("1");

                        Toast.makeText(WaterlooAdmissionCalculatorActivity.this, "Attempt" +
                                " to set invalid score for AIF; set to default of 1",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                // Check if the EditText widget is the Interview Score text field
                else if (v.getId() == R.id.interviewScoreEditText) {

                    // If the user attempts to enter a score above 3 for their Interview,
                    // set to 0 as the default value (It is impossible to score higher than 3)
                    if ((Integer.parseInt(interviewScoreEditText.getText().toString())) > 3) {
                        interviewScoreEditText.setText("0");

                        // Display warning message to user
                        Toast.makeText(WaterlooAdmissionCalculatorActivity.this, "Attempt" +
                                        " to set invalid score for Interview; set to default of 0",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                // Because the user has not edited the AIF or Interview score fields, it is assumed
                // that they edited one of the course mark fields; ensure that the user has not
                // entered a value greater than 100 (It is impossible to get over 100% in a course)
                else if (Double.parseDouble(v.getText().toString()) > 100.0) {
                    v.setText(String.format("%.1f", 100.0));

                    // Warn the user with a pop-up message
                    Toast.makeText(WaterlooAdmissionCalculatorActivity.this, "Attempt to" +
                            " set invalid mark; set to default mark of 100%", Toast.LENGTH_SHORT).show();

                }
            }
            // Close the soft keyboard at the end
            return false;
        }
    }

    /*
    This class is responsible for handling all the events which pertain to our Button widgets. It
    implements the android.widget.Button.OnClickListener class, and contains the method
    onClick, which allows for events to be handled when interaction with Button widgets
    occurs.
     */
    class ButtonListener implements OnClickListener {
        @Override

        /*
        This method is called whenever a Button widget is pressed. It accepts the Button that was
        clicked as a parameter (as an object), and returns nothing. Using the getId() method, this
        method carries out different functions based on the button that was pressed (Calculate or
        clear).
         */
        public void onClick(View v) {

            // If the user presses the calculate button, calculate their admission chance
            if (v.getId() == R.id.calculateButton) {
                calculateAdmissionChanceAndDisplay();
            }

            // This else-if block is executed if the user presses the clear button, restoring all
            // values and selected choice to their default values
            else if (v.getId() == R.id.clearButton) {

                // Pop-up message displayed to user
                Toast.makeText(WaterlooAdmissionCalculatorActivity.this,"Cleared!",
                        Toast.LENGTH_SHORT).show();

                // Clear the EditText widgets for each mark, aif, and interview scores
                englishMarkEditText.setText("");
                functionsMarkEditText.setText("");
                calculusMarkEditText.setText("");
                physicsMarkEditText.setText("");
                chemistryMarkEditText.setText("");
                otherCourseMarkEditText.setText("");
                aifScoreEditText.setText("");
                interviewScoreEditText.setText("");

                // Reset the program and course spinners to their default values
                programSpinner.setSelection(0);
                schoolSpinner.setSelection(0);

                // Set the IB Switch to its default value of OFF
                ibStatusSwitch.setChecked(false);

                // Set the admission chance label back to default of 0%
                chanceTextView.setText("0%");
            }
        }
    }

    /*
    This accessor method is responsible for obtaining the user's selected school choice, and hence
    the adjustment factor that must be applied to their average. It accepts no parameters and returns
    a double value representing the adjustment factor of the school selected by the user in the spinner
    widget. Each school corresponds to a different numerical position in the spinner.
     */
    private double getSchoolAdjustmentFactor() {
        int selectedSchool = schoolSpinner.getSelectedItemPosition();

        // Dr. Norman Bethune CI adjustment factor
        if (selectedSchool == 0)
            return 10.4;

            // Sir John A Macdonald CI adjustment factor
        else if (selectedSchool == 1)
            return 10.9;

            // Don Mills CI adjustment factor
        else if (selectedSchool == 2)
            return 10.3;

            // Marc Garneau CI adjustment factor
        else if (selectedSchool == 3)
            return 12.8;

            // Grimsby SS adjustment factor
        else if (selectedSchool == 4)
            return 27.5;

            // South Ontario College adjustment factor
        else if (selectedSchool == 5)
            return 25.7;

            // Generic Ontario Secondary School adjustment factor
        else
            return 16.3;
    }

    /*
    This method is responsible for calculating the user's RAW admission score (The University of
    Waterloo conducts their admissions slightly differently - instead of taking one's high school
    average as it is, they apply a few adjustments FIRST then compare these adjusted values AFTER).
    The raw admission score is a combination of one's average, the adjustment factor of their
    school, plus their interview/AIF scores and IB status. This method accepts no parameters and
    returns a value representing the applicant's raw admission score.
     */
    private double calculateRawAdmissionScore() {

        // Declare variable representing the raw admission score (Combination of the applicant's
        // average, interview and AIF scores, whether or not they are in IB, and their school's
        // adjustment factor)
        double rawAdmissionScore = 0.0;

        // Declare a variable representing the "boost" a student would receive to their raw
        // admission score by studying under the IB Curriculum; default value is 0.0
        double ibAdditionFactor = 0.0;

        // This for loop ensures that all EditText widgets contain SOME value prior to calculation
        for (EditText editTextField : editTextArray) {
            if (editTextField.getText().toString().equals("") || editTextField.getText().toString() == null) {
                return rawAdmissionScore;
            }
        }

        // Retrieve the applicant's marks by converting them to doubles
        double englishMark = Double.parseDouble(englishMarkEditText.getText().toString());
        double functionsMark = Double.parseDouble(functionsMarkEditText.getText().toString());
        double calculusMark = Double.parseDouble(calculusMarkEditText.getText().toString());
        double physicsMark = Double.parseDouble(physicsMarkEditText.getText().toString());
        double chemistryMark = Double.parseDouble(chemistryMarkEditText.getText().toString());
        double otherCourseMark = Double.parseDouble(otherCourseMarkEditText.getText().toString());

        // Retrieve the adjustment factor to be applied to the applicant's average
        // based upon the school they selected
        double adjustmentFactor = getSchoolAdjustmentFactor();

        // Retrieve the applicant's perceived AIF and Interview score (Both marked out of 5) as
        // integer values that will be factored into their admission chance
        int aifScore = Integer.parseInt(aifScoreEditText.getText().toString());
        int interviewScore = Integer.parseInt(interviewScoreEditText.getText().toString());

        // Determine whether or not to apply the IB Factor to the applicant's raw admission average
        if (ibStatusSwitch.isChecked())
            ibAdditionFactor = 3.0;

        // All University of Waterloo Engineering Programs require that:
        // 1. Pre-requisite courses have at LEAST a 70%
        // 2. The AIF HAS been completed (i.e. does not have a score of 0; 1 is the minimum)
        // If the applicant does not fulfill one or more of these requirements, they are rejected
        if ((englishMark >= 70 && englishMark <= 100) && (functionsMark >= 70 && functionsMark <= 100) &&
                (calculusMark >= 70 && calculusMark <= 100) && (physicsMark >= 70 && physicsMark <= 100) &&
                (chemistryMark >= 70 && chemistryMark <= 100) && (otherCourseMark >= 70 && otherCourseMark <= 100) &&
                (aifScore > 0 && aifScore <= 5) && (interviewScore >= 0 && interviewScore <= 3)){

            // Calculate the applicant's Top 6 Average
            double markAverage = ((englishMark + functionsMark + calculusMark + physicsMark +
                    chemistryMark + otherCourseMark) / 6.0);

            // Calculate the applicant's RAW admission score
            rawAdmissionScore = (markAverage - adjustmentFactor) + aifScore + interviewScore + ibAdditionFactor;
        }

        // If the user does not fulfill the requirements, then 0.0 will be returned
        // as to represent their immediate rejection from the program
        return rawAdmissionScore;
    }

    /*
    This method is responsible for calculating the user's admission chance and subsequently
    displaying their calculated % chance in the activity window. Relying on formulas (devised
    through VISUAL regression from https://mycurvefit.com/), this method is able to approximate
    the chance that the user will be admitted into the engineering program of their choice. The
    formulas below are based upon the graphs showcased in The Road to Engineering blog (ran by
    William Bishop, head of Engineering Admissions at UWaterloo). It accepts no parameters and
    returns nothing.
     */
    private void calculateAdmissionChanceAndDisplay() {

        // Obtain the user's raw score
        double applicantScore = calculateRawAdmissionScore();

        // Check if the user's applicant score was 0 (Meaning they failed to attain a 70 in
        // one or more courses, or did not complete their AIF) - if so,
        if (applicantScore == 0.0)
            chanceTextView.setText("0%");

        // If the applicant fulfilled all the admission requirements, execute this block
        else {

            int selectedProgram = programSpinner.getSelectedItemPosition();

            double applicantAdmissionChance = 0;

            // This if block pertains to the programs of: Architectual Eng, Architecture,
            // Chemical Eng, Civil Eng, Environmental Eng, Geological Eng, Management Eng and
            // Nanotechnology Eng (These programs all have the same general competitive requirements)
            if (selectedProgram == 0 || selectedProgram == 1 || selectedProgram == 3 ||
                    selectedProgram == 4 || selectedProgram == 7 || selectedProgram == 8 ||
                    selectedProgram == 9 || selectedProgram == 12) {

                // This formula was taken from https://mycurvefit.com/, where I was able to
                // model the approximate admission chance curve provided by the University of Waterloo
                // themselves VISUALLY into an equation (Used regression analysis)
                applicantAdmissionChance = (110803.1 - 5478.955 * applicantScore) + 100.8293 *
                        Math.pow(applicantScore, 2) - 0.8188373 * Math.pow(applicantScore, 3)
                        + (0.00247847 * Math.pow(applicantScore, 4));

                // "Hard-fix" for applicant chances regarding the engineering programs listed above.
                // Because we are using a regression quartic function to model their chances, applicants
                // With very high raw scores can be subject to SMALLER chances than those who have lower
                // raw applicant scores. This if-block is merely a band-aid solution for this issue
                if (applicantAdmissionChance <= 95.20 && applicantScore > 87.555) {
                    applicantAdmissionChance = 95.0;
                }
            }

            // This else if block pertains to the programs of: Computer Eng, Electrical Eng,
            // Mechanical Eng, Mechatronics Eng, and Systems Design Eng
            // (These programs all have the same general competitive requirements)
            else if (selectedProgram == 5 || selectedProgram == 6 || selectedProgram == 10 ||
                    selectedProgram == 11 || selectedProgram == 14) {

                // This formula was taken from https://mycurvefit.com/, where I was able to
                // model the approximate admission chance curve provided by the University of Waterloo
                // themselves VISUALLY into an equation (Used regression analysis)
                applicantAdmissionChance = (-145034.3 + 7546.192 * applicantScore) - (146.6711 *
                        Math.pow(applicantScore, 2)) + (1.26155 * Math.pow(applicantScore, 3)) -
                        (0.004049259 * Math.pow(applicantScore, 4));

                // A "hard-fix" for applicant chances regarding the engineering programs listed above.
                // Because we are using a regression quartic function to model their chances, applicants
                // With above a 90 raw admission score can actually receive a LOWER chance of admission
                // Hence, if they have very high averages, they'd incorrectly receive a low chance
                if (applicantAdmissionChance <= 89.302 && applicantScore > 88.667) {
                    applicantAdmissionChance = 90.0;
                }
            }

            // This else if block pertains to the programs of Biomedical Engineering and Software
            // Engineering
            else if (selectedProgram == 2 || selectedProgram == 13) {

                // This formula was taken from https://mycurvefit.com/, where I was able to
                // model the approximate admission chance curve provided by the University of Waterloo
                // themselves VISUALLY into an equation (Used regression analysis)
                applicantAdmissionChance = (1167821 - 74876.3 * applicantScore) +
                        (1916.098 * Math.pow(applicantScore, 2)) - (24.45985 * Math.pow(applicantScore, 3)) +
                        (0.1557373 * Math.pow(applicantScore, 4)) - (0.0003955991 * Math.pow(applicantScore, 5));

                // This is a "hard-fix" for applicant chances regarding software engineering / bio
                // medical engineering, as we are using a regression quintic function to model their
                // chances; the curve has a local max at 90, after which the function curves DOWN
                // (Meaning that very high averages are incorrectly given very low chances)
                if (applicantAdmissionChance <= 68.312 && applicantScore > 90.104) {
                    applicantAdmissionChance = 70.0;
                }
            }

            // The regression formulas used above are mathematical, and hence they CAN return
            // negative admission chance values (or values that exceed 100) if the applicant's
            // raw score is VERY low or VERY high; hence, these are final checks that ensure there
            // are logical limits by which the user's admission chance abides
            if (applicantAdmissionChance < 5.0)
                applicantAdmissionChance = 5.0;

            else if (applicantAdmissionChance > 95.0)
                applicantAdmissionChance = 95.0;

            // Display the results of the user's admission chance with a formatted string
            chanceTextView.setText(String.format("%.1f%%", applicantAdmissionChance));
        }
    }
}
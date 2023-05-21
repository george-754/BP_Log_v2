package com.grhprogramming.bplogv2.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;

import com.google.android.material.textfield.TextInputLayout;
import com.grhprogramming.bplogv2.MainActivity;
import com.grhprogramming.bplogv2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewBpActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.wordlistsql.REPLY";
    private NumberPicker npSystolic;
    private NumberPicker npDystolic;
    private NumberPicker npHeartrate;
    private NumberPicker npHour;
    private NumberPicker npMinute;
    private NumberPicker npAMPM;
    private NumberPicker npMonth;
    private NumberPicker npDay;
    private NumberPicker npYear;

    private String[] pickerMinutes;
    private String[] pickerAMPM;

    private String[] pickerMonth;

    private DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bp);

        Calendar date = Calendar.getInstance();

        npSystolic = findViewById(R.id.np_systolic);
        npDystolic = findViewById(R.id.np_dystolic);
        npHeartrate = findViewById(R.id.np_heartrate);
        npHour = findViewById(R.id.np_hour);
        npMinute = findViewById(R.id.np_minute);
        npAMPM = findViewById(R.id.np_ampm);
        npMonth = findViewById(R.id.np_month);
        npDay = findViewById(R.id.np_day);
        npYear = findViewById(R.id.np_year);

        // Default values for systolic blood pressure
        npSystolic.setMinValue(40);
        npSystolic.setMaxValue(240);
        npSystolic.setValue(120);

        // Default values for dystolic blood pressure
        npDystolic.setMinValue(20);
        npDystolic.setMaxValue(180);
        npDystolic.setValue(80);

        // Default values for heart rate
        npHeartrate.setMinValue(0);
        npHeartrate.setMaxValue(280);
        npHeartrate.setValue(80);

        // Default values for time hour
        npHour.setMinValue(1);
        npHour.setMaxValue(12);
        npHour.setValue(date.get(Calendar.HOUR));

        // Default values for time minutes
        pickerMinutes = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npMinute.setDisplayedValues(pickerMinutes);
        npMinute.setValue(date.get(Calendar.MINUTE));

        // Default values for time AM/PM
        pickerAMPM = new String[]{"AM", "PM"};
        npAMPM.setMinValue(0);
        npAMPM.setMaxValue(1);
        npAMPM.setDisplayedValues(pickerAMPM);
        npAMPM.setValue(date.get(Calendar.AM_PM));

        // Default values for date month
        pickerMonth = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        npMonth.setMinValue(0);
        npMonth.setMaxValue(11);
        npMonth.setDisplayedValues(pickerMonth);
        npMonth.setValue(date.get(Calendar.MONTH));

        // Default values for date day
        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        npDay.setValue(date.get(Calendar.DAY_OF_MONTH));

        // Default values for date year
        npYear.setMinValue(1900);
        npYear.setMaxValue(2080);
        npYear.setValue(date.get(Calendar.YEAR));

        db = new DbHandler(this);

        final Button button = findViewById(R.id.btn_save);

        button.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();

            // input 0 for the id. It will automatically increment
            int iId = 0;

            //Dates are pre selected to the current day
            int iMonth = npMonth.getValue() + 1;
            int iDay = npDay.getValue();
            int iYear = npYear.getValue();

            // Retrieve values from the edit boxes
            int iHour = 0;
            // Make hour 24 hour time check if it makes it greater than 24 and change to 0 if so
                /*if (npAMPM.getValue() != 0) {
                    iHour += 12;
                    if (iHour >= 24) {
                        iHour = 0;
                    }
                }*/

            // CONVERT TIME TO 24 HOUR TIME TO GET THE HOUR IN 24 HOUR FORMAT ///////////////////
            String time = "";
            if (npAMPM.getValue() != 0) {
                time = npHour.getValue() + ":" + npMinute.getValue() + " PM";
            } else {
                time = npHour.getValue() + ":" + npMinute.getValue() + " AM";
            }

            SimpleDateFormat twoFourFormat = new SimpleDateFormat("HH");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
            try {
                Date outputDate = parseFormat.parse(time);
                String twoFourHour = twoFourFormat.format(outputDate);

                // Extract the hour string anc convert it to integer
                iHour = Integer.parseInt(twoFourHour);

                // Log.d("DEBUG", twoFourHour);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            ////////////////////////////////////////////////////////////////////////////////////
            int iMinute = npMinute.getValue();
            int iSystolic = npSystolic.getValue();
            int iDystolic = npDystolic.getValue();
            int iHeartrate = npHeartrate.getValue();

            // BP bp = new BP(iId, iHour, iMinute, iMonth, iDay, iYear, iSystolic, iDystolic, iHeartrate);

            db.insertBp(iHour, iMinute, iMonth, iDay, iYear, iSystolic, iDystolic, iHeartrate);

            Intent main = new Intent(NewBpActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        });

    }
}
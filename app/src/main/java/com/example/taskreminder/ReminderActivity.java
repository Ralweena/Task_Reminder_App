package com.example.taskreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    Button Submitbtn, Datebtn, Timebtn;
    private Calendar calendar;
    EditText Titledit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Titledit = (EditText) findViewById(R.id.editTitle);
        //assigned all the material reference to get and set data
        Datebtn = (Button) findViewById(R.id.btnDate);
        Timebtn = (Button) findViewById(R.id.btnTime);
        Submitbtn = (Button) findViewById(R.id.btnSubmit);

        Timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when we click on the choose time button it calls the select time method
                selectTime();
            }
        });

        Datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when we click on the choose date button it calls the select date method
                selectDate();
            }
        });

        Submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //access the data form the input field
                String title = Titledit.getText().toString().trim();
                //access the date form the choose date button
                String date = Datebtn.getText().toString().trim();

                //access the time form the choose time button
                String time = Timebtn.getText().toString().trim();

                if (title.isEmpty()) {
                    //shows the toast if input field is empty
                    Toast.makeText(getApplicationContext(), "Please Enter text", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (time.equals("TIME") || date.equals("DATE")) {
                        //shows toast if date and time are not selected
                        Toast.makeText(getApplicationContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            processinsert(title, date, time);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //this method performs the time picker task
    private void selectTime() {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int style = TimePickerDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                //temp variable to store the time to set alarm
                String time = hour + ":" + minute;
                //sets the button text as selected time
                Timebtn.setText(time);

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    //this method performs the date picker task
    private void selectDate() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int style = DatePickerDialog.THEME_HOLO_LIGHT;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                //sets the selected date as test for button
                String Date = day + "-" + (month + 1) + "-" + year;
                Datebtn.setText(Date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void processinsert(String title, String date, String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse(time);
        long alarmTimeInMillis = date1.getTime();
        if (alarmTimeInMillis <= System.currentTimeMillis())
        {
            Toast.makeText(this,"You can't set alarm for past",Toast.LENGTH_LONG).show();
        }
        else {
            Bundle categoryBundle = getIntent().getExtras();
            String category = categoryBundle.getString("category");
            //inserts the title,date,time into sql lite database
            String result = new dbManager(this).addreminder(title, date, time, category);
            Titledit.setText("");
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            // Setting Alarm
            setAlarm(title);
        }
    }

    private void setAlarm(String title) {

        // Get the alarm time from the calendar
        long alarmTime = calendar.getTimeInMillis();

        // Create a unique PendingIntent for the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Bundle bundle = new Bundle();
        bundle.putString("event",title);

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.putExtras(bundle);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this,0, intent, PendingIntent.FLAG_IMMUTABLE);

        if(alarmManager != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, alarmPendingIntent);
                Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, alarmPendingIntent);
                Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show();
            }

        }

        Intent intentBack = new Intent(this, categories.class);
        startActivity(intentBack);
    }
}

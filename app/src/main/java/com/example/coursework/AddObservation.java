package com.example.coursework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddObservation extends AppCompatActivity {

    EditText observationNameInput, observationCommentInput;

    private TextView observationTimeInput;
    Button add_observation_btn_internal,  backToObservationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        add_observation_btn_internal = findViewById(R.id.add_observation_btn_internal);
        add_observation_btn_internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addObservation();
            }
        });

        backToObservationBtn = findViewById(R.id.backToObservationBtn);
        backToObservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        observationTimeInput = findViewById(R.id.observationTimeInput);
        observationTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFDateDialog();
                openFTimeDialog();

            }
        });
    }


    private void openFTimeDialog()
    {
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                observationTimeInput.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
            }
        },13,00,true);

        timeDialog.show();

    }

    private void openFDateDialog()
    {
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                observationTimeInput.setText(observationTimeInput.getText() + " " + String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day));
            }
        }, 2023, 11, 1);

        dateDialog.show();
    }

    public void addObservation()
    {
        DatabaseHelper db = new DatabaseHelper(this);

        observationNameInput = findViewById(R.id.observationNameInput);
        observationTimeInput = findViewById(R.id.observationTimeInput);
        observationCommentInput = findViewById(R.id.observationCommentInput);

        String observation_name = observationNameInput.getText().toString();
        String observation_time = observationTimeInput.getText().toString();
        String observation_comment = observationCommentInput.getText().toString();

        Bundle extras = getIntent().getExtras();

        String hike_id = extras.getString("Hike id");

        Boolean observation_condition_check = observation_name.equals("") || observation_time.equals("") || observation_comment.equals("");
        if (observation_condition_check)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error!");
            builder.setMessage("Please fill all the information in the form");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            long observationId = db.insertObservationRow(observation_name, observation_time, observation_comment, hike_id);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Success!");
            builder.setMessage("An observation has been added!");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    onBackPressed();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }
}
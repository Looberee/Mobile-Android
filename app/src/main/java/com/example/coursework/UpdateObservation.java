package com.example.coursework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class UpdateObservation extends AppCompatActivity {

    EditText editName, editComment;
    TextView editTime;
    Button update_observation_btn_internal, backToObservationBtnFromUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);

        Bundle extras = getIntent().getExtras();

        int int_obs_id = extras.getInt("ObsId");
        String obs_id = String.valueOf(int_obs_id);

        String observation_name = extras.getString("ObsName");
        String observation_time = extras.getString("ObsTime");
        String observation_comment = extras.getString("ObsComment");
        String observation_hikeId = extras.getString("ObsHikeId");

        editName = findViewById(R.id.editObservationNameInput);
        editTime = findViewById(R.id.editObservationTimeInput);
        editComment = findViewById(R.id.editObservationCommentInput);

        editName.setText(observation_name);
        editTime.setText(observation_time);
        editComment.setText(observation_comment);

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFDateDialog();
                openFTimeDialog();
            }
        });

        update_observation_btn_internal = findViewById(R.id.update_observation_btn_internal);
        update_observation_btn_internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_updated = editName.getText().toString();
                String time_updated = editTime.getText().toString();
                String comment_updated = editComment.getText().toString();

                Observation updatedObsObject = new Observation(int_obs_id,name_updated,time_updated,comment_updated,observation_hikeId);
                updateObservation(obs_id, updatedObsObject);
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateObservation.this);
                builder.setTitle("Success!");
                builder.setMessage("A observation has been updated!");
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
        });


        backToObservationBtnFromUpdate = findViewById(R.id.backToObservationBtnFromUpdate);
        backToObservationBtnFromUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void openFTimeDialog()
    {
        TimePickerDialog timeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                editTime.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
            }
        },13,00,true);

        timeDialog.show();

    }

    private void openFDateDialog()
    {
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                editTime.setText(editTime.getText() + " " + String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day));
            }
        }, 2023, 11, 1);

        dateDialog.show();
    }

    public void updateObservation(String id, Observation obs)
    {
        try {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.updateObservation(id, obs);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
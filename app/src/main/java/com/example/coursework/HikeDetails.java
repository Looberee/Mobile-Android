package com.example.coursework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class HikeDetails extends AppCompatActivity {

    EditText editNameInput, editLocationInput, editDateInput, editDescriptionInput;

    RadioGroup editStatusGroup;
    RadioButton yesRdb, noRdb;

    Spinner editLevelSpinner, editLengthSpinner;

    Button update_hike, backToHikeBtn;

    private String [] lengthInput = {"100","200","300","400"};
    private String [] levelInput = {"LOW","MEDIUM", "HIGH"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details);

        editNameInput = findViewById(R.id.editNameInput);
        editLocationInput = findViewById(R.id.editLocationInput);
        editDateInput = findViewById(R.id.editDateInput);
        editStatusGroup = findViewById(R.id.editParkingRadioGroup);
        yesRdb = findViewById(R.id.yesRdb);
        noRdb = findViewById(R.id.noRdb);
        editLengthSpinner = findViewById(R.id.editLengthInput);
        editLevelSpinner = findViewById(R.id.editLevelInput);
        editDescriptionInput = findViewById(R.id.editDesInput);

        Bundle extras = getIntent().getExtras();


        int int_hike_id = extras.getInt("Hike id");
        String hike_id = String.valueOf(int_hike_id);

        String hike_name = extras.getString("Hike name");
        String hike_location = extras.getString("Hike location");
        String hike_date = extras.getString("Hike date");
        String status = extras.getString("Hike status");
        String hike_length = extras.getString("Hike length");
        String hike_level = extras.getString("Hike level");
        String hike_description = extras.getString("Hike description");

        editNameInput.setText(hike_name);
        editLocationInput.setText(hike_location);
        editDateInput.setText(hike_date);
        if (status.equals("Yes"))
        {
            yesRdb.setChecked(true);
        }
        else{
            noRdb.setChecked(true);
        }
//        editLengthInput.setText(hike_length);
//        editLevelInput.setText(hike_level);

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lengthInput);
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, levelInput);

        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editLengthSpinner.setAdapter(lengthAdapter);
        editLevelSpinner.setAdapter(levelAdapter);

        editLengthSpinner.setSelection(lengthAdapter.getPosition(hike_length));
        editLevelSpinner.setSelection(levelAdapter.getPosition(hike_level));


        editDescriptionInput.setText(hike_description);

        update_hike = findViewById(R.id.edit_hike_btn);
        update_hike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updated_hike_name = editNameInput.getText().toString();
                String updated_hike_location = editLocationInput.getText().toString();
                String update_hike_date = editDateInput.getText().toString();
                RadioButton checkedRdb = findViewById(editStatusGroup.getCheckedRadioButtonId());
                String updated_hike_status = checkedRdb.getText().toString();
                String updated_hike_length = editLengthSpinner.getSelectedItem().toString();
                String updated_hike_level = editLevelSpinner.getSelectedItem().toString();
                String updated_hike_description = editDescriptionInput.getText().toString();

                Hike updatedHikeObject = new Hike(int_hike_id, updated_hike_name, updated_hike_location, update_hike_date,
                        updated_hike_status,updated_hike_length,updated_hike_level,updated_hike_description);
                updateHike(hike_id, updatedHikeObject);
            }
        });

        backToHikeBtn = findViewById(R.id.backToHikeBtn);
        backToHikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void updateHike(String id, Hike hike)
    {
        try {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.updateHike(id,hike);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Success!");
            builder.setMessage("The information has been updated successfully");
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
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
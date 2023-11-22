package com.example.coursework;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

public class AddFragment extends Fragment {

    private TextView dateInput;
    private String [] lengthInput = {"100","200","300","400"};
    private String [] levelInput = {"LOW","MEDIUM", "HIGH"};

    Spinner lengthSpinner;
    Spinner levelSpinner;
    String length;
    String level;

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = day + "/" + (month + 1) + "/" + year;
            AddFragment addFragment = (AddFragment) getTargetFragment();
            if (addFragment != null) {
                addFragment.updateDate(date);
            }
        }
    }

    public void updateDate(String date) {
        if (dateInput != null) {
            dateInput.setText(date);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        dateInput = view.findViewById(R.id.dateInput);

        Button add_view = view.findViewById(R.id.add_hike_btn);

        lengthSpinner = (Spinner) view.findViewById(R.id.lengthInput);

        levelSpinner = (Spinner) view.findViewById(R.id.levelInput);

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, lengthInput);
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, levelInput);

        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lengthSpinner.setAdapter(lengthAdapter);
        levelSpinner.setAdapter(levelAdapter);

        add_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_hike(getContext());
            }
        });

        if (dateInput != null) {
            dateInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.setTargetFragment(AddFragment.this, 0);
                    newFragment.show(getFragmentManager(), "datePicker");
                }
            });
        }

        return view;
    }


    private void add_hike(Context context)
    {
        DatabaseHelper db = new DatabaseHelper(context);

        EditText nameInput = getView().findViewById(R.id.nameInput);
        EditText locationInput = getView().findViewById(R.id.locationInput);
        dateInput = getView().findViewById(R.id.dateInput);
        RadioGroup status_group = getView().findViewById(R.id.parkingRadioGroup);
        RadioButton selected_status_rdb = getView().findViewById(status_group.getCheckedRadioButtonId());
        EditText descriptionInput = getView().findViewById(R.id.desInput);

        String name = nameInput.getText().toString();
        String location = locationInput.getText().toString();
        String date = dateInput.getText().toString();
        String selected_status = selected_status_rdb.getText().toString();
        length = lengthSpinner.getSelectedItem().toString();
        level = levelSpinner.getSelectedItem().toString();
        String description = descriptionInput.getText().toString();

        Boolean value_checking = (name.equals("") || location.equals("") || date.equals("") || description.equals(""));
        if (value_checking)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
            long hikeId = db.insertRow(name, location, date, selected_status, length, level, description);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Success!");
            builder.setMessage("A hike has been added to the database!");
            builder.setCancelable(true);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    nameInput.setText("");
                    locationInput.setText("");
                    dateInput.setText("");
                    RadioButton yes = getView().findViewById(R.id.Yes);
                    status_group.check(yes.getId());
                    descriptionInput.setText("");


                    HomeFragment homeF = new HomeFragment();
                    FragmentManager fragmentManager = getFragmentManager();

                    if (fragmentManager != null) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flFragment, homeF);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ObservationDetails extends AppCompatActivity {
    private ObservationAdapter Observation_adapter;
    private RecyclerView observationRecyclerView;
    private Button new_observation_btn, backToHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_details);

        int hike_id = getIntent().getIntExtra("Hike id",0);
        String id_string = String.valueOf(hike_id);

        new_observation_btn = findViewById(R.id.new_observation_btn);
        new_observation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), AddObservation.class);
                i.putExtra("Hike id", id_string);
                startActivity(i);
            }
        });

        backToHomeFragment = findViewById(R.id.backToHomeFragment);
        backToHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        observationRecyclerView = findViewById(R.id.observationRecyclerView);
        observationRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadObservations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadObservations();
    }

    private void loadObservations() {
        DatabaseHelper db = new DatabaseHelper(this);
        int hike_id = getIntent().getIntExtra("Hike id",0);

        ArrayList<Observation> observations = db.getAllObservation();
        ArrayList<Observation> obsArray = new ArrayList<Observation>();

        for ( Observation observation : observations)
        {
            if (observation.getObsHikeId().equals(String.valueOf(hike_id)))
            {
                obsArray.add(observation);
            }
        }

        observations.clear();
        observations.addAll(db.getAllObservation());

        Observation_adapter = new ObservationAdapter(obsArray);
        observationRecyclerView.setAdapter(Observation_adapter);
        Observation_adapter.notifyDataSetChanged();
    }
}

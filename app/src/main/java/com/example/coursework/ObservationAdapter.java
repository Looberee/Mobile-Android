package com.example.coursework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ContactViewHolder> {
    private ArrayList<Observation> observations;

    private Context context;

    public ObservationAdapter(ArrayList<Observation> observations) {
        this.observations = observations;
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.observation_card, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Observation observation= observations.get(position);
        holder.observationName.setText(observation.getObsName());
        holder.edit_observation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), UpdateObservation.class);
                i.putExtra("ObsId", observation.getObsId());
                i.putExtra("ObsName", observation.getObsName());
                i.putExtra("ObsTime", observation.getObsTime());
                i.putExtra("ObsComment", observation.getObsComment());
                i.putExtra("ObsHikeId", observation.getObsHikeId());
                view.getContext().startActivity(i);
            }
        });

        holder.delete_observation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DatabaseHelper db = new DatabaseHelper(view.getContext());
                    db.deleteObservation(String.valueOf(observation.getObsId()));
                    observations.remove(position);
                    notifyItemRemoved(position);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView observationName;
        Button delete_observation_btn, edit_observation_btn;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            observationName = itemView.findViewById(R.id.observationName);
            edit_observation_btn = itemView.findViewById(R.id.edit_observation_btn);
            delete_observation_btn = itemView.findViewById(R.id.delete_observation_btn);
        }

    }
}
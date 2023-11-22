package com.example.coursework;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private ArrayList<Hike> hikes;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(Hike hike);
    }

    public ContactAdapter(ArrayList<Hike> hikes, OnDeleteClickListener onDeleteClickListener) {
        this.hikes = hikes;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public ContactAdapter(ArrayList<Hike> hikes) {
        this.hikes = hikes;;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_card, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Hike hike = hikes.get(position);
        holder.hikeName.setText(hike.getName());

        holder.hikeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), HikeDetails.class);
                i.putExtra("Hike id", hike.getId());
                i.putExtra("Hike name", hike.getName());
                i.putExtra("Hike location", hike.getLocation());
                i.putExtra("Hike date", hike.getDate());
                i.putExtra("Hike status", hike.getStatus());
                i.putExtra("Hike length", hike.getLength());
                i.putExtra("Hike level", hike.getLevel());
                i.putExtra("Hike description", hike.getDescription());
                view.getContext().startActivity(i);

            }
        });

        holder.add_observation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ObservationDetails.class);
                i.putExtra("Hike id", hike.getId());
                view.getContext().startActivity(i);
            }
        });

        holder.delete_hike_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DatabaseHelper db = new DatabaseHelper(view.getContext());
                    db.deleteHike(String.valueOf(hike.getId()));
                    hikes.remove(position);
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
        return hikes.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView hikeName;
        Button delete_hike_btn, add_observation_btn;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.hikeName);
            add_observation_btn = itemView.findViewById(R.id.add_observation_btn);
            delete_hike_btn = itemView.findViewById(R.id.delete_hike_btn);
        }

    }
}
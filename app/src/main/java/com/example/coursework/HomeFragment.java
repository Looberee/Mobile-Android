package com.example.coursework;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.coursework.HikeDetails;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ArrayList<Hike> hikes;
    private DatabaseHelper db;
    Button deleteAllHikesBtn;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view_home = inflater.inflate(R.layout.fragment_home, container, false);

        Context context = getContext();

        db = new DatabaseHelper(context);

        recyclerView = view_home.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        hikes = db.getAll();

        adapter = new ContactAdapter(hikes);
        recyclerView.setAdapter(adapter);

        deleteAllHikesBtn = view_home.findViewById(R.id.deleteAllbtn);
        deleteAllHikesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Success!");
                    builder.setMessage("All the data has been clear");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteAllHikes();
                            hikes.clear();
                            hikes.addAll(db.getAll());
                            adapter.notifyDataSetChanged();
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
        });

        return view_home;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        loadHikes();
    }

    private void loadHikes()
    {
        hikes.clear();
        hikes.addAll(db.getAll());
        adapter.notifyDataSetChanged();
    }
}

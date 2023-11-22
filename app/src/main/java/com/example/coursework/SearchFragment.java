package com.example.coursework;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    EditText searchInput;
    Button searchBtn;
    private RecyclerView recyclerView;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view_search = inflater.inflate(R.layout.fragment_search, container, false);
        searchInput = view_search.findViewById(R.id.searchInput);
        recyclerView = view_search.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBtn = view_search.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hikeName = searchInput.getText().toString();
                if (hikeName.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Error!");
                    builder.setMessage("You are searching nothing!");
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
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    ArrayList<Hike> searchedHikeArray = db.getSearchedHike(hikeName);

                    ContactAdapter adapter = new ContactAdapter(searchedHikeArray);
                    recyclerView.setAdapter(adapter);

                }
            }
        });

        return view_search;
    }
}
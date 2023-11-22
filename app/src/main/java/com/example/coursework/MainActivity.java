package com.example.coursework;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.addNav);

    }

    AddFragment addFragment = new AddFragment();
    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.addNav) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, addFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.homeNav) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, homeFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.searchNav) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, searchFragment)
                    .commit();
            return true;
        } else {
            return false;
        }
    }

 }
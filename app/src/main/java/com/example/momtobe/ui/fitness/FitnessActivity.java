package com.example.momtobe.ui.fitness;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.momtobe.R;


public class FitnessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FitnessFragment())
                    .commit();
        }
    }
}
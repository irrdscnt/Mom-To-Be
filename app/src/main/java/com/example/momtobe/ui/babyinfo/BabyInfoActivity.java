package com.example.momtobe.ui.babyinfo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.momtobe.R;

public class BabyInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babyinfo);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new BabyInfoFragment())
                    .commit();
        }
    }
}
package com.example.android.popularmovie.ZokaPackage.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovie.R;
import com.example.android.popularmovie.ZokaPackage.Fragment.DetailsFragment;

/**
 * Created by Mohamed Rabie on 4/7/2016.
 */
public class DetailsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_details);
        if (null==savedInstanceState)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.Detalis_frame_layout, new DetailsFragment()).commit();
        }
    }
}

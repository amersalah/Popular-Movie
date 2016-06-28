package com.example.android.popularmovie.ZokaPackage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.android.popularmovie.MovisListener;
import com.example.android.popularmovie.R;
import com.example.android.popularmovie.Schema.MovieSchema;
import com.example.android.popularmovie.ZokaPackage.Fragment.DetailsFragment;
import com.example.android.popularmovie.ZokaPackage.Fragment.MainFragment;
import com.example.android.popularmovie.ZokaPackage.StaticVar;

public class MainActivity extends AppCompatActivity implements MovisListener {

    Boolean mTowPan;
    StaticVar staticVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staticVar =new StaticVar();
        FrameLayout flpanel2= (FrameLayout) findViewById(R.id.f_panel_2);
        if (null==flpanel2) {mTowPan=false;} else {mTowPan=true;}


        if (null==savedInstanceState)
        {MainFragment mainFragment=new MainFragment();
            mainFragment.SetMovieListener(this);


           getSupportFragmentManager().beginTransaction().replace(R.id.f_panel_1, mainFragment).commit();
        }
    }

    @Override
    public void Set_SelectedMovie(MovieSchema movieDtails) {

        //Single pan Ui
        if(mTowPan) //Case Tow pan Ui
        {

            DetailsFragment dFragment=new DetailsFragment();
            Bundle extra=new Bundle();
         extra.putSerializable(staticVar.Extra, movieDtails);
            dFragment.setArguments(extra);
            getSupportFragmentManager().beginTransaction().replace(R.id.f_panel_2,dFragment ).commit();

        }
        else { //Case One  pan Ui
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra(staticVar.Extra, movieDtails);
            startActivity(i);

        }

    }
}

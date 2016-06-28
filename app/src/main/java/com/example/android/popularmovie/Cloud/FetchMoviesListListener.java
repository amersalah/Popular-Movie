package com.example.android.popularmovie.Cloud;

import com.example.android.popularmovie.Schema.MovieSchema;

import java.util.ArrayList;

/**
 * Created by Mohamed Rabie on 4/14/2016.
 */
public interface FetchMoviesListListener {

    public void OnTaskFinish(ArrayList<MovieSchema> arrayList) ;


}

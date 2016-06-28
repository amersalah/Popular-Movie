package com.example.android.popularmovie.ZokaPackage;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Mohamed Rabie on 4/18/2016.
 */
public class StaticVar {
    public static final  String API_KEY="3eaf25965e78bf12e550365b00e183a7"; // Assign your own Api_key !

    public static final  String Popular="popular";
    public static  final String Favourite_Message="Favorite List is Empty";
    public static final  String Top_Rated="top_rated";
    public static final  String Extra="DETAILS";
    public static final  String BASE_URI="http://api.themoviedb.org/3/movie/";
    public static final  String REVIEW_S="/reviews?api_key=";
    public static final  String TRAILER_S="/videos?api_key=";
    public static final  String Fetch_S="?api_key=";
    public static final  String YOUTUBE_BASE="http://www.youtube.com/watch?v=";




    public static final void ToastMessage(Activity activity)
    {
        Toast.makeText(activity, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
}

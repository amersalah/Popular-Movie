package com.example.android.popularmovie.ZokaBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovie.Schema.MovieSchema;
import java.util.ArrayList;
/**
 * Created by Mohamed Rabie on 4/4/2016.
 */

public class CashedMovies {

    static final String    DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "movie";
    //---------------------------------------------
    public static final String ID ="ID";
    public static final String TITLE = "title";
    public static final String POSTER_IMAGE = "poster_image";
    public static final String BACKDROP_IMAGE = "back_drop";
    public static final String OVERVIEW = "overview";
    public static final String RATE = "rating";
    public static final String DATE = "date";
    private final Context context;

    SQLiteDatabase sqLiteDatabase;
    Helper helper;



    public CashedMovies
            (Context c)
    {
        this.context=c;
    }

    public  class Helper extends SQLiteOpenHelper
    {

        public Helper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }


        public void onCreate(SQLiteDatabase db)
        {
            final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                   ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  TITLE + " TEXT NOT NULL, " +
                    POSTER_IMAGE + " TEXT, " +
                    BACKDROP_IMAGE + " TEXT, " +
                    OVERVIEW + " TEXT, " +
                    RATE + " TEXT, " +
                    DATE + " TEXT);";

            db.execSQL(SQL_CREATE_MOVIE_TABLE);

        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

        {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
            onCreate(db);

        }
    }


    public void openData()
    {
        Helper dBhelper=new Helper(context);
        sqLiteDatabase=dBhelper.getWritableDatabase();

    }
    public void close()

    {
        sqLiteDatabase.close();
    }



    public  void CasheData(MovieSchema modelMovies)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, modelMovies.getTitle());
        contentValues.put(DATE, modelMovies.getDate());
        contentValues.put(POSTER_IMAGE, modelMovies.getPosterPath());
        contentValues.put(BACKDROP_IMAGE, modelMovies.getBackDrop());
        contentValues.put(OVERVIEW, modelMovies.getDesc());
        contentValues.put(RATE, modelMovies.getRate());
        contentValues.put(ID, modelMovies.getId());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }
    public ArrayList<MovieSchema> getchashedData()
    {
        ArrayList<MovieSchema>array=new ArrayList<>();
        String [] coulms={ID,DATE,POSTER_IMAGE,BACKDROP_IMAGE,OVERVIEW,RATE,TITLE};
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,coulms,null,null,null,null,null,null);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            MovieSchema modelMovies=new MovieSchema();
            modelMovies.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            modelMovies.setDate( cursor.getString(cursor.getColumnIndex(DATE)));
            modelMovies.setPosterPath( cursor.getString(cursor.getColumnIndex(POSTER_IMAGE)));
            modelMovies.setBackDrop( cursor.getString(cursor.getColumnIndex(BACKDROP_IMAGE)));
            modelMovies.setDesc( cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            modelMovies.setId( cursor.getString(cursor.getColumnIndex(ID)));
            modelMovies.setRate(cursor.getString(cursor.getColumnIndex(RATE)));
            array.add(modelMovies);
        }
        return array;
    }

}

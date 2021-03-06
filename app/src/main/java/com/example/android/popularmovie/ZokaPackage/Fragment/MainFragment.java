
package com.example.android.popularmovie.ZokaPackage.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.popularmovie.Cloud.FetchMoviesList;
import com.example.android.popularmovie.Cloud.FetchMoviesListListener;
import com.example.android.popularmovie.MovisListener;
import com.example.android.popularmovie.R;
import com.example.android.popularmovie.Schema.MovieSchema;
import com.example.android.popularmovie.ZokaBase.CashedMovies;
import com.example.android.popularmovie.ZokaBase.FavoriteMovies;
import com.example.android.popularmovie.ZokaPackage.StaticVar;
import com.example.android.popularmovie.adapters.GridViewAdapter;

import java.util.ArrayList;
/**
 * Created by Mohamed Rabie on 3/29/2016.
 */
public class MainFragment extends Fragment {
    private FetchMoviesList fetchMoviesList;
    private GridViewAdapter adapter;
    private SwipeRefreshLayout Swiper;
    private GridView gridView;
    MovisListener mListener;
    StaticVar staticVar;
    ArrayList<MovieSchema> arrayList;

    public ArrayList<MovieSchema> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<MovieSchema> arrayList) {
        this.arrayList = arrayList;
    }

    //------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        setHasOptionsMenu(true);


        gridView= (GridView) rootView.findViewById(R.id.gridView);
        staticVar =new StaticVar();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieSchema movieSchema = (MovieSchema) adapter.getItem(position);

                mListener.Set_SelectedMovie(movieSchema);
                /*
                Intent i =new Intent(getActivity(),DetailsActivity.class);
                i. putExtra(staticVar.Extra,movieSchema);
                startActivity(i);*/



            }
        });



        //this Methode get called when ever ther
        // user swipe the screen
        //Refreshing the main activity and load the popular movie
        // list.............

        Swiper= (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        Swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SortMyMoviesList(staticVar.Popular);
                Swiper.setRefreshing(false);
            }
        });

        return rootView;
    }

    //this Methode called when ther  need to change sort (Popular or Top Rated)......................
         private void SortMyMoviesList(String SortType) {
           fetchMoviesList=new FetchMoviesList();
        fetchMoviesList.execute(SortType);
        fetchMoviesList.setTaskListener(new FetchMoviesListListener() {
            @Override
            public void OnTaskFinish(ArrayList<MovieSchema> arrayList) {
                setArrayList(arrayList);
                if (arrayList.size()<1)
                {
                    staticVar.ToastMessage(getActivity());
                }
                else
                {

                adapter=new GridViewAdapter(getActivity(),arrayList);
                gridView.setAdapter(adapter);
                    SaveData();

            }}
        });
    }

    public void SaveData()
    {
        for (int i=0;i<getArrayList().size();i++)
        {
            CashedMovies CashedMovies = new CashedMovies(getActivity());
            CashedMovies.openData();
            try {
                if (getArrayList().get(i).id=="206647")
                {
                    continue;
                }
                else{ CashedMovies.CasheData(getArrayList().get(i));}

            }
            catch (Exception r)
            {

            }
            CashedMovies.close();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        ShowCacheData();

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.menu_item_share)
        {
            SortMyMoviesList(staticVar.Top_Rated);

        }
        else
        if (id==R.id.menu_popular)
        {
            SortMyMoviesList(StaticVar.Popular);
        }
        else
        if (id==R.id.menu_item_favourite)
        {
            Favorite(); // call favourite function to get favourite movie from database
        }
        return super.onOptionsItemSelected(item);
    }

    // This Methode Load favoute Movie from dataBase.................

    private void  Favorite()
    {
        FavoriteMovies dataBaseController=new FavoriteMovies(getActivity());
        dataBaseController.OpenData();
        ArrayList<MovieSchema> movieSchemaArrayList=new ArrayList<>();
        movieSchemaArrayList=dataBaseController.getData();
        if(movieSchemaArrayList.size()<1)
        {
            Toast.makeText(getActivity(),staticVar.Favourite_Message, Toast.LENGTH_SHORT).show();
        }
        else {
            adapter = new GridViewAdapter(getActivity(), movieSchemaArrayList);
            gridView.setAdapter(adapter);
            dataBaseController.Close();
        }
    }
    private void ShowCacheData()
    {
        CashedMovies cashedMovies=new CashedMovies(getActivity());
        cashedMovies.openData();
        ArrayList<MovieSchema>array=new ArrayList<>();
        array=cashedMovies.getchashedData();
        adapter=new GridViewAdapter(getActivity(),array);
        gridView.setAdapter(adapter);
        cashedMovies.close();
    }

    public void SetMovieListener(MovisListener movisListener) {
        mListener=movisListener;
    }
}

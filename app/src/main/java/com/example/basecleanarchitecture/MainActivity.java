package com.example.basecleanarchitecture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.basecleanarchitecture.adpters.MovieRecycleView;
import com.example.basecleanarchitecture.adpters.OnMovieListener;
import com.example.basecleanarchitecture.adpters.PopularViewHolder;
import com.example.basecleanarchitecture.models.MovieModel;
import com.example.basecleanarchitecture.viewmodels.MovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieListener {

    private static String TAG = "Tag";

    private RecyclerView recyclerView;

    private MovieRecycleView movieRecycleViewAdapter;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetupSearchView();

        recyclerView = findViewById(R.id.recycleView);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();

        ObserveAnyChange();

        ObservePopularMovies();

        movieListViewModel.searchMoviePopApi(1);

    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    movieRecycleViewAdapter.setmMovies(movieModels);
                }
            }
        });
    }

    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null){
                    movieRecycleViewAdapter.setmMovies(movieModels);
                }
            }
        });
    }

    private void ConfigureRecyclerView(){
        movieRecycleViewAdapter = new MovieRecycleView( this);
        recyclerView.setAdapter(movieRecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });

    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecycleViewAdapter.getSelected(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }


    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1

                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPopular = false;
            }
        });
    }

}
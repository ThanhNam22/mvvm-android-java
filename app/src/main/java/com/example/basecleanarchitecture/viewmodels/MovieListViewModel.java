package com.example.basecleanarchitecture.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.basecleanarchitecture.models.MovieModel;
import com.example.basecleanarchitecture.repository.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }


    public void searchMovieApi(String query, int pageNumber){
        movieRepository.searchMovieApi(query, pageNumber);
    }

    public void searchMoviePopApi(int pageNumber){
        movieRepository.searchMoviePopApi(pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}

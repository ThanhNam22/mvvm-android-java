package com.example.basecleanarchitecture.response;

import com.example.basecleanarchitecture.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    // 1 - Finding the Movie Object
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}

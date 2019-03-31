package com.example.popularmoviesapp.popularmoviesapp.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.popularmoviesapp.popularmoviesapp.models.Movie;
import com.example.popularmoviesapp.popularmoviesapp.models.MovieReview;
import com.example.popularmoviesapp.popularmoviesapp.models.MovieTrailer;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface DataRepository {
    Single<List<Movie>> getMostPopularMovies();

    Single<List<Movie>> getHighestRatedMovies();

    LiveData<List<Movie>> getFavoriteMovies(Context context);

    Single<List<MovieTrailer>> getMovieTrailers(int movieId);

    Single<List<MovieReview>> getMovieReviews(int movieId);

    Completable addMovieToFavorites(Context context, Movie movie);

    Completable removeMovie(Context context, Movie movie);

    Single<Boolean> isFavorite(Context context, int movieId);
}

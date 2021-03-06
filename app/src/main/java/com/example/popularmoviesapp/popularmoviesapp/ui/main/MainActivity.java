package com.example.popularmoviesapp.popularmoviesapp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.popularmoviesapp.popularmoviesapp.databinding.ActivityMainBinding;
import com.example.popularmoviesapp.popularmoviesapp.repository.RepoFactory;
import com.example.popularmoviesapp.popularmoviesapp.ui.moviedetails.MovieDetailsActivity;
import com.example.popularmoviesapp.popularmoviesapp.R;
import com.example.popularmoviesapp.popularmoviesapp.models.Movie;

public class MainActivity extends AppCompatActivity implements com.example.popularmoviesapp.popularmoviesapp.ui.main.MovieAdapter.MovieAdapterListener {


    private static final int CODE_FAVORITE_CHANGED = 1;
    private com.example.popularmoviesapp.popularmoviesapp.ui.main.MovieAdapter movieAdapter;
    private com.example.popularmoviesapp.popularmoviesapp.ui.main.MainViewModel mViewModel;
    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = ViewModelProviders
                .of(this, new com.example.popularmoviesapp.popularmoviesapp.ui.main.MainViewModelFactory(getApplication(), RepoFactory.getDataRepository()))
                .get(com.example.popularmoviesapp.popularmoviesapp.ui.main.MainViewModel.class);

        activityMainBinding.setViewModel(mViewModel);
        movieAdapter = new com.example.popularmoviesapp.popularmoviesapp.ui.main.MovieAdapter(this);
        activityMainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, getNumberOfColumns()));
        activityMainBinding.recyclerView.setHasFixedSize(true);
        activityMainBinding.recyclerView.setAdapter(movieAdapter);
        mViewModel.getMoviesLiveData()
                .observe(this, movies -> movieAdapter.setMovies(movies));

    }
    private int getNumberOfColumns() {
        return getResources().getInteger(R.integer.default_grid_column_number);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_highest_rated:
                loadHighestRated();
                return true;
            case R.id.action_most_popular:
                loadMostPopular();
                return true;
            case R.id.action_favorite:
                loadFavoriteMovies();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loadMostPopular() {
        mViewModel.loadMostPopular();
    }

    private void loadHighestRated() {
        mViewModel.loadHighestRated();
    }

    private void loadFavoriteMovies() {
        mViewModel.loadFavoriteMovies();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.ARG_MOVIE, movie);
        startActivityForResult(intent, CODE_FAVORITE_CHANGED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check if the favorite movies have changed
        if (requestCode == CODE_FAVORITE_CHANGED && resultCode == RESULT_OK) {
            mViewModel.notifyFavoriteChanged();
        }
    }
}

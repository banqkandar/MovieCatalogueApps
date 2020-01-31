package com.bangkandar.moviecatalogefinished.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.adapter.AdapterMovies;
import com.bangkandar.moviecatalogefinished.entity.Movie;
import com.bangkandar.moviecatalogefinished.viewmodel.ViewModelMovies;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    private AdapterMovies adapterMovies;
    private ProgressBar progressBar;
    private ViewModelMovies viewModelMovies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapterMovies = new AdapterMovies();
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        RecyclerView rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(adapterMovies);

        progressBar = view.findViewById(R.id.progressBar);
        viewModelMovies = ViewModelProviders.of(this).get(ViewModelMovies.class);
        viewModelMovies.getMovies().observe(this, getMovie);
        viewModelMovies.setMovies("EXTRA_MOVIE");

        showLoading(true);
        return view;
    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapterMovies.setData(movies);
            }
            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}

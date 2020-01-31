package com.bangkandar.moviecatalogefinished.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.activity.MovieDetailActivity;
import com.bangkandar.moviecatalogefinished.adapter.AdapterMovies;
import com.bangkandar.moviecatalogefinished.adapter.ItemClickSupport;
import com.bangkandar.moviecatalogefinished.db.MoviesHelper;
import com.bangkandar.moviecatalogefinished.entity.Movie;
import com.bangkandar.moviecatalogefinished.entity.TvShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.bangkandar.moviecatalogefinished.activity.MovieDetailActivity.EXTRA_MOVIE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements com.bangkandar.moviecatalogefinished.fragment.DbCallback.DbCallback {
    private ProgressBar mProgressBar;
    private MoviesHelper mMovieHelper;
    private AdapterMovies movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Movie> items = new ArrayList<>();

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_favorite, container, false);
        mProgressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rv_movie_fav);

        mMovieHelper = MoviesHelper.getINSTANCE(getContext());
        mMovieHelper.open();

        showRecyclerView();

        recyclerView.addOnItemTouchListener(new ItemClickSupport(getContext(), recyclerView, new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Movie mItem = new Movie();
                mItem.setId(items.get(position).getId());
                mItem.setJudul(items.get(position).getJudul());
                mItem.setDeskripsi(items.get(position).getDeskripsi());
                mItem.setRilis(items.get(position).getRilis());
                mItem.setRating(items.get(position).getRating());
                mItem.setBahasa(items.get(position).getBahasa());
                mItem.setGambar(items.get(position).getGambar());
                Intent detail = new Intent(getContext(), MovieDetailActivity.class);

                detail.putExtra(EXTRA_MOVIE, mItem);
                startActivity(detail);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Movie mItem = new Movie();
                mItem.setId(items.get(position).getId());
                mItem.setJudul(items.get(position).getJudul());
                mItem.setDeskripsi(items.get(position).getDeskripsi());
                mItem.setRilis(items.get(position).getRilis());
                mItem.setRating(items.get(position).getRating());
                mItem.setBahasa(items.get(position).getBahasa());
                mItem.setGambar(items.get(position).getGambar());
                Intent detail = new Intent(getContext(), MovieDetailActivity.class);

                detail.putExtra(EXTRA_MOVIE, mItem);
                startActivity(detail);
            }
        }));
        new MovieFavoriteFragment.LoadMovieAsync(mMovieHelper, this).execute();
        return view;
    }

    private void showRecyclerView() {
        movieAdapter = new AdapterMovies();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void preExecuteMovie() {

    }

    @Override
    public void preExecuteTv() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecuteMovie(ArrayList<Movie> movies) {
        mProgressBar.setVisibility(View.GONE);
        movieAdapter.setData(movies);
        items.addAll(movies);
    }

    @Override
    public void postExecuteTvshow(ArrayList<TvShow> tvShows) {

    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        WeakReference<MoviesHelper> moviesHelperWeakReference;
        WeakReference<com.bangkandar.moviecatalogefinished.fragment.DbCallback.DbCallback> loadFavCallbackWeakReference;

        public LoadMovieAsync(MoviesHelper movieHelper, DbCallback.DbCallback context) {
            moviesHelperWeakReference = new WeakReference<>(movieHelper);
            loadFavCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecuteMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> mItem) {
            super.onPostExecute(mItem);
            loadFavCallbackWeakReference.get().postExecuteMovie(mItem);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return moviesHelperWeakReference.get().getAllMovie();
        }
    }

}

package com.bangkandar.moviecatalogefinished.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.adapter.AdapterMovies;
import com.bangkandar.moviecatalogefinished.db.Database;
import com.bangkandar.moviecatalogefinished.entity.Movie;
import com.bangkandar.moviecatalogefinished.entity.TvShow;
import com.bangkandar.moviecatalogefinished.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements DbCallback {
    private ProgressBar progressBar;
    private AdapterMovies adapterMovies;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        RecyclerView rvFavorites = view.findViewById(R.id.rv_favorite);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorites.setHasFixedSize(true);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getActivity() != null) {
            getActivity().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        }

        movieAdapter = new MovieAdapter(getActivity());
        rvFavorites.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                movieAdapter.setMovieList(list);
            }
        }
        return view;

        public static class DataObserver extends ContentObserver {

            final Context context;

            public DataObserver(Handler handler, Context context) {
                super(handler);
                this.context = context;
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                new LoadNoteAsync(context, (LoadNotesCallback) context).execute();

            }
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNoteAsync(context, (LoadNotesCallback) context).execute();

        }
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<Note>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNoteAsync(Context context, LoadNotesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataCursor = context.getContentResolver().query(Database.MovieColumns.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapterMovies.getMovieList());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movies.size() > 0) {
            adapterMovies.setData(movies);
        } else {
            adapterMovies.setData(new ArrayList<Movie>());
        }
    }

}

interface DbCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);

    interface DbCallback {

        void preExecuteMovie();

        void preExecuteTv();
        void postExecute(Cursor movies);

        void postExecuteMovie(ArrayList<Movie> movies);

        void postExecuteTvshow(ArrayList<TvShow> tvShows);
    }
}

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
import com.bangkandar.moviecatalogefinished.activity.TvShowDetailActivity;
import com.bangkandar.moviecatalogefinished.adapter.AdapterTvShow;
import com.bangkandar.moviecatalogefinished.adapter.ItemClickSupport;
import com.bangkandar.moviecatalogefinished.db.TvshowsHelper;
import com.bangkandar.moviecatalogefinished.entity.Movie;
import com.bangkandar.moviecatalogefinished.entity.TvShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.bangkandar.moviecatalogefinished.activity.TvShowDetailActivity.EXTRA_TVSHOW;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFavoriteFragment extends Fragment implements com.bangkandar.moviecatalogefinished.fragment.DbCallback.DbCallback {

    private ProgressBar mProgressBar;
    private TvshowsHelper mTvshowHelper;
    private AdapterTvShow tvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<TvShow> items = new ArrayList<>();

    public TVFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow_favorite, container, false);
        mProgressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rv_tv_fav);

        mTvshowHelper = TvshowsHelper.getINSTANCE(getContext());
        mTvshowHelper.open();

        showRecyclerView();
        recyclerView.addOnItemTouchListener(new ItemClickSupport(getContext(), recyclerView, new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TvShow mItem = new TvShow();
                mItem.setId(items.get(position).getId());
                mItem.setJudul(items.get(position).getJudul());
                mItem.setDeskripsi(items.get(position).getDeskripsi());
                mItem.setRilis(items.get(position).getRilis());
                mItem.setPopular(items.get(position).getPopular());
                mItem.setRating(items.get(position).getRating());
                mItem.setBahasa(items.get(position).getBahasa());
                mItem.setGambar(items.get(position).getGambar());
                Intent detail = new Intent(getContext(), TvShowDetailActivity.class);
                detail.putExtra(EXTRA_TVSHOW, mItem);
                startActivity(detail);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        new TVFavoriteFragment.LoadTvAsync(mTvshowHelper, this).execute();
        return view;
    }

    private void showRecyclerView() {
        tvAdapter = new AdapterTvShow();
        tvAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(tvAdapter);
    }


    @Override
    public void preExecuteTv() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void preExecuteMovie() {

    }

    @Override
    public void postExecuteMovie(ArrayList<Movie> movies) {

    }

    @Override
    public void postExecuteTvshow(ArrayList<TvShow> tvShows) {
        mProgressBar.setVisibility(View.GONE);
        tvAdapter.setTvData(tvShows);
        items.addAll(tvShows);
    }

    private class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        WeakReference<TvshowsHelper> tvHelperWeakReference;
        WeakReference<com.bangkandar.moviecatalogefinished.fragment.DbCallback.DbCallback> loadFavCallbackWeakReference;

        public LoadTvAsync(TvshowsHelper tvHelper, DbCallback.DbCallback context) {
            tvHelperWeakReference = new WeakReference<>(tvHelper);
            loadFavCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecuteTv();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> mItem) {
            super.onPostExecute(mItem);
            loadFavCallbackWeakReference.get().postExecuteTvshow(mItem);
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
//            Log.d("test", "test" + tvHelperWeakReference.get().getAllTv());
            return tvHelperWeakReference.get().getAllTv();
        }
    }
}

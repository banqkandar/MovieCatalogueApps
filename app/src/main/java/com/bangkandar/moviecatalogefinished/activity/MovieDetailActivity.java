package com.bangkandar.moviecatalogefinished.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bangkandar.moviecatalogefinished.BuildConfig;
import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.db.MoviesHelper;
import com.bangkandar.moviecatalogefinished.entity.Movie;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MovieDetailActivity extends AppCompatActivity {
    private TextView tv_judul, tv_deskripsi, tv_rating, tv_bahasa, tv_rilis;
    private ImageView img_movie;
    private ProgressBar progressBar;
    private FloatingActionButton fab;

    public static final String EXTRA_MOVIES = "extra_movies";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;

    int position;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        img_movie = findViewById(R.id.img_poster);
        tv_judul = findViewById(R.id.txt_title);
        tv_rating = findViewById(R.id.txt_user_score);
        tv_bahasa = findViewById(R.id.txt_runtime);
        tv_rilis = findViewById(R.id.txt_rilis);
        tv_deskripsi = findViewById(R.id.txt_overview);

        progressBar = findViewById(R.id.progressDetailMovie);

        fab = findViewById(R.id.favorite);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIES);
        int id = movie.getId();
        getMovieDetails(id);

        if (movie != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isFavorite = false;
        } else {
            movie = new Movie();
        }

        Uri uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) movie = new Movie(cursor);
                cursor.close();
            }
        }
    }

    private void getMovieDetails(int id) {
        String url = "http://api.themoviedb.org/3/discover/movie/" + id + "?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    String response = new String(responseBody);
                    JSONObject object = new JSONObject(response);
                    Movie movies = new Movie(object);

                    String rating = Double.toString(movies.getRating());
                    String img_url = "http://image.tmdb.org/t/p/original/" + movies.getGambar();

                    tv_judul.setText(movies.getJudul());
                    tv_bahasa.setText(movies.getBahasa());
                    tv_deskripsi.setText(movies.getDeskripsi());
                    tv_rilis.setText(movies.getRilis());
                    tv_rating.setText(rating);
                    Glide.with(getApplicationContext())
                            .load(img_url)
                            .into(img_movie);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fabClick() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (insert && act) {
            movie.setJudul(movie.getJudul());
            movie.setGambar(movie.getGambar());
            movie.setBahasa(movie.getBahasa());
            movie.setRating(movie.getRating());
            movie.setRilis(movie.getRilis());
            movie.setDeskripsi(movie.getDeskripsi());
            long result = moviesHelper.insertMovie(movie);
            if (result > 0) {
                Toast.makeText(MovieDetailActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                Intent intent = new Intent(MovieDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (!delete && !act) {
            long result = moviesHelper.deleteMovie(movie.getJudul());
            if (result > 0) {
                Toast.makeText(MovieDetailActivity.this, R.string.success_hapus, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MovieDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MovieDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

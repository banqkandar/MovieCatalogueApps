package com.bangkandar.moviecatalogefinished.activity;

import android.content.Intent;
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

import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.db.TvshowsHelper;
import com.bangkandar.moviecatalogefinished.entity.TvShow;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class TvShowDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TVSHOW = "information";

    private TextView tv_judul, tv_deskripsi, tv_rating, tv_genre, tv_rilis, tv_bahasa;
    private ImageView img_tvshow;
    private ProgressBar progressBar;

    FloatingActionButton fab;

    TvshowsHelper tvshowsHelper;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        img_tvshow = findViewById(R.id.img_poster);
        tv_judul = findViewById(R.id.txt_title);
        tv_rating = findViewById(R.id.txt_user_score);
        tv_genre = findViewById(R.id.txt_genres);
        tv_rilis = findViewById(R.id.txt_rilis);
        tv_deskripsi = findViewById(R.id.txt_overview);
        tv_bahasa = findViewById(R.id.txt_bahasa);
        progressBar = findViewById(R.id.progressBarShowDetail);

        fab = findViewById(R.id.fab_tvshow);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        tvshowsHelper = TvshowsHelper.getINSTANCE(getApplicationContext());
        String tvShowTitle = tvShow.getJudul();
//        Log.d("test", "onCreate: " + tvShowTitle + tvshowsHelper.getOne(tvShowTitle));
        if (tvshowsHelper.getOne(tvShowTitle)) {
            //delete
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else if (!tvshowsHelper.getOne(tvShowTitle)) {
            //insert
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });

        data();

        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception ignored) {

                }
                handler.post(new Runnable() {
                    public void run() {
                        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

                        String rating = Double.toString(tvShow.getRating());
                        String popular = Double.toString(tvShow.getPopular());
                        String url_image = "https://image.tmdb.org/t/p/original" + tvShow.getGambar();

                        tv_judul.setText(tvShow.getJudul());
                        tv_rilis.setText(tvShow.getRilis());
                        tv_rating.setText(rating);
                        tv_genre.setText(popular);
                        tv_deskripsi.setText(tvShow.getDeskripsi());
                        tv_bahasa.setText(tvShow.getBahasa());

                        Glide.with(TvShowDetailActivity.this)
                                .load(url_image)
                                .placeholder(R.color.abu_terang)
                                .dontAnimate()
                                .into(img_tvshow);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    private void data() {
        progressBar.setVisibility(View.GONE);
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        String rating = Double.toString(tvShow.getRating());
        String popular = Double.toString(tvShow.getPopular());
        String url_image = "http://image.tmdb.org/t/p/original/" + tvShow.getGambar();

        tv_judul.setText(tvShow.getJudul());
        tv_deskripsi.setText(tvShow.getDeskripsi());
        tv_bahasa.setText(tvShow.getBahasa());
        tv_rating.setText(rating);
        tv_genre.setText(popular);
        tv_rilis.setText(tvShow.getRilis());
        Glide.with(getApplicationContext())
                .load(url_image)
                .placeholder(R.color.abu_terang)
                .dontAnimate()
                .into(img_tvshow);
    }

    private void fabClick() {
        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);
        Log.d("test", "fabClick: " + delete + act + insert);
        if (insert && act) {
            tvShow.setJudul(tvShow.getJudul());
            tvShow.setGambar(tvShow.getGambar());
            tvShow.setBahasa(tvShow.getBahasa());
            tvShow.setPopular(tvShow.getPopular());
            tvShow.setRating(tvShow.getRating());
            tvShow.setRilis(tvShow.getRilis());
            tvShow.setDeskripsi(tvShow.getDeskripsi());
            long result = tvshowsHelper.insertTvshow(tvShow);
            if (result > 0) {
                Toast.makeText(TvShowDetailActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                Intent intent = new Intent(TvShowDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(TvShowDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (!delete && !act) {
            long result = tvshowsHelper.deleteTvshow(tvShow.getJudul());
            if (result > 0) {
                Toast.makeText(TvShowDetailActivity.this, R.string.success_hapus, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TvShowDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(TvShowDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

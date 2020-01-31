package com.bangkandar.moviecatalogefinished.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.activity.MovieDetailActivity;
import com.bangkandar.moviecatalogefinished.entity.Movie;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import static com.bangkandar.moviecatalogefinished.activity.MovieDetailActivity.EXTRA_MOVIES;
import static com.bangkandar.moviecatalogefinished.activity.MovieDetailActivity.EXTRA_POSITION;
import static com.bangkandar.moviecatalogefinished.db.Database.MovieColumns.CONTENT_URI;

public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.GridViewHolder> {
    private Activity activity;
    private ArrayList<Movie> movieData = new ArrayList<>();

    public AdapterMovies(Activity activity) {
        this.activity = activity;
    }

    public AdapterMovies() {
    }

    public ArrayList<Movie> getMovieList() {
        return movieData;
    }

    public void setData(ArrayList<Movie> items) {
        movieData.clear();
        movieData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMovies.GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list, viewGroup, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Movie movies = getMovieList().get(position);
        String rating = Double.toString(movies.getRating());
        holder.tvTitle.setText(movies.getJudul());
        holder.tvRilis.setText(movies.getRilis());
        holder.tvRating.setText(rating);

        Glide.with(activity)
                .load("http://image.tmdb.org/t/p/original/" + movieData.get(position).getGambar())
                .into(holder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRilis, tvRating;
        ImageView imgMovie;

        GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txt_title);
            imgMovie = itemView.findViewById(R.id.img_poster);
            tvRating = itemView.findViewById(R.id.user_score);
            tvRilis = itemView.findViewById(R.id.txt_bahasa);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(activity, MovieDetailActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI + "/" + getMovieList().get(position).getId());
                    intent.setData(uri);
                    intent.putExtra(EXTRA_POSITION, position);
                    intent.putExtra(EXTRA_MOVIES, movieData.get(position));
                    activity.startActivityForResult(intent, MovieDetailActivity.REQUEST_UPDATE);
                }
            });
        }
    }
}

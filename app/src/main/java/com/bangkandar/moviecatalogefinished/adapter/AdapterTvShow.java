package com.bangkandar.moviecatalogefinished.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogefinished.R;
import com.bangkandar.moviecatalogefinished.activity.TvShowDetailActivity;
import com.bangkandar.moviecatalogefinished.entity.TvShow;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterTvShow extends RecyclerView.Adapter<AdapterTvShow.TvshowViewHolder> {
    private ArrayList<TvShow> tvShows = new ArrayList<>();

    public void setTvData(ArrayList<TvShow> items) {
        tvShows.clear();
        tvShows.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvshowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list, viewGroup, false);
        return new TvshowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvshowViewHolder holder, int position) {
        holder.bind(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    class TvshowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvRating, tvBahasa;
        ImageView imgTvshow;

        TvshowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txt_title);
            imgTvshow = itemView.findViewById(R.id.img_poster);
            tvRating = itemView.findViewById(R.id.user_score);
            tvBahasa = itemView.findViewById(R.id.txt_bahasa);
            itemView.setOnClickListener(this);
        }

        void bind(TvShow tvShow) {
            String rating = Double.toString(tvShow.getRating());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getGambar();
            tvTitle.setText(tvShow.getJudul());
            tvRating.setText(rating);
            tvBahasa.setText(tvShow.getBahasa());
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.abu_terang)
                    .dontAnimate()
                    .into(imgTvshow);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TvShow tvShow = tvShows.get(position);

            tvShow.setJudul(tvShow.getJudul());
            tvShow.setRating(tvShow.getRating());
            tvShow.setBahasa(tvShow.getBahasa());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), TvShowDetailActivity.class);
            moveWithObjectIntent.putExtra(TvShowDetailActivity.EXTRA_TVSHOW, tvShow);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }


}

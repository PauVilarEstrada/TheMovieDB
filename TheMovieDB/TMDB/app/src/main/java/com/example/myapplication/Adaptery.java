package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MovieDetailActivity;
import com.example.myapplication.MovieModelClass;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private static final int VIEW_TYPE_LIST = 1;
    private static final int VIEW_TYPE_GRID = 2;

    private Context mContext;
    private List<MovieModelClass> mData;
    private boolean isGrid;

    public Adaptery(Context mContext, List<MovieModelClass> mData, boolean isGrid) {
        this.mContext = mContext;
        this.mData = mData;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPE_LIST) {
            v = LayoutInflater.from(mContext).inflate(R.layout.movie_item, parent, false);
        } else {
            v = LayoutInflater.from(mContext).inflate(R.layout.grid_layout, parent, false);
        }
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText("Score: " + mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());
        holder.releaseDate.setText("Release Date: " + mData.get(position).getReleaseDate());
        holder.popularity.setText("Popularity: " + mData.get(position).getPopularity() + "%");
        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500" + mData.get(position).getImg())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieModelClass selectedMovie = mData.get(position);
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("title", selectedMovie.getName());
                intent.putExtra("release_date", selectedMovie.getReleaseDate());
                intent.putExtra("popularity", selectedMovie.getPopularity());
                intent.putExtra("overview", mData.get(position).getSynopsis());
                intent.putExtra("poster_path", mData.get(position).getImg());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return isGrid ? VIEW_TYPE_GRID : VIEW_TYPE_LIST;
    }

    public void setGrid(boolean grid) {
        this.isGrid = grid;
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView name;
        private ImageView img;
        private TextView releaseDate;
        private TextView popularity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_txt);
            name = itemView.findViewById(R.id.name_txt);
            img = itemView.findViewById(R.id.imageView2);
            releaseDate = itemView.findViewById(R.id.release_date_txt);
            popularity = itemView.findViewById(R.id.popularity_txt);

        }
    }

}

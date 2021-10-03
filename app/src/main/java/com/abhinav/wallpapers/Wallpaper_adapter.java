package com.abhinav.wallpapers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Wallpaper_adapter extends RecyclerView.Adapter<Wallpaper_adapter.ViewHolder> {
    private ArrayList<String> wallpaperImageArrayList;
    private Context context;

    public Wallpaper_adapter(ArrayList<String> wallpaperImageArrayList, Context context) {
        this.wallpaperImageArrayList = wallpaperImageArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Wallpaper_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item,parent,false);
        return new Wallpaper_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Wallpaper_adapter.ViewHolder holder, int position) {
        Glide.with(context).load(wallpaperImageArrayList.get(position)).into(holder.wallpaperImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,Wallpaper_Second_Activity.class);
                intent.putExtra("imageUrl",wallpaperImageArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperImageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView wallpaperImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperImageView = itemView.findViewById(R.id.idIVWallpaper);
        }
    }
}

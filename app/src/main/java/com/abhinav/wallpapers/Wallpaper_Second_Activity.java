package com.abhinav.wallpapers;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;

public class Wallpaper_Second_Activity extends AppCompatActivity {
    private ImageView wallpaperImageView;
    private Button set_wallpaper_button;
    private String imgUrl;
    WallpaperManager wallpaperManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper__second_);
        wallpaperImageView = findViewById(R.id.second_activity_image_view);
        set_wallpaper_button = findViewById(R.id.set_wallpaper_button);
        imgUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imgUrl).into(wallpaperImageView);
        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        set_wallpaper_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(Wallpaper_Second_Activity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(Wallpaper_Second_Activity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(Wallpaper_Second_Activity.this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                }).submit();
                FancyToast.makeText(Wallpaper_Second_Activity.this,"Wallpaper set to home screen",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            }

        });



    }
}
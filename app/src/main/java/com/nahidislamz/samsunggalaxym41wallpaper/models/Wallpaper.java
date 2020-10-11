package com.nahidislamz.samsunggalaxym41wallpaper.models;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nahidislamz.samsunggalaxym41wallpaper.R;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;


public class Wallpaper extends AbstractItem<Wallpaper,Wallpaper.WallpaperViewHolder> {

    public int uid;
    public String  url;


    public Wallpaper(int uid, String url) {
        this.uid=uid;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public long getIdentifier(){
        return uid;
    }
    @Override
    public int getType() {
        return R.id.card_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recyclerview_wallpapers;
    }



    @NonNull
    @Override
    public WallpaperViewHolder getViewHolder(View v) {
        return new WallpaperViewHolder(v);
    }


    public static class WallpaperViewHolder extends FastAdapter.ViewHolder<Wallpaper> {

        ImageView imageView;
        ProgressBar progressBar;

        public WallpaperViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            progressBar = itemView.findViewById(R.id.progressbar);

        }

        @Override
        public void bindView(Wallpaper item, List<Object> payloads) {

            CubeGrid cubeGrid = new CubeGrid();
            progressBar.setIndeterminateDrawable(cubeGrid);
            progressBar.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext())
                    .load(item.url)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
        }
        @Override
        public void unbindView(Wallpaper item) {
        }
    }

}

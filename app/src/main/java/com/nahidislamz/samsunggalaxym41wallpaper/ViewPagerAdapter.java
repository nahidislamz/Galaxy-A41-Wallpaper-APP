package com.nahidislamz.samsunggalaxym41wallpaper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nahidislamz.samsunggalaxym41wallpaper.ui.common.Common;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> pList;

    ViewPagerAdapter(Context context, List<String> pList) {
        this.context = context;
        this.pList=pList;
    }

    @Override
    public int getCount() {
        return pList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        final ProgressBar progressBar = new ProgressBar(context);
        DoubleBounce doubleBounce =new DoubleBounce();
        doubleBounce.setColor(R.color.colorPrimary);
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.VISIBLE);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .load(pList.get(position))
                .override(Common.WIDTH,Common.HIEGHT)
                .fitCenter().centerCrop()
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
                .into(imageView)
        ;

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
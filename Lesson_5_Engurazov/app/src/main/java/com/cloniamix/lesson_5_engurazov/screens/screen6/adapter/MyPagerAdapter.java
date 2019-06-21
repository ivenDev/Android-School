package com.cloniamix.lesson_5_engurazov.screens.screen6.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cloniamix.lesson_5_engurazov.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<String> imageUrls;

    public MyPagerAdapter( ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String url = imageUrls.get(position);

        View layout = LayoutInflater.from(container.getContext()).inflate(R.layout.image_view, container, false);
        container.addView(layout);
        ImageView imageView = container.findViewById(R.id.imageView);
        Glide.with(layout)
                .load(url)
                .centerCrop()
                /*.skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)*/
                .placeholder(container.getContext().getResources().getDrawable(R.drawable.ic_crop))
                .into(imageView);

        return layout;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}

package com.oywj.usefulviews.ui.callback;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oywj.usefulviews.ui.views.BannerLayout;

public class GlideImageLoader implements BannerLayout.ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        final RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(context).load(path).apply(options).into(imageView);
    }
}

package com.oywj.usefulviews.ui.callback;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oywj.usefulviews.R;
import com.oywj.usefulviews.ui.views.BannerLayout;

public class GlideImageLoader implements BannerLayout.ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        final RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.mipmap.ad_default)
                .error(R.mipmap.ad_default);
        Glide.with(context).load(path).apply(options).into(imageView);
    }
}

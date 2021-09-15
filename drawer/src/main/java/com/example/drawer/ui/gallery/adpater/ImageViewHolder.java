package com.example.drawer.ui.gallery.adpater;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.drawer.databinding.ItemWithImageBinding;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.ZHActivityManager;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class ImageViewHolder extends BaseViewHolder {
    private static final String TAG = "ImageViewHolder";

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public ImageViewHolder(ItemWithImageBinding itemWithImageBinding) {
        super(itemWithImageBinding.getRoot());
        viewDataBinding = itemWithImageBinding;
    }

    @BindingAdapter("imageUrl")
    public static final void setImage(ImageView imageView, String url) {
        ZHLog.d(TAG, "load imageView with " + url);
        Glide.with(ZHActivityManager.INSTANCE.getContext()).load(url).into(imageView);
    }
}

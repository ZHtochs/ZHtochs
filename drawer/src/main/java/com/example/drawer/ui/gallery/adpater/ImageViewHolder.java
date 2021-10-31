package com.example.drawer.ui.gallery.adpater;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.example.drawer.databinding.ItemWithImageBinding;
import com.example.drawer.ui.gallery.beans.ImageBean;
import com.example.drawer.ui.gallery.beans.ItemBean;
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
    public static void setImage(ImageView imageView, String url) {
        ZHLog.d(TAG, "load imageView with " + url);
        Glide.with(ZHActivityManager.INSTANCE.getContext()).load(url).into(imageView);
    }

    @Override
    protected void onBind(ItemBean itemBean,int position) {
        super.onBind(itemBean, position);
        ((ItemWithImageBinding) viewDataBinding).setImageViewEntry((ImageBean) itemBean);
    }
}

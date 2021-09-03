package com.example.drawer.ui.gallery.adpater;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    public static final int IMAGE_TYPE = 2;
    public static final int TEXT_TYPE = 1;

    public ViewDataBinding viewDataBinding;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}

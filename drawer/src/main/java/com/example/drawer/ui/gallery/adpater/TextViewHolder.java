package com.example.drawer.ui.gallery.adpater;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.drawer.databinding.ItemTextOnlyBinding;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class TextViewHolder extends BaseViewHolder {
    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public TextViewHolder(ItemTextOnlyBinding itemTextOnlyBinding) {
        super(itemTextOnlyBinding.getRoot());
        viewDataBinding = itemTextOnlyBinding;
    }
}

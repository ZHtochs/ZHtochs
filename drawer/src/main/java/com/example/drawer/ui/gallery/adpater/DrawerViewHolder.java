package com.example.drawer.ui.gallery.adpater;

import android.view.Gravity;
import android.view.View;
import androidx.annotation.NonNull;
import com.example.drawer.databinding.ItemDrawerBinding;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.DrawerLayoutUtil;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-10-29 23:04
 **/
public class DrawerViewHolder extends BaseViewHolder {
    private static final String TAG = "DrawerViewHolder";

    public DrawerViewHolder(@NonNull ItemDrawerBinding drawerBinding) {
        super(drawerBinding.getRoot());
        viewDataBinding = drawerBinding;
    }

    @Override
    protected void onBind(ItemBean itemBean, int position) {
        super.onBind(itemBean, position);
        ItemDrawerBinding binding = (ItemDrawerBinding) viewDataBinding;
        DrawerLayoutUtil.setDrawerLeftEdgeSize(viewDataBinding.getRoot().getContext(), binding.drawerLayout, 0.5, Gravity.RIGHT);
        binding.viewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLister.delete(getLayoutPosition());
                binding.drawerLayout.closeDrawers();
                binding.drawerLayout.setVisibility(View.GONE);
            }
        });
    }
}
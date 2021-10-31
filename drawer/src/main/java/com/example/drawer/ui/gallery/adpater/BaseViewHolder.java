package com.example.drawer.ui.gallery.adpater;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.ZHLog;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "BaseViewHolder";
    public static final int IMAGE_TYPE = 2;
    public static final int TEXT_TYPE = 1;
    public static final int DRAWER_TYPE = 3;

    public ViewDataBinding viewDataBinding;

    protected MyAdapter.DeleteLister deleteLister;

    protected int position;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        ZHLog.d(TAG, "BaseViewHolder");
    }

    protected void onBind(ItemBean itemBean, int position) {
        this.position = position;
    }

    public void setDeleteLister(MyAdapter.DeleteLister deleteLister) {
        this.deleteLister = deleteLister;
    }
    public void setDeleteLister(A a) {
    }

    interface A {
        void test();
    }
    interface B {
        void test();
    }
}

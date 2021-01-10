package com.example.drawer.ui.gallery;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.domain.GetTextItem;
import com.example.drawer.R;

import java.util.List;

public class MyAdapter extends BaseQuickAdapter<GetTextItem, BaseViewHolder> {
    public MyAdapter(int layoutResId, @Nullable List<GetTextItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetTextItem item) {
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.publish_data, item.getPublishTime());
        Glide.with(helper.itemView.getContext()).load(item.getCover())
                .into((ImageView) helper.getView(R.id.imageview));
    }
}

package com.example.drawer.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.databinding.DataBindingUtil;
import com.example.drawer.R;
import com.example.drawer.databinding.ItemImageBinding;
import com.example.drawer.ui.gallery.beans.ImageBean;

import java.util.ArrayList;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-09-15 23:02
 **/
public class GridAdapter extends BaseAdapter {

    private ArrayList<ImageBean> imageBeans;

    public GridAdapter() {
    }

    public GridAdapter(ArrayList<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
    }

    @Override
    public int getCount() {
        return imageBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return imageBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemImageBinding itemImageBinding;
        if (convertView == null) {
            itemImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_image, parent, false);
            convertView = itemImageBinding.getRoot();
            convertView.setTag(itemImageBinding);
        } else {
            itemImageBinding = (ItemImageBinding) convertView.getTag();
        }
        itemImageBinding.setImageViewEntry(imageBeans.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemImageBinding.getRoot().setPressed(true);
            }
        });
        return itemImageBinding.getRoot();
    }
}
package com.example.drawer.ui.gallery.adpater;

import static com.example.drawer.ui.gallery.adpater.BaseViewHolder.IMAGE_TYPE;
import static com.example.drawer.ui.gallery.adpater.BaseViewHolder.TEXT_TYPE;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.databinding.ItemTextOnlyBinding;
import com.example.drawer.databinding.ItemWithImageBinding;
import com.example.drawer.ui.gallery.beans.ImageBean;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.ZHLog;

import java.util.ArrayList;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = MyAdapter.class.getName();
    ArrayList<? extends ItemBean> arrayList = new ArrayList<>();

    public MyAdapter(ArrayList<ItemBean> itemBeans) {
        this.arrayList = itemBeans;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder;
        switch (viewType) {
            case IMAGE_TYPE:
                ItemWithImageBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_with_image
                    , parent, false);
                baseViewHolder = new ImageViewHolder(viewDataBinding);
                break;
            case TEXT_TYPE:
                ItemTextOnlyBinding textOnlyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_text_only
                    , parent, false);
                baseViewHolder = new TextViewHolder(textOnlyBinding);
                break;
            default:
                throw new IllegalArgumentException("invalid type with :" + viewType);
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        ZHLog.d(TAG, "viewType  " + viewType + " position " + position);
        switch (viewType) {
            case IMAGE_TYPE:
                ItemWithImageBinding itemWithImageBinding = (ItemWithImageBinding) holder.viewDataBinding;
                itemWithImageBinding.setImageViewEntry((ImageBean) arrayList.get(position));
                break;
            case TEXT_TYPE:
                ItemTextOnlyBinding textOnlyBinding = (ItemTextOnlyBinding) holder.viewDataBinding;
                textOnlyBinding.setTextViewEntry(arrayList.get(position));
                break;
            default:
                throw new IllegalArgumentException("invalid type with :" + viewType);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = arrayList.get(position) instanceof ImageBean ? IMAGE_TYPE : TEXT_TYPE;
        ZHLog.d(TAG, "position " + position + " type " + type);
        return type;
    }
}

package com.example.drawer.ui.gallery.adpater;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.R;
import com.example.drawer.databinding.ItemDrawerBinding;
import com.example.drawer.databinding.ItemTextOnlyBinding;
import com.example.drawer.databinding.ItemWithImageBinding;
import com.example.drawer.ui.gallery.GalleryFragment;
import com.example.drawer.ui.gallery.beans.DrawerBean;
import com.example.drawer.ui.gallery.beans.ImageBean;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.ZHLog;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.drawer.ui.gallery.adpater.BaseViewHolder.*;

/**
 * 功能描述
 *
 * @author zhuhe
 * @since 2021-09-03
 */
public class MyAdapter extends RecyclerView.Adapter<BaseViewHolder> implements GalleryFragment.ItemTouchStatus {

    private static final String TAG = "MyAdapter";
    ArrayList<ItemBean> arrayList = new ArrayList<>();

    public MyAdapter(ArrayList<ItemBean> itemBeans) {
        this.arrayList = itemBeans;
    }

    public void setArrayList(ArrayList<ItemBean> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<ItemBean> getArrayList() {
        return arrayList;
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
            case DRAWER_TYPE:
                ItemDrawerBinding drawerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_drawer
                        , parent, false);
                baseViewHolder = new DrawerViewHolder(drawerBinding);
                break;
            default:
                throw new IllegalArgumentException("invalid type with :" + viewType);
        }
        baseViewHolder.setIsRecyclable(false);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        ZHLog.d(TAG, "onBindViewHolder viewType  " + viewType + " position " + position);
        holder.onBind(arrayList.get(position), position);
        holder.setDeleteLister(new DeleteListerImpl());
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ItemBean bean = arrayList.get(position);
        if (bean instanceof DrawerBean) {
            return DRAWER_TYPE;
        }
        if (bean instanceof ImageBean) {
            return IMAGE_TYPE;
        }
        return TEXT_TYPE;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(arrayList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public boolean onItemRemove(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        return false;
    }

    private class DeleteListerImpl implements DeleteLister {
        @Override
        public void delete(int position) {
            arrayList.remove(position);
            //RecycleView移除当前子项（有动画效果）
            notifyItemRemoved(position);
            //RecycleView局部更新，防止position错乱
            if (position != arrayList.size()) {
                notifyItemRangeChanged(position, arrayList.size() - position);
            }
        }
    }

    interface DeleteLister {
        void delete(int position);
    }
}

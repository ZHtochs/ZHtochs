package com.example.drawer.ui.slideshow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-20 01:04
 **/
public abstract class DeleteViewHolder extends RecyclerView.ViewHolder {

    protected ViewBinding binding;

    public DeleteViewHolder(@NonNull @NotNull ViewBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

    public abstract void bindViewEntry(int position, @DeleteWrapperAdapter.Duration int state, DeleteListener deleteListener);
}
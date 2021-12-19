package com.example.drawer.ui.paging;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.databinding.ItemPagingBinding;
import com.github.zhtouchs.Utils.ZHLog;

public class MyPagedAdapter extends PagingDataAdapter<Student, MyPagedAdapter.MyViewHolder> {
    private static final String TAG = "MyPagedAdapter";

    public MyPagedAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return true;
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ZHLog.d(TAG, "onBindViewHolder");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPagingBinding itemPagingBinding = ItemPagingBinding.inflate(inflater);
        return new MyViewHolder(itemPagingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ZHLog.d(TAG, "onBindViewHolder");
        Student student = getItem(position);
        if (student != null) {
            holder.itemPagingBinding.studentId.setText("" + student.getId());
            holder.itemPagingBinding.studentNumber.setText("" + student.getStudentName());
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPagingBinding itemPagingBinding;

        public MyViewHolder(@NonNull ItemPagingBinding itemPagingBinding) {
            super(itemPagingBinding.getRoot());
            this.itemPagingBinding = itemPagingBinding;
        }
    }
}

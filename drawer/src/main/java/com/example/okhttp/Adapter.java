package com.example.okhttp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.domain.GetTextItem;
import com.example.drawer.R;
import com.github.zhtouchs.Utils.ZHLog;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.InnterHolder> {
    private static final String TAG = "Adapter";

    private List<GetTextItem> getTextItems;

    @NonNull
    @Override
    public InnterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_text_layout, parent, false);
        return new InnterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnterHolder holder, int position) {
        GetTextItem item = getTextItems.get(position);
        ZHLog.d(TAG, item);
        View view = holder.itemView;
        TextView textView = view.findViewById(R.id.title);
        textView.setText(getTextItems.get(position).getTitle());
        TextView textView2 = view.findViewById(R.id.id);
        textView2.setText(getTextItems.get(position).getId());
        TextView textView3 = view.findViewById(R.id.user_name);
        textView3.setText(getTextItems.get(position).getUserName());
        TextView textView4 = view.findViewById(R.id.publish_time);
        textView4.setText(getTextItems.get(position).getPublishTime());
        ImageView imageView = view.findViewById(R.id.imageview);
        Glide.with(view.getContext()).load(OkHttpTest.URL + item.getCover()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return getTextItems.size();
    }

    public List<GetTextItem> getGetTextItems() {
        return getTextItems;
    }

    public void setGetTextItems(List<GetTextItem> getTextItems) {
        this.getTextItems = getTextItems;
    }

    public class InnterHolder extends RecyclerView.ViewHolder {
        public InnterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

package com.example.drawer.ui.slide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.R;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-11-17 23:50
 **/
public class SlideLayoutAdapter extends RecyclerView.Adapter<SlideLayoutAdapter.SlideLayoutViewHolder> {
    private static final String TAG = "SlideLayoutAdapter";

    private SlideLayout openedSlideLayout;

    private List<Object> list;

    public SlideLayoutAdapter(List<Object> list) {
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public SlideLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SlideLayoutViewHolder(inflater.inflate(R.layout.item_slide, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideLayoutViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class SlideLayoutViewHolder extends RecyclerView.ViewHolder {
        public SlideLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind() {
            SlideLayout slidingPaneLayout = itemView.findViewById(R.id.slide);
            slidingPaneLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            slidingPaneLayout.setParallaxDistance(itemView.getContext().getResources().getDimensionPixelOffset(R.dimen.slide_2));
            ZHLog.d(TAG, "slidingPaneLayout " + slidingPaneLayout);
            itemView.findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (slidingPaneLayout.isOpen()) {
                        return;
                    }
                    Toast.makeText(itemView.getContext(), "onClick", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.findViewById(R.id.content).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (slidingPaneLayout.isOpen()) {
                        return false;
                    }
                    Toast.makeText(itemView.getContext(), "onLongClick", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            itemView.findViewById(R.id.side).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (slidingPaneLayout.isOpen()) {
                        Toast.makeText(itemView.getContext(), "onClick side", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            slidingPaneLayout.setListener(new SlideLayout.SlideListener() {
                @Override
                public void isGoingToSlide(SlideLayout slideLayout) {
                    ZHLog.d(TAG, "isGoingToSlide");
                    if (openedSlideLayout != null && openedSlideLayout != slideLayout
                            && openedSlideLayout.isOpen()) {
                        ZHLog.d(TAG, "isGoingToClose");
                        openedSlideLayout.closePane();
                    }
                }

                @Override
                public void onPanelSlide(@NonNull @NotNull View panel, float slideOffset) {
                }

                @Override
                public void onPanelOpened(@NonNull @NotNull View panel) {
                    ZHLog.d(TAG, "onPanelOpened");
                    openedSlideLayout = slidingPaneLayout;
                }

                @Override
                public void onPanelClosed(@NonNull @NotNull View panel) {
                    ZHLog.d(TAG, "onPanelClosed");
                }
            });
        }
    }

    public final void appendList(List<Object> data) {
        int positionStart = list.size();
        list.addAll(data);
        int itemCount = list.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(positionStart + 1, itemCount);
        }
    }
}
package com.example.drawer;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.ui.slide.SlideLayoutAdapter;
import com.github.zhtouchs.activity.BaseActivity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-11-17 22:31
 **/
public class TestActivity extends BaseActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_fragment);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new Object());
        }
        SlideLayoutAdapter adapter = new SlideLayoutAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) {
//                    LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int position = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
//                    ZHLog.d(TAG, "onScrolled position " + position);
//                    if (adapter.getItemCount() - position < 10) {
//                        ZHLog.d(TAG, "addItem");
//                        List<Object> list = new ArrayList<>();
//                        for (int i = 0; i < 20; i++) {
//                            list.add(new Object());
//                        }
//                        recyclerView.post(() -> adapter.appendList(list));
//                    }
//                }
            }
        });
    }
}
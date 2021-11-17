package com.example.drawer;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.ui.slide.SlideLayoutAdapter;
import com.github.zhtouchs.activity.BaseActivity;

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
        SlideLayoutAdapter adapter = new SlideLayoutAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
package com.example.drawer.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.domain.GetTextItem;
import com.example.drawer.R;
import com.github.zhtouchs.Utils.ZHLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    private RecyclerView recyclerView;

    private final List<GetTextItem> getTextItem = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = root.findViewById(R.id.recycle);
        for (int i = 0; i < 10; i++) {
            getTextItem.add(GetTextItem.getTextItemCreator());
        }
        MyAdapter myAdapter = new MyAdapter(R.layout.cardview, getTextItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        myAdapter.isFirstOnly(false);
        myAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                int i = new Random().nextInt(3);
                ZHLog.d(TAG, "onLoadMoreRequested:" + i);
                if (i == 0) {
                    //数据全部加载完毕
                    myAdapter.loadMoreEnd();
                }
                if (i == 1) {
                    for (int j = 0; j < 10; j++) {
                        myAdapter.addData(GetTextItem.getTextItemCreator());
                    }
                    myAdapter.loadMoreComplete();
                }
                if (i == 2) {
                    myAdapter.loadMoreFail();
                }
            }
        }, recyclerView);
        return root;
    }
}
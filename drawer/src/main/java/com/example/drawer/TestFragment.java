package com.example.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.drawer.databinding.DragViewLayoutBinding;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-02 23:30
 **/
public class TestFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TestFragment";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        DragViewLayoutBinding dragViewLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.drag_view_layout,
                container, false);
        dragViewLayoutBinding.setInfo(this);
        return dragViewLayoutBinding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                ZHLog.d(TAG, "tv1");
                break;
            case R.id.tv2:
                ZHLog.d(TAG, "tv2");
                break;
            case R.id.tv3:
                ZHLog.d(TAG, "tv3");
                break;
        }
    }
}
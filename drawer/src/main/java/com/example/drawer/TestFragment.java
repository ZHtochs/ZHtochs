package com.example.drawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.drawer.databinding.DragViewLayoutBinding;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.activity.BaseFragment;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-02 23:30
 **/
public class TestFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "TestFragment";

    private Pair pair;

    DragViewLayoutBinding dragViewLayoutBinding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ZHLog.d(TAG, "onCreateView");
        dragViewLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.drag_view_layout,
                container, false);
        dragViewLayoutBinding.setInfo(this);
        dragViewLayoutBinding.button.setOnClickListener(this);
        FragmentManager parentFragmentManager = getChildFragmentManager();
        parentFragmentManager.beginTransaction().add(R.id.fragment_container, new TestFragment2()).commit();
        ZHLog.d(TAG, pair + " " + this);
        return dragViewLayoutBinding.getRoot();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        ZHLog.d(TAG, "onAttach " + this);
    }

    public void setAbc(Pair pair) {
        ZHLog.d(TAG, "setAbc");
        this.pair = pair;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Activity activity = getActivity();
                TestActivity testActivity = (TestActivity) activity;
                ViewPager2 viewPager2 = testActivity.findViewById(R.id.view_pager2);
                FragmentStateAdapter fragmentStateAdapter = (FragmentStateAdapter) viewPager2.getAdapter();
                ZHLog.d(TAG, TestFragment.this);
                ZHLog.d(TAG, "view_pager2 " + viewPager2);
                ZHLog.d(TAG, "pair " + pair);
                break;
        }
    }
}
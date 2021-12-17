package com.github.zhtouchs.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.zhtouchs.Utils.ZHLog;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-11 20:57
 **/
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ZHLog.d(TAG, "onAttach " + this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZHLog.d(TAG, "onCreate " + this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ZHLog.d(TAG, "onCreateView " + this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ZHLog.d(TAG, "onStart " + this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ZHLog.d(TAG, "onResume " + this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ZHLog.d(TAG, "onPause " + this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ZHLog.d(TAG, "onStop " + this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ZHLog.d(TAG, "onDestroy " + this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ZHLog.d(TAG, "onDetach " + this);
    }
}
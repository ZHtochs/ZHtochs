package com.example.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.drawer.databinding.TestLayoutBinding;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-02 23:30
 **/
public class TestFragment2 extends Fragment implements View.OnClickListener {
    private static final String TAG = "TestFragment";

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TestLayoutBinding dragViewLayoutBinding = TestLayoutBinding.inflate(inflater ,container, false);
        return dragViewLayoutBinding.getRoot();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                ZHLog.d(TAG, "button");
                break;
            case R.id.button2:
                ZHLog.d(TAG, "button2");
                break;

        }
    }
}
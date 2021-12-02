package com.example.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.drawer.databinding.DragViewLayoutBinding;
import org.jetbrains.annotations.NotNull;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-02 23:30
 **/
public class TestFragment extends Fragment {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        DragViewLayoutBinding dragViewLayoutBinding = DragViewLayoutBinding.inflate(
                inflater
                , container, false);
        return dragViewLayoutBinding.getRoot();
    }
}
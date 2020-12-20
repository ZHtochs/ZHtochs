package com.example.app.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.MobileNavigationArgs;
import com.example.app.R;
import com.example.app.ui.home.HomeViewModel;
import com.github.zhtouchs.Utils.ZHLog;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ZHLog.d(TAG, "onCreateView");

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        if (getArguments() != null) {
            int age = MobileNavigationArgs.fromBundle(getArguments()).getAge();
            String name = MobileNavigationArgs.fromBundle(getArguments()).getName();
            ZHLog.d(TAG, age + "  " + name);
        }
        HomeViewModel homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        ZHLog.d(TAG, "homeViewModel:" + homeViewModel);
        return root;
    }
}
package com.example.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.app.MobileNavigationArgs;
import com.example.app.R;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;

    private Button button;

    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ZHLog.d(TAG, "onCreateView");
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        ZHLog.d(TAG, "homeViewModel:" + homeViewModel);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        button = root.findViewById(R.id.button_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ZHLog.d(TAG, "onChanged:" + s);
                textView.setText(s);
            }
        });
        ZHThreadPool.INSTANCE.runOnUi(TAG, 2000, new Runnable() {
            @Override
            public void run() {
                homeViewModel.setmText("1231");
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new MobileNavigationArgs.Builder().setAge(123).setName("456").build().toBundle();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHLog.d(TAG, "onClick");
                homeViewModel.setmText("this is home fragment");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
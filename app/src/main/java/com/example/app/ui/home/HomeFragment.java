package com.example.app.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.app.MobileNavigationArgs;
import com.example.app.R;
import com.github.zhtouchs.Utils.ZHLog;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";
    private final static String CODELABS_ACTION = "com.huawei.codelabpush.action";

    private HomeViewModel homeViewModel;

    private Button getid;
    private Button removeid;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ZHLog.d(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        getid = root.findViewById(R.id.getid);
        getid.setOnClickListener(this);
        removeid = root.findViewById(R.id.removeid);
        removeid.setOnClickListener(this);
        return root;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getid:
                ZHLog.d(TAG,"getid");
                Intent intent = new Intent();
                intent.setAction(CODELABS_ACTION);
                intent.putExtra("method", "onMessageSent");

                getActivity().sendBroadcast(intent);
                break;
            case R.id.removeid:

                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = new MobileNavigationArgs.Builder().setAge(123).setName("456").build().toBundle();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
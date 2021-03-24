package com.example.app.ui.home;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.app.Activity2;
import com.example.app.MobileNavigationArgs;
import com.example.app.R;
import com.example.app.hook.HookHelper;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.ZHActivityManager;

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
        root.findViewById(R.id.removeid2).setOnClickListener(this);
        return root;
    }


    @Override
    public void onClick(View v) {
        ZHLog.d(TAG, "onClick " + v.getId());

        switch (v.getId()) {
            case R.id.getid:
                String str = Settings.System.getString(ZHActivityManager.INSTANCE.getContext()
                                .getContentResolver(),
                        Settings.System.NEXT_ALARM_FORMATTED);
                ZHLog.d(TAG, str);
                AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
                AlarmManager.AlarmClockInfo info = alarmManager.getNextAlarmClock();
                alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(1616369400000L + 86400000L, null)
                        , null);
                break;
            case R.id.removeid:
                try {
                    HookHelper.hookAMS();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.removeid2:
                getActivity().startActivity(new Intent(getActivity(), Activity2.class));
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
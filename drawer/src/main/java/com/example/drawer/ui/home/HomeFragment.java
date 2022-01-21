package com.example.drawer.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.example.domain.FeedReaderDbHelper;
import com.example.drawer.R;
import com.example.drawer.databinding.FragmentHomeBinding;
import com.example.drawer.databinding.ItemTextOnlyBinding;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.activity.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    private final String urlString = "https://pic1.zhimg.com/80/v2-15b2a9a8a8ac38d7ddd47fe9b792232b_720w.jpg?source=1940ef5c";
    private HomeViewModel homeViewModel;

    private ExecutorService service = Executors.newSingleThreadExecutor();

    FragmentHomeBinding fragmentHomeBinding;

    FeedReaderDbHelper dbHelper;

    private boolean isExpanded = false;

    Handler looper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ZHLog.d(TAG, "onChanged" + s);
            }
        });
        homeViewModel.setmText("Sadasdada");
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = fragmentHomeBinding.getRoot();
        root.findViewById(R.id.button_get).setOnClickListener(this);
        root.findViewById(R.id.button_post).setOnClickListener(this);
        root.findViewById(R.id.button_upload_file).setOnClickListener(this);
        root.findViewById(R.id.button_download_file).setOnClickListener(this);
        SlidingPaneLayout slidingPaneLayout = root.findViewById(R.id.slide);
        slidingPaneLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        slidingPaneLayout.setParallaxDistance(getResources().getDimensionPixelOffset(R.dimen.dp_40));
        dbHelper = new FeedReaderDbHelper(getActivity());
        ArrayList<View> arrayList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ItemBean bean = new ItemBean("6", "" + new Random().nextFloat());
            ItemTextOnlyBinding itemTextOnlyBinding
                    = ItemTextOnlyBinding.inflate(getLayoutInflater(), fragmentHomeBinding.testLinearLayout, false);
            itemTextOnlyBinding.setTextViewEntry(bean);
            arrayList.add(itemTextOnlyBinding.getRoot());
        }
        fragmentHomeBinding.testLinearLayout.setViewList(arrayList);
        return root;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get:
                fragmentHomeBinding.testLinearLayout.switchState();
                break;
            case R.id.button_post:
                break;
            case R.id.button_upload_file:
                getActivity().finish();
                break;

            case R.id.button_download_file:
                ZHLog.d(TAG, "onClick");
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.view_anim);
                animation.setFillAfter(true);
                animation.setFillBefore(false);
                fragmentHomeBinding.buttonDownloadFile.startAnimation(animation);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ZHLog.d(TAG, "requestCode:" + requestCode + permissions[0]);
    }
}
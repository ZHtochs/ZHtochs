package com.example.drawer.ui.home;

import android.media.MediaPlayer;
import android.net.Uri;
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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    private final String urlString = "https://pic1.zhimg.com/80/v2-15b2a9a8a8ac38d7ddd47fe9b792232b_720w.jpg?source=1940ef5c";
    private HomeViewModel homeViewModel;

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    FragmentHomeBinding fragmentHomeBinding;

    FeedReaderDbHelper dbHelper;

    private final boolean isExpanded = false;

    Handler looper;
    private List<String> list;

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
        addView(15);
        return root;
    }

    private void addView(int size) {
        ArrayList<ItemBean> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ItemBean bean = new ItemBean("6", "" + new Random().nextFloat());
            arrayList.add(bean);
        }

        fragmentHomeBinding.testLinearLayout.setViewList(arrayList, R.layout.item_text_only, (viewBinding, itemBean) -> {
            if (viewBinding instanceof ItemTextOnlyBinding) {
                ItemTextOnlyBinding textOnlyBinding = (ItemTextOnlyBinding) viewBinding;
                textOnlyBinding.setTextViewEntry(itemBean);
            }
        });
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

    public static String au = "com.example.myapplication.test";
    public static String au2 = "com.example.myapplication.fileProvider";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get:
                Bundle call = getActivity().getContentResolver().call(Uri.parse("content://" + au), "", "", null);
                String string = call.getString(au);
                Uri uri = Uri.parse(string);
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getActivity(), uri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_post:
                Bundle call2 = getActivity().getContentResolver().call(Uri.parse("content://" + au), "", "", null);
                String string2 = call2.getString(au);
                ZHLog.d(TAG, "zhuhe " + string2);
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(Uri.parse(string2));
                    File file = new File(getActivity().getFilesDir() + File.separator + "mp3.mp3");
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] b = new byte[1024];
                    while ((is.read(b)) != -1) {
                        fos.write(b);// 写入数据
                    }
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_upload_file:
                fragmentHomeBinding.testLinearLayout.switchState();

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
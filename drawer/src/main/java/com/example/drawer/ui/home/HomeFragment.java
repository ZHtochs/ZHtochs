package com.example.drawer.ui.home;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.example.domain.FeedDatabase;
import com.example.domain.FeedEntry;
import com.example.domain.FeedReaderContract;
import com.example.domain.FeedReaderDbHelper;
import com.example.drawer.R;
import com.example.okhttp.OkHttpTest;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;
import com.github.zhtouchs.ZHActivityManager;
import com.github.zhtouchs.activity.BaseFragment;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    private final String urlString = "https://pic1.zhimg.com/80/v2-15b2a9a8a8ac38d7ddd47fe9b792232b_720w.jpg?source=1940ef5c";
    private HomeViewModel homeViewModel;

    FeedReaderDbHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ZHLog.d(TAG,"onCreateView");
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ZHLog.d(TAG, "onChanged" + s);
            }
        });
        homeViewModel.setmText("Sadasdada");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        root.findViewById(R.id.button_get).setOnClickListener(this);
        root.findViewById(R.id.button_post).setOnClickListener(this);
        root.findViewById(R.id.button_upload_file).setOnClickListener(this);
        root.findViewById(R.id.button_download_file).setOnClickListener(this);
        SlidingPaneLayout slidingPaneLayout = root.findViewById(R.id.slide);
        slidingPaneLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        slidingPaneLayout.setParallaxDistance(getResources().getDimensionPixelOffset(R.dimen.dp_40));
        dbHelper = new FeedReaderDbHelper(getActivity());

        return root;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZHLog.d(TAG,"onCreate");
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ZHLog.d(TAG,"onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ZHLog.d(TAG,"onViewStateRestored");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get:
                ZHThreadPool.INSTANCE.execute(TAG, new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        for (int i = 0; i < 10; i++) {
                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "title" + i);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "subtitle" + i);

// Insert the new row, returning the primary key value of the new row
                            long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                        }
                    }
                });
                break;
            case R.id.button_post:
                ZHThreadPool.INSTANCE.execute(TAG, new Runnable() {
                    @Override
                    public void run() {
                        FeedDatabase feedDatabase = FeedDatabase.getInstance(getContext());
                        List<FeedEntry> allFeeds = feedDatabase.getFeedDao().getAllFeeds();
                        ZHLog.d(TAG, allFeeds.size());
                    }
                });

                break;
            case R.id.button_upload_file:
                ZHThreadPool.INSTANCE.execute(TAG, new Runnable() {
                    @Override
                    public void run() {
                        FeedDatabase feedDatabase = FeedDatabase.getInstance(getContext());
                        List<FeedEntry> list = new ArrayList<>();
                        FeedEntry feedEntry = new FeedEntry();
                        feedEntry.setSubTitle("324234");
                        feedEntry.setTitle("dfsfgsdfgdfgfd");
                        list.add(feedEntry);
                        feedDatabase.getFeedDao().insertFeed(feedEntry);
                    }
                });
                break;

            case R.id.button_download_file:
                OkHttpTest.okGet("https://fgo.wiki/images/thumb/9/99/%E8%8A%A6%E5%B1%8B%E9%81%93%E6%BB%A12-KMurasaki.jpeg/750px-%E8%8A%A6%E5%B1%8B%E9%81%93%E6%BB%A12-KMurasaki.jpeg",
                        new OkHttpTest.OkHttpCallBack() {
                            @Override
                            public void onResponse(@NotNull Response response) {
                                File file1 = new File(ZHActivityManager.INSTANCE.getContext().getDataDir().getPath() + "/downloadFile.jpg");
                                if (file1.exists()) {
                                    file1.delete();
                                }
                                try (InputStream inputStream = response.body().byteStream();
                                     FileOutputStream out = new FileOutputStream(file1)) {
                                    byte[] buf = new byte[2048];
                                    long sum = 0;
                                    int len = 0;

                                    while ((len = inputStream.read(buf)) != -1) {
                                        out.write(buf, 0, len);
                                        sum += len;
                                    }
                                } catch (IOException e) {

                                }
                            }
                        });
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
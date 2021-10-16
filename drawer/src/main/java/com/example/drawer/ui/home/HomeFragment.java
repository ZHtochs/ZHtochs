package com.example.drawer.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.domain.CommentItem;
import com.example.drawer.R;
import com.example.okhttp.OkHttpTest;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.ZHActivityManager;
import com.github.zhtouchs.ZHAsyncTask;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    ImageView imageView;

    private final String urlString = "https://pic1.zhimg.com/80/v2-15b2a9a8a8ac38d7ddd47fe9b792232b_720w.jpg?source=1940ef5c";
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        imageView = root.findViewById(R.id.imageView_home);
        root.findViewById(R.id.button_get).setOnClickListener(this);
        root.findViewById(R.id.button_post).setOnClickListener(this);
        root.findViewById(R.id.button_upload_file).setOnClickListener(this);
        root.findViewById(R.id.button_download_file).setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get:
                ZHAsyncTask.create(new ZHAsyncTask.Subscriber<String>() {
                    @Override
                    public void subscribe(ZHAsyncTask<? super String> tAsycTask) throws Throwable {
                        ZHLog.d(TAG, "subscribe");
                        tAsycTask.onNext("123");
                    }
                }).addObserver(new ZHAsyncTask.Observer<String>() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(String s) {
                        ZHLog.d(TAG, "onNext" + s);
                    }
                });
                break;
            case R.id.button_post:
                CommentItem commentItem = new CommentItem("12313213", "辣是真的牛皮");
                Gson gson = new Gson();
                String jsonStr = gson.toJson(commentItem);
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody requestBody = RequestBody.create(jsonStr, mediaType);
                OkHttpTest.okPost(OkHttpTest.URL + "/post/comment", requestBody, response -> {
                    ZHLog.d(TAG, response.code());
                    if (response.code() == 200) {
                        try {
                            ZHLog.d(TAG, "comment" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.button_upload_file:
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                int permission = ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }
                File file = new File("/sdcard/Pictures/Screenshots/Screenshot_20201122_002437_com.example.myapplication2.jpg");
                MediaType fileMediaType = MediaType.parse("image/jpg");
                RequestBody requestBodyFile = RequestBody.create(file, fileMediaType);
                RequestBody requestBody1 = new MultipartBody.Builder()
                        .addFormDataPart("file", file.getName(), requestBodyFile)
                        .build();
                OkHttpTest.okPost(OkHttpTest.URL + "/file/upload", requestBody1, new OkHttpTest.OkHttpCallBack() {
                    @Override
                    public void onResponse(@NotNull Response response) {
                        try {
                            ZHLog.d(TAG, "comment" + response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
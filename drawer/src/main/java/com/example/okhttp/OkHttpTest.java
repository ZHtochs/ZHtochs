package com.example.okhttp;

import com.github.zhtouchs.Utils.ZHLog;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpTest {
    public static final String URL = "https://www.httpbin.org/";

    private static final String TAG = "OkHttpTest";

    private OkHttpTest() {
    }

    public static void okGet() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .url(URL + "get?a=1&b=2")
                .build();
        Call task = okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ZHLog.d(TAG, "onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ZHLog.d(TAG, "onResponse:" + response.code());
                ZHLog.d(TAG, "onResponse:" + response.body().string());
            }
        });
    }

    public static void okPost() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        FormBody formBody = new FormBody.Builder().add("a", "1").add("b", "2").build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(URL+"post")
                .build();
        Call task = okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                ZHLog.d(TAG, "onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ZHLog.d(TAG, "onResponse:" + response.code());
                ZHLog.d(TAG, "onResponse:" + response.body().string());
            }
        });
    }



}

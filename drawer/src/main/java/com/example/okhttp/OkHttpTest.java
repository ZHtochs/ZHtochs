package com.example.okhttp;

import com.github.zhtouchs.Utils.ZHLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTest {
    public static final String URL = "http://192.168.1.108:9102";

    private static final String TAG = "OkHttpTest";

    private OkHttpTest() {
    }

    public static void okGet(String urlString, OkHttpCallBack callBack) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .get()
                .url(urlString)
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
                callBack.onResponse(response);
            }
        });
    }

    public static void okPost(String urlString,RequestBody requestBody, OkHttpCallBack callBack) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(urlString)
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
                callBack.onResponse(response);
            }
        });
    }

    public interface OkHttpCallBack {
        void onResponse(@NotNull Response response);
    }

}

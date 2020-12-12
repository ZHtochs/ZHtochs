package com.example.okhttp;

import com.github.zhtouchs.Utils.ZHLog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OkHttpTest {
    public static final String URL = "http://192.168.1.108:9102";

    private static final String TAG = "OkHttpTest";

    private OkHttpTest() {
    }

    public static List<GetTextItem> loadJson(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Language", " zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("Accept", "*/*");
            connection.connect();
            int request = connection.getResponseCode();
            ZHLog.d(TAG, request);
            if (request == 200) {
                Map<String, List<String>> map = connection.getHeaderFields();
                for (String key : map.keySet()) {
                    ZHLog.d(TAG, "key:" + key + " value:" + map.get(key));
                }
            }
            ZHLog.d(TAG, "length:" + connection.getContentLength());
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String text = bufferedReader.readLine();
            JSONObject jsonObject = new JSONObject(text);
            JSONArray jsonArray = jsonObject.optJSONArray("data");
            List<GetTextItem> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                list.add(new Gson().fromJson(object.toString(), GetTextItem.class));
            }
            return list;
        } catch (IOException e) {
            ZHLog.d(TAG, "IOException:" + e.getMessage());
        } catch (JSONException e) {
            ZHLog.d(TAG, "JSONException:" + e.getMessage());
        }
        return null;
    }

}

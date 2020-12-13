package com.example.drawer.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.okhttp.Adapter;
import com.example.domain.GetTextItem;
import com.example.okhttp.OkHttpTest;
import com.github.zhtouchs.ZHActivityManager;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment{

    private static final String TAG = "GalleryFragment";

    private GalleryViewModel galleryViewModel;

    private RecyclerView recyclerView;

    private final List<GetTextItem> getTextItem=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        Button button = root.findViewById(R.id.load_button);
        recyclerView = root.findViewById(R.id.recycle);

        button.setOnClickListener(v -> OkHttpTest.okGet(OkHttpTest.URL + "/get/text", response -> {
            try {
                String text = response.body().string();
                ZHLog.d(TAG, "123:" + text);
                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    getTextItem.add(new Gson().fromJson(object.toString(), GetTextItem.class));
                }
                ZHThreadPool.INSTANCE.runOnUi(TAG, () -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(ZHActivityManager.INSTANCE.getContext()));
                    Adapter adapter = new Adapter();
                    adapter.setGetTextItems(getTextItem);
                    recyclerView.setAdapter(adapter);
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }));
        return root;
    }

}
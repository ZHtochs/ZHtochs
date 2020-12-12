package com.example.drawer.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.okhttp.Adapter;
import com.example.okhttp.GetTextItem;
import com.example.okhttp.OkHttpTest;
import com.github.zhtouchs.ActivityManager;
import com.github.zhtouchs.Utils.ZHThreadPool;

import java.util.List;

public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    private GalleryViewModel galleryViewModel;

    private RecyclerView recyclerView;

    private List<GetTextItem> getTextItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        Button button = root.findViewById(R.id.load_button);
        recyclerView = root.findViewById(R.id.recycle);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHThreadPool.INSTANCE.execute(TAG, () -> {
                    getTextItem = OkHttpTest.loadJson(OkHttpTest.URL + "/get/text");
                    ZHThreadPool.INSTANCE.runOnUi(TAG, new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityManager.INSTANCE.getContext()));
                            Adapter adapter = new Adapter();
                            adapter.setGetTextItems(getTextItem);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                });
            }
        });

        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
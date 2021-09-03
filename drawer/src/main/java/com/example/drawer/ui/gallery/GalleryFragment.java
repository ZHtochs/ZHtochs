package com.example.drawer.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.databinding.FragmentGalleryBinding;
import com.example.drawer.ui.gallery.adpater.MyAdapter;
import com.example.drawer.ui.gallery.beans.ImageBean;
import com.example.drawer.ui.gallery.beans.ItemBean;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    public static final String URL = "https://avatar.csdnimg.cn/2/A/5/1_u014803950.jpg";

    private static final String TAG = "GalleryFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentGalleryBinding fragmentGalleryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        RecyclerView recyclerView = fragmentGalleryBinding.recycle;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<ItemBean> itemBeans = new ArrayList<>();
        itemBeans.add(new ImageBean("1", "123", URL));
        itemBeans.add(new ItemBean("2", "123"));
        itemBeans.add(new ItemBean("3", "123"));
        itemBeans.add(new ItemBean("4", "123"));
        itemBeans.add(new ImageBean("5", "123", URL));
        itemBeans.add(new ItemBean("6", "123"));
        itemBeans.add(new ItemBean("7", "123"));
        MyAdapter myAdapter = new MyAdapter(itemBeans);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));
        return fragmentGalleryBinding.getRoot();
    }
}
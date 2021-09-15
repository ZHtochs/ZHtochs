package com.example.drawer.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.drawer.R;
import com.example.drawer.databinding.FragmentSlideshowBinding;
import com.example.drawer.ui.gallery.beans.ImageBean;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    public static final String URL = "https://avatar.csdnimg.cn/2/A/5/1_u014803950.jpg";
    public static final String URL2 = "https://csdnimg.cn/medal/chizhiyiheng@240.png";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentSlideshowBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_slideshow, container, false);
        GridView gridView = binding.gridView;

        ArrayList<ImageBean> itemBeans = new ArrayList<>();
        itemBeans.add(new ImageBean(URL));
        itemBeans.add(new ImageBean(URL2));
        itemBeans.add(new ImageBean(URL));
        itemBeans.add(new ImageBean(URL2));
        GridAdapter myAdapter = new GridAdapter(itemBeans);
        gridView.setAdapter(myAdapter);
        return binding.getRoot();
    }
}
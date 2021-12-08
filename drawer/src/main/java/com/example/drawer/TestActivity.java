package com.example.drawer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.drawer.ui.gallery.GalleryFragment;
import com.example.drawer.ui.home.HomeFragment;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.activity.BaseActivity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-11-17 22:31
 **/
public class TestActivity extends BaseActivity {
    private static final String TAG = "TestActivity";

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_test);
        ArrayList<Fragment> arrayList = new ArrayList<>();
        TestFragment testFragment = new TestFragment();
        ZHLog.d(TAG, "testFragment " + testFragment);
        testFragment.setAbc(new Pair(1, 2));
        ZHLog.d(TAG, "setAbc");
        arrayList.add(testFragment);
        arrayList.add(new GalleryFragment());
        arrayList.add(new HomeFragment());
        List<Long> ids = Arrays.asList((long) arrayList.get(0).hashCode(), (long) arrayList.get(1).hashCode(), (long) arrayList.get(2).hashCode());
        ViewPager2 viewPager2 = findViewById(R.id.view_pager2);
        ZHLog.d(TAG, "view_pager2 " + viewPager2);
        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int position) {
                return arrayList.get(position);
            }

            @Override
            public int getItemCount() {
                return arrayList.size();
            }

            @Override
            public long getItemId(int position) {
                return ids.get(position);
            }

            @Override
            public boolean containsItem(long itemId) {
                return ids.contains(itemId);
            }

            //            @Override
//            public void onBindViewHolder(@NonNull @NotNull FragmentViewHolder holder, int position, @NonNull @NotNull List<Object> payloads) {
//
//                String tag = "f" + holder.getItemId();
//                FragmentManager fragmentManager = TestActivity.this.getSupportFragmentManager();
//                Fragment fragment = fragmentManager.findFragmentByTag(tag);
//
//                if (fragment != null) {
//                    fragmentManager.beginTransaction().remove(fragment).commit();
//                } else {
//                    // fragment might be null, if it`s call of notifyDatasetChanged()
//                    // which is updates whole list, not specific fragment
//                    super.onBindViewHolder(holder, position, payloads);
//                }
//            }
        });

        FragmentStateAdapter fragmentStateAdapter;
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
package com.example.drawer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.domain.FeedReaderDbHelper;
import com.example.drawer.R;
import com.example.drawer.databinding.FragmentHomeBinding;
import com.example.drawer.databinding.ItemTextOnlyBinding;
import com.example.drawer.databinding.ItemTextOnlyBindingImpl;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.activity.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    private final String urlString = "https://pic1.zhimg.com/80/v2-15b2a9a8a8ac38d7ddd47fe9b792232b_720w.jpg?source=1940ef5c";
    private HomeViewModel homeViewModel;

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    FragmentHomeBinding fragmentHomeBinding;

    FeedReaderDbHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = fragmentHomeBinding.getRoot();
        root.findViewById(R.id.button_get).setOnClickListener(this);
        root.findViewById(R.id.button_post).setOnClickListener(this);
        root.findViewById(R.id.button_upload_file).setOnClickListener(this);
        root.findViewById(R.id.button_download_file).setOnClickListener(this);
        dbHelper = new FeedReaderDbHelper(getActivity());

        ArrayList<ItemBean> arrayList1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ItemBean bean = new ItemBean(String.valueOf(i), "" + new Random().nextFloat());

            arrayList1.add(bean);
        }
        fragmentHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()) );
        fragmentHomeBinding.recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @NotNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                ItemTextOnlyBinding inflate = ItemTextOnlyBindingImpl.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new MyViewHolder(inflate);
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                if (position >= 0 && position < arrayList1.size()) {
                    myViewHolder.inflate.setTextViewEntry(arrayList1.get(position));
                }
                ZHLog.d(TAG, "onBindViewHolder  " + holder);
            }

            @Override
            public int getItemCount() {
                return arrayList1.size();
            }
        });

        ItemTextOnlyBinding inflate = ItemTextOnlyBindingImpl.inflate(LayoutInflater.from(getContext()), null, false);
        inflate.getRoot().measure(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        itemHeight = inflate.getRoot().getMeasuredHeight();
        ViewGroup.LayoutParams layoutParams = fragmentHomeBinding.recyclerView.getLayoutParams();
        layoutParams.height = itemHeight * 5;
        totalHeight = itemHeight * arrayList1.size();
        fragmentHomeBinding.recyclerView.setLayoutParams(layoutParams);
        ZHLog.d(TAG, "totalHeight " + totalHeight + " itemHeight " + itemHeight);
        return root;
    }

    private int totalHeight;
    private int itemHeight;

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemTextOnlyBinding inflate;

        public MyViewHolder(ItemTextOnlyBinding itemView) {
            super(itemView.getRoot());
            inflate = itemView;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_get:
                expand(fragmentHomeBinding.recyclerView);

                break;
            case R.id.button_post:
                collapse(fragmentHomeBinding.recyclerView);
                break;
            case R.id.button_upload_file:

                break;

            case R.id.button_download_file:
                ZHLog.d(TAG, "onClick");
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.view_anim);
                animation.setFillAfter(true);
                animation.setFillBefore(false);
                fragmentHomeBinding.buttonDownloadFile.startAnimation(animation);
                break;
            default:
                break;
        }
    }

    public void expand(final View view) {
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);
        fragmentHomeBinding.recyclerView.isAnimating = true;
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    fragmentHomeBinding.recyclerView.isAnimating = false;
                    view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else {
                    view.getLayoutParams().height = (int) (totalHeight * interpolatedTime);
                }
                ZHLog.d(TAG, "interpolatedTime " + interpolatedTime + " expand " + view.getHeight());
                view.requestLayout();
            }
        };
        animation.setDuration(1000);
        animation.setInterpolator(new FastOutLinearInInterpolator());
        view.startAnimation(animation);
    }

    public void collapse(final View view) {
        fragmentHomeBinding.recyclerView.isAnimating = true;

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                    fragmentHomeBinding.recyclerView.isAnimating = false;
                } else {
                    view.getLayoutParams().height = totalHeight - (int) (totalHeight * interpolatedTime);
                }
                ZHLog.d(TAG, "collapse " + view.getHeight());
                view.requestLayout();
            }
        };
        animation.setDuration(1000);
        animation.setInterpolator(new FastOutLinearInInterpolator());
        view.startAnimation(animation);
    }
}
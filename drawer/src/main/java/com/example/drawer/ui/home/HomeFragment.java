package com.example.drawer.ui.home;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
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
import com.example.drawer.ui.view.PoemContent;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.activity.BaseFragment;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        fragmentHomeBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        List<List<PoemContent>> list = new ArrayList<>();
        list.add(Arrays.asList(new PoemContent("豫", "yù"),
                new PoemContent("章", "zhānɡ"),
                new PoemContent("故", "ɡù"),
                new PoemContent("郡", "jùn"),
                new PoemContent(",", "")));

        list.add(Arrays.asList(new PoemContent("洪", "hónɡ"),
                new PoemContent("都", "dū"),
                new PoemContent("新", "xīn"),
                new PoemContent("府", "fǔ"),
                new PoemContent("，", "")));

        list.add(Arrays.asList(new PoemContent("洪", "hónɡ"),
                new PoemContent("都", "dū"),
                new PoemContent("新", "xīn"),
                new PoemContent("府", "fǔ"),
                new PoemContent("府", "fǔ"),
                new PoemContent("府", "fǔ"),
                new PoemContent("府", "fǔ"),
                new PoemContent("。", "")));

        list.add(Arrays.asList(new PoemContent("洪", "hónɡ"),
                new PoemContent("都", "dū"),
                new PoemContent("都", "dū"),
                new PoemContent("都", "dū"),
                new PoemContent("新", "xīn"),
                new PoemContent("府", "fǔ"),
                new PoemContent(".", "")));

        fragmentHomeBinding.pinyinview1.setList(list.get(0));
        fragmentHomeBinding.pinyinview2.setList(list.get(1));
        fragmentHomeBinding.pinyinview3.setList(list.get(2));
        fragmentHomeBinding.pinyinview4.setList(list.get(3));

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
                File zhuhe = getContext().getExternalFilesDir("zhuhe");
                File cacheDir = getContext().getExternalCacheDir();
                try {
                    File file = new File(zhuhe, "zhuhe.text");
                    file.createNewFile();
                    File file2 = new File(cacheDir, "zhuhe.text");
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ZHLog.d(TAG, "123");
                break;
            case R.id.button_post:
                ZHLog.d(TAG, Environment.getExternalStorageDirectory());
                ZHLog.d(TAG, getContext().getExternalFilesDir("BaiduNetdisk"));
                File file1 = new File("/sdcard/BaiduNetdisk/动漫/轻小说/人类衰退之后/资源来源.txt");
                File file2 = new File("/sdcard/BaiduNetdisk/动漫/轻小说/人类衰退之后/人类衰退之后第七卷.docx");
                ZHLog.d(TAG, "file1 " + file1.exists());
                ZHLog.d(TAG, "file2 " + file2.exists());
                Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.example.myapplication.test", file1);
                ZHLog.d(TAG, "uriForFile " + uriForFile);
                Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setType("*/*");
                ArrayList<Uri> arrayList = new ArrayList<>();
                arrayList.add(FileProvider.getUriForFile(getContext(), "com.example.myapplication.test", file1));
                arrayList.add(FileProvider.getUriForFile(getContext(), "com.example.myapplication.test", file2));
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
                startActivity(intent);

                break;
            case R.id.button_upload_file:
                int read = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                int write = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ZHLog.e(TAG, "read " + write + " write " + write);
                getActivity().requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                read = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                write = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ZHLog.e(TAG, "read " + write + " write " + write);
                break;

            case R.id.button_download_file:
                Intent image = null;
                // 拍照
//                Intent image = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //  选择图片
                //  Intent image = new Intent(Intent.ACTION_PICK);
                //  image.setType( "image/*");

//                Intent image = new Intent(Intent.ACTION_GET_CONTENT);
//                image.setType("*/*");
//                image.addCategory(Intent.CATEGORY_OPENABLE);

                intentActivityResultLauncher.launch(image);
                break;
            default:
                break;
        }
    }

    ActivityResultLauncher<Intent> intentActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            ZHLog.e(TAG, "onActivityResult");

        }
    });

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
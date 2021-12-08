package com.example.drawer.ui.gallery;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.R;
import com.example.drawer.databinding.FragmentGalleryBinding;
import com.example.drawer.ui.gallery.adpater.MyAdapter;
import com.example.drawer.ui.gallery.beans.ItemBean;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalleryFragment extends Fragment {

    public static final String URL = "https://avatar.csdnimg.cn/2/A/5/1_u014803950.jpg";

    private static final String TAG = "GalleryFragment";

    private Random random = new Random();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentGalleryBinding fragmentGalleryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        RecyclerView recyclerView = fragmentGalleryBinding.recycle;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<ItemBean> itemBeans = new ArrayList<>();
//        itemBeans.add(new ImageBean("1", "123", URL));
//        itemBeans.add(new ItemBean("2", "123"));
//
//        itemBeans.add(new ImageBean("5", "123", URL));
        MyAdapter myAdapter = new MyAdapter(itemBeans);
        recyclerView.setAdapter(myAdapter);
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this.requireActivity()).get(GalleryViewModel.class);
        for (int i = 0; i < 100; i++) {
            itemBeans.add(new ItemBean("6", "" + random.nextFloat()));
        }
        galleryViewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ItemBean>>() {
            @Override
            public void onChanged(ArrayList<ItemBean> itemBeans) {
                ZHLog.d(TAG, "onChanged");
                List<ItemBean> list = myAdapter.getArrayList();
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallBack(list, itemBeans));

                list.clear();
                myAdapter.setArrayList(itemBeans);
                //使用DiffResult分发给apdate热更新
                diffResult.dispatchUpdatesTo(myAdapter);
            }
        });

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CustomItemTouchCallback(myAdapter));
//        itemTouchHelper.attachToRecyclerView(recyclerView);
        fragmentGalleryBinding.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHThreadPool.INSTANCE.execute(TAG, new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            ZHLog.d(TAG, "run");
                            SystemClock.sleep(100);

                            List<ItemBean> list = myAdapter.getArrayList();

                            ArrayList<ItemBean> arrayList = new ArrayList<>(list);
                            arrayList.add(new ItemBean("6", "" + random.nextFloat()));

                            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallBack(list, arrayList));

                            list.clear();
                            myAdapter.setArrayList(arrayList);
                            //使用DiffResult分发给apdate热更新

                            getActivity().runOnUiThread(() -> diffResult.dispatchUpdatesTo(myAdapter));
                        }
                    }
                });
            }
        });
        fragmentGalleryBinding.scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(fragmentGalleryBinding.editText.getText().toString());
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) fragmentGalleryBinding.recycle.getLayoutManager();
                linearLayoutManager.scrollToPositionWithOffset(myAdapter.getItemCount() - 1, Integer.MIN_VALUE);
            }
        });
        return fragmentGalleryBinding.getRoot();
    }

    public class CustomItemTouchCallback extends ItemTouchHelper.Callback {

        private final ItemTouchStatus mItemTouchStatus;

        public CustomItemTouchCallback(ItemTouchStatus itemTouchStatus) {
            mItemTouchStatus = itemTouchStatus;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            // 上下拖动
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 向左滑动
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            // 交换在数据源中相应数据源的位置
            return mItemTouchStatus.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // 从数据源中移除相应的数据
            mItemTouchStatus.onItemRemove(viewHolder.getAdapterPosition());
        }
    }

    public interface ItemTouchStatus {

        boolean onItemMove(int fromPosition, int toPosition);

        boolean onItemRemove(int position);
    }

    public static class MyDiffUtilCallBack extends DiffUtil.Callback {

        private List<ItemBean> oldList;
        private List<ItemBean> newList;

        public MyDiffUtilCallBack(List<ItemBean> oldList, List<ItemBean> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList == null ? 0 : oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return true;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).publishData.equals(newList.get(newItemPosition).publishData)
                    && oldList.get(oldItemPosition).title.equals(newList.get(newItemPosition).title);
        }
    }
}
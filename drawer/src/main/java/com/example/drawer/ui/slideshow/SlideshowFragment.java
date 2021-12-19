package com.example.drawer.ui.slideshow;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.R;
import com.example.drawer.databinding.FragmentSlideshowBinding;
import com.example.drawer.ui.paging.Student;
import com.example.drawer.ui.paging.StudentDao;
import com.example.drawer.ui.paging.StudentsDatabase;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class SlideshowFragment extends Fragment {

    private static final String TAG = "SlideshowFragment";

    private StudentDao studentDao;

    DeleteWrapperAdapter slideAdapter;

    FragmentSlideshowBinding binding;

    private OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (slideAdapter.getState() == DeleteWrapperAdapter.Duration.EDIT) {
                slideAdapter.setState(DeleteWrapperAdapter.Duration.COMMON, true);
            }
            callback.setEnabled(false);
        }
    };

    private NotifyListener notifyListener = new NotifyListener() {
        @Override
        public void notifyEnterEdit() {
            callback.setEnabled(true);
        }

        @Override
        public void notifyCheckItemChange(int count) {
            ZHLog.d(TAG, "notifyCheckItemChange " + count);
        }

        @Override
        public void notifyEmptyData() {
            ZHLog.d(TAG, "notifyEmptyData");
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentDao = StudentsDatabase.getInstance(getContext()).getStudentDao();
        getActivity().getOnBackPressedDispatcher().addCallback(callback);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_slideshow, container, false);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHThreadPool.INSTANCE.execute(TAG, new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        for (int i = 0; i < 20; i++) {
                            Student student = new Student();
                            student.setStudentName(Student.getRandomJianHan(3));
                            student.setClassId(random.nextInt(3));
                            studentDao.insertStudents(student);
                        }
                    }
                });
            }
        });
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideAdapter.deleteChecked();
            }
        });
        slideAdapter = new DeleteWrapperAdapter(new SlideAdapter(), notifyListener);
        binding.slideRecycler.setAdapter(slideAdapter);
        binding.slideRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
                int offset = getContext().getResources().getDimensionPixelOffset(R.dimen.dp_4);
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, offset, 0, offset);
            }
        });
        binding.slideRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.slideRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (mLinearLayoutManager == null) {
                    return;
                }
                int position = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int currentSum = slideAdapter.getItemCount();
                if (currentSum - position < 10) {
                    ZHLog.d(TAG, "addItem");
                    ZHThreadPool.INSTANCE.execute(TAG, () -> {

                        Student student = slideAdapter.getStudents().get(currentSum - 1);
                        ZHLog.d(TAG, "student id " + student.getId());
                        List<Student> students = studentDao.getSameClassStudent(0, student.getId(), 10);
                        ZHLog.d(TAG, "student size " + students.size());
                        recyclerView.post(() -> slideAdapter.appendList(students));
                    });

                }
            }
        });
        ZHThreadPool.INSTANCE.execute(TAG, () -> {
            List<Student> students = studentDao.getSameClassStudent(0, 0, 50);
            binding.slideRecycler.post(() -> {
                slideAdapter.appendList(students);
                slideAdapter.notifyDataSetChanged();
            });
        });
        return binding.getRoot();
    }
}
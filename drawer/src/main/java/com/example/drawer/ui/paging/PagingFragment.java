package com.example.drawer.ui.paging;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.drawer.databinding.FragmentPagingBinding;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;

import java.util.Random;

public class PagingFragment extends Fragment {
    private static final String TAG = "PagingFragment";

    StudentDao studentDao;

    StudentsDatabase studentsDatabase;

    PagingViewModel pagingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPagingBinding binding =
                FragmentPagingBinding.inflate(inflater, container, false);

        // 数据库
        studentsDatabase = StudentsDatabase.getInstance(requireActivity());
        studentDao = studentsDatabase.getStudentDao();

        MyPagedAdapter pagedAdapter = new MyPagedAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setStackFromEnd(true);
        binding.recycleView.setLayoutManager(linearLayoutManager);
        binding.recycleView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        binding.recycleView.setAdapter(pagedAdapter);

        pagingViewModel = new ViewModelProvider(this).get(PagingViewModel.class);
        pagingViewModel.setPagingSource(studentDao);

        pagingViewModel.getPaging().observe(getViewLifecycleOwner(), new Observer<PagingData<Student>>() {
            @Override
            public void onChanged(PagingData<Student> studentPagingData) {
                ZHLog.d(TAG, "onChanged");
                pagedAdapter.submitData(getViewLifecycleOwner().getLifecycle(), studentPagingData);
            }
        });
        binding.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHThreadPool.INSTANCE.execute("insert", () -> {
                    Student student = new Student();
                    Random random = new Random();
                    student.setStudentName(""+random.nextInt());
                    studentDao.insertStudents(student);
                });
            }
        });

        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHThreadPool.INSTANCE.execute("deleteAllStudents", () -> {
                    studentDao.deleteAllStudents();
                });
            }
        });

        binding.getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZHThreadPool.INSTANCE.execute("updateStudents", () -> {

                });
            }
        });
        return binding.getRoot();
    }
}
package com.example.drawer.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.databinding.ItemSlideBinding;
import com.example.drawer.ui.paging.Student;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-19 11:48
 **/
public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {
    private static final String TAG = "SlideAdapter";

    private List<Student> students = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemSlideBinding itemSlideBinding = ItemSlideBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SlideViewHolder(itemSlideBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SlideViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public List<Student> getStudents() {
        return students;
    }

    public class SlideViewHolder extends DeleteViewHolder {

        ItemSlideBinding itemSlideBinding;

        public SlideViewHolder(@NonNull @NotNull ItemSlideBinding itemView) {
            super(itemView);
            this.itemSlideBinding = itemView;
        }

        @Override
        public void bindViewEntry(int position, @DeleteWrapperAdapter.Duration int adapterState, DeleteListener listener) {
            ZHLog.d(TAG, "bind " + position);
            Student student = getStudents().get(position);
            itemSlideBinding.slide.closePane();
            itemSlideBinding.slide.setPanelSlideListener(listener);
            itemSlideBinding.slide.setCanSlide(listener.isCanSlide());
            itemSlideBinding.position.setText("adapter位置 " + position);
            itemSlideBinding.studentId.setText("学生ID " + student.getId());
            itemSlideBinding.studentClassId.setText("班级ID " + student.getClassId());
            itemSlideBinding.studentName.setText("学生姓名 " + student.getStudentName());
            itemSlideBinding.deleteImage.setOnClickListener(v -> listener.onDeleteClick(position));
            itemSlideBinding.content.setOnLongClickListener(listener);
            if (adapterState == DeleteWrapperAdapter.Duration.EDIT) {
                itemSlideBinding.checkbox.setVisibility(View.VISIBLE);
            } else {
                itemSlideBinding.checkbox.setVisibility(View.GONE);
            }
            itemSlideBinding.checkbox.setChecked(student.isChecked());
            itemSlideBinding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                ZHLog.d(TAG, "position " + position + " onCheckedChanged " + isChecked);
                listener.onCheck(student, isChecked);
            });
        }
    }

    public final void appendList(List<Student> data) {
        students.addAll(data);
    }
}
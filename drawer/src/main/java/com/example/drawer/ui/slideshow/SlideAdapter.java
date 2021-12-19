package com.example.drawer.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.example.drawer.databinding.ItemSlideBinding;
import com.example.drawer.ui.paging.Student;
import com.github.zhtouchs.Utils.ZHLog;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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

    @Duration
    private int state = Duration.COMMON;

    private SlideLayout slideLayout;

    @NonNull
    @NotNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemSlideBinding itemSlideBinding = ItemSlideBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        itemSlideBinding.slide.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(@NonNull @NotNull View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(@NonNull @NotNull View panel) {
                ZHLog.d(TAG, "onPanelOpened");
                if (SlideAdapter.this.slideLayout != null) {
                    SlideAdapter.this.slideLayout.closePane();
                }
                SlideAdapter.this.slideLayout = itemSlideBinding.slide;
            }

            @Override
            public void onPanelClosed(@NonNull @NotNull View panel) {
                ZHLog.d(TAG, "onPanelClosed");
                if (SlideAdapter.this.slideLayout == itemSlideBinding.slide) {
                    SlideAdapter.this.slideLayout = null;
                }
            }
        });
        return new SlideViewHolder(itemSlideBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SlideViewHolder holder, int position) {
        holder.bind(position, students.get(position), state);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public List<Student> getStudents() {
        return students;
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {

        ItemSlideBinding itemSlideBinding;

        public SlideViewHolder(@NonNull @NotNull ItemSlideBinding itemView) {
            super(itemView.getRoot());
            this.itemSlideBinding = itemView;
        }

        public void bind(int position, Student student, @Duration int state) {
            itemSlideBinding.position.setText("adapter位置 " + position);
            itemSlideBinding.studentId.setText("学生ID " + student.getId());
            itemSlideBinding.studentClassId.setText("班级ID " + student.getClassId());
            itemSlideBinding.studentName.setText("学生姓名 " + student.getStudentName());
            itemSlideBinding.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZHLog.d(TAG, "onClick " + position);
                    delete(position);
                }
            });
            itemSlideBinding.content.setLongClickable(true);
            itemSlideBinding.content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setState(Duration.EDIT);
                    return true;
                }
            });
            if (state == Duration.EDIT) {
                itemSlideBinding.checkbox.setVisibility(View.VISIBLE);
            } else {
                itemSlideBinding.checkbox.setVisibility(View.GONE);
            }
            itemSlideBinding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ZHLog.d(TAG, "position " + position + " onCheckedChanged " + isChecked);
                    student.setChecked(isChecked);
                }
            });
        }
    }

    public final void appendList(List<Student> data) {
        int positionStart = students.size();
        students.addAll(data);
        int itemCount = students.size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(positionStart + 1, itemCount);
        }
    }

    public void delete(int position) {
        students.remove(position);
        //RecycleView移除当前子项（有动画效果）
        notifyItemRemoved(position);
        //RecycleView局部更新，防止position错乱
        if (position != students.size()) {
            notifyItemRangeChanged(position, students.size() - position);
        }
        if (slideLayout != null) {
            slideLayout.closePane();
        }
    }

    @Duration
    public int getState() {
        return state;
    }

    public void setState(@Duration int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void deleteChecked() {
        for (int i = students.size() - 1; i >= 0; i--) {
            Student student = students.get(i);
            if (student.isChecked()) {
                delete(i);
            }
        }
    }

    @IntDef({Duration.EDIT, Duration.COMMON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
        int EDIT = 0;
        int COMMON = 1;
    }
}
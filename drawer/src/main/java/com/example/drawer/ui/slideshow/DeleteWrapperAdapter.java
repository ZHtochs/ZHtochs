package com.example.drawer.ui.slideshow;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drawer.ui.paging.Student;
import com.example.drawer.ui.paging.StudentsDatabase;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.Utils.ZHThreadPool;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-12-20 01:00
 **/
public class DeleteWrapperAdapter extends RecyclerView.Adapter<DeleteViewHolder> implements DeleteListener {

    private static final String TAG = "DeleteWrapperAdapter";

    private SlideAdapter slideAdapter;

    private SlideLayout slideLayout;

    private NotifyListener notifyListener;

    @Duration
    private int state = Duration.COMMON;

    public DeleteWrapperAdapter(@NonNull SlideAdapter slideAdapter) {
        this.slideAdapter = slideAdapter;
    }

    public DeleteWrapperAdapter(SlideAdapter slideAdapter, NotifyListener notifyListener) {
        this.slideAdapter = slideAdapter;
        this.notifyListener = notifyListener;
    }

    @NonNull
    @NotNull
    @Override
    public DeleteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return slideAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DeleteViewHolder holder, int position) {
        holder.bindViewEntry(position, state, this);
    }

    @Override
    public int getItemCount() {
        return slideAdapter.getItemCount();
    }

    public void delete(int position) {
        List<Student> students = slideAdapter.getStudents();
        Student student = students.get(position);
        students.remove(position);
        //RecycleView移除当前子项（有动画效果）
        notifyItemRemoved(position);
        //RecycleView局部更新，防止position错乱
        if (position != students.size()) {
            notifyItemRangeChanged(position, students.size() - position);
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (slideLayout != null) {
                slideLayout.closePane();
            }
        }, 200);

        ZHThreadPool.INSTANCE.execute(TAG, () -> StudentsDatabase.getInstance().getStudentDao().deleteWithId(student.getId()));
    }

    @Duration
    public int getState() {
        return state;
    }

    public void setState(@Duration int state, boolean isNeedNotifyChange) {
        this.state = state;
        if (isNeedNotifyChange) {
            notifyDataSetChanged();
        }
    }

    public void deleteChecked() {
        int sum = slideAdapter.getStudents().size();
        int deleteCount = 0;
        for (int i = sum - 1; i >= 0; i--) {
            Student student = slideAdapter.getStudents().get(i);
            if (student.isChecked()) {
                delete(i);
                deleteCount++;
            }
        }
        if (notifyListener != null) {
            notifyListener.notifyCheckItemChange(0);
        }
        if (slideAdapter.getStudents().isEmpty()) {
            if (notifyListener != null) {
                notifyListener.notifyEmptyData();
            }
        }
    }

    public List<Student> getStudents() {
        return slideAdapter.getStudents();
    }

    public final void appendList(List<Student> data) {
        int positionStart = getStudents().size();
        slideAdapter.appendList(data);
        int itemCount = getStudents().size() - positionStart;

        if (positionStart == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(positionStart + 1, itemCount);
        }
    }

    @Override
    public void onDeleteClick(int position) {
        delete(position);
        if (notifyListener != null) {
            if (slideAdapter.getStudents().isEmpty()) {
                notifyListener.notifyEmptyData();
            }
        }
        setState(Duration.COMMON, false);
    }

    @Override
    public void onClick(int position) {
        ZHLog.d(TAG, "onClick " + position);
        if (state == Duration.COMMON) {
            //TODO
        }
    }

    @Override
    public boolean isCanSlide() {
        return state != Duration.EDIT;
    }

    @Override
    public void onCheck(Student student, boolean isChecked) {
        student.setChecked(isChecked);
        int sum = 0;
        for (Student student1 : getStudents()) {
            if (student1.isChecked()) {
                sum++;
            }
        }
        if (notifyListener != null) {
            notifyListener.notifyCheckItemChange(sum);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (state == Duration.COMMON) {
            setState(DeleteWrapperAdapter.Duration.EDIT, true);
            if (notifyListener != null) {
                notifyListener.notifyEnterEdit();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onPanelSlide(@NonNull @NotNull View panel, float slideOffset) {
        ZHLog.d(TAG, "onPanelSlide");

    }

    @Override
    public void onPanelOpened(@NonNull @NotNull View panel) {
        ZHLog.d(TAG, "onPanelOpened");
        if (panel.getParent() instanceof SlideLayout) {
            slideLayout = (SlideLayout) panel.getParent();
        }
        state = Duration.OPENED;
    }

    @Override
    public void onPanelClosed(@NonNull @NotNull View panel) {
        ZHLog.d(TAG, "onPanelClosed");
        slideLayout = null;
    }

    @IntDef({Duration.EDIT, Duration.COMMON, Duration.OPENED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
        int EDIT = 0;
        int COMMON = 1;
        int OPENED = 2;
    }

}
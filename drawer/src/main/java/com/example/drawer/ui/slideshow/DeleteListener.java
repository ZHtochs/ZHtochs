package com.example.drawer.ui.slideshow;

import android.view.View;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import com.example.drawer.ui.paging.Student;

public interface DeleteListener extends SlidingPaneLayout.PanelSlideListener, View.OnLongClickListener {
    public void onDeleteClick(int position);

    public void onClick(int position);

    public boolean isCanSlide();

    void onCheck(Student student, boolean isChecked);
}

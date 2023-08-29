package com.example.drawer.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2023-08-19 21:55
 **/
public class PieView extends View {
    private int w;
    private int h;
    private Paint paint = new Paint();

    private List<Data> data;

    public PieView(Context context) {
        super(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    public void setData(List<Data> data) {
        this.data = data;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null) {
            return;
        }
        int sum = data.stream().mapToInt(Data::getPercent).sum();
        int current = 0;
        for (int i = 0; i < data.size(); i++) {
            Data data = this.data.get(i);
            paint.setColor(data.color);
            int angle = (int) (data.percent * 1.0 / sum * 360);

            canvas.drawArc(0, 0, w / 2, h / 2, current, angle, true, paint);
            current += angle;
        }
    }

    public static class Data {
        public int color;

        public int percent;

        public Data(int color, int percent) {
            this.color = color;
            this.percent = percent;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }
}
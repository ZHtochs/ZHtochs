package com.example.drawer.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.drawer.R;
import com.github.zhtouchs.ZHActivityManager;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2023-08-30 23:25
 **/
public class PinYinView extends View {
    private static final String TAG = "PinYinView";

    private int itemWidth = ZHActivityManager.INSTANCE.getContext().getResources().getDimensionPixelSize(R.dimen.cs_36_dp);

    private int itemWidthHalf = ZHActivityManager.INSTANCE.getContext().getResources().getDimensionPixelSize(R.dimen.cs_18_dp);

    private int biaodiao = ZHActivityManager.INSTANCE.getContext().getResources().getDimensionPixelSize(R.dimen.cs_12_dp);

    private HashSet<String> characters = new HashSet<String>() {{
        add(",");
        add("，");
        add("。");
        add(".");
        add("？");
        add("?");
        add(":");
        add("：");
    }};

    private List<PoemContent> list;

    private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);

    public PinYinView(Context context) {
        this(context, null);
    }

    public PinYinView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinYinView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PinYinView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        paint.setTextSize(context.getResources().getDimension(R.dimen.sp_14));
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setList(List<PoemContent> list) {
        this.list = list;
    }

    Gson gson = new Gson();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (list == null || list.isEmpty()) {
            return;
        }
        paint.setTextAlign(Paint.Align.CENTER);
        int paddingTop = getPaddingTop();
        int paddingStart = getPaddingStart();
        for (int i = 0; i < list.size(); i++) {
            PoemContent poemContent = list.get(i);
            int baseX = itemWidthHalf * (2 * i + 1) + paddingStart;
            if (i == list.size() - 1 && characters.contains(poemContent.pinyin) && TextUtils.isEmpty(poemContent.text)) {
                canvas.drawText(poemContent.text, baseX - biaodiao, 50 + paddingTop, paint);
                canvas.drawText(poemContent.pinyin, baseX - biaodiao, 100 + paddingTop, paint);
            } else {
                canvas.drawText(poemContent.text, baseX, 50 + paddingTop, paint);
                canvas.drawText(poemContent.pinyin, baseX, 100 + paddingTop, paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 固定宽度
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        setMeasuredDimension((itemWidth * 8 + getPaddingStart() + getPaddingEnd()),
                (getContext().getResources().getDimensionPixelSize(R.dimen.cs_36_dp) + paddingTop + paddingBottom));
    }
}
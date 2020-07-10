package com.listgrid.demo.tv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.listgrid.demo.utils.logger.Logger;

public class TvImageView extends AppCompatImageView {

    public TvImageView(Context context) {
        super(context);
    }

    public TvImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TvImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Logger.i("onSizeChanged w:"+w+", h:"+h);
//        Logger.i("onSizeChanged oldw:"+oldw+", oldh:"+oldw);

    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
//        Logger.i("gainFocus:"+gainFocus);
    }
}

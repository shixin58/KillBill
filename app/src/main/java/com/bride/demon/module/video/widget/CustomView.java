package com.bride.demon.module.video.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bride.baselib.ResUtils;

/**
 * 测试Path用法
 * <p>Created by shixin on 2018/10/30.
 */
public class CustomView extends View {
    public static final int MAX_COUNT = 10;

    private int mCnt = MAX_COUNT;
    private Paint mTimePaint;

    private Path mPath;
    private Paint mPathPaint;

    private int reopened;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Avoid object allocations during draw/layout operations (preallocate and reuse instead)
        mTimePaint = new Paint();
        mTimePaint.setColor(Color.RED);
        mTimePaint.setTextSize(ResUtils.sp2px(20f));

        mPath = new Path();
        mPath.moveTo(0f, 0f);
        mPath.lineTo(ResUtils.dp2px(80f), ResUtils.dp2px(80f));
        mPath.lineTo(ResUtils.dp2px(80f), 0f);
        mPath.lineTo(0f, 0f);
        mPath.lineTo(0f, ResUtils.dp2px(80f));
        mPath.lineTo(ResUtils.dp2px(80f), ResUtils.dp2px(80f));

        mPathPaint = new Paint();
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(5f);
        mPathPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mCnt +"", getWidth()/2f, getHeight()/2f, mTimePaint);
        canvas.drawPath(mPath, mPathPaint);
    }

    public int getCnt() {
        return mCnt;
    }

    public void setCnt(int cnt) {
        this.mCnt = cnt;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.reopened = reopened;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCustomState(ss);
    }

    public void setCustomState(SavedState savedState) {
        reopened = savedState.reopened;
    }

    static class SavedState extends BaseSavedState {

        private int reopened;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel source) {
            super(source);
            reopened = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(reopened);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            reopened ++;
        }
    }
}

package com.max.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 高度WRAP_CONTENT下，解决GridView跟ScrollView滚动冲突的bug
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // widthMeasureSpec，前两位表示mode，后30位表示size
        // AT_MOST模式下，把parent传过来的size设置超级大，就会使用child实际size
        int fixedHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, fixedHeightMeasureSpec);
    }
}

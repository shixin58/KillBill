package com.bride.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
        // widthMeasureSpec/heightMeasureSpec，前两位表示mode，后30位表示size
        // AT_MOST模式下，把parent传过来的size设置超级大，就会使用child实际size
        // 屏幕高度2160 pixels，状态栏+标题栏240 pixels

        // ListView内嵌GridView：0 1920(屏幕剩余可用), 0 1920, 0 1350(ListView大小)
        // 线性布局内嵌GridView：AT_MOST 1920, AT_MOST 1920。ListView内嵌GridView：0 1560, 0 1560, 0 1350

        // ListView内嵌NoScrollGridView：0 1920, 0 1920, 0 1590
        // 线性布局内嵌NoScrollGridView：AT_MOST 1920, AT_MOST 1920。ListView内嵌NoScrollGridView：UNSPECIFIED 1560, UNSPECIFIED 1560, UNSPECIFIED 1560
        Log.i("NoScrollGridView", hashCode()+": "+MeasureSpec.getMode(heightMeasureSpec)+" "+MeasureSpec.getSize(heightMeasureSpec));
        // 默认UNSPECIFIED，取GridView单行高度；改为AT_MOST，根据行数计算实际需要高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package com.wanpg.together.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 此ListView是固定高度的
 *
 * @author wanpg
 */
public class HeightBasedChildListView extends ListView {

    public HeightBasedChildListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public HeightBasedChildListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public HeightBasedChildListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}

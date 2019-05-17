package com.wangbin.go_isp.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.wangbin.go_isp.R;


/**
 * 限制最大高度的ScrollView控件
 * 也解决 {@link ScrollView} 滚动监听问题，系统并未直接提供此方法
 * 原创来自：Ti
 */

public class TiFitScrollView extends ScrollView {

    private float mMaxHeight = 0;
    private OnScrollChangedListener mScrollChangedListener;

    public TiFitScrollView(Context context) {
        super(context);
        init(null);
    }

    public TiFitScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TiFitScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TiFitScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray lTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TiFitScrollView);
            if (lTypedArray != null) {
                mMaxHeight = lTypedArray.getDimension(R.styleable.TiFitScrollView_max_height, 0);
                lTypedArray.recycle();
            }
        }
    }

    public void setMaxHeight(float mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            //设置控件高度不能超过mMaxHeight
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) mMaxHeight, MeasureSpec.AT_MOST);
        }
        //计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollChangedListener != null) {
            mScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener aListener) {
        mScrollChangedListener = aListener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(TiFitScrollView aView, int aLeft, int aTop, int aOldLeft, int aOldTop);
    }
}

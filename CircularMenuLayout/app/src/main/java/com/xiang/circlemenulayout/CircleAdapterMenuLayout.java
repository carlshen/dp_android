package com.xiang.circlemenulayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * Created by carl on 2018/9/11 0011.
 */

public class CircleAdapterMenuLayout extends ViewGroup {
    //圆形直径
    private int mRadius;
    //item的默认尺寸
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    //内边距
    private static final float RADIO_PADDING_LAYOUT = 1 / 12f;
    //内边距
    private float mPadding;
    //布局开始时角度
    private double mStartAngle = 0;
    private ListAdapter mAdapter;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
    }

    public CircleAdapterMenuLayout(Context context) {
        this(context, null);
    }

    public CircleAdapterMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleAdapterMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setPadding(0, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureMyself(widthMeasureSpec, heightMeasureSpec);
        measureItems();
    }

    private void measureItems() {
        //获得半径
        mRadius = Math.max(getMeasuredHeight(), getMeasuredWidth());
        //item数量
        final int count = getChildCount();
        //item尺寸
        int childSize = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        int childMode = MeasureSpec.EXACTLY;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            //计算item的尺寸，设置模式，对item进行测量
            int makeMeasureSpec = -1;
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
        }
        //mPadding = RADIO_PADDING_LAYOUT + mRadius;
    }

    private void measureMyself(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            resWidth = getSuggestedMinimumWidth();
            resWidth = resWidth == 0 ? getWidth() : resWidth;

            resHeight = getSuggestedMinimumHeight();
            resHeight = resHeight == 0 ? getHeight() : resHeight;
        } else {
            resWidth = resHeight = Math.min(width, height);
        }
        setMeasuredDimension(resWidth, resHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int childCount = getChildCount();
        int left, top;
        int itemWidth = (int) (mRadius * RADIO_DEFAULT_CHILD_DIMENSION);
        float angleDelay = 60;
        if (childCount > 0) {
            angleDelay = 360 / childCount;
        }
        float distanceFromCenter = mRadius / 2f - itemWidth / 2 - mPadding;
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            //菜单项的起始角度
            mStartAngle %= 360;

            //计算中心点到item中心的距离
            //distanceFromCenter cosa item中心点的left坐标
            left = mRadius / 2 + (int) (Math.round(distanceFromCenter * Math.cos(Math.toRadians(mStartAngle))
                    - 1 / 2f * itemWidth));
            //distanceFromCenter sina 就是item的纵坐标
            top = mRadius / 2 + (int)
                    (Math.round(distanceFromCenter * Math.sin(Math.toRadians(mStartAngle)) - 1 / 2f * itemWidth));
            child.layout(left, top, left + itemWidth, top + itemWidth);
            mStartAngle += angleDelay;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter != null)
            buildMenuItem();
        super.onAttachedToWindow();
    }

    private void buildMenuItem() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View itemView = mAdapter.getView(i, null, this);
            final int position = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(itemView, position);
                    }
                }
            });
            addView(itemView);
        }
    }
}

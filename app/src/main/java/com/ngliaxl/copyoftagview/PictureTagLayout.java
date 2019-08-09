package com.ngliaxl.copyoftagview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class PictureTagLayout extends RelativeLayout implements OnTouchListener {

    private static final int CLICKRANGE = 5;
    int startX = 0;
    int startY = 0;
    private PictureTagView mTagView;

    private OnAddPointListener mOnAddPointListener;
    private OnPointClickListener mOnPointClickListener;
    private List<PictureTagView> mTagList;
    private int mTagViewHeight;
    private int mTagViewWidth;
    private int mIndex;

    public PictureTagLayout(Context context) {
        super(context, null);
    }

    public PictureTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        mTagList = new ArrayList<>();
        mTagViewHeight = dp2px(getContext(), 25);
        mTagViewWidth = dp2px(getContext(), 25);
    }


    public void setOnAddPointListener(OnAddPointListener listener) {
        this.mOnAddPointListener = listener;
    }

    public void setOnPointClickListener(OnPointClickListener listener) {
        this.mOnPointClickListener = listener;
    }

    public List<PictureTagView> getTagList() {
        return mTagList;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTagView = null;
                startX = (int) event.getX();
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int endX = (int) event.getX();
                int endY = (int) event.getY();
                if (hasView(startX, startY)) {
                } else {
                    if (mTagView == null && Math.abs(endX - startX) < CLICKRANGE && Math.abs(endY -
                            startY) < CLICKRANGE) {
                        addTagViewFromTouch(startX, startY, ++mIndex);

                    }
                }
                //如果挪动的范围很小，则判定为单击
                if (mTagView != null && Math.abs(endX - startX) < CLICKRANGE && Math.abs(endY -
                        startY) < CLICKRANGE) {
                    if (mOnPointClickListener != null) {
                        mOnPointClickListener.onClick(mTagView);
                    }
                } else {

                }
                mTagView = null;
                break;
        }
        return true;
    }


    public void addTagViewFromTouch(int x, int y, int index) {
        PictureTagView view = new PictureTagView(getContext());
        view.mTouchX = x;
        view.mTouchY = y;
        view.setText(String.valueOf(index));
        view.setIndex(index);
        mTagList.add(view);
        addView(view, generateLayoutParams(x, y));
        if (mOnAddPointListener != null) {
            mOnAddPointListener.onAdded(startX, startY, view);
        }
    }


    private RelativeLayout.LayoutParams generateLayoutParams(int x, int y) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
                .WRAP_CONTENT);

        params.leftMargin = x - mTagViewWidth / 2;
        params.topMargin = y - mTagViewHeight / 2;
        // top
        if (params.topMargin < 0) {
            params.topMargin = 0;
        }
        // bottom
        else if ((params.topMargin + mTagViewHeight) > getHeight()) {
            params.topMargin = getHeight() - mTagViewHeight;
        }
        // left
        else if (params.leftMargin < 0) {
            params.leftMargin = 0;
        }
        // right
        else if ((params.leftMargin + mTagViewWidth) > getWidth()) {
            params.leftMargin = getWidth() - mTagViewWidth;
        }

        return params;
    }


    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        if (index < 0) index = 0;
        this.mIndex = index;
    }

    public interface OnAddPointListener {
        void onAdded(int x, int y, PictureTagView view);
    }

    public interface OnPointClickListener {
        void onClick(PictureTagView view);
    }

    public void removeTagView(int index) {
        PictureTagView view = null;
        if (mTagList != null) {
            for (PictureTagView tag : mTagList) {
                if (tag.getIndex() == index) {
                    view = tag;
                }
            }
        }
        if (view != null) {
            removeView(view);
            mTagList.remove(view);
        }
    }

    public void removeAllTagViews() {
        if (mTagList != null) {
            mTagList.clear();
        }
        mIndex = 0;
        removeAllViews();
    }


    public List<PictureTagView> getValidTagViewList() {
        if (mTagList == null) return null;
        List<PictureTagView> tagList = new ArrayList<>();
        for (PictureTagView tag : mTagList) {
            if (tag.getIndex() != -1) tagList.add(tag);
        }
        return tagList;
    }


    private boolean hasView(int x, int y) {
        //循环获取子view，判断xy是否在子view上，即判断是否按住了子view
        for (int index = 0; index < getChildCount(); index++) {
            View view = getChildAt(index);
            int left = (int) view.getX();
            int top = (int) view.getY();
            int right = view.getRight();
            int bottom = view.getBottom();
            Rect rect = new Rect(left, top, right, bottom);
            boolean contains = rect.contains(x, y);
            //如果是与子view重叠则返回真,表示已经有了view不需要添加新view了
            if (contains) {
                mTagView = (PictureTagView) view;
                mTagView.bringToFront();
                return true;
            }
        }
        mTagView = null;
        return false;
    }


    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context
                .getResources().getDisplayMetrics());
    }

}

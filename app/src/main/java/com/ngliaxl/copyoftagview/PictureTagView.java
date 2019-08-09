package com.ngliaxl.copyoftagview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PictureTagView extends RelativeLayout {

    private Context context;
    private TextView tvPictureTagLabel;
    private int mIndex;
    private int mSelectedButtonTag;

    public int mTouchX;
    public int mTouchY;
    public String mPath;

    public PictureTagView(Context context) {
        super(context);
        this.context = context;
        initViews();

    }

    /**
     * 初始化视图
     **/
    protected void initViews() {
        LayoutInflater.from(context).inflate(R.layout.layout_picturetagview, this, true);
        tvPictureTagLabel = findViewById(R.id.tvPictureTagLabel);
    }

    public void setText(String text) {
        tvPictureTagLabel.setText(text);
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }


    public void setSelectedButtonTag(int selectedButtonTag) {
        this.mSelectedButtonTag = selectedButtonTag;
    }

    public int getSelectedButtonTag() {
        return mSelectedButtonTag;
    }

    public int getIndex(){
        return mIndex;
    }

}

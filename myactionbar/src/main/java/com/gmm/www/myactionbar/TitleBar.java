package com.gmm.www.myactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author:gmm
 * @date:2020/4/1
 * @类说明:
 */
public class TitleBar extends RelativeLayout {
    private RelativeLayout layout_titlebar_rootlayout;
    private ImageView iv_titlebar_left;
    private TextView tv_titlebar_title;
    private ImageView iv_titlebar_right;

    private int mColor = Color.WHITE;
    private int mTextColor = Color.BLACK;
    private String titleName;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context,attrs);
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context,attrs);
        initView(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.TitleBar);
        mColor = mTypedArray.getColor(R.styleable.TitleBar_title_bg,Color.WHITE);
        mTextColor = mTypedArray.getColor(R.styleable.TitleBar_title_text_color,Color.BLACK);
        titleName = mTypedArray.getString(R.styleable.TitleBar_title_text);

        //获取资源后 及时回收
        mTypedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_customtitle,this,true);
        layout_titlebar_rootlayout = findViewById(R.id.layout_titlebar_rootlayout);
        iv_titlebar_left = findViewById(R.id.iv_titlebar_left);
        tv_titlebar_title = findViewById(R.id.tv_titlebar_title);
        iv_titlebar_right = findViewById(R.id.iv_titlebar_right);

        layout_titlebar_rootlayout.setBackgroundColor(mColor);
        tv_titlebar_title.setTextColor(mTextColor);
        tv_titlebar_title.setText(titleName);
    }

    public void setTitle(String titleName) {
        if (!TextUtils.isEmpty(titleName)) {
            tv_titlebar_title.setText(titleName);
        }
    }

    public void setLeftClickListener(OnClickListener onClickListener) {
        iv_titlebar_left.setOnClickListener(onClickListener);
    }

    public void setRightClickListener(OnClickListener onClickListener) {
        iv_titlebar_right.setOnClickListener(onClickListener);
    }
}



package com.gmm.www.changeskindemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author:gmm
 * @date:2020/4/19
 * @类说明:
 */
public class SkinFactory implements LayoutInflater.Factory2 {

    private AppCompatDelegate mDelegate;
    private List<SkinView> cacheSkinView = new ArrayList<>();

    final Object[] mConstructorArgs = new Object[2];

    static final Class<?>[] mConstructorSignature = new Class[] {
            Context.class, AttributeSet.class};

    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
            new HashMap<String, Constructor<? extends View>>();

    static final String[] prefixs = new String[] {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = mDelegate.createView(parent,name,context,attrs);
        if (null == view) {
            mConstructorArgs[0] = context;

            if (-1 == name.indexOf(".")) {//系统的view
                view = createView(context,name,prefixs,attrs);
            } else {
                view = createView(context,name,null,attrs);
            }

        }
        if (null != view) {
            collectSkinView(context,attrs,view);
        }
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    public View createView(Context context,String name,String[] prefixs,AttributeSet attrs) {
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        Class<? extends View> clazz = null;

        return null;
    }


    public void setmDelegate(AppCompatDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    /**
     * 收集需要换肤的控件
     * @param context
     * @param attrs
     * @param view
     */
    private void collectSkinView(Context context,AttributeSet attrs,View view) {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Skinable);
        boolean isSupport = a.getBoolean(R.styleable.Skinable_isSupport,false);
        if (isSupport) {
            int len = attrs.getAttributeCount();
            HashMap<String,String> attrMap = new HashMap<>();
            for (int i = 0; i < len; i++) {
                String attrName = attrs.getAttributeName(i);
                String attrValue = attrs.getAttributeValue(i);
                attrMap.put(attrName,attrValue);
            }
            SkinView skinView = new SkinView();
            skinView.view = view;
            skinView.attrsMap = attrMap;
            cacheSkinView.add(skinView);
        }
        a.recycle();
    }

    public void changeSkin() {
        for (SkinView skinView : cacheSkinView) {

        }
    }



    static class SkinView {
        View view;
        HashMap<String,String> attrsMap;

        public void changeSkin() {
            if (!TextUtils.isEmpty(attrsMap.get("background"))) {
                int bgId = Integer.parseInt(attrsMap.get("background").substring(1));
                String attrType = view.getResources().getResourceTypeName(bgId);
                if (TextUtils.equals(attrType,"drawable")) {
//                    view.setBackgroundDrawable(SkinEngine.getInstance().getDrawable(bgId));
                } else if (TextUtils.equals(attrType,"color")) {
//                    view.setBackgroundColor(SkinEngine.getInstance().getColor(bgId));
                }
            }

            if (view instanceof TextView) {
                if (!TextUtils.isEmpty(attrsMap.get("textColor"))) {
                    int textColorId = Integer.parseInt(attrsMap.get("textColor".substring(1)));
//                    ((TextView) view).setTextColor(SkinEngine.getInstance().getColor(textColorId));
                }
            }
        }
    }
}

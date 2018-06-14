package com.hacai.exchange.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Rongkui.xiao on 2017/5/4.
 *
 * @description
 */

public class ResizeLayout extends FrameLayout {
    private InputListener mListener;

    public interface InputListener {
        void OnInputListener(boolean isHideInput);
    }

    public void setOnResizeListener(InputListener l) {
        mListener = l;
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean mHasInit = false;
    private boolean mHasKeyboard = false;
    private int mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit) {
            mHasInit = true;
            mHeight = b;
            System.out.println("mHeight= " + b);
        } else {
            mHeight = mHeight < b ? b : mHeight;
        }

        if (mHasInit && mHeight > b) { // mHeight代表键盘的真实高度 ,b代表在窗口中的高度 mHeight>b
            mHasKeyboard = true;
            mListener.OnInputListener(false);
        }
        if (mHasInit && mHasKeyboard && mHeight == b) { // mHeight = b
            mHasKeyboard = false;
            mListener.OnInputListener(true);
        }
    }
}

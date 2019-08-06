package com.wlan.familymembers.dingyiview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebView extends WebView {
    private ScrollChangedListener scrollChangedListener;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // webview的高度
        float webcontent = getContentHeight() * getScale();
        //当前webview的高度
        float webnow = getHeight() + getScrollY();
        if (Math.abs(webcontent - webnow) < 1) { //处于底端
            scrollChangedListener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) { //处于顶端
            scrollChangedListener.onPageTop(l, t, oldl, oldt);
        } else {
            scrollChangedListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public interface ScrollChangedListener {


        void onPageEnd(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setListener(ScrollChangedListener listener) {
        this.scrollChangedListener = listener;
    }
}

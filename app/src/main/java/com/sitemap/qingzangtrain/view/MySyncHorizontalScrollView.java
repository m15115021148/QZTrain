package com.sitemap.qingzangtrain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * @desc 滚动选择器
 * Created by chenmeng on 2017/2/8.
 */
public class MySyncHorizontalScrollView extends HorizontalScrollView{
    /**
     * 产生联动的view对象
     */
    private View mSyncView;

    public MySyncHorizontalScrollView(Context context) {
        super(context);
    }

    public MySyncHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySyncHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //让需要联动的view也设置相同的滚动
        if(mSyncView != null) {
            mSyncView.scrollTo(l, t);


        if (l == 0){
            //            scrollStateListener.onScrollMostLeft();
//            Log.e("result","11111111");
        }
        else if (oldl == 0){
            //            scrollStateListener.onScrollFromMostLeft();
//            Log.e("result","2222222");
        }
        int mostRightL = this.getChildAt(0).getWidth() - getWidth();
        if (l >= mostRightL){
            //            scrollStateListener.onScrollMostRight();
//            Log.e("result","333333");
        }
        if (oldl >= mostRightL && l < mostRightL){
            //            scrollStateListener.onScrollFromMostRight();
//            Log.e("result","44444444444");

        }
        }
    }

    public View getmSyncView() {
        return mSyncView;
    }

    public void setmSyncView(View mSyncView) {
        this.mSyncView = mSyncView;
    }
}

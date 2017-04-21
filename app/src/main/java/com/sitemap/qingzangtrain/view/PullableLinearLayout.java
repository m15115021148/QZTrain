package com.sitemap.qingzangtrain.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by chenmeng on 2016/11/22.
 */
public class PullableLinearLayout extends LinearLayout implements Pullable {

    public ListView getLv() {
        return lv;
    }

    public void setLv(ListView lv) {
        this.lv = lv;
    }

    private ListView lv;

    public boolean isSlide() {
        return isSlide;
    }

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    private boolean isSlide;//是否有下拉刷新

    public PullableLinearLayout(Context context) {
        super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean canPullDown() {
        if (lv == null) {
            return false;
        }
        if (isSlide) {
            return false;
        }
        if (lv.getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (lv.getFirstVisiblePosition() == 0) {
            if (lv.getChildAt(0) == null) {
                return false;
            } else {
                if (lv.getChildAt(0).getTop() >= 0) {
                    // 滑到ListView的顶部了
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (lv == null) {
            return false;
        }
        if (lv.getCount() == 0) {
            // 没有item的时候不可以上拉加载
            return false;
        } else if (lv.getLastVisiblePosition() == (lv.getCount() - 1)) {
            // 滑到底部了
            if (lv.getChildAt(lv.getLastVisiblePosition() - lv.getFirstVisiblePosition()) != null
                    && lv.getChildAt(
                    lv.getLastVisiblePosition()
                            - lv.getFirstVisiblePosition()).getBottom() <= lv.getMeasuredHeight())
                return true;
        }
        return false;
    }
}
